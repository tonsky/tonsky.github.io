---
layout: post
title: "Desencantado con el Software"
category: blog
---

_Translated by <a href="https://twitter.com/gosub_20">@gosub_20</a> <a href="..">from English</a>_

He estado programando durante 15 años. Últimamente nuestra falta de cuidado por la eficiencia, la simplicidad y la excelencia empieza a afectarme, hasta el punto de deprimirme por mi propia carrera y el IT en general.

Los coches modernos funcionan, digamos que por el bien de la disertación, al 98% de lo que es físicamente posible con el diseño actual. Los edificios modernos usan suficiente material para cumplir perfectamente su función y ser seguros bajo el rango de condiciones de diseño. Todos los aviones han convergido en el óptimo tamaño/forma/carga y básicamente son todos parecidos.

Solamente en el software, está bien que un programa funcione al 1% o incluso 0.01% de su posible rendimiento. Todo el mundo parece estar cómodo con esta situación. Incluso hay veces que hasta parecen orgullosos de ello, como cuando dicen "¿porque me voy a preocupar?, los ordenadores son mucho más rápidos":

> [@tveastman](https://twitter.com/tveastman/status/1039002300600147968): Tengo un programa en Python que funciona todos los días, tarda 1.5 segundos. Dedique 6 horas a re-escribirlo entero en Rust, ahora solo tarda 0.06 segundos. Esta eficiencia de tiempo significa que recuperaré mi tiempo invertido en 41 años y 24 meses :)

Seguramente has escuchado este mantra: " El tiempo de un programador es muchísimo más caro que el tiempo de computación". Lo que básicamente viene a decir que estamos desperdiciando computación en una escala sin precedentes. ¿Te comprarías un coche si gasta 100 litros a los 100? ¿Y si fuesen 1000? Pues con el software pasa constantemente.

<figure><a href="https://xkcd.com/2021/"><img src="../software_development_2x.gif" height="440"></a></figure>

## Todo es insoportablemente lento

Mira alrededor: nuestros portátiles son miles de veces más potentes que los ordenadores que llevaron al hombre a la luna. Y aun así, las páginas web tienen problemas para mantener un desplazamiento suave a 60 fps en el último MacBook Pro tope-de-gama. Puedo jugar a juegos confortablemente, ver videos 4K pero ¿no puedo hacer un desplazamiento suave en una página web? ¿Cómo es posible?

Google Inbox, una aplicación web escrita por Google, ejecuta un navegador Chrome escrito también por Google, [tarda unos 13 segundos en abrir un email de tamaño moderado](https://twitter.com/nikitonsky/statuses/968882438024941568):

<figure><iframe src="https://twitter.com/i/videos/tweet/968882438024941568?embed_source=clientlib&player_id=0&rpc_init=1&autoplay=1&language_code=en&use_syndication_guest_id=true" style="width: 650px; height: 406px" allowfullscreen allow="autoplay; fullscreen" frameBorder="0"></iframe></figure>

Anima también las cajas vacías en vez de solo pintarlas, porque es la única forma de que se puedan haber animaciones con un rendimiento decente. No, decente no significa 60fps, es más como "tan rápido como la página web pueda ir". Me muero por ver cómo responde la comunidad web cuando las pantallas de 120Hz sean lo normal. La mierda actual difícilmente llega a 60Hz.

Windows 10 [tarda 30 minutos en actualizarse](https://grumpy.website/post/0PeXr1S7N). ¿Qué demonios podrá estar haciendo que tarde tanto tiempo? Ese tiempo es suficiente para formatear mi disco SSD, bajarte una copia nueva e instalarla 5 veces seguidas.

<figure><img src="../windows_update.gif" height="435"></figure>

> [Pavel Fatin](https://pavelfatin.com/typing-with-pleasure/): Escribir en un editor es un proceso relativamente fácil, incluso los PC 286 eran capaces de darnos una experiencia de escritura fluida.

Los editores de textos modernos, tienen una latencia superior que el Emacs, un programa de edición de hace 42 años. ¡Editores de texto! ¿Que puede haber más sencillo? En cada tecleo, solo has de actualizar una pequeña región rectangular y los editores de texto modernos, no son capaz de hacerlo en 16ms. Es muchísimo tiempo. MUCHÍSIMO. Un juego en 3D puede llenar la pantalla entera de cientos de miles (!!!) de polígonos en esos 16ms y además procesar los dispositivos de entrada, recalcular el mundo y dinámicamente cargar/descargar recursos. ¿Cómo?

Como tendencia general, no estamos obteniendo un software más rápido con más prestaciones. Estamos obteniendo un Hardware que ejecuta software más lento con las mismas características. Todo funciona muy por debajo de sus posibles velocidades. Te has preguntado ¿por qué tu teléfono necesita de 30 a 60 segundos para arrancar? ¿Por qué no puede arrancar, pongamos en un segundo? No hay una limitación física para que así sea. Me encantaría verlo. Me encantaría explorar y alcanzar los límites, utilizando cada bit de rendimiento que podamos obtener para algo realmente significativo de una forma significativa.

## Todo es ENOOOORME

Además está hinchado. Las aplicaciones web pueden abrirse 10 veces más rápido solo bloqueando los anuncios. Google suplica a todo el mundo que deje de dispararse en el pie y use la iniciativa AMP — una solución tecnológica a un problema que no necesita de ninguna tecnología, solo un poco se sentido común. Si eliminas la hinchazón, la Web va increíblemente rápida. ¿Hace falta ser muy listo para entenderlo?

Il sistema operativo Android, senza app installate, [richiede quasi 6 Gb](https://grumpy.website/post/0Oz1lDOq5). Pensate solo per un secondo a quanto è esagerata questa cifra.
Che c’è dentro, film in HD?  Scommetto che ci sarà in pratica solo del codice: il kernel e dirvers. Qualche stringa in più, e le risorse per funzionare , certamente, ma quanto possono essere grandi? Quindi, ma di quanti driver ha bisogno un telefono per funzionare?

El sistema Android sin aplicaciones [ocupa casi 6 Gigas (6 GB)](https://grumpy.website/post/0Oz1lDOq5). Piensa un segundo cuan obscenamente GRANDE es ese número. ¿Qué hay dentro, películas HD? Creo que es básicamente código: kernel, drivers. Algunos textos y recursos también, seguro, pero no pueden ser tan grandes. Así que, ¿cuántos drivers necesitas para un teléfono?

<figure><img src="../android_storage.jpg" height="489"></figure>

El Windows 95 ocupaba 30 MB. Actualmente tenemos páginas web más pesadas! El Windows 10 ocupa 4 GB, que son 133 veces más aún. ¿Pero es 133 veces mejor? Quiero decir, funcionalmente son básicamente lo mismo. Cierto, tenemos Cortana, pero dudo mucho que ocupe 3970 MB. Pero con todo lo que es Windows 10, ¿Android es un 150% más que Windows?

La aplicación de teclado de Google constantemente ocupa 150 MB. ¿Es una aplicación que pinta 30 caracteres en una pantalla realmente 5 veces más compleja que todo el Windows 95? Google app, que básicamente es un paquete para el buscador de web de Google, ¡ocupa 350 MB! Google Play Services, que ni siquiera uso (No compro libros, música o videos ahí) — 300 MB ahí apalancados y no se pueden borrar.

<figure><img src="../apps_storage.gif" height="480"></figure>

Todo esto me deja alrededor de un 1 GB para mis fotos después de que instale las aplicaciones esenciales, (social, chats, mapas, taxi, bancos etc.). Y eso sin instalar juegos, ¡y sin nada de música! ¿Recuerdas aquellos tiempos en que todo, el sistema operativo, las aplicaciones y tus datos cabían en un floppy?

Tu aplicación de escritorio de "TODO" (lista de cosas por hacer) esta probablemente escrita en Electrón y este [tiene un controlador de Xbox 360 en el](https://josephg.com/blog/electron-is-flash-for-the-desktop/), puede pintar gráficos en 3D, poner música y fotografiar desde tu cámara web.

<figure><img src="../slack_memory.jpg" height="388"></figure>

Un simple chat de texto es notorio por su velocidad y consumo de memoria. Si, tienes que considerar Slack como una aplicación que gasta muchísimos recursos. Quiero decir, las habitaciones de chat, y un simple editor de texto, son dos de las aplicaciones que menos recursos deberían consumir del mundo. Bienvenido al 2018.

Al menos funciona, puedes decir. Bueno, más grande no implica mejor. Más grande significa que alguien ha perdió el control. Más grande significa que no sabemos que lo que está pasando. Más grande implica una taxa de complejidad, una taxa a de rendimiento. Esto no es la norma y no debería convertirse en la norma. Aplicaciones sobre pesadas deberían de levantar una bandera roja. Debería significar huye despavorido.

## Todo se pudre

Un teléfono de 16GB Android era perfecto hace 3 años. Actualmente con el 8.1 de Android, apenas se puede usar porque cada una de las aplicaciones para Android se han hecho el doble de grandes _sin ninguna razón aparente_. No hay funcionalidades adicionales. No son más rápidas, ni están más optimizadas. No tienen una apariencia distinta, solo ¿Crecen?

El iPhone 4s fue lanzado con el iOS 5, pero difícilmente puede ejecutar el iOS 9. Pero no es porque sea mucho mejor, básicamente son iguales. Pero su nuevo hardware es mucho más potentes, e hicieron el software más lento. No te preocupes, tienes nuevas y excitantes capacidades, como... ¡ejecutar las mismas aplicaciones a la misma velocidad! No sé.

El iOS 11 abandonó el soporte a aplicaciones en 32 bits. Esto significa que si el desarrollador no la actualizo cuando salió el iOS 11 o no está dispuesto a actualizar una aplicación antes-perfecta, seguramente que no vuelvas a verla nunca más...

> @[jckarter](https://twitter.com/jckarter/statuses/1017071794245623808): Un programa de DOS puede funcionar sin modificaciones en casi cualquier ordenador hecho desde los 80s, Una aplicación en JavaScript puede dejar de funcionar en la próxima actualización de Chrome.

Las páginas web que funcionan hoy [pueden no ser compatibles con ningún navegador dentro de 10 años.](http://tonsky.me/blog/chrome-intervention/) (Probablemente antes)

“Hace falta correr todo cuanto una pueda para permanecer en el mismo sitio.” ¿A dónde quiero ir a parar? Ocasionalmente puedo disfrutar comprándome un teléfono nuevo y un nuevo MacBook, tanto como cualquier otro. Pero ¿Solo para poder ejecutar las mismas aplicaciones que se vuelven más lentas?

Creo que podemos y debemos hacerlo mejor. Todo el mundo está ocupado construyendo software para ahora, para hoy, raramente para mañana. Estaría bien que tener software que duren un poco más de tiempo.

## Peor es mejor

Nadie entiende nada a estas alturas. Tampoco creas que quieren. Simplemente arrojamos mierda recién horneada, esperamos lo mejor y lo llamamos "sabiduría de startup".

Las páginas web te piden que las refresques si algo sale mal. ¿Quién tiene tiempo para saber que ha ido pasado?

<figure><img src="../reload.jpg" height="185"></figure>

Cualquier aplicación web produce una constante serie de líneas de errores JS "aleatorios", incluso en los navegadores compatibles.

Toda la arquitectura de página web/ base de datos SQL se basa en la premisa (esperanza, incluso) de que nadie toque tus datos mientras miras la página web pintarse.

Muchas de las implementaciones colaborativas hacen "lo mejor que pueden" y tienen muchos escenarios normales en donde pierden datos. Alguna vez has visto ese dialogo que dice "¿Qué versión quieres mantener?" Es decir, el listón está tan bajo actualmente que tus usuarios están contentos de tener al menos un mensaje como ese.

<figure><img src="../icloud_conflict.jpg" height="468"></figure>

Y no, en mi mundo, una aplicación que te dice "voy a destruir algo de tu trabajo, pero te dejo elegir cual" no está bien.

Linux Elimina procesos aleatoriamente <em>por diseño</em>. Y aun así es el sistema operativo más popular en el lado backend, o servidor.

Todos los dispositivos que tengo, fallan regularmente de una forma u otra. Mi monitor Dell necesita ser reseteado de vez en cuando porque tiene software dentro. ¿Airdrop? Tienes suerte si detecta tu dispositivo, por otro lado, ¿Qué hacer? ¿Bluetooth? Las especificaciones son tan complejas que los dispositivos [no se hablan entre si](https://thewirecutter.com/blog/understanding-bluetooth-pairing-problems/) y [reiniciarse periódicamente es la mejor manera de hacerlo](http://time.com/4358533/bluetooth-fix-how/).

<figure><img src="../plz_connect.jpg" height="450"></figure>

Y ni siquiera estoy tocando el [Internet de las cosas](https://twitter.com/internetofshit). Es tal descojone que no sé qué añadir.

Quiero sentirme orgulloso de mi trabajo. Quiero  entregar softwares estables y funcionales. Para hacer eso, necesitamos entender que estamos construyendo, por dentro y por fuera, y eso es imposible en sistemas inflados y sobre-diseñados.

## En programación es el mismo lío

Parece que nadie está ya interesado en construir material de calidad, rápido, eficiente, duradero y fundacional. Incluso cuando se conocen soluciones eficientes desde hace años, seguimos peleándonos con los mismos problemas: gestión de los paquetes, construcción de sistemas, compiladores, diseño de lenguajes, IDEs (entornos de trabajo).

Construir sistemas es intrínsecamente poco fiable y requieren de una periódica limpieza total, incluso cuando toda la información está allí. No hay nada que nos impida hacer procesos de construcción fiables, predecibles y que sean 100% repetibles. Simplemente nadie cree que sea importante. NPM tiene el estatus de "a veces funciona" desde hace años...

> [@przemyslawdabek](https://twitter.com/przemyslawdabek/status/940547268729606145): Me parece que `rm -rf node_modules` es una parte indispensable del flujo de trabajo cuando desarrollas proyectos en  Node.js/JavaScript.

¿Y los tiempos de compilación? Nadie piensa en que los compiladores tarden minutos o incluso horas son un problema. ¿Dónde queda lo de "el tiempo del programador es más importante"? Casi todos los compiladores, los pre y post procesamientos añaden tiempos significativos, a veces desastrosos al compilar sin añadir un beneficio sustancial proporcional.

<figure><a href="https://xkcd.com/303/" target="_blank"><img src="../compiling.gif" height="360"></a></figure>

Es de esperar que un programador tome decisiones generalmente racionales, aun así a veces hacen todo lo contrario como por ejemplo elegir Hadoop [incluso cuando es más lento que ejecutar la misma tarea en un solo escritorio](https://www.chrisstucchio.com/blog/2013/hadoop_hatred.html).

El Machine learning y La "IA" han girado el software hacia la adivinación en una época en la que la gran mayoría de los ordenadores aun no funcionan con fiabilidad.

> [@rakhim](https://twitter.com/freetonik/status/1039826129190875136): Cuando una aplicación o un servicio se describe como "alimentado con IA" o "basado en ML", lo que leo es "poco confiable, impredecible e imposible de razonar sobre su comportamiento". Intentó evitar la "IA" porque quiero que los ordenadores sean lo opuesto: confiables, predecibles, razonables.

Pusimos máquinas virtuales dentro de Linux, y luego pusimos a Docker dentro de las máquinas virtuales, simplemente porque nadie fue capaz de limpiar el desorden que producen la mayoría de los programas, idiomas y su entorno. Cubrimos la mierda con mantas simplemente para no lidiar con ella. Por ejemplo el "Single binary" sigue siendo un GRAN punto para Go. Sin problemas == éxito.

<figure><a href="https://xkcd.com/1987/" target="_blank"><img src="../python_environment_2x.gif" height="594"></a></figure>

¿Y las dependencias? La gente añade fácilmente paquetes sobre diseñados en forma de “full package solutions” para resolver los más simples problemas, sin considerar el costo que esto añade. Y estas dependencias de paquetes, tienen dependencias de otros paquetes. Y terminas con un árbol que a veces esta entre una peli de miedo (dios mío tan grande y lleno de conflictos) y la comedia (no hay un porque añadirlo, [pero aquí esta](https://medium.com/@jdan/i-peeked-into-my-node-modules-directory-and-you-wont-believe-what-happened-next-b89f63d21558)):

<figure><img src="../dependencies.gif" height="440"></figure>

Los programas ya no pueden funcionar durante años sin tener que reiniciarse. A veces [es mucho pedir incluso días](https://docs.gitlab.com/ee/administration/operations/unicorn.html#unicorn-worker-killer). Pasan cosas aleatorias y nadie sabe por qué.

Y lo que es peor, nadie tiene tiempo para detenerse y descubrir qué es lo que ha pasado. ¿Para qué preocuparse si siempre puedes pagar para evitar el problema?. Lanzas otra instancia de AWS. Reinicias el proceso. Tiras y actualizas la base de datos. Escribes un soft que reinicie la aplicación cada 20 minutos. Incluye los mismos recursos [multiples veces, comprime y envía](https://blog.timac.org/2017/0410-analysis-of-the-facebook-app-for-ios-v-87-0/). Muévete rápido, no lo arregles.

No es ingeniería. Simplemente es vagancia. Ingeniería es entender los resultados, la estructura, los límites de lo que construyes, en profundidad. Combinar software mal escrito con cosas peor escritas va en contra de la ingeniería. Para progresar, necesitamos comprender qué y por qué estamos haciendo.

## Estamos atrapados

Todo es solo un montón de código que apenas funciona puesto encima de otro código anterior que apenas funciona también. Continúa creciendo en tamaño y complejidad, disminuyendo las posibilidades de cambiar.

Para tener un ecosistema saludable _necesitas_ ir atrás y revisitarlo. _Necesitas_ ocasionalmente deshacerte de código y reemplazarlo con otro mejor.

<figure><img src="../design_process.jpg" height="657"></figure>

Pero, ¿quién tiene tiempo para eso? No hemos hecho nuevos diseños de sistemas operativos desde hace cuánto, ¿25 años? Es demasiado complejo para simplemente re-escribirlo entero ahora. Los navegadores están tan llenos de casos extremos y precedentes históricos, que nadie se atreve a escribir el motor de diseño entero desde cero.

La definición actual de progreso es o arrojar más combustible al fuego:

> [@sahrizv](https://twitter.com/sahrizv/status/1018184792611827712): 2014 - Necesitamos adoptar los  #microservices para resolver todos los problemas de los monolitos. <br>2016 - Debemos adoptar #docker para solucionar todos los problemas de los micro servicios.<br>2018 - Tenemos que adoptar  #kubernetes para resolver todos los problemas de docker

o reinventando la rueda:

> [@dr_c0d3](https://twitter.com/dr_c0d3/status/1040092903052378112): 2000: Escribe cientos de líneas de XML para configurar "declarativamente" tus servlets y EJBs (EJBeans).<br>2018: Escribe cientos de líneas de YAML para configurar "declarativamente" tus micro servicios.<br> AL menos XML tenia esquemas...

Estamos atrapados con lo que tenemos, y nadie nos salvará.

## Las empresas no se preocupan

Tampoco lo harán los usuarios. Solo se les enseña a esperar lo que podemos ofrecerles. Nosotros (los ingenieros) decimos que cada aplicación de Android necesita 350 MB, Ok, se amoldaran a eso. Decimos que no podemos darles un desplazamiento suave, Ok, vivirán con un teléfono que tartamudea. Decimos "si no funciona, reinicie" Y reiniciarán. Después de todo, no tienen otra opción.

No hay competencia tampoco. Todos están construyendo los mismos productos lentos, hinchados y poco fiables. El salto ocasional en la calidad aporta una ventaja competitiva (iPhone / iOS frente a otros teléfonos inteligentes, Chrome frente a otros navegadores) y obliga a los demás a reagruparse, pero no por mucho tiempo.

Por lo tanto, nuestra misión como ingenieros es mostrarle al mundo lo que es posible hacer con los ordenadores actuales en términos de rendimiento, fiabilidad, calidad y facilidad de uso. Si nos importa, la gente aprenderá. Y no hay nadie más que pueda demostrarles que es muy posible. Si nos importase.

## No todo es malo

Hay algunos puntos brillantes que indican que la mejora sobre lo último en software es posible.

Work [Martin Thompson](https://twitter.com/mjpt777) ha estado haciendo ([LMAX Disruptor](https://github.com/LMAX-Exchange/disruptor), [SBE](https://github.com/real-logic/simple-binary-encoding), [Aeron](https://github.com/real-logic/aeron)) es impresionante, refescantemente simple y eficiente.

[El editor Xi](https://github.com/google/xi-editor) de Raph Levien parece estar escrito con los principios correctos en mente.

[Jonathan Blow](https://www.youtube.com/user/jblow888) tiene un lenguaje que ha desarrollado el solo para su juego, capaz de compilar 500k líneas por segundo en su ordenador portátil. Eso es compilación en bruto, sin almacenamientos en caches intermedias ni compilaciones incrementales.

No hay que ser un genio para escribir programas rápidos. No es magia. Lo único que se necesitas es no construir sobre la gran pila de basura que es la cadena de herramientas moderna.

## El Mejor manifiesto mundial

Quiero ver progreso. Quiero cambiar. Quiero que lo último en ingeniería de software mejore, no que se quede quieto. No quiero reinventar lo mismo una y otra vez, con menos rendimiento y más hinchado cada vez. Quiero algo en lo que creer, un objetivo final digno, un futuro mejor que el que tenemos hoy, y quiero una comunidad de ingenieros que comparta esa visión.

Lo que tenemos ahora no es progreso. Apenas cumplimos los objetivos comerciales con herramientas deficientes aplicadas unas encima de otras. Estamos atrapados en óptimos locales y nadie quiere moverse. Ni siquiera es un buen lugar, está hinchado y es ineficiente. De alguna manera nos hemos acostumbramos.

Así que quiero decirlo: donde estamos hoy es una mierda. Como ingenieros, podemos, debemos y lo haremos mejor. Podemos tener mejores herramientas, podemos construir mejores aplicaciones, más rápidas, más predecibles, más fiables, usando menos recursos (¡órdenes de magnitud menos!). Necesitamos entender profundamente qué estamos haciendo y por qué. Necesitamos entregar: de manera fiable, predecible, con la mejor calidad. Podemos, y debemos enorgullecernos de nuestro trabajo. No sólo "dando lo que teníamos..." -¡Sin peros!

Espero no estar solo en esto. Espero que haya personas que quieran hacer lo mismo. Agradecería que al menos empecemos a hablar de cuán absurdamente mala es nuestra situación actual en la industria del software. Y luego tal vez descubramos cómo salir.
