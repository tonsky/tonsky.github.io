---
layout: post
title: "Disincanto Software"
category: blog
---

_Translated by Adam Kecskemeti <a href="..">from English</a>_

15 éve programozom. A közelmúltban kezdett igazán megfogni iparágunkban a hatékonyság, az egyszerűség és a kiválóság iránti törődés hiánya, egészen addig a pontig, hogy lehangolódtam a saját karrierem és általában az IT miatt.

A modern autók, mondjuk a vita kedvéért, 98%-ában működnek annak, ami a jelenlegi motorkonstrukcióval fizikailag lehetséges. A modern épületek éppen annyi anyagot használnak fel, hogy az adott körülmények között teljesítsék funkciójukat és biztonságosak maradjanak. Minden sík konvergál az optimális mérethez/formához/terheléshez, és alapvetően ugyanúgy néz ki.

Csak szoftveresen elfogadható, ha egy program a lehetséges teljesítmény 1%-án vagy akár 0,01%-án fut. Úgy tűnik, mindenki rendben van vele. Az emberek gyakran még büszkék is arra, hogy mennyire nem hatékony, „miért aggódjunk, a számítógépek elég gyorsak”:

> [@tveastman](https://twitter.com/tveastman/status/1039002300600147968): Van egy Python programom, amit minden nap futtatok, 1,5 másodpercig tart. Hat órát töltöttem az újraírással Rust-ban, most 0,06 másodperc. Ez a hatékonyságnövekedés azt jelenti, hogy 41 év, 24 nap múlva behozom az időmet amit programozással töltöttem :-)

Valószínűleg hallottad már ezt a mantrát: „A programozói idő drágább, mint a számítógép ideje.” Ez alapvetően azt jelenti, hogy soha nem látott mértékben pazaroljuk el a számítógépeket. Veszel-e autót, ha 100 litert eszik 100 kilométeren? Mit szólnál 1000 literhez? A számítógépekkel mindig ezt tesszük.

<figure><a href="https://xkcd.com/2021/" target="_blank"><img src="../software_development_2x.gif" height="440"></a></figure>

## Minden elviselhetetlenül lassú

Nézz körül: hordozható számítógépeink ezerszer erősebbek, mint azok, amelyek az embert a Holdra vitték. Ennek ellenére minden más weboldal küzd a zökkenőmentes, 60 képkocka/mp sebességű görgetés fenntartásával a legújabb, csúcskategóriás MacBook Pro. Kényelmesen játszhatok, nézhetek 4K videókat, de nem görgethetek weboldalakat? Ez hogy van rendben?

A Google Inbox, a Google által írt webalkalmazás, amely a Google által kreált Chrome böngészőben fut, [13 másodpercet vesz igénybe a közepes méretű e-mailek megnyitásához](https://twitter.com/nikitonsky/statuses/968882438024941568).

<figure><video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="../inbox.mp4" type="video/mp4"></video></figure>

A tartalom megjelenítése helyett üres fehér dobozokat is animál, mert csak így lehet bármit is animálni egy weboldalon megfelelő teljesítménnyel. Nem, a tisztességes nem azt jelenti, hogy 60 képkocka/másodperc, hanem „olyan gyors, amennyire ez a weboldal csak tud”. Kíváncsi vagyok a webes közösség válaszára, amikor a 120 Hz-es kijelzők általánossá válnak. Ez a szar már most alig éri el a 60 Hz-et.

A Windows 10 [frissítése 30 percet vesz igénybe](https://grumpy.website/post/0PeXr1S7N). Mit csinálhat ilyen sokáig? Ennyi idő elég ahhoz, hogy teljesen formázzam az SSD-meghajtómat, letöltsek egy friss buildet, és 5-ször egymás után telepítsem.

<figure><img src="../windows_update.gif" height="435"></figure>

> [Pavel Fatin](https://pavelfatin.com/typing-with-pleasure/): A szerkesztőben való gépelés viszonylag egyszerű folyamat, így még a 286 PC is elég gördülékeny gépelési élményt tudott nyújtani.

A modern szövegszerkesztők nagyobb késleltetéssel rendelkeznek, mint a 42 éves E-Mac. Szövegszerkesztők! Mi lehet egyszerűbb? Minden egyes billentyűleütésnél csak egy apró téglalap alakú területet kell frissítenie, és a modern szövegszerkesztők ezt nem tudják 16 ms alatt megtenni. Nagyon sok idő. NAGYON. Egy 3D-s játék képes az egész képernyőt több százezer (!!!) poligonnal megtölteni ugyanazon 16 ms alatt, valamint feldolgozni a bemenetet, újraszámolni a világot és dinamikusan betölteni/kirakni az erőforrásokat. Hogy-hogy?

Általános tendencia, hogy nem kapunk gyorsabb szoftvereket több funkcióval. Egyre gyorsabb hardvert kapunk, amely lassabban fut, ugyanazokkal a funkciókkal. Minden a lehetséges sebesség alatt működik. Gondolkozott már azon, hogy miért van szüksége telefonjának 30-60 másodpercre a rendszerindításhoz? Miért nem indul el mondjuk egy másodperc alatt? Ennek nincsenek fizikai korlátai. Ezt szívesen látnám. Szívesen látnám, ha elérnék és feltárnák a határokat, és az általunk elért teljesítmény minden darabját értelmes módon hasznosítanák valami értelmes dologra.

## Minden HATTTTTTALMAS

És akkor van puffadás. A webalkalmazások akár 10-szer gyorsabban is megnyílhatnak, ha egyszerűen letiltják az összes hirdetést. A Google arra kér mindenkit, hogy ne löje magát lábon az AMP kezdeményezéssel – egy technológiai megoldás egy olyan problémára, amelyhez nincs szükség semmiféle technológiára, csak egy kis józan észre. Ha eltávolítjuk a “dagadást”, az internet őrült gyorssá válik. Mennyire kell okosnak lenni, hogy ezt megértsd?

Egy alkalmazás nélküli Android [rendszer csaknem 6 GB-ot foglal el](https://grumpy.website/post/0Oz1lDOq5). Gondolj cask bele egy pillanatra, milyen obszcén HATALMAS ez a szám. Mi van ott, HD filmek? Azt hiszem, ez alapvetően kód: kernel, illesztőprogramok. Természetesen néhány karakterlánc és erőforrás is, de ezek nem lehetnek nagyok. Szóval, hány driver kell egy telefonhoz?

<figure><img src="../android_storage.jpg" height="489"></figure>

A Windows 95 30 MB volt. Ma ennél nehezebb weboldalaink vannak! A Windows 10 4 GB-os, ami 133-szor akkora. De vajon 133-szor jobb? Úgy értem, funkcionálisan alapvetően ugyanazok. Igen, van Cortana, de kétlem, hogy 3970 MB-ot vesz igénybe. De bármi legyen is a Windows 10, az Android valóban ennek 150%-a?

A Google billentyűzetalkalmazása rutinszerűen 150 MB-ot fogyaszt. Valóban ötször bonyolultabb egy olyan alkalmazás, amely 30 billentyűt rajzol egy képernyőre, mint az egész Windows 95? A Google alkalmazás, amely alapvetően csak egy csomag a Google Internetes Keresőhöz, 350 MB! Google Play-szolgáltatások, amelyeket nem használok (nem veszek ott könyveket, zenéket vagy videókat) – 300 MB, amelyek csak ott vannak, és nem tudom törölni.

<figure><img src="../apps_storage.gif" height="480"></figure>

Mindez körülbelül 1 GB-ot hagy a fényképeim számára, miután telepítettem az összes alapvető (közösségi, chat-, térkép-, taxi-, banki stb.) alkalmazást. És ez játék és zene nélkül! Emlékszel azokra az időkre, amikor egy operációs rendszer, az alkalmazások és az összes adat elfért egy hajlékonylemezen?

Az asztali ToDo (emlékeztetők) alkalmazás valószínűleg Electron nyelven íródott, így van benne egy felhasználói felület az [Xbox 360 vezérlőhöz](https://josephg.com/blog/electron-is-flash-for-the-desktop/), képes 3D grafikát renderelni, hangot lejátszani és fényképeket készíteni webkamerájával.

<figure><img src="../slack_memory.jpg" height="388"></figure>

Az egyszerű szöveges csevegés betöltési sebességéről és memóriafogyasztásáról híres. Igen, a Slacket valóban erőforrás-igényes alkalmazásnak kell tekinteni. Úgy értem, chatroom és barebone szövegszerkesztő, ezek állítólag a két kevésbé igényes alkalmazás az egész világon. Üdvözöljük 2018-ban.

Legalább működik, mondhatni. Nos, a nagyobb nem azt jelenti, hogy jobb. A nagyobb azt jelenti, hogy valaki elvesztette az irányítást. A nagyobb azt jelenti, hogy nem tudjuk, mi történik. A nagyobb komplexitási adót, teljesítményadót, megbízhatósági adót jelent. Ez nem norma, és nem is szabad normává válnia. A túlsúlyos alkalmazásoknak piros zászlót kell jelenteniük. Azt kell jelenteniük, hogy fuss messzire tőlem.

## Minden rohad

Egy 16 GB-os Android telefon 3 éve teljesen rendben volt. Manapság az Android 8.1-el alig használható, mert minden egyes alkalmazás legalább kétszer akkora lett, minden látható ok nélkül. Nincsenek további funkciók. Nem gyorsabbak vagy optimalizáltabbak. Nem néznek ki másként. Csak… nőnek?

Az iPhone 4s-t iOS 5-tel adták ki, de alig tudja futtatni az iOS 9-et. És ez nem azért van, mert az iOS 9 sokkal jobb – alapvetően ugyanaz. De az új hardverük gyorsabb, ezért lassabbra tették a szoftvereket. Ne aggódjon – izgalmas új képességekkel rendelkezik, mint például… ugyanazon alkalmazások futtatása ugyanolyan sebességgel! Nem tom.

Az iOS 11 megszüntette a 32 bites alkalmazások támogatását. Ez azt jelenti, hogy ha a fejlesztő nincs jelen az iOS 11 kiadásakor, vagy nem hajlandó visszamenni, és frissíteni egy valaha tökéletes alkalmazást, akkor valószínűleg soha többé nem fogja látni az alkalmazását.

> @[jckarter](https://twitter.com/jckarter/statuses/1017071794245623808): A DOS-os programot nagyjából minden 80-as évek óta gyártott számítógépen módosítatlanul futtatni lehet. Egy JavaScript-alkalmazás megszakadhat a holnapi Chrome-frissítéssel.

[A ma működő weboldalak 10 év múlva](http://tonsky.me/blog/chrome-intervention/) (valószínűleg hamarabb) nem lesznek kompatibilisek egyetlen böngészővel sem.

"Minden erőfeszítést megtesz ahhoz, hogy helyen maradj." De mi értelme van? Lehet, hogy alkalmanként annyira élvezem, hogy veszek egy új telefont és új MacBookot, mint a bárki más, de csak azért, hogy ugyanazokat az alkalmazásokat tudjam futtatni, amelyek éppen lassabbak lettek?

Szerintem ennél jobban is teljesíthetünk, és kell is teljesítenünk. Mindenki azzal van elfoglalva, hogy dolgokat építsen most, mára, ritkán holnapra. De jó lenne, ha lenne olyan dolog is, ami ennél kicsit tovább bírja.

## Minnél rosszabb annál jobb

Ezen a ponton senki nem ért semmit. Nem is akarnak. Csak kidobjuk az alig megsült szart, reméljük a legjobbakat, és „startup wisdom”-nak nevezzük.

A weboldalak frissítést kérnek, ha bármi baj van. Kinek van ideje rájönni, mi történt?

<figure><img src="../reload.jpg" height="185"></figure>

Bármely webalkalmazás folyamatosan „véletlenszerű” JS-hibákat produkál, még kompatibilis böngészőkön is.

Az egész weboldal/SQL adatbázis architektúra arra az előfeltevésre épül (remélhetőleg), hogy senki sem fog hozzányúlni az Ön adataihoz, miközben a megjelenített weboldalt nézi.

A legtöbb együttműködésen alapuló megvalósítás a „legjobb erőfeszítés”, és számos olyan közös élethelyzettel rendelkezik, amelyek során adatvesztésre kerül sor. Láttad már ezt a kérdést „melyik verziót akarja megtartani?” Úgy értem, ma olyan alacsony a léc, hogy a felhasználók örülnének legalább egy ilyen ablaknak.

<figure><img src="../icloud_conflict.jpg" height="468"></figure>

És nem, az én világomban egy olyan alkalmazás, amely azt mondja: „Tönkre fogom tenni a munkáid egy részét, de legalább kiválaszthatod, hogy melyiket” az nem oké. 

A Linux úgy van tervezve, hogy megöljön random folyamatokat. És mégis ez a legnépszerűbb szerveroldali operációs rendszer.

Minden eszközöm rendszeresen meghibásodik így vagy úgy. A Dell monitoromat időnként újra kell indítani, mert szoftver van benne. Airdrop? Szerencsés vagy, ha észleli a készülékedet, különben mit tegyek? Bluetooth? A specifikáció annyira összetett, [hogy az eszközök nem beszélnek egymással](https://thewirecutter.com/blog/understanding-bluetooth-pairing-problems/), [és a rendszeres visszaállítás a legjobb megoldás](http://time.com/4358533/bluetooth-fix-how/).

<figure><img src="../plz_connect.jpg" height="450"></figure>

És nem is nyúlok az Internet of [Thingshez](https://twitter.com/internetofshit). Annyira túl van a vicces határon, hogy nem is tudom, mit tegyek hozzá.

Büszke akarok lenni a munkámra. Működőképes, stabil dolgokat szeretnék készíteni. Ehhez meg kell értenünk, hogy mit építünk be és ki, és ez lehetetlen megtenni a duzzadó, túltervezett rendszerekben.

## A programozás ugyanaz a káosz

Csak úgy tűnik, már senkit sem érdekel a minőségi, gyors, hatékony, tartós, alapos építés. Még akkor is, ha a hatékony megoldások már régóta ismertek, ugyanazokkal a problémákkal küzdünk: csomagkezelés, build rendszerek, fordítók, nyelvi tervezés, IDE-k.

A build rendszerek eredendően megbízhatatlanok, és időnként teljes tisztítást igényelnek, bár az érvénytelenítéshez minden információ rendelkezésre áll. Semmi sem akadályoz meg bennünket abban, hogy az építési folyamatokat megbízhatóvá, kiszámíthatóvá és 100%-ban reprodukálhatóvá tegyük. Csak senki nem tartja fontosnak. Az NPM évek óta „néha működik” állapotban maradt.

> [@przemyslawdabek](https://twitter.com/przemyslawdabek/status/940547268729606145): Nekem úgy tűnik, hogy `rm -rf node_modules` ez a munkafolyamat elengedhetetlen része a Node.js/JavaScript projektek fejlesztésénél.

És a build idők? Senki sem gondolja, hogy a percekig vagy akár órákig működő compiler probléma lenne. Mi történt azzal, hogy „a programozó ideje fontosabb”? Szinte az összes compiler, elő- és utófeldolgozók jelentős, néha katasztrofális extra időt adnak a buildhez anélkül, hogy arányosan jelentős előnyöket biztosítanának.

<figure><a href="https://xkcd.com/303/" target="_blank"><img src="../compiling.gif" height="360"></a></figure>

Azt várnánk a programozóktól, hogy többnyire racionális döntéseket hozzanak, de néha ennek pont az ellenkezőjét teszik. Például a Hadoop [használata akkor is, ha lassabb, mintha ugyanazt a feladatot egyetlen desktopon futtatná](https://www.chrisstucchio.com/blog/2013/hadoop_hatred.html).

A gépi tanulás és az „AI” a szoftvereket a találgatásokra helyezte át azokban az időkben, amikor a legtöbb számítógép eleve nem is elég megbízható.

> [@rakhim](https://twitter.com/freetonik/status/1039826129190875136): Amikor egy alkalmazást vagy szolgáltatást „AI-alapúnak” vagy „ML-alapúnak” írnak le, akkor azt úgy olvasom, hogy „megbízhatatlan, kiszámíthatatlan és lehetetlen megindokolni a viselkedését”. Igyekszem elkerülni az „AI”-t, mert azt akarom, hogy a számítógépek az ellenkezője legyenek: megbízhatóak, kiszámíthatóak, ésszerűek.

Virtuális gépeket helyeztünk a Linuxba, majd a Dockert a virtuális gépekbe, egyszerűen azért, mert senki sem tudta felszámolni a legtöbb program, nyelv és környezet által okozott zűrzavart. A szart takaróval takarjuk le, hogy ne foglalkozzunk vele. Az „single binary” például továbbra is HATALMAS értékesítési pont a Go számára. Nincs rendetlenség == siker.

<figure><a href="https://xkcd.com/1987/" target="_blank"><img src="../python_environment_2x.gif" height="594"></a></figure>

És a függőségek (dependencies)? Az emberek könnyen hozzáadnak túltervezett „teljes csomagmegoldásokat”, hogy megoldják a legegyszerűbb problémákat anélkül, hogy figyelembe vennék a költségeket. És ezek a függőségek más függőségeket is hoznak. A végén egy kusza ágú fát kapsz, amely valami a horror történet (olyan nagy és konfliktusokkal teli OMG) és a vígjáték ([nem indokolt, hogy ezeket belefoglaljuk, de itt vannak](https://medium.com/@jdan/i-peeked-into-my-node-modules-directory-and-you-wont-believe-what-happened-next-b89f63d21558)) keveréke.

<figure><img src="../dependencies.gif" height="440"></figure>

A programok nem működnek évekig újraindítás nélkül. [Néha még pár nap is túl nagy kérés](https://docs.gitlab.com/ee/administration/operations/unicorn.html#unicorn-worker-killer). Véletlen dolgok történnek, és senki sem tudja, miért.

Ami még rosszabb, senkinek nincs ideje megállni és rájönni, mi történt. Miért is tennénk, ha mindig meg tudod vásárolni a kiutat. Pörgessen egy másik AWS-példányt. Indítsa újra a folyamatot. Dobja el és állítsa vissza a teljes adatbázist. Írj egy watchdog-ot, amely 20 percenként újraindítja az elromlott alkalmazást. [Tartalmazza többször ugyanazokat az erőforrásokat, tömörítse és szállítsa](https://blog.timac.org/2017/0410-analysis-of-the-facebook-app-for-ios-v-87-0/). Gyorsan haladj, ne javíts.

Ez nem mérnöki munka. Ez csak lusta programozás. A mérnöki tevékenység a teljesítmény, a struktúra és az általad felépített korlátok mélyreható megértése. A rosszul megírt dolgok és a rosszul megírt dolgok kombinálása szigorúan ellenkezik ezzel. A fejlődéshez meg kell értenünk, mit és miért teszünk.

## Elakadtunk vele

Tehát minden csak egy halom alig működő kód, amelyet a korábban megírt, alig működő kód tetejére raknak. Mérete és összetettsége folyamatosan növekszik, csökkentve a változás esélyét.

Az egészséges ökoszisztéma érdekében vissza kell térni, és újra meg újra kell látogatnia. Meg kell, hogy időnként kidobjunk dolgokat jobbakra cseréljük.

<figure><img src="../design_process.jpg" height="657"></figure>

De kinek van erre ideje? Nem láttunk új operációs rendszer kernelt mi, vagy 25 éve? Túl bonyolult ahhoz, hogy mostanra egyszerűen átírjuk. A böngészők mára annyira tele vannak szélsőséges esetekkel és történelmi precedensekkel, hogy senki sem meri a semmiből újraírni a layout engine-t.

A haladás mai definíciója, hogy vagy több olajat dob a tűzbe:

> [@sahrizv](https://twitter.com/sahrizv/status/1018184792611827712): 2014 - El kell fogadnunk a #mikroszolgáltatásokat, hogy megoldjunk minden monolith problémát.<br />2016 – El kell fogadnunk a #dockert a mikroszolgáltatásokkal kapcsolatos összes probléma megoldásához.<br />2018 – El kell fogadnunk a #kubernetes alkalmazást, hogy megoldjuk a dockerrel kapcsolatos összes problémát

vagy újra feltalálja a kereket:

> [@dr_c0d3](https://twitter.com/dr_c0d3/status/1040092903052378112): 2000: Írjon 100 sornyi XML-t, hogy „deklaratívan” konfigurálja a szervleteket és az EJB-ket.<br />2018: Írjon 100 sornyi YAML-t a mikroszolgáltatások „deklaratív” konfigurálásához.<br />Az XML-nek legalább sémája volt…

Abban vagyunk, amink van, és soha senki nem fog megmenteni minket.

## Az üzleti világot meg nem érdekli

A felhasználókat sem. Csak azt tanulták meg, hogy elvárják, amit mi nyújtani tudunk. Mi (mérnökök) azt mondjuk, hogy minden Android-alkalmazás 350 MB-ot vesz igénybe? Oké, élni fognak vele. Azt mondjuk, nem tudjuk biztosítani a sima görgetést? Oké, meg fognak tanulni élni egy dadogó telefonnal. Azt mondjuk, "ha nem működik, indítsa újra"? Újraindítanak. Hiszen nincs más választásuk.

Nincs verseny sem. Mindenki ugyanazokat a lassú, dagadt, megbízhatatlan termékeket készíti. A minőség időnkénti előrelépése versenyelőnyt jelent (iPhone/iOS vs. más okostelefonok, Chrome vs. egyéb böngészők), és mindenkit összerendeződésre kényszerít, de nem sokáig.

Mérnökként tehát az a küldetésünk, hogy megmutassuk a világnak, mi lehetséges a mai számítógépekkel a teljesítmény, a megbízhatóság, a minőség és a használhatóság terén. Ha mi kimutatjuk törődésünk, az emberek hajlandóak tanulni. És rajtunk kívül nincs más, aki megmutassa nekik, hogy ez nagyon is lehetséges. Bárcsak érdekelne ez minket.

## Nem minden rossz

Vannak olyan reménysugarak, amelyek bizonyítják, hogy a legmodernebb fejlesztésekhez képest sem lehetetlen fejlődni.

A [Martin Thompson](https://twitter.com/mjpt777) által végzett munka ([LMAX Disruptor](https://github.com/LMAX-Exchange/disruptor), [SBE](https://github.com/real-logic/simple-binary-encoding), [Aeron](https://github.com/real-logic/aeron)) lenyűgöző, üdítően egyszerű és hatékony.

Raph Levien [Xi editor](https://github.com/google/xi-editor) szerkesztője úgy tűnik, hogy a megfelelő elvek figyelembevételével készült.

[Jonathan Blow](https://www.youtube.com/user/jblow888) - nak van egy nyelve, amelyet egyedül fejleszt a játékához, és amely másodpercenként 500 000 sort képes lefordítani a laptopján. Ez hideg fordítás, nincs köztes gyorsítótár, nincs dagadó build.

Nem kell zseninek lenned ahhoz, hogy gyors programokat írj. Nincs varázstrükk. Az egyetlen dolog, amire szükség van, hogy ne építsünk egy hatalmas vacakhalom tetejére, mint a modern toolchain.

## Jobb világkiáltvány

Haladást akarok látni. Változást akarok. Azt akarom, hogy a legmodernebb szoftverfejlesztés fejlődjön, ne csak álljon. Nem akarom újra és újra feltalálni ugyanazt a cuccot, kevésbé teljesítőképes és minden alkalommal egyre duzzadóbb. Szeretnék valamiben hinni, méltó végcélt, jobb jövőt, mint amink van, és olyan mérnökök közösségét akarom, akik osztják ezt a jövőképet.

Ami ma van, az nem haladás. A rossz alapokra halmozott mindenféle eszközökkel alig érjük el az üzleti célokat. Megrekedtünk a helyi optimumban, és senki sem akar elköltözni. Még csak nem is jó hely, felfújt és nem hatékony. Csak valahogy megszoktuk.

Szóval ki akarom mondani: ahol ma tartunk, az baromság. Mérnökként jobban tudunk, és jobban is kell, és fogunk is tenni. Jobb eszközeink lehetnek, jobb alkalmazásokat készíthetünk, gyorsabban, kiszámíthatóbban, megbízhatóbban, kevesebb erőforrás felhasználásával (nagyságrendekkel kevesebb!). Mélységében meg kell értenünk, mit és miért teszünk. Ugyanígy kell a munkát elvégezni is: megbízhatóan, kiszámíthatóan, a legjobb minőségben. Büszkék lehetünk – és kell is – a munkánkra. Nem csak „hát a körülményekhez képest” – semmi hát!

Remélem nem vagyok ezzel egyedül. Remélem, vannak olyanok, akik ugyanezt szeretnék tenni. Örülnék, ha legalább elkezdenénk beszélni arról, milyen abszurdan rossz a jelenlegi helyzetünk a szoftveriparban. Aztán talán kitaláljuk, hogyan szálljunk ki.
