---
layout: post
title: "Le désenchantement du logiciel"
category: blog
---

_Traduit <a href="..">de l'anglais</a> par <a href="https://blog.romainfallet.fr/desenchantement-logiciel/" target="_blank">Romain Fallet</a>._

Je programme depuis 15 ans maintenant. Récemment, le manque d’attention de l’industrie du logiciel en matière d’efficacité, de simplicité et d’excellence a commencé réellement à me peser, au point d’être déprimé par ma propre carrière et l’informatique en général.

Les voitures modernes utilisent 98 % — pour ne pas dire 100 % — de ce que permettent les limites physiques actuelles induites par la conception de leurs moteurs. Les constructions modernes utilisent juste ce qu’il faut de matières premières pour remplir leur fonction tout en étant suffisamment résistantes pour garantir la sécurité de leur ensemble sous certaines conditions. Tous les avions convergent vers le meilleur rapport poids/taille/capacité de chargement et fonctionnent fondamentalement sur le même principe.

Il n’y a qu’en logiciel qu’on accepte qu’un programme tourne à 1 % voire même 0,01 % de ses performances optimales. Tout le monde semble être d’accord avec ça. Les gens sont même souvent fiers de leur niveau d’inefficacité, « Pourquoi s’inquiéter ? Les ordinateurs sont bien assez rapides. » :

> [@tveastman](https://twitter.com/tveastman/status/1039002300600147968) : J’ai un programme Python qui s’exécute tous les jours en 1,5 seconde. J’ai passé six heures à le réécrire en Rust, il s’exécute maintenant en 0,06 seconde. Compte tenu de l’amélioration des performances, j’aurai rentabilisé ce temps investi dans 41 ans et 24 jours :-)

Vous avez probablement déjà entendu ce mantra : « le temps d’un programmeur coûte plus cher que celui d’un ordinateur ». Cela signifie simplement que nous gaspillons les ressources de nos ordinateurs à une échelle sans précédent. Est-ce que vous achèteriez une voiture si elle consommait 100 litres au 100 kilomètres ? Et si c’était 1000 litres au 100 kilomètres ? Avec les ordinateurs, nous faisons ça constamment.

<figure>
  <a href="https://xkcd.com/2021/" target="_blank"><img src="../software_development_2x.gif" height="440"></a>
</figure>

## Tout est insupportablement lent

Regardez autour de vous : nos ordinateurs portables sont mille fois plus puissants que ceux qui ont emmené l’Homme sur la Lune. Et pourtant, les pages web ont du mal à maintenir une vitesse de défilement constante de 60 fps sur la dernière version du MacBook Pro. Je peux jouer à des jeux confortablement, regarder des vidéos 4K mais pas défiler des pages web ? Comment ça peut être acceptable ?

L’application web Google Inbox développée par Google, qui tourne dans le navigateur Chrome de la même firme, [met 13 secondes pour ouvrir un courriel de taille moyenne](https://twitter.com/nikitonsky/statuses/968882438024941568).

<figure><video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="../inbox.mp4" type="video/mp4"></video></figure>

Et elle anime des boîtes blanches vides plutôt que d’en afficher les contenus parce que c’est la seule façon pour les animations web de fonctionner avec des performances décentes.

Attention, je ne parle pas de vitesse d’animation décente en 60 fps, c’est plutôt une animation « qui va aussi vite que la page web le permet ». Je meurs d’envie de voir comment va réagir la communauté web quand les écrans 120 Hz deviendront la norme. On arrive déjà rarement à 60 fps.

Windows 10 [met 30 minutes à se mettre à jour](https://grumpy.website/post/0PeXr1S7N). Qu’est-ce qu’il peut bien faire pendant tout ce temps ? Ça me laisse assez de temps pour formater complètement mon SSD, télécharger un nouveau fichier d’installation et l’installer 5 fois de suite.

<figure><img src="../windows_update.gif" height="435"></figure>

> [Pavel Fatin](https://pavelfatin.com/typing-with-pleasure/) : Taper dans un éditeur de texte est un processus relativement simple, même les ordinateurs 286 étaient capables de fournir une expérience d’écriture fluide.

Les éditeurs de texte modernes ont plus de latence qu’un Emacs vieux de 42 ans. Des éditeurs de texte ! Qu’est-ce qui peut être plus simple ? À chaque appui d’une touche de clavier, tout ce qu’il faut faire c’est mettre à jour une petite zone rectangulaire, et les éditeurs de textes modernes ne peuvent pas le faire en 16 ms. C’est beaucoup. VRAIMENT. En comparaison, un jeu 3D peut remplir tout l’écran avec des centaines de milliers de polygones pendant ces mêmes 16 ms tout en gérant les actions du joueur, recalculant la carte et actualisant dynamiquement les éléments chargés. Comment est-ce possible ?

De façon générale, nous ne faisons pas des logiciels plus rapides avec plus de fonctionnalités. Ce sont les composants informatiques qui eux sont de plus en plus rapides et qui exécutent des logiciels de plus en plus lents, toujours avec les mêmes fonctionnalités. Tout ça fonctionne bien moins vite que ce qu’il serait possible de faire. Vous ne vous êtes jamais demandé pourquoi votre téléphone a besoin de 30 à 60 secondes pour démarrer ? Pourquoi ne pourrait-il pas démarrer en disons, une seconde ? Il n’y a aucune limitation physique qui explique ça. J’adorerais voir ça. J’adorerais voir les limites atteintes et explorées, qu’on utilise chaque bit de performance possible pour créer quelque chose de significatif et de manière significative.

## Tout est ÉNORME

Et puis, il y a le poids. Les applications web pourraient s’ouvrir jusqu’à 10 fois plus vite si on bloquait simplement toutes les publicités. Google demande à tout le monde de ne plus se tirer une balle dans le pied avec l’initiative AMP, une solution technologique à un problème qui n’a pas besoin de technologie, juste d’un peu de bon sens. Si on supprime le poids superflu, le web devient incroyablement rapide. À quel point faut-il être intelligent pour comprendre ça ?

Le système Android sans aucune application installée [pèse quasiment 6 Go](https://grumpy.website/post/0Oz1lDOq5). Prenez juste une seconde pour vous rendre compte à quel point ce chiffre est énorme. Il y a quoi là-dedans ? Des films HD ? Je suppose que c’est essentiellement du code : kernel, drivers. Avec un peu de texte et quelques ressources, très certainement, mais ces derniers ne peuvent pas être si gros. Donc, de combien de drivers un téléphone a-t-il besoin pour fonctionner ?

<figure><img src="../android_storage.jpg" height="489"></figure>

Windows 95 pesait 30 Mo. Aujourd’hui, on a des pages web plus lourdes que ça ! Windows 10 pèse 4 Go, ce qui est 133 fois plus lourd. Est-ce qu’il en est 133 fois supérieur pour autant ? Je veux dire, fonctionnellement parlant, c’est la même chose. Oui, il y a Cortana, mais je ne pense pas qu’elle pèse 3970 Mo. Et même si on laisse Windows de côté, est-ce qu’Android est 150 % meilleur ?

L’application clavier de Google consomme constamment 150 Mo de mémoire. Est-ce qu’une application qui dessine 30 caractères sur un écran est réellement cinq fois plus complexe que Windows 95 tout entier ? L’application Google, qui est juste la recherche web empaquetée pèse 350 Mo ! Les services Google Play, que je n’utilise pas (je n’achète pas de livres, de musique, ni de film par ce biais) : 300 Mo qui sont encore pris et que je ne peux même pas libérer.

<figure><img src="../apps_storage.gif" height="480"></figure>

Tout ça me laisse avec environ 1 Go pour mes photos une fois les applications essentielles installées (sociales, messageries instantanées, cartes & navigation, taxi, banques, etc.). Et encore, c’est si je n’installe aucun jeu ni musique ! Vous vous souvenez de l’époque où un système d’exploitation, tous ses logiciels et vos données tenaient sur une disquette ?

Votre application de bureau de liste de tâches est probablement écrite avec Electron, qui [embarque un pilote de contrôleur Xbox 360](https://josephg.com/blog/electron-is-flash-for-the-desktop/), peut afficher des éléments 3D, jouer de la musique et prendre des photos avec votre webcam.

<figure><img src="../slack_memory.jpg" height="388"></figure>

Une application simple de messagerie instantanée est d’ailleurs connue pour son temps de chargement et sa consommation de mémoire. Oui, Slack fait réellement partie des applications gourmandes en ressources. Je veux dire, un salon de chat et un éditeur de texte qui sont minimalistes et peu gourmands sont supposés être deux des applications les moins demandées au monde. Bienvenue en 2018.

Au moins ça fonctionne, vous pourrez me dire. Eh bien, plus gros ne veut pas forcément dire meilleur. Plus gros veut simplement dire qu’on ne sait pas ce qui se passe. Plus gros est synonyme d’impact significatif sur la complexité, les performances et la fiabilité. Ce n’est pas la norme et ça ne devrait pas le devenir. Les applications surchargées devraient être synonymes de drapeau rouge, devraient faire peur et faire fuir.

## Tout finit par devenir obsolète

Un téléphone Android de 16 Go était parfait il y a 3 ans. Aujourd’hui, avec Android 8.1, c’est à peine utilisable étant donné que chaque application est devenue au moins deux fois plus lourde pour aucune raison apparente. Il n’y a pas de fonctionnalités supplémentaires. Elles ne sont pas plus rapides, ni plus optimisées. Elles n’ont pas l’air différentes. Elles sont juste… plus lourdes ?

L’iPhone 4S est sorti avec iOS 5, mais peut à peine exécuter iOS 9. Pas parce qu’iOS 9 est à ce point supérieur, c’est quasiment la même chose. Mais vu que les nouveaux composants matériels sont plus rapides, ils rendent les logiciels plus lents. Ne vous inquiétez pas, vous avez droit aux toutes nouvelles fonctionnalités comme… lancer les mêmes applications avec la même vitesse ! Ça me dépasse.

iOS 11 a arrêté le support des applications 32 bits. Cela signifie que si le développeur n’est pas dans le coin à la sortie d’iOS 11 ou ne veut tout simplement pas revenir en arrière pour mettre à jour une application parfaitement fonctionnelle, il y a de fortes chances que vous ne puissiez plus jamais utiliser son application.

> @[jckarter](https://twitter.com/jckarter/statuses/1017071794245623808) : Un programme DOS peut être fait pour s’exécuter sans modification sur pratiquement n’importe quel ordinateur construit depuis les années 80. Une application JavaScript peut cesser de fonctionner avec la prochaine mise à jour de Chrome.

Les pages web d’aujourd’hui [ne seront plus compatibles avec aucun navigateur dans 10 ans](http://tonsky.me/blog/chrome-intervention/) (et probablement plus tôt).

« Il vous faut toute votre capacité de travail pour faire du sur-place ». Dans quel but ? Je peux occasionnellement apprécier l’achat d’un nouveau téléphone ou d’un nouveau MacBook comme n’importe qui, mais quel est l’intérêt si c’est juste pour pouvoir exécuter toutes les applications qui sont devenues plus lentes ?

Je pense que l’on peut et que l’on doit faire mieux que ça. Tout le monde est occupé à concevoir pour aujourd’hui, très peu le font pour demain. Pourtant, ne serait-ce pas agréable d’avoir des choses qui durent un petit plus dans le temps ?

## L’apologie de la médiocrité

Les programmeurs ne comprennent plus rien à ce qu’ils font au stade où on en est. Ils ne le veulent pas non plus. Nous lançons simplement des merdes qui tiennent à peine la route, en espérant que ça fonctionne et on appelle ça « la sagesse des startups ».

Les pages web vous demandent de rafraîchir dès que quelque chose ne marche pas. Après tout, pourquoi perdre du temps à vérifier ce qui ne va pas ?

<figure><img src="../reload.jpg" height="185"></figure>

N’importe quelle page web couplée à une architecture de base de données SQL est construite sur l’hypothèse que personne ne viendra toucher aux données pendant qu’on regarde la page générée.

La plupart des implémentations collaboratives constituent ce qui se fait de mieux dans le domaine, mais ont tout de même un certain nombre de scénarios où ils perdent des données.

Vous avez déjà vu cette boîte de dialogue qui vous demande « quelle version conserver » ? Je veux dire, la barre est placée tellement bas aujourd’hui que les utilisateurs peuvent déjà être heureux d’avoir une boîte de dialogue comme ça.

<figure><img src="../icloud_conflict.jpg" height="468"></figure>

Et non, dans mon monde, une application n’a pas le droit de me dire : « je vais détruire une partie de ton travail, mais c’est à toi de choisir laquelle ».

La conception même de Linux fait qu’il met fin aléatoirement à des processus en cours d’exécution. Et pourtant, c’est le système le plus répandu côté serveur.

Chaque appareil que j’ai plante régulièrement d’une façon ou d’une autre. Mon écran Dell a besoin d’un redémarrage forcé de temps en temps parce qu’il y a des logiciels dedans. AirDrop ? On a déjà de la chance s’il détecte notre appareil, et sinon, on fait quoi ? Le Bluetooth ? Les spécifications sont tellement complexes que les appareils [ne communiquent pas entre eux](https://thewirecutter.com/blog/understanding-bluetooth-pairing-problems/) et leur [redémarrage périodique](http://time.com/4358533/bluetooth-fix-how/) est la meilleure façon de les faire fonctionner.

<figure><img src="../plz_connect.jpg" height="450"></figure>

Et je ne parle même pas de l’[Internet des objets](https://twitter.com/internetofshit). Ça va tellement loin qu’on a dépassé le stade où on peut en rire, je ne saurais même pas quoi ajouter.

Je veux être fier de mon travail. Je veux fournir des choses qui fonctionnent, qui sont stables. Pour faire ça, il faut que l’on comprenne ce que nous faisons, à l’entrée et à la sortie, et ça ne peut pas se faire dans un système fourre-tout avec autant de surcouches.

## Programmer, c’est le même bordel

On dirait simplement que plus personne n’est intéressé à faire de la qualité, rapide, efficace, durable et qui puisse devenir les fondations d’autres choses. Même quand des solutions très efficaces sont connues depuis des lustres, on lutte toujours avec les mêmes problèmes : gestion des paquets et des dépendances, systèmes de compilation, compilateurs, conception de langage et IDEs.

Les systèmes de compilation sont intrinsèquement peu fiables et nécessitent un nettoyage complet, même si toutes les informations relatives à l’invalidation des données générées sont disponibles. Il n’y a rien qui nous empêche de rendre ces systèmes fiables, prédictibles et 100 % reproductibles. Personne ne trouve cela important, voilà tout. NPM est resté dans un état « qui fonctionne parfois » pendant des années.

> [@przemyslawdabek](https://twitter.com/przemyslawdabek/status/940547268729606145) : On dirait que la commande rm -rf node_modules est une part indispensable du workflow quand on développe des projets Node.js/JavaScript.

Et qu’en est-il des temps de compilation ? Personne ne trouve qu’un compilateur qui tourne pendant des minutes voire des heures pose problème. Qu’est devenu le fameux « le temps du programmeur est plus important que celui de l’ordinateur » ? Quasiment tous les compilateurs, pré et post processeurs ont un impact significatif, parfois désastreux sur les temps de développement, sans réels bénéfices substantiels.

<figure><a href="https://xkcd.com/303/" target="_blank"><img src="../compiling.gif" height="360"></a></figure>

On pourrait attendre des programmeurs qu’ils prennent une majorité de décisions rationnelles, et pourtant, des fois, ils font l’exact opposé. Ex. choisir Hadoop, [même quand c’est plus lent qu’exécuter la même tâche sur un simple ordinateur de bureau](https://www.chrisstucchio.com/blog/2013/hadoop_hatred.html).

> [@rakhim](https://twitter.com/freetonik/status/1039826129190875136) : Quand une application ou un service est décrit comme étant « alimenté par une IA » ou « basé sur de l’AM », je lis « peu fiable, imprévisible et impossible de raisonner face à un comportement ». J’essaye d’éviter les « IAs » parce que j’attends des ordinateurs qu’ils soient tout le contraire : fiables, prévisibles et raisonnés.

Nous avons mis des machines virtuelles à l’intérieur de Linux, ensuite, on a mis Docker à l’intérieur des machines virtuelles, uniquement parce que personne n’était capable de nettoyer le bordel que la plupart des programmes, langages et leur environnement produisent. On cache cette merde sous le tapis pour ne plus avoir à nous en préoccupper. Par exemple, « un seul binaire » est toujours l’argument marketing primordial du langage Go. Comme on dit en anglais « no mess == success ».

<figure>
  <a href="https://xkcd.com/1987/" target="_blank"><img src="../python_environment_2x.gif" height="594"></a>
</figure>

Et les dépendances ? On en parle ? Les développeurs utilisent souvent des packages de solutions complètes pour résoudre les plus simples des problèmes, sans considérer leur coût. Et ces dépendances amènent d’autres dépendances. On finit avec un arbre entre histoire d’horreur (OMG, tellement énorme, et source de conflits) et comédie (il n’y a aucune raison d’inclure ça, et [pourtant c’est là](https://medium.com/@jdan/i-peeked-into-my-node-modules-directory-and-you-wont-believe-what-happened-next-b89f63d21558)).

<figure><img src="../dependencies.gif" height="440"></figure>

Les programmes ne peuvent plus fonctionner pendant des années sans redémarrer. Parfois, [quelques jours seulement c’est déjà trop en demander](https://docs.gitlab.com/ee/administration/operations/unicorn.html#unicorn-worker-killer). Des trucs aléatoires surviennent et personne ne sait pourquoi.

Le pire dans tout ça, c’est que personne n’a le temps de s’arrêter et de prendre le temps de trouver ce qui ne va pas. En même temps, pourquoi se prendre la tête quand on peut toujours faire autrement ? Lancer une nouvelle instance AWS. Redémarrer un processus. Exporter et restaurer entièrement la base de données. Écrire un programme qui redémarre automatiquement l’application en panne toutes les 20 minutes. Inclure les mêmes éléments [plusieurs fois, zippé et déployé](https://blog.timac.org/2017/0410-analysis-of-the-facebook-app-for-ios-v-87-0/). Avancer vite, ne pas réparer.

Ceci n’est pas de l’ingénierie. C’est de la programmation de paresseux. L’ingénierie est synonyme de performance, de structure et implique de comprendre les limites profondes de ce qu’on construit. Combiner des programmes mal écrits, avec des programmes encore plus mal rédigés va directement à l’encontre de ça. Pour progresser, nous devons comprendre ce que nous faisons et pourquoi.

## On est bloqué avec

En résumé, tout est juste un tas de code qui fonctionne à peine, ajouté par dessus du code qui fonctionnait déjà à peine. Et ça continue de grossir, de prendre de l’ampleur en gagnant en complexité, ce qui diminue les chances que cela change.

Par avoir un écosystème sain, vous devez revenir en arrière et réadapter. Vous devez occasionnellement jeter des choses à la poubelle et les remplacer par des éléments plus efficaces.

<figure><img src="../design_process.jpg" height="657"></figure>

Mais qui a le temps pour ça ? On n’a pas vu de nouveau système d’exploitation en quoi, 25 ans ? C’est juste trop complexe pour simplement réécrire tout maintenant. De la même façon, les navigateurs sont tellement remplis de précédents historiques que personne n’ose écrire un nouveau moteur de rendu de zéro.

La définition actuelle du progrès est soit de jeter plus d’huile sur le feu :

> [@sahrizv](https://twitter.com/sahrizv/status/1018184792611827712) :<br/>2014 - On doit adopter les #microservices pour résoudre tous les problèmes liés aux monolithes.<br/>2016 - On doit adopter #docker pour résoudre tous les problèmes liés aux microservices.<br/>2018 - On doit adopter #kubernetes pour résoudre tous les problèmes avec Docker.

soit réinventer la roue :

> [@dr_c0d3](https://twitter.com/dr_c0d3/status/1040092903052378112) :<br/>2000: On écrit des centaines de lignes XML pour « déclarativement » configurer les servlets et EJBs.<br/>2018: On écrit des centaines de lignes YAML pour « déclarativement » configurer les microservices.<br/>Au moins, le XML avait des schémas…

Nous sommes bloqués avec ce que nous avons, et personne ne va venir nous sauver.

## Les entreprises ne s’y intéressent pas

Les utilisateurs non plus. Ils sont uniquement éduqués pour attendre ce qu’on peut fournir. On (programmeurs) leur dit que chaque application Android pèse 350 Mo ? Pas de problème, ils vont faire avec. On leur dit qu’on ne peut pas faire de défilement fluide ? Pas de problème, ils vont faire avec un téléphone qui rame. Si on leur dit « si ça ne marche pas, redémarre », ils vont le faire. Après tout, ils n’ont pas d’autres options.

Il n’y a même pas de compétition. Tout le monde construit les mêmes produits lents, peu fiables et fourre-tout. Occasionnellement, on va faire un bond en avant dans la qualité, ce qui va amener un avantage compétitif (iPhone/iOS vs les autres smartphones, Chrome vs les autres navigateurs) et forcer tout le monde à se regrouper, mais ça ne dure jamais longtemps.

C’est donc notre mission en tant que programmeurs de montrer au monde ce qu’il est possible de faire avec les ordinateurs d’aujourd’hui en termes de performance, fiabilité, qualité et facilité d’utilisation. Si nous y faisons attention, les gens vont apprendre. Et il n’y a personne à part nous pour leur montrer que c’est possible. Ce n’est possible que si on y fait attention.

## Tout n’est pas mauvais

Il y a certains points qui indiquent que passer outre cet état de l’art n’est pas impossible.

Le travail de [Martin Thompson](https://twitter.com/mjpt777) ([LMAX Disruptor](https://github.com/LMAX-Exchange/disruptor), [SBE](https://github.com/real-logic/simple-binary-encoding), [Aeron](https://github.com/real-logic/aeron)) est impressionnant, rafraîchissant, simple et efficace.

[Xi editor](https://github.com/google/xi-editor) de Raph Levien semble avoir été conçu avec les bons principes en tête.

[Jonathan Blow](https://www.youtube.com/user/jblow888) a un langage qu’il développe seul pour son jeu et qui peut compiler 500k lignes par seconde sur son ordinateur portable (compilé à froid, sans mise en cache intermédiaire, sans génération incrémentielle).

Il n’y a pas besoin d’être un génie pour écrire des programmes rapides. Il n’y a pas de tour de magie. La seule chose, c’est de ne pas construire par dessus cette énorme pile de conneries que sont nos outils modernes.

## Manifeste pour un monde meilleur

Je veux voir du progrès. Je veux du changement. Je veux que l’état de l’art en développement logiciel s’améliore et ne fasse pas du surplace. Je ne veux pas que l’on réinvente la roue encore et encore avec moins d’efficacité. Je veux quelque chose auquel croire, un objectif final digne, un avenir meilleur que ce que nous avons aujourd’hui et une communauté d’ingénieurs et de programmeurs qui partage cette vision.

Ce que nous avons aujourd’hui n’est pas le progrès. Nous répondons avec peine à des besoins commerciaux, le tout, avec des outils bas de gamme. Nous sommes coincés dans des optimas locaux et personne ne veut s’en déloger. Ce n’est même pas un bon endroit dans lequel évoluer, c’est fourre-tout et inefficace. On s’y est juste habitué.

C’est pourquoi je veux me faire entendre : là où nous sommes aujourd’hui, c’est la merde. En tant qu’ingénieurs, nous pouvons, et devons, et allons faire mieux. Nous pouvons fabriquer de meilleurs outils, concevoir de meilleures applications, plus rapides, plus prédictibles, plus fiables, en utilisant moins de ressources (d’ordre de grandeur inférieur !). Nous devons comprendre profondément ce que nous faisons et pourquoi. Nous devons fournir : avec fiabilité, de façon prévisible, avec la plus haute qualité envisageable. Nous pouvons — et devons — retirer de la fierté de notre travail. Et pas juste « étant donné ce que nous avions… », il n’y a pas de « mais » qui tienne.

J’espère ne pas être le seul ici. J’espère qu’il y a des gens qui veulent faire la même chose. J’apprécierais que nous commencions au moins à parler de la gravité absurde de notre situation actuelle dans l’industrie du logiciel. Et peut-être un jour, savoir comment en sortir !