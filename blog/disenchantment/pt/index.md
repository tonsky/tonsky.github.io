---
layout: post
title: "O Desencanto do Software"
category: blog
---

_Translated by [Bruno Paulino](https://twitter.com/brunojppb) <a href="..">from English</a>_

Eu tenho programado por 15 anos. Recentemente nossa industria tem perdido o cuidado com eficiência, simplicidade e exelência. Isso começou a me incomodar ao ponto de me deixar depressivo com minha própria carreira e a área de TI como um todo.

Carros modernos funcionam, digamos assim, a 98% do que é fisicamente possível com o atual design do motor. Prédios modernos utilizam apenas o material necessário para funcionar e serem adequadamente seguros para as condições que foram projetados. Todos os planos convergem para uma ótima forma/tamanho/carga e basicamente são bem parecidos.

Apenas em software é normal que um programa rode a 1% ou ate mesmo a 0.01% de sua capacidade máxima e as pessoas parecem tranquilas quanto a isso. As vezes essas mesmas pessoas se mostram orgulhosas de quanto ineficiente seu software é, ainda dizendo "porque se preocupar, computadores são rápidos pra caramba":

> [@tveastman:](https://twitter.com/tveastman/status/1039002300600147968) Eu tenho um programa em Python que rodo todo dia e ele demora 1.5 segundos. Eu gastei 6 horas reescrevendo em Rust e agora ele roda em 0.006 segundos. Essa melhoria na eficiência significa que eu receberei meu tempo de volta em 41 anos e 24 dias :-)

Você provavelmente ja ouviu o mantra: "O tempo do programador é mais caro que o tempo do computador". O que isso significa é que estamos desperdiçando computadores em uma escala absurda. Você compraria um carro se ele consumisse 100 litros por 100 quilômetros? e 1000 litros por 100 quilômetros? Estamos fazendo isso com computadores o tempo todo.

<figure><a href="https://xkcd.com/2021/"><img src="../software_development_2x.gif" height="440"></a></figure>

## Tudo é muito lento

Olhe em volta: Nosso computadores portáteis são milhares de vezes mais poderosos que os que levaram o homem a lua. Mesmo assim, algumas páginas web sofrem para manter um scroll de 60fps no mais moderno Macbook Pro. Eu consigo jogar em alta resolução, assistir videos em 4K porém não consigo rolar uma página web? Como isso é aceitável?

Google Inbox, um web app criado pelo Google, rodando no Chrome também desenvolvido pelo Google, [demora 13 segundos para abrir um email de tamanho moderado.](https://twitter.com/nikitonsky/statuses/968882438024941568)

<figure><video autoplay="" muted="" loop="" preload="auto" playsinline="" controls><source src="../inbox.mp4" type="video/mp4"></video></figure>

O app também anima caixas vazias ao invés de mostrar o conteúdo do email, porque essa é a única forma de animar coisas na web com uma performance decente. Não, performance decente não significa 60fps, significa "tão rápido quanto uma página web pode ser". Não posso esperar para ver o que a comunidade web vai dizer quando monitores 120Hz se tornarem comuns. As coisas mal conseguem rodar a 60Hz agora.

Windows 10 demora 30 minutos para atualizar. O que poderia justificar toda essa demora? Todo esse tempo é suficiente para formatar meu Disco SSD, baixar um build do zero e instalar 5 vezes seguidas.

<figure><img src="../windows_update.gif" height="435"></figure>

> [Pavel Fatin](https://pavelfatin.com/typing-with-pleasure/): Digitar em um editor é um processo relativamente simples, mesmo computadores 286 eram capazes de fornecer uma experiência decente.

Editores de texto modernos possuem uma latência maior que Emacs com já 42 anos de idade. Editores de texto! o que poderia ser mais simples? Em cada tecla prescionada, tudo que você tem que fazer é atualizar pequenas regiões na tela e parece que editores modernos não conseguem fazer em 16 milissegundos. É muito, muito tempo. Jogos 3D preenchem a tela inteira com milhões de polígonos nos mesmo 16 milissegundos e ainda processa inputs, recalculam o mundo e carregam e descarregam recursos dinamicamente. Como isso é possível?

Como uma modinha global, nós não estamos desenvolvendo software mais rápidos com mais funcionalidades. Nós estamos desenvolvendo hardware mais poderosos que rodam software mais lentos com as mesmas funcionalidades. Tudo funciona muito abaixo da velocidade possível. Já se perguntou porque seu smartphone demora entre 30 e 60 segundos para inicializar? Por que não pode inicializar em 1 segundo? Não existem limitações físicas para justificar isso. Eu adoraria ver os limites serem alcançados e explorados, utilizando toda performance disponível para fazer algo significativo.

## Tudo é gigaaaaante

Aplicações web poderiam rodar 10 vezes mais rápido se você simplemente bloquear as propagandas. Google implora para que todo mundo pare de atirar no próprio pé com sua iniciativa chamada AMP (Accelerated Mobile Pages) - Uma solução tecnológica para um problema que não precisa de nem uma tecnologia, apenas um pouco de senso comum. Se removermos o desnecessário, a web se tornaria incrivelmente rápida. Quão inteligente você precisa ser para entender isso?

O sistema Android sozinho [usa mais de 6GB](https://grumpy.website/post/0Oz1lDOq5). Apenas pense por um minuto o quanto gigante esse número é. O que tem dentro desse sistema? Filmes em HD? Creio que seja basicamente código: Kernel e drivers. Algumas Strings e recursos também, claro. Porém não pode ser tão grande assim. Quantos drivers você precisa para um smartphone?

<figure><img src="../android_storage.jpg" height="489"></figure>

O Windows 95 tem 30MB. Hoje temos páginas web mais pesadas que isso! Windows 10 tem 4GB, o que justifica ser 113 vezes maior? Funcionalmente, eles são basicamente o mesmo. Sim, nós temos Cortana, porém eu não acredito que isso justifique os 3970 MB a mais. De qualquer forma, o que o Windows 10 for, o Android realmente precisa ser 150% disso?

O app de teclado do Google consome 150MB. Isso é serio que um app que renderezida 30 teclas na tela é mais complexo que todo o Windows 95? Google app, que é basicamente um pacote incluíndo Google Web Search, é 350MB! Google Play Services, o qual eu não uso (Eu não compro livros, música ou vídeos lá) - 300MB que apenas existem lá e que eu não posso deletar.

<figure><img src="../apps_storage.gif" height="480"></figure>

Depois de tudo isso, eu fico com 1GB para minhas fotos depois que instalo os apps essenciais(mídias sociais, chats, mapas, taxi, bancos e etc). E isso não inclue nem um jogos ou música! Lembra de um tempo que um Sistema Operacional com todos os dados e aplicativos cabiam em um disquete?

Seu app para tarefas é provavelmente escrito em Electron, o qual [contém drivers para controle de Xbox 360](https://josephg.com/blog/electron-is-flash-for-the-desktop/), pode renderizar gráficos 3D, tocar áudio e tirar fotos com sua camera.

<figure><img src="../slack_memory.jpg" height="388"></figure>

Uma simples aplicação de chat é visivelmente pesada, consumindo muita memória e tempo de carregamento. Sim, você tem que contar o Slack como uma aplicação que consome muitos recursos. Um chat e um simples editor de texto deveriam ser considerados as aplicações mais leves existente no mundo. Bem-vindo a 2018.

Pelo menos funciona, você pode dizer. Bom, maior não significa melhor. Maior significa que alguém perdeu o controle. Maior significa que nós não sabemos o que está acontecendo. Maior significa mais débito em complexidade, em performance e em consistência. Isso não é a norma e não deve se tornar a norma. Aplicativos gigantes deveriam ser um sinal vermelho. Deveriam significar "Fuja o mais rápido que puder".

## Tudo Apodrece

Um smartphone Android com 16GB era perfeitamente funcional 3 anos atrás. Hoje, com a versão 8.1 do Android, esses mesmos smartphones são praticamente inutilizáveis, porque cada app se tornou pelo menos 2 vezes maior sem nenhum motivo aparente. Sem nenhuma funcionalidade nova. Não são mais rápidos ou mais otimizados. Eles não parecem diferente. Eles apenas... aumentam de tamanho?

O iPhone 4S foi lançado com o iOS 5, porém mal consegue rodar o iOS 9. E não é porque o iOS 9 é muito mais superior. São  praticamente a mesma coisa. Porém, o novo hardware é mais rápido, então eles fizeram o software mais lento. Não se preocupe, você agora tem novas funcionalidades como... rodar os mesmos apps com a mesma velocidade! Não consigo entender.

iOS 11 deixou de suportar apps em 32-bit. Isso significa que se desenvolvedor não estiver lançando atualizações durante o lançamento do iOS 11 ou se não quiser atualizar um app que está rodando perfeitamente, chances são grandes de você nunca mais ver esse app.

> [@jckarter](https://twitter.com/jckarter/statuses/1017071794245623808): Um programa DOS pode rodar sem nenhuma modificação em praticamente qualquer computador feito desde os anos 80. Um app javascript provavelmente quebrará com a próxima atualização do Chrome.

Páginas web atuais provelmente [não serão compatíveis com nenhum navegador web daqui a 10 anos](http://tonsky.me/blog/chrome-intervention/)(Talvés até antes).

"Consome tudo que você tem apenas para se manter no lugar". Porém, qual o sentido disso? Eu talvés goste de comprar um novo smartphone ou um novo MacBook, porém apenas para poder rodar os mesmos aplicativos que se tornaram devagar?

Eu acho que nós podemos e devemos fazer melhor que isso. Todo mundo está ocupado construindo alguma coisa agora, hoje, raramente para o amanhã. Seria muito legal se tudo isso que construimos durasse mais um pouco.

## Pior é Melhor

Ninguem entende nada nesse ponto. Nem querem entender. Nós apenas jogamos coisas feitas pela metade no mercado, esperamos pelo melhor e ainda chamos isso de "saberia da startup".

Páginas web pedem para você atualizar se algo sair errado. Quem tem tempo para verificar o que aconteceu?

<figure><img src="../reload.jpg" height="185"></figure>

Todo aplicativo web produz constantemente erros javascript aleatórios, até mesmo em navegadores compatíveis.

A arquitetura página web/banco de dados SQL é feita baseada na premissa (assim espera-se) quem ninguém toque nos dados enquanto você olha na página renderizada.

A maioria dos aplicativos colaborativos implementam "o melhor esforço"  e tem vários cenários comuns onde eles perdem dados. Já viu essa menssagem "qual versão manter?". Eu digo, o nível ficou tão baixo que os usuários ficariam felizes se pelo menos eles tivessem uma messagem dessas.

<figure><img src="../icloud_conflict.jpg" height="468"></figure>

E no meu mundo onde um app fala "Eu vou destruir parte de seu trabalho, no entanto você pode escolher que parte perder" isso não é okay.

Linux mata processos por padrão. E ainda assim é o sistema operacional para servidores mais popular.

Todo dispositivo que tenho, falha muitas vezes de um forma ou de outra. Meu monitor Dell precisa de um hard reset de tempos em tempos só porque tem software rodando lá. Airdrop? Você é um sortudo se ele detectar seu dispositivo. Bluetooth? A especificação é tão complexa que a grande parte dos dispositivos [não se comunicam entre si](https://thewirecutter.com/blog/understanding-bluetooth-pairing-problems/) e [resets periódicos são a melhor forma de consertar.](http://time.com/4358533/bluetooth-fix-how/)

<figure><img src="../plz_connect.jpg" height="450"></figure>

E eu não estou nem considerando IoT(Internet Of Things). Tudo funciona tão mal que eu não sei nem o que acrescentar aqui.

Eu quero ser orgulhoso do meu trabalho. Quero entregar coisas funcionando, sólidas e estáveis. Para isso, nós precisamos entender o que estamos construindo, de ponta a ponta, e isso é impossível de fazer em sistemas gigantes com engenharia desnecessária.

## A bagunça é a mesma na programação

Apenas parece que ninguém está interesado em construir coisas de qualidade, rápidas, eficiente, duradouras. Mesmo quando soluções eficientes tem funcionado por décadas, nós ainda esbarramos nos mesmos problemas: Gerenciamente de pacotes, systemas de build, compiladores, design de linguagens, IDEs.

Sistemas de build não são mais confiáveis e geralmente precisamos excluir tudo, apesar de toda a informação para invalidação existir lá. Nada nos impede de fazer com que o processo de build seja confiável, previsível e 100% reproduzível. Apenas ninguem pensa que é importante. NPM está na situação de "as vezes funciona" tem alguns anos já.

> [@przemyslawdabek](https://twitter.com/przemyslawdabek/status/940547268729606145): Para mim, rm -rf node_modules é um comando indispensável no meu workflow durante desenvolvimento de aplicações Node.js e Javascript.

E tempo de compilação? Ninguem pensa que o compilador funciona por minutos ou até por horas é um problema. O que acontece com "A hora do programador é mais importante"? Quase todos os compiladores, pré e pós-processadores, adicionam muito mais tempo em seu processo de build sem nem uma justificativa.

<figure><a href="https://xkcd.com/303/" target="_blank"><img src="../compiling.gif" height="360"></a></figure>

Você pode esperar que programadores geralmente tomam decisões racionais, ainda assim, as vezes eles fazem totalmente o contrário. Por exemplo, escolhendo usar Hadoop, [mesmo quando é mais devagar que rodar a mesma tarefa em um único desktop.](https://www.chrisstucchio.com/blog/2013/hadoop_hatred.html)

Machine Learning e "AI" transformaram software em "achismos" no tempo em que computadores não são nem confiáveis o suficiente.

> [@rakhim](https://twitter.com/freetonik/status/1039826129190875136): Quando um app ou serviço é descrito como "Feito com AI" ou "Baseado em ML", eu leio "imprevisivo, não confiável e impossível de entender seu comportamento". Eu tento evitar "AI" porque eu quero que computadores façam exatamente o oposto: "previsível, confiável e que eu possa entender".

Nós colocamos uma máquina virtual dentro do Linux, e depois colocamos o Docker dentro de máquinas virtuais, simplesmente porque ninguém foi capaz de limpar a bagunça da maioria dos programas, linguagens e o ambiente que isso produz. Nós cobrimos a bagunça com um pano, apenas para não ter que lidar com isso. "Único binário" ainda é a maior jogada de marketing da linguagem Go, por exemplo. Sem bagunça == sucesso.

<figure><a href="https://xkcd.com/1987/" target="_blank"><img src="../python_environment_2x.gif" height="594"></a></figure>

E as dependências? Desenvolvedores geralmente adicionam "pacotes completos" para resolver os mais simples dos problemas sem considerar os custos. E essas dependências trazem outras dependências. Você termina com uma árvore que se parece com algo como uma história de terror(gigante e cheia de conflitos) e uma comédia (Não tem motivo para incluí-las, [más mesmo assim nós adicionamos](https://medium.com/@jdan/i-peeked-into-my-node-modules-directory-and-you-wont-believe-what-happened-next-b89f63d21558)).

<figure><img src="../dependencies.gif" height="440"></figure>

Programas não podem funcionar por anos sem reiniciar de tempos em tempos. As vezes [alguns dias é pedir muito.](https://docs.gitlab.com/ee/administration/operations/unicorn.html#unicorn-worker-killer) Coisas aleatórias acontecem e ninguém sabe o porque.

O que é pior, ninguém tem tempo para parar e descobrir o que está acontecendo. Por que se preocupar se você pode comprar a solução? Rode outra instância AWS, reinicie o processo, destrua e restaure o banco de dados. Escreva um watchdog que reiniciará o aplicativo quebrado a cada 20 minutos. Inclua os mesmos recursos [várias vezes, gere o zip e envie.](https://blog.timac.org/2017/0410-analysis-of-the-facebook-app-for-ios-v-87-0/) Mova rápido e não conserte.

Isso não é engenharia. É Apenas preguiça de programar. Engenharia é entender performance, estrutura e os limites do que você está construindo, profundamente. Combinar coisas escritas de qualquer forma com mais coisas escritas de qualquer forma vai totalmente contra esse princípio. Para progredir, nós precisamos entender o que e porque estamos fazendo.

## Nós estamos presos com isso

Então tudo é apenas uma montanha de coisas feitas pela metade por cima de outras coisas escritas de qualquer forma que mal funcionam. E isso fica só crescendo e aumentando a complexidade, diminuindo qualquer chance para mudança.

Para ter um ecosistema saudável, você precisa voltar e revisitar. Você precisa, ocasionalmente, jogar coisas fora e substituir por coisas melhores.

<figure><img src="../design_process.jpg" height="657"></figure>

Mas quem tem tempo para isso? Nós não temos visto novos Kernels de sistemas operacionais em 25 anos. São simplesmente muito complexos para simplesmente serem reescritos agora. Navegadores são tão cheios de casos isolados que ninguem nem se atreve a escrever uma engine de layout do zero.

A definição de hoje para progresso é jogar mais lenha na fogueira:

> [@sahrizv](https://twitter.com/sahrizv/status/1018184792611827712): 2014 - Nós precisamos adaptar #microservices para resolver todos os problemas com monolitos.
2016 - Nós precisamos adotar #docker para resolver todos os nossos problemas com microservices
2018 - Nós precisamos adotar #kubernetes para resolver nossos problemas com docker

Ou reinventar a roda:

> [@dr_c0d3](https://twitter.com/dr_c0d3/status/1040092903052378112): 2000: Escrever centenas de linhas de XML para "declarativamente" configurar servlets e EJBs.
2018 - Escrever centenas de linhas de YAML para "declarativamente" configurar seus microservices.
Pelo menos XML possuia schemas...

Nós estamos presos com o que temos e ninguém virá nos salvar.

## Business não ligam

Bem como os usuários. Eles apenas aprenderam a usar o que nós construímos. Nós (engenheiros) falamos que todo app Android precisa de 350MB? Okay, eles viverão com isso. Nós falamos que não podemos entregar uma rolagem flúida? Okay, eles viverão com um smartphone lento. Nós falamos "Se não funcionar, reset"? Eles irão resetar. Afinal de contas, eles não tem escolha.

Também não existe competição. Todo mundo está construindo algum produto lento, gigante e não confiável. Pulos em qualidade que acontecem raramente trazem uma vantagem competitiva (iPhone/iOS vs outros smartphones, Chrome vs outros navegadores) que força todo mundo a se reagrupar, porém não por muito tempo.

Isso mostra que é a nossa missão como engenheiros de mostrar ao mundo o que é possível com os computadores atuais em termos de performance, confabilidade, qualidade e usabilidade. Se nós nos preocuparmos, as pessoas irão aprender. E não existem outras pessoas, a não ser nós mesmos, para mostrá-los que isso é possível. Se apenas nos preocuparmos.

## Nem tudo está perdido

Existem alguns pontos de esperança indicando que melhorar coisas state-of-the-art não é impossível.

O trabalho que [Martin Thompson](https://twitter.com/mjpt777) vem fazendo ([LMAX Disruptor](https://github.com/LMAX-Exchange/disruptor), [SBE](https://github.com/real-logic/simple-binary-encoding), [Aeron](https://github.com/real-logic/aeron)) é de se admirar. simples e eficiente.

[Xi editor](https://github.com/google/xi-editor) por Ralph Levien parece ser construido com os princípios corretos em mente.

[Jonathan Blow](https://www.youtube.com/user/jblow888) tem uma linguagem que ele mesmo desenvolveu para o seu game que compila 500k linhas por segundo em seu laptop. Isso é uma compilação fria, sem cache intermediário e sem build incrementais.

Você não precisa ser um gênio para escrever programas rápidos. Não existe mágica. A única coisa necessária é não escrever seus programas sobre uma pilha de lixo que são muitas dessas ferramentas modernas.

## Manifesto para um mundo melhor

Eu quero ver o progresso. Eu quero mudança. Eu quero ver state-of-the-art em engenharia de software melhorar e não só ficar parado no tempo. Eu não quero reinventar a mesma coisa toda vez, geralmente maior e menos performática. Eu quero algo que eu possa acreditar, um propósito válido. Um futuro melhor do que temos hoje e eu quero uma comunidade de engenheiros que compartilham dessa visão.

O que temos hoje não é progresso. Nós mal atingimos os objetivos de negócio usando ferramentas pobres. Nós estamos travados no mesmo local e ninguém quer se mover. Não é nem um lugar legal, é gigante e ineficiente. Nós simplesmente nos acostumamos.

Então eu quero falar em voz alta: Onde estamos hojé é estúpido. Como engenheiros, nós podemos e devemos fazer melhor. Nós podemos ter melhores ferramentas, nós podemos construir apps melhores, mais rápidos, mais previsíveis, mais confiáveis, usando menos recursos(milhares de vezes menos!). Nós precisamos entender profundamente o que estamos fazendo e o porque. Nós precisamos entregar: Segurança, previsíbilidade e a melhor qualidade possível. Nós podemos e devemos ter orgulho de nosso trabalho. Não apenas "dar o que temos".

Eu espero que não esteja sozinho nisso. Eu espero que tenha pessoas ai fora que queiram fazer a mesma coisa. Eu ficaria muito feliz se, pelo menos, nós começarmos a discutir sobre como a situação atual na indústria de software é ruim e depois talvés descobrir como sair desse estado.