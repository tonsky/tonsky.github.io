---
layout: post
title: "Desencantado con el Software"
category: blog
---

_Translated <a href="..">from English</a> by <a href="https://twitter.com/gosub_20">@gosub_20</a> and <a href="https://twitter.com/rojasgorky">@rojasgorky</a>_

He estado programando durante 15 años. Últimamente nuestra falta de cuidado por la eficiencia, la simplicidad y la excelencia  ha empezado a afectarme, hasta el punto de deprimirme por mi carrera y la industria en general.

Los coches modernos funcionan —digamos que— al 98% de lo que es físicamente posible con el diseño de motor actual. Los edificios modernos utilizan tan solo el material suficiente para cumplir su función y mantenerse seguros bajo las condiciones para los cuales fueron diseñados. Los aviones convergieron en el tamaño, la forma y la capacidad óptima y básicamente son todos iguales.

Solamente en software está bien que un programa funcione al 1% o incluso 0.01% de su rendimiento posible. Todo el mundo parece aceptarlo. Incluso hay veces que hasta parecen orgullosos de ello, como diciendo "¿por qué preocuparse? las computadoras son bastante rapidas":

> [@tveastman](https://twitter.com/tveastman/status/1039002300600147968): Tengo un programa en Python que ejecuto todos los días, tarda 1.5 segundos. Duré 6 horas re-escribiendolo en Rust, ahora solo tarda 0.06 segundos. Esa mejora en la eficiencia significa que voy a recuperar mi tiempo invertido en 41 años y 24 días :-)

Seguramente has escuchado este mantra: "el tiempo del programador es más importante que el tiempo del computador". Lo que significa básicamente es que estamos desperdiciando computación en una escala sin precedentes. ¿Comprarías un auto que gasta 100 litros en 100 kilometros? ¿Y si fueran 1000 litros? Pues con el software lo hacemos todo el tiempo.

<figure><a href="https://xkcd.com/2021/"><img src="../software_development_2x.gif" height="440"></a></figure>

## Todo es insoportablemente lento

Mira alrededor: nuestros portátiles son miles de veces más potentes que los ordenadores que llevaron al hombre a la luna. Aún así, las páginas web tienen problemas para desplazarse fluídamente a 60fps en la última MacBook Pro. Puedo jugar cómodamente, ver videos 4K, ¿pero no desplazarme fluídamente en una página web? ¿Cómo es posible?

Google Inbox, una aplicación web escrita por Google, ejecutando en el navegador Chrome, también escrito por Google, [tarda 13 segundos en abrir un email de tamaño moderado](https://twitter.com/nikitonsky/statuses/968882438024941568):

<figure><video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="../inbox.mp4" type="video/mp4"></video></figure>

También anima cajas blancas vacías en vez de mostrar su contenido, porque es la única forma en la que se puede animar en una página web con rendimiento decente. No, decente no significa 60fps, decente significa "tan rápido como sea posible". Me muero por ver cómo responde la comunidad web cuando las pantallas de 120Hz sean lo normal. La mierda actual difícilmente llega a 60Hz.

Windows 10 [tarda 30 minutos en actualizarse](https://grumpy.website/post/0PeXr1S7N). ¿Qué demonios podría estar haciendo que tarde tanto tiempo? Ese tiempo es suficiente para formatear mi disco SSD, descargar una copia nueva e instalarla 5 veces seguidas.

<figure><img src="../windows_update.gif" height="435"></figure>

> [Pavel Fatin](https://pavelfatin.com/typing-with-pleasure/): Escribir en un editor es un proceso relativamente fácil, por lo que incluso los PC 286 eran capaces de darnos una experiencia de escritura fluida.

Los editores de textos modernos tienen una latencia superior que el Emacs, un programa de edición de hace 42 años. ¡Editores de texto! ¿Qué puede ser más sencillo? En cada tecleo, solo tienes que actualizar una pequeña región rectangular de la pantalla y los editores de texto modernos no son capaces de hacerlo en 16ms. Eso es muchísimo tiempo. MUCHÍSIMO. Un juego en 3D puede llenar la pantalla entera de cientos de miles (!!!) de polígonos en esos 16ms y además procesar los dispositivos de entrada, recalcular el mundo y dinámicamente cargar/descargar recursos. ¿Cómo?

La tendencia general no es recibir software más rápido con más funciones. Estamos recibiendo hardware que ejecuta software más lento con las mismas funciones. Todo funciona muy por debajo del mejor rendimiento posible. ¿Alguna vez te has preguntado por qué tu teléfono toma de 30 a 60 segundos para arrancar? ¿Por qué no puede arrancar, digamos, en un segundo? No hay alguna limitación física para ello. Me encantaría ver eso. Me encantaría ver límites alcanzados y explorados, utilizando hasta la última gota de rendimiento posible para algo realmente significativo, de una forma significativa.

## Todo es ENOOOORME

Y está lleno de basura. Las aplicaciones web pudieran cargar 10 veces más rápido con solo bloquear los anuncios. Google le suplica a todos que dejen de meter la pata, utilizando la iniciativa AMP —una solución tecnológica a un problema que no necesita de tecnología, solo un poco de sentido común. Si eliminas la basura, la web se vuelve increíblemente rápida. ¿Qué tan listo hay que ser para entenderlo?

El sistema operativo Android, sin aplicaciones, [ocupa casi 6 GB](https://grumpy.website/post/0Oz1lDOq5). Piensa un segundo lo ENORME que es ese número. ¿Qué hay dentro, películas HD? Yo supongo que es básicamente código: kernel, drivers. Algunos textos y recursos también, claro, pero no pueden ser tan grandes. Así que, ¿cuántos drivers necesitas para un teléfono?

<figure><img src="../android_storage.jpg" height="489"></figure>

Windows 95 ocupaba 30 MB. ¡Actualmente hay páginas web más pesadas! Windows 10 ocupa 4 GB, que son 133 veces más que Windows 95. ¿Pero es 133 veces mejor? Quiero decir, funcionalmente son básicamente lo mismo. Claro, tenemos a Cortana, pero dudo mucho que ocupe 3970 MB. Pero sea lo que sea Windows 10, ¿es realmente Android un 50% más?

La aplicación de teclado de Google constantemente ocupa 150 MB. ¿Es una aplicación que pinta 30 teclas en pantalla realmente 5 veces más compleja que todo el Windows 95? ¡La aplicación de Google, que básicamente es un paquete para el buscador web de Google, ocupa 350 MB! Google Play Services, que ni siquiera uso (no compro libros, música o videos ahí)—300 MB que no hacen nada ahí y que no se pueden borrar.

<figure><img src="../apps_storage.gif" height="480"></figure>

Todo eso solo me deja alrededor de un 1 GB para mis fotos, después de instalar las aplicaciones esenciales (redes, chats, mapas, taxi, bancos, etc.). Y eso es sin instalar juegos, ¡y sin nada de música! ¿Recuerdas aquellos tiempos en los que todo el sistema operativo, las aplicaciones y todos tus datos cabían en un disquete?

Tu aplicación de listas de cosas por hacer probablemente esta escrita en Electron y por tanto [tiene un controlador de Xbox 360 en ella](https://josephg.com/blog/electron-is-flash-for-the-desktop/), puede pintar gráficos en 3D, reproducir música y tomar fotos con tu cámara web.

<figure><img src="../slack_memory.jpg" height="388"></figure>

Un simple chat de texto es notorio por su tiempo de arranque y consumo de memoria. Sí, realmente hay que considerar Slack como una aplicación intensiva. Quiero decir, se supone que un chat y un simple editor de texto son dos de las funciones que menos recursos deberían consumir en el mundo. Bienvenido al 2018.

"Al menos funciona", tal vez dices. Bueno, más grande no implica mejor. Más grande significa que alguien ha perdido el control. Más grande significa que no sabemos qué es lo que está pasando. Más grande implica costos de complejidad y costos en rendimiento. Esa no es la norma y no debería convertirse en la norma. Aplicaciones sobre-pesadas deberían ser una alerta. Debería significar huye despavorido.

## Todo se pudre

Un teléfono Android de 16GB era perfecto hace 3 años. Actualmente con Android 8.1 apenas se puede usar porque cada una de las aplicaciones para Android se han hecho el doble de grandes _sin razón aparente_. No hay funciones adicionales. No son más rápidas, ni están más optimizadas. No se ven diferente. Solo... ¿crecen?

El iPhone 4s fue lanzado con iOS 5, pero difícilmente soporta el iOS 9. Y no es porque este sea mucho mejor, son básicamente iguales. Pero el nuevo hardware es mucho más potente, e hicieron el software más lento. No te preocupes —tienes nuevas y excitantes capacidades, como... ¡ejecutar las mismas aplicaciones a la misma velocidad! Yo qué sé.

El iOS 11 abandonó el soporte para aplicaciones de 32 bits. Eso significa que si el desarrollador no la actualizó cuando salió el iOS 11 o no está dispuesto a actualizar una aplicación antes-perfecta, lo más probable es que no vuelvas a verla nunca más...

> @[jckarter](https://twitter.com/jckarter/statuses/1017071794245623808): Un programa de DOS puede funcionar sin modificaciones en casi cualquier computadora fabricada a partir de los 80s. Una aplicación en JavaScript puede dejar de funcionar en la próxima actualización de Chrome.

Las páginas web de hoy [no serán compatibles con ningún navegador dentro de 10 años](http://tonsky.me/blog/chrome-intervention/) (probablemente antes).

"Hay que correr con todo, solo para permanecer en el mismo sitio". ¿Cuál es mi punto? Ocasionalmente disfruto comprándome un teléfono nuevo y una MacBook nueva, tanto como cualquier otro. Pero, ¿solo para poder correr las mismas aplicaciones que se volvieron más lentas?

Creo que podemos y debemos hacerlo mejor. Todo el mundo está ocupado construyendo software para ahora mismo, hoy, raramente para mañana. Sería bonito tener software que dure un poco más que eso.

## Peor es mejor

Nadie entiende nada a estas alturas. Ni quieren hacerlo. Simplemente arrojamos mierda recién horneada, esperamos lo mejor y lo llamamos "sabiduría de la empresa".

Las páginas web te piden que las refresques si algo sale mal. ¿Quién tiene tiempo para averiguar qué ha pasado?

<figure><img src="../reload.jpg" height="185"></figure>

Cualquier aplicación web produce una secuencia constante de errores de JS al azar, incluso en navegadores compatibles.

La arquitectura de las páginas web/bases de datos SQL se basa en la premisa (esperanza, diría yo) de que nadie tocará tus datos mientras miras la página web.

Muchas de las implementaciones colaborativas hacen su "mejor esfuerzo" y tienen muchos casos cotidianos de perdida de datos. ¿Has visto alguna vez ese dialogo que dice "Cuál versión quieres conservar"? Digo, las expectativas son tan bajas actualmente que tus usuarios están contentos de siquiera tener un mensaje como ese.

<figure><img src="../icloud_conflict.jpg" height="468"></figure>

Y no, en mi mundo, una aplicación que te dice "voy a destruir una parte de tu trabajo, pero te dejo elegir cuál" no está bien.

Linux termina procesos al azar <em>por diseño</em>. Y aun así es el sistema operativo más popular para servidores.

Todos los dispositivos que tengo fallan regularmente de una forma u otra. Mi monitor Dell necesita un reseteo de vez en cuando ya que tiene software dentro. ¿Airdrop? Tienes suerte si detecta tu dispositivo, de lo contrario, ¿qué puedo hacer? ¿Bluetooth? Las especificaciones son tan complejas que los dispositivos [pierden la comunicación](https://thewirecutter.com/blog/understanding-bluetooth-pairing-problems/) y [reiniciarlos periódicamente es la mejor solución](http://time.com/4358533/bluetooth-fix-how/).

<figure><img src="../plz_connect.jpg" height="450"></figure>

Y ni siquiera voy a tocar el [Internet de las Cosas](https://twitter.com/internetofshit). No vale la pena añadir nada.

Quiero sentirme orgulloso de mi trabajo. Quiero entregar aplicaciones estables y funcionales. Para eso necesitamos entender qué estamos construyendo, por dentro y por fuera, y eso es imposible en sistemas sobre-cargados y sobre-diseñados.

## La programación sufre el mismo problema

Parece que ya nadie está interesado en construir material de calidad, rápido, eficiente, duradero y fundacional. Aunque se conocen soluciones eficientes desde hace años, seguimos luchando con los mismos problemas: gestión de los paquetes, compiladores, sistemas de automatización de compilado, diseño de lenguajes, IDEs.

Los sistemas de automatización de compilado son intrínsecamente poco fiables y requieren de limpieza total periódica, incluso cuando toda la información de invalidación está ahí. No hay nada que nos impida hacer procesos de automatización de compilado fiables, predecibles y 100% reproducibles. Simplemente nadie cree que sea importante. NPM se ha mantenido en estado de "a veces funciona" desde hace años...

> [@przemyslawdabek](https://twitter.com/przemyslawdabek/status/940547268729606145): Me parece que `rm -rf node_modules` es una parte indispensable del flujo de trabajo cuando desarrollas proyectos en Node.js/JavaScript.

¿Y la duración de compilado? Nadie piensa que el que los compiladores tarden minutos o incluso horas es un problema. ¿Dónde quedó lo de que "el tiempo del programador es más importante"? Casi todos los compiladores, pre- y post-procesadores añaden costos de duración significativos, a veces desastrosos, al compilado sin añadir un beneficio sustancial proporcional.

<figure><a href="https://xkcd.com/303/" target="_blank"><img src="../compiling.gif" height="360"></a></figure>

Se espera que un programador tome decisiones racionales en general, pero a veces hacen todo lo contrario. Como por ejemplo, utilizar Hadoop [incluso cuando es más lento que ejecutar la misma tarea en una sola computadora de escritorio](https://www.chrisstucchio.com/blog/2013/hadoop_hatred.html).

El Aprendizaje Automático y la "Inteligencia Artificial" han puesto al software a adivinar, cuando en primer lugar la gran mayoría de las computadoras no son lo suficientemente exactas.

> [@rakhim](https://twitter.com/freetonik/status/1039826129190875136): Cuando una aplicación o un servicio se auto-describe como "impulsado por IA" o "basado en ML", lo que leo es "inexacto, impredecible e imposible de razonar sobre su comportamiento". Intento evitar la "IA" porque quiero que loas computadoras sean lo opuesto: exactas, predecibles, razonables.

Pusimos máquinas virtuales dentro de Linux, y luego pusimos a Docker dentro de las máquinas virtuales, porque simplemente nadie fue capaz de lidiar con el desorden que producen la mayoría de los programas, idiomas y su entorno. Cubrimos la mierda con mantas para no lidiar con ella. Por ejemplo, el hecho de que produce un unico binario sigue siendo una GRAN ventaja de Go. Menos desorden -> menos problemas.

<figure><a href="https://xkcd.com/1987/" target="_blank"><img src="../python_environment_2x.gif" height="594"></a></figure>

¿Y las dependencias? La gente fácilmente añade paquetes sobre-diseñados —“full package solutions”— para resolver los más simples problemas, sin considerar el costo. Y estas dependencias de paquetes tienen dependencias de otros paquetes. Y terminas con un árbol que es algo entre una película de terror (tan grande y lleno de conflictos) y una comedia (no hay razón para añadirlos, [pero ahí están](https://medium.com/@jdan/i-peeked-into-my-node-modules-directory-and-you-wont-believe-what-happened-next-b89f63d21558)):

<figure><img src="../dependencies.gif" height="440"></figure>

Los programas ya no pueden funcionar durante años sin tener que reiniciarse. A veces [tan solo días es mucho pedir](https://docs.gitlab.com/ee/administration/operations/unicorn.html#unicorn-worker-killer). Pasan cosas al azar y nadie sabe por qué.

Y lo que es peor, nadie tiene tiempo para detenerse y averiguar qué es lo que ha pasado. ¿Para qué preocuparse si puedes pagar para evitar el problema?. Lanza otra instancia de AWS. Reinicia el proceso. Vacía y regenera la base de datos. Crea un proceso de vigilancia que reinicie la aplicación cada 20 minutos. Incluye los mismos recursos [múltiples veces, comprime y envía](https://blog.timac.org/2017/0410-analysis-of-the-facebook-app-for-ios-v-87-0/). Muévete rápido, no lo arregles.

Eso no es ingeniería. Eso es vagancia. Ingeniería es entender el rendimiento, la estructura, los límites de lo que construyes, profundamente. Combinar software mal escrito con cosas peor escritas va estrictamente en contra. Para progresar, necesitamos comprender qué estamos haciendo y por qué.

## Estamos atrapados

Todo es solo un montón de código que apenas funciona encima de otro código que apenas funciona también. Continúa creciendo en tamaño y complejidad, disminuyendo las posibilidades de cambio.

Para tener un ecosistema saludable _necesitas_ ir atrás y revisitarlo. _Necesitas_ ocasionalmente deshacerte de código y reemplazarlo con otro mejor.

<figure><img src="../design_process.jpg" height="657"></figure>

Pero, ¿quién tiene tiempo para eso? No hemos hecho nuevos sistemas operativos desde hace cuánto, ¿25 años? Simplemente ya es demasiado complejo para reescribirlo. Los navegadores están tan llenos de casos extremos y precedentes históricos, que nadie se atreve a escribir el motor de diseño entero desde cero.

La definición actual de progreso es o arrojar más combustible al fuego:

> [@sahrizv](https://twitter.com/sahrizv/status/1018184792611827712): 2014 - Necesitamos adoptar #microservicios para resolver todos los problemas de los monolítos.  
2016 - Debemos adoptar #docker para solucionar todos los problemas de los micro-servicios.  
2018 - Tenemos que adoptar #kubernetes para resolver todos los problemas de docker.

o reinventando la rueda:

> [@dr_c0d3](https://twitter.com/dr_c0d3/status/1040092903052378112): 2000: Escribe cientos de líneas de XML para configurar "declarativamente" tus servlets y EJBs.  
2018: Escribe cientos de líneas de YAML para configurar "declarativamente" tus micro servicios.  
AL menos XML tenia esquemas...

Estamos atrapados con lo que tenemos, y nadie nos salvará.

## A las empresas no les importa

Ni tampoco a los usuarios. Aprenden a esperar lo que les demos. ¿Nosotros (los ingenieros) decimos que cada aplicación de Android necesita 350 MB? Okay, se ajustarán. ¿Decimos que no podemos darles un desplazamiento suave? Okay, vivirán con un teléfono que laguea. ¿Decimos "si no funciona, reinícialo"? Y lo reiniciarán. Después de todo, no tienen otra opción.

No hay competencia tampoco. Todos están construyendo los mismos productos lentos, sobre-cargados y poco fiables. El salto ocasional en la calidad aporta una ventaja competitiva (iPhone / iOS frente a otros teléfonos inteligentes, Chrome frente a otros navegadores) y obliga a los demás a reagruparse, pero no por mucho tiempo.

Por lo tanto, nuestra misión como ingenieros es mostrarle al mundo lo que es posible hacer con los ordenadores actuales en términos de rendimiento, fiabilidad, calidad y facilidad de uso. Si nos importa, la gente aprenderá. Y no hay nadie más que pueda demostrarles que es muy posible. Si nos importara.

## No todo es malo

Hay esperanzas de que mejorar la vanguardia del software no es imposible:

El trabajo que [Martin Thompson](https://twitter.com/mjpt777) ha estado haciendo ([LMAX Disruptor](https://github.com/LMAX-Exchange/disruptor), [SBE](https://github.com/real-logic/simple-binary-encoding), [Aeron](https://github.com/real-logic/aeron)) es impresionante, refescantemente simple y eficiente.

[El editor Xi](https://github.com/google/xi-editor) de Raph Levien parece estar escrito con los principios correctos en mente.

[Jonathan Blow](https://www.youtube.com/user/jblow888) tiene un lenguaje que ha desarrollado él solo para su juego, capaz de compilar 500k líneas por segundo en su ordenador portátil. Eso es compilación en bruto, sin almacenamientos en caches intermedias ni compilaciones incrementales.

No hay que ser un genio para escribir programas rápidos. No es magia. Lo único que se necesitas es no construir sobre la gran pila de basura que es la cadena de herramientas moderna.

## Manifiesto de un Mundo Mejor

Quiero ver progreso. Quiero cambio. Quiero que la vanguardia de ingeniería de software mejore, no que se quede quieta. No quiero reinventar lo mismo una y otra vez, con menos rendimiento y con más sobre-cargado cada vez. Quiero algo en lo que creer, una meta digna, un mejor futuro del que tenemos hoy, y quiero una comunidad de ingenieros que comparta esa visión.

Lo que tenemos hoy no es progreso. A duras penas cumplimos los objetivos comerciales con herramientas deficientes aplicadas sin medida. Estamos atrapados en óptimos locales y nadie quiere moverse. Ni siquiera es un buen lugar, es sobre-cargado e ineficiente. Solo que de alguna manera nos hemos acostumbramos.

Así que quiero decirlo: la situación actual es una mierda. Como ingenieros, podemos, y debemos, hacerlo mejor. Podemos tener mejores herramientas, podemos construir mejores aplicaciones, más rápidas, más predecibles, más fiables, usando menos recursos (¡órdenes de magnitud menos!). Necesitamos entender profundamente qué estamos haciendo y por qué. Necesitamos entregar: de manera fiable, predecible, con la mejor calidad. Podemos —y debemos— enorgullecernos de nuestro trabajo. No quiero escuchar "dado lo que tenenemos..." —¡Sin peros!

Espero no estar solo en esto. Espero que haya personas que quieran hacer lo mismo. Agradecería que al menos empecemos a hablar de cuán absurdamente mala es nuestra situación actual en la industria del software. Y luego tal vez descubramos cómo salir.
