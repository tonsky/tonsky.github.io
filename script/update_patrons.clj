#!/usr/bin/env bb

(def working-dir (.getParentFile (.getParentFile (.getCanonicalFile (io/file *file*)))))

(def today (java.time.LocalDate/now))

(defn print-progress [prefix percent]
  (let [percent-int (int (* percent 20))]
    (print "\u001B[1000D\u001B[K") ;; clear line
    (print prefix "[ ")
    (dotimes [_ percent-int]
      (print "▓"))
    (dotimes [_ (- 20 percent-int)]
      (print "░"))
    (print " ]")
    (flush)))

(defn cache [prefix fetch-fn]
  (let [file-name (format (str prefix "-%04d-%02d-%02d.edn") (.getYear today) (.getMonthValue today) (.getDayOfMonth today))
        file      (io/file working-dir "patrons" file-name)]
    (if (.exists file)
      (do
        ; (println "Loading" (.getPath file))
        (->> (slurp file)
          (clojure.edn/read-string)))
      (let [data (fetch-fn)]
        ; (println "Saving" (.getPath file))
        (spit file (pr-str data))
        data))))

(defn layer [member]
  (condp <= (:pledge member)
    50 :sponsor
    10 :supporter
    5  :fan
    nil))

(defn map-by [f xs]
  (into {} (map #(vector (f %) %)) xs))

;; Patrons

(defn fetch-patrons []
  (let [url     "https://www.patreon.com/api/oauth2/v2/campaigns/2077079/members"
        token   (System/getenv "PATREON_ACCESS_TOKEN")
        _       (when (str/blank? token)
                  (throw (ex-info "PATREON_ACCESS_TOKEN is not set" {})))
        headers {"Authorization" (str "Bearer " token)}
        query   {"fields[member]" (str/join ","
                                    ["currently_entitled_amount_cents"
                                     "pledge_cadence"
                                     "full_name"
                                     "last_charge_status"
                                     "patron_status"
                                     "email"
                                     ; "last_charge_date"
                                     "lifetime_support_cents"
                                     ; "pledge_relationship_start"
                                     ; "will_pay_amount_cents"
                                     ])}]
    (print-progress "Fetching patrons" 0)
    (loop [cursor  nil
           patrons []]
      (let [query'   (cond-> query
                       (some? cursor) (assoc "page[cursor]" cursor))
            response (-> (org.httpkit.client/get url {:query-params query' :headers headers :as :text})
                       (deref)
                       :body
                       (json/parse-string true))
            patrons' (into patrons (:data response))]
        (print-progress "Fetching patrons" (/ (count patrons') (-> response :meta :pagination :total)))
        (if-some [cursor' (-> response :meta :pagination :cursors :next)]
          (recur cursor' patrons')
          (do
            (println)
            patrons'))))))

(defn patron-active? [{:keys [attributes] :as patron}]
  (and (= "Paid" (:last_charge_status attributes))
    (= "active_patron" (:patron_status attributes))))

(defn normalize-patron [{:keys [attributes] :as patron}]
  {:name      (str/trim (:full_name attributes))
   :id        (:email attributes)
   :pledge    (-> (:currently_entitled_amount_cents attributes)
                (/ (:pledge_cadence attributes))
                (/ 100)
                (int))
   :platforms #{:patreon}})

(defn patrons []
  (->> (cache "patrons" fetch-patrons)
    (filter patron-active?)
    (mapv normalize-patron)))

;; Sponsors

(defn sponsors-query [cursor]
  (->
    (str 
"{\"query\": \"{
  viewer {
    sponsorshipsAsMaintainer(first: 10, after: " cursor ") {
      totalCount
      pageInfo {
        hasNextPage, endCursor
      }
      nodes {
        createdAt, id
        sponsorEntity {
          ... on User {
            id, email, name, login
          }
        }
        tier {
          monthlyPriceInDollars
        }
      }
    }
  }
}\"}")
    (clojure.string/replace "\n" "\\n")))

(defn fetch-sponsors []
  (let [url     "https://api.github.com/graphql"
        token   (System/getenv "GITHUB_TOKEN_SPONSORS")
        _       (when (str/blank? token)
                  (throw (ex-info "GITHUB_TOKEN_SPONSORS is not set" {})))
        headers {"Authorization" (str "Bearer " token)}]
    (print-progress "Fetching sponsors" 0)
    (loop [cursor   nil
           sponsors []]
      (let [query   (if (some? cursor)
                      (sponsors-query (str "\\\"" cursor "\\\""))
                      (sponsors-query "null"))
            response (-> (org.httpkit.client/post url {:body query :headers headers :as :text})
                       (deref)
                       :body
                       (json/parse-string true))
            sponsors' (into sponsors
                        (-> response :data :viewer :sponsorshipsAsMaintainer :nodes))]
        (print-progress "Fetching sponsors"
          (/ (count sponsors')
            (-> response :data :viewer :sponsorshipsAsMaintainer :totalCount)))
        (if (-> response :data :viewer :sponsorshipsAsMaintainer :pageInfo :hasNextPage)
          (recur (-> response :data :viewer :sponsorshipsAsMaintainer :pageInfo :endCursor) sponsors')
          (do
            (println)
            sponsors'))))))

(defn normalize-sponsor [{entity :sponsorEntity :as sponsor}]
  {:name      (str/trim (or (:name entity) (:login entity)))
   :id        (if (str/blank? (:email entity))
                (:login entity)
                (:email entity))
   :pledge    (-> sponsor :tier :monthlyPriceInDollars)
   :platforms #{:github}})

(defn sponsors []
  (->> (cache "sponsors" fetch-sponsors)
    (filter #(not-empty (:sponsorEntity %)))
    (mapv normalize-sponsor)))

;; Merging

(defn merge-members [m1 m2]
  (-> m1
    (update :pledge + (:pledge m2))
    (update :platforms clojure.set/union (:platforms m2))))

(defn render-tier [key members]
  (when-not (empty? members)
    (str "<h2>" (str/capitalize (name key)) "s</h2>\n"
      (->> members
        (sort-by #(str/lower-case (:name %)))
        (map #(str "<li>" (:name %) "</li>\n"))
        (str/join)))))

(defn render-members []
  (let [all-members (->> 
                      (merge-with merge-members
                        (map-by :id (patrons))
                        (map-by :id (sponsors)))
                      (vals)
                      (group-by layer))]
    (->> [:sponsor :supporter :fan]
      (map #(render-tier % (get all-members %)))
      (str/join "\n"))))

;; Commands

(defn sh! [& args]
  (println (str "> " (str/join " " args)))
  (let [{:keys [out err exit]} (apply shell/sh args)]
    (when (not= 0 exit)
      (throw (Exception. (str "Failed to run \"" (str/join " " args) "\"\n" out err))))
    out))

(defn update-patrons! []
  (let [template (slurp (io/file working-dir "patrons" "index.template"))
        updated  (-> template
                   (str/replace "${date}" (.format today (java.time.format.DateTimeFormatter/ofPattern "MMMM dd, yyyy")))
                   (str/replace "${members}" (render-members)))
        target   (io/file working-dir "patrons" "index.html")]
    (println "Writing" (.getPath target))
    (spit target updated)))

(update-patrons!)

(shell/with-sh-dir (.getPath working-dir)
  (sh! "git" "add" "patrons/index.html")
  (sh! "git" "commit" "-m" (str "Patrons update " (.format today (java.time.format.DateTimeFormatter/ofPattern "MMMM yyyy"))))
  (sh! "git" "push"))

'Done!