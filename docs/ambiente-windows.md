# Caixa de Sugestões — preparação do ambiente Windows

Tutorial para configurar **Oracle JDK 17 e IntelliJ IDEA gratuito** no **Windows 10 e Windows 11**, antes de iniciar a construção da API de Caixa de Sugestões.

**Padrão da turma: Java 17, projetos em `C:\dev\projetos` e cache do Gradle em `C:\dev\gradle-cache`. Não crie projetos dentro da pasta do usuário.**

## 1. Entenda as versões usadas

A trilha usa as versões abaixo. A main contém apenas material didático; os arquivos de projeto serão criados por você no módulo 01 e também existem nas branches -impl.

| Componente | Configuração do projeto |
| --- | --- |
| Java | JDK 17, definido pela toolchain no `build.gradle` |
| Spring Boot | 4.1.1 |
| Gradle Wrapper | 9.7.1, definido em `gradle/wrapper/gradle-wrapper.properties` |
| IDE | IntelliJ IDEA com recursos gratuitos para Java |

O **Spring Boot 4 exige Java 17 ou superior**. Java 8 não serve para este projeto. Aqui, “Spring 4” significa **Spring Boot 4**, e não o antigo Spring Framework 4: são produtos com numerações diferentes. Consulte os [requisitos oficiais do Spring Boot](https://docs.spring.io/spring-boot/system-requirements.html).

O JDK inclui o compilador (`javac`) e o executável (`java`). Instalar somente um JRE não é suficiente para desenvolver. O Gradle Wrapper baixa a versão de Gradle definida pelo projeto: **não é necessário instalar Gradle separadamente**.

## 2. Prepare as pastas antes de instalar

Nos computadores da turma, o perfil pode aparecer como `/Users/Cesário`; no Windows, o caminho usual é `C:\Users\Cesário`. Pastas como Área de Trabalho, Documentos, Downloads, OneDrive e o local padrão `IdeaProjects` normalmente ficam dentro desse perfil.

**Não use essas pastas para criar, extrair ou executar projetos das aulas.** O acento em `Cesário` pode causar problemas em ferramentas, scripts e integrações que não tratam corretamente caracteres Unicode. Isso não significa que o Java proíba acentos; a padronização evita problemas no conjunto de ferramentas usado pela turma.

1. Abra o Explorador de Arquivos com `Win + E`.
2. Acesse **Este Computador → Disco Local (C:)**.
3. Crie a pasta `dev` e, dentro dela, as pastas `projetos` e `gradle-cache`.
4. Se o Windows impedir a criação ou gravação, peça ao responsável pelo laboratório para criar essas pastas e conceder acesso de escrita à sua conta.

Estrutura esperada:

```text
C:\dev\
├── projetos\
│   └── CaixaDeSugestoes\
└── gradle-cache\
```

Use nomes de pastas de projetos sem acentos, cedilha ou espaços, como `CaixaDeSugestoes` ou `caixa-de-sugestoes`. Em computadores compartilhados por contas diferentes, o responsável pode separar as pastas por identificadores sem acentos, por exemplo `C:\dev\aluno01\projetos` e `C:\dev\aluno01\gradle-cache`; nesse caso, adapte os caminhos deste tutorial.

Não renomeie manualmente `C:\Users\Cesário` e não altere `USERPROFILE`. Não é necessário mudar o nome da conta.

## 3. Baixe o Oracle JDK 17 em formato MSI

1. Abra a [página oficial de downloads da Oracle](https://www.oracle.com/java/technologies/downloads/#java17).
2. Localize **Java 17 / Java SE Development Kit 17**. A página também oferece outras versões; confirme o número **17**.
3. Selecione **Windows**.
4. Escolha **x64 MSI Installer**, com arquivo semelhante a `jdk-17..._windows-x64_bin.msi`. Os números após `17` mudam conforme as atualizações; use a atualização do Java 17 indicada pelo professor.
5. Leia e aceite os termos apresentados pela Oracle e, se solicitado, entre com uma conta Oracle para concluir o download.

Este roteiro usa Windows **x64 (Intel/AMD)**. Para conferir, abra **Configurações → Sistema → Sobre → Tipo de sistema**. Se aparecer ARM ou sistema de 32 bits, informe o professor antes de escolher o instalador.

É possível baixar o instalador em Downloads; a restrição de pasta se aplica aos projetos e aos caminhos de trabalho. Não escolha JRE, arquivo ZIP ou instalador de outra versão do Java.

Referência: [instalação oficial do JDK 17 no Windows](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-microsoft-windows-platforms.html).

## 4. Instale o JDK 17

1. Feche o IntelliJ e os terminais abertos.
2. Execute o arquivo `.msi` baixado.
3. Autorize a instalação na janela do Windows. No laboratório, pode ser necessária a senha do administrador.
4. Siga o assistente e mantenha a instalação em **Program Files**, fora do perfil do usuário.
5. Conclua a instalação.
6. No Explorador, abra `C:\Program Files\Java` e anote o nome exato da pasta instalada.
7. Dentro dessa pasta, confirme a existência de `bin\java.exe` e `bin\javac.exe`.

Nos próximos exemplos, usaremos `C:\Program Files\Java\jdk-17`. **Se a pasta instalada tiver outro nome, substitua esse caminho pelo caminho real em todas as etapas.** O espaço em `Program Files` é normal; nos comandos, usaremos aspas quando necessário.

## 5. Configure as variáveis de ambiente

### Abra a mesma janela no Windows 10 ou 11

1. Pressione `Win + R`.
2. Digite `sysdm.cpl` e pressione Enter.
3. Abra a aba **Avançado**.
4. Clique em **Variáveis de Ambiente**.

A janela tem duas áreas: variáveis do usuário e variáveis do sistema. Neste roteiro, configure `JAVA_HOME` e `Path` nas **variáveis do sistema**, com ajuda do administrador quando necessário. Isso também evita que um caminho antigo no `Path` do sistema tenha prioridade sobre o Java configurado apenas no usuário.

### Configure `JAVA_HOME`

1. Em **Variáveis do sistema**, clique em **Novo**; se `JAVA_HOME` já existir, clique em **Editar**.
2. Preencha:

```text
Nome:  JAVA_HOME
Valor: C:\Program Files\Java\jdk-17
```

O valor deve apontar para a **raiz do JDK**, sem `\bin` e sem aspas. Se houver também um `JAVA_HOME` nas variáveis do usuário, atualize-o para o mesmo caminho ou remova apenas essa definição duplicada antiga, para evitar conflito.

### Configure `Path`

1. Em **Variáveis do sistema**, selecione `Path` e clique em **Editar**.
2. Clique em **Novo** e adicione `%JAVA_HOME%\bin`.
3. Use **Mover para cima** para colocar essa entrada antes das entradas de outras instalações Java.
4. Revise também o `Path` do usuário: identifique entradas antigas de JDK/JRE 8 ou outras versões.
5. Remova somente entradas Java obsoletas que não sejam necessárias a outros programas, com orientação do responsável pelo laboratório. **Não apague o conteúdo inteiro do `Path`.**

Uma entrada como `C:\Program Files\Common Files\Oracle\Java\javapath` pode apontar para outra instalação. Deixe `%JAVA_HOME%\bin` antes dela e confirme o resultado com `where.exe java`, conforme a próxima seção.

### Configure `GRADLE_USER_HOME`

Mover apenas o projeto não muda o cache padrão do Gradle, que normalmente fica em `%USERPROFILE%\.gradle`, por exemplo `C:\Users\Cesário\.gradle`.

Em **Variáveis do usuário**, crie ou edite:

```text
Nome:  GRADLE_USER_HOME
Valor: C:\dev\gradle-cache
```

Essa pasta deve existir e permitir gravação pela sua conta. O Gradle baixará novamente o que precisar; não é necessário copiar o cache antigo. A [documentação do Gradle](https://docs.gradle.org/current/userguide/directory_layout.html) descreve essa variável e a organização dos caches.

Confirme todas as janelas com **OK**. **Feche completamente o IntelliJ, o Windows Terminal, o PowerShell e o Prompt de Comando e abra-os novamente.** Processos já abertos podem continuar usando as variáveis antigas. Se necessário, encerre a sessão do Windows e entre novamente.

## 6. Confira o Java antes de abrir o projeto

Abra um **novo PowerShell** e execute, um comando por vez:

```powershell
$env:JAVA_HOME
$env:GRADLE_USER_HOME
where.exe java
where.exe javac
java -version
javac -version
& "$env:JAVA_HOME\bin\java.exe" -version
```

O resultado deve atender a estes critérios:

| Verificação | Resultado esperado |
| --- | --- |
| `JAVA_HOME` | Pasta real do JDK 17, sem `\bin` |
| `GRADLE_USER_HOME` | `C:\dev\gradle-cache` |
| Primeiro resultado de `where.exe java` | Executável no `bin` do JDK 17 escolhido |
| Primeiro resultado de `where.exe javac` | Compilador no `bin` do mesmo JDK 17 |
| `java -version` | Versão iniciada por `17` |
| `javac -version` | Versão iniciada por `17` |

Se aparecer `1.8.0_291`, ainda há Java 8 sendo selecionado. Se o comando com o caminho completo mostrar 17, mas `java -version` mostrar outra versão, corrija a ordem do `Path`. **Não avance até que `java` e `javac` indiquem Java 17.**

No Prompt de Comando (`cmd`), a consulta equivalente das variáveis é `echo %JAVA_HOME%` e `echo %GRADLE_USER_HOME%`. Os exemplos restantes deste tutorial usam PowerShell.

## 7. Instale o IntelliJ IDEA Community ou sua versão gratuita atual

A JetBrains passou a distribuir o IntelliJ IDEA em um instalador unificado a partir da versão 2025.3. Os recursos centrais de Java da antiga **Community Edition continuam gratuitos**. Por isso, o site atual pode exibir apenas “IntelliJ IDEA”, sem um download separado chamado Community. Veja a [explicação oficial](https://www.jetbrains.com/help/idea/intellij-idea-single-distribution.html).

1. Acesse o [download oficial do IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=windows).
2. Escolha a versão **estável para Windows**, compatível com seu computador. Evite versões EAP de teste.
3. Execute o instalador e siga o assistente. Prefira a instalação em `C:\Program Files\JetBrains\...` quando disponível.
4. Se desejar, habilite o atalho na área de trabalho e a opção de abrir pastas como projetos.
5. Abra o IntelliJ e use os recursos gratuitos; não é necessário contratar Ultimate para as atividades Java deste projeto.

Se o laboratório já tiver uma Community Edition compatível com Java 17 e com o Gradle do projeto, ela pode ser usada. Se uma versão antiga não importar o Gradle 9.7.1, atualize a IDE.

O IntelliJ traz seu próprio runtime para executar a IDE. **Esse runtime não define o JDK do projeto.** Ainda será necessário selecionar o Oracle JDK 17 instalado. Referências: [instalação da IDE](https://www.jetbrains.com/help/idea/installation-guide.html) e [configuração de SDKs](https://www.jetbrains.com/help/idea/sdk.html).

## 8. Abra o projeto no local correto

1. No [módulo 01](01-projeto-spring-boot.md), crie seu próprio projeto em `C:\dev\projetos\CaixaDeSugestoes`. Se ainda está preparando as ferramentas, retorne a esta seção depois de gerar o projeto. O clone da main é material de consulta e não contém código executável.
2. Confirme que essa pasta contém `build.gradle`, `settings.gradle`, `gradlew.bat` e a pasta `src`. Se o ZIP criou outra pasta interna, abra a pasta que realmente contém esses arquivos.
3. No IntelliJ, clique em **Open** e selecione essa pasta.
4. Confirme a confiança no projeto se ele veio do material da aula.
5. Se a IDE iniciar uma sincronização com Java incorreto, cancele-a e configure os itens da próxima seção antes de sincronizar novamente.

Para projetos novos, sempre altere **Location** para uma pasta dentro de `C:\dev\projetos`. Não aceite automaticamente `C:\Users\Cesário\IdeaProjects`.

Se o projeto já estiver no perfil do usuário, feche a IDE antes de movê-lo, preserve os arquivos e reabra a cópia no novo local. Não continue usando o atalho para a pasta antiga.

## 9. Selecione Java 17 em todos os pontos do IntelliJ

Os nomes dos menus podem variar um pouco conforme a versão e o idioma. Use a busca das configurações se necessário.

### SDK do projeto e dos módulos

1. Abra **File → Project Structure** (`Ctrl + Alt + Shift + S`).
2. Em **Project → SDK**, selecione o JDK 17.
3. Se ele não aparecer, escolha **Add SDK → JDK** e selecione `C:\Program Files\Java\jdk-17` ou o caminho real da instalação. Não selecione a subpasta `bin` e não use **Download JDK**, que pode salvar outro JDK dentro do perfil com acento.
4. Em **Language level**, selecione **17**, sem recursos Preview, ou **SDK default** quando o SDK selecionado for 17.
5. Em **Modules → Dependencies**, selecione **Project SDK** para os módulos Java. Remova a seleção de Java 8, se houver.
6. Clique em **Apply** e **OK**.

### JVM e cache do Gradle

1. Abra **File → Settings** (`Ctrl + Alt + S`).
2. Acesse **Build, Execution, Deployment → Build Tools → Gradle**.
3. Em **Distribution / Use Gradle from**, selecione **Wrapper / gradle-wrapper.properties**.
4. Em **Gradle JVM**, selecione explicitamente o **JDK 17** instalado.
5. Em **Gradle user home**, confirme ou informe `C:\dev\gradle-cache`.
6. Em **Build and run using** e **Run tests using**, selecione **Gradle**.
7. Aplique as alterações e clique em **Reload All Gradle Projects** na janela Gradle.

A primeira sincronização precisa de internet para baixar a distribuição e as dependências. Referência: [configurações do Gradle no IntelliJ](https://www.jetbrains.com/help/idea/gradle-settings.html).

### Java usado para executar a aplicação

Em **Run → Edit Configurations**, quando houver uma configuração Java/Application, confira o campo **JRE / Java runtime** e selecione **Project SDK (17)** ou o JDK 17. Se o campo estiver oculto, procure-o em **Modify options**. Configurações de execução via Gradle usam a JVM configurada para o Gradle.

No `build.gradle` que você criará no módulo 01, configure:

```groovy
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
```

Mantenha essa configuração. A toolchain define o Java usado nas tarefas Java; a **Gradle JVM** define o Java que executa o Gradle. Para esta aula, ambos devem usar **17**.

## 10. Valide o ambiente com o Wrapper

Depois de criar o projeto no módulo 01, abra um novo terminal PowerShell, inclusive dentro do IntelliJ, e execute:

```powershell
Set-Location C:\dev\projetos\CaixaDeSugestoes
java -version
javac -version
Write-Output $env:GRADLE_USER_HOME
.\gradlew.bat --stop
.\gradlew.bat --version
.\gradlew.bat javaToolchains
.\gradlew.bat compileJava
```

Confira:

- O Java e o compilador mostram versão **17**.
- O cache aponta para `C:\dev\gradle-cache`.
- O Wrapper mostra **Gradle 9.7.1**, conforme o Wrapper do projeto criado no módulo 01.
- Os campos de JVM do Wrapper apontam para Java 17; a lista de toolchains inclui o JDK 17 instalado em Program Files.
- A compilação termina com **BUILD SUCCESSFUL**.

Use `gradlew.bat`, não um comando `gradle` instalado separadamente. No PowerShell, o prefixo `.\` indica que o arquivo está na pasta atual. A compilação valida o ambiente de desenvolvimento sem exigir que a API e o banco já estejam configurados.

## 11. Resolva os problemas mais comuns

| Sintoma | O que conferir |
| --- | --- |
| `java` não é reconhecido | Confira `%JAVA_HOME%\bin` no `Path` e abra um terminal novo. |
| `javac` não é reconhecido | Confirme que instalou o JDK e que existe `bin\javac.exe`. |
| `java -version` ainda mostra Java 8 | Execute `where.exe java`; ajuste a prioridade das entradas Java no `Path`. |
| `JAVA_HOME is set to an invalid directory` | Use a pasta real do JDK, sem aspas e sem `\bin`. |
| Gradle exige JVM 17 ou superior | Confira `JAVA_HOME` no terminal e **Gradle JVM** na IDE. |
| `UnsupportedClassVersionError` | Compare o Java da execução com o da compilação. Java 17 usa versão de classe 61; Java 8 usa 52. |
| `invalid source release: 17` | O compilador selecionado é antigo; revise SDK, Gradle JVM e toolchain. |
| Não foi encontrada uma instalação Java 17 | Adicione o JDK instalado à IDE e confira a saída de `javaToolchains`. |
| Terminal funciona, mas IntelliJ falha | Reinicie a IDE e confira Project SDK, Module SDK, Gradle JVM e runtime da execução. |
| Erro de caminho menciona `Cesário` ou `OneDrive` | Confira o local do projeto e o Gradle user home. Mova o projeto para `C:\dev\projetos` e use o cache configurado. |
| Gradle continua selecionando outro Java | Procure `org.gradle.java.home` nos arquivos `gradle.properties` do projeto e do Gradle user home; uma definição antiga pode sobrepor a escolha esperada. Revise também `gradle/gradle-daemon-jvm.properties`, se existir. |
| Download do Gradle ou de dependências falha | Confira internet, proxy e acesso à rede do laboratório. Desative o modo offline do Gradle se estiver habilitado. |
| Acesso negado em `C:\dev` | Peça permissão de escrita ao responsável; não use a IDE permanentemente como administrador. |

Se um erro continuar citando a pasta do usuário mesmo após esses ajustes, registre a mensagem completa: arquivos temporários e dados internos da IDE ainda podem usar o perfil. O professor deve identificar o caminho específico antes de alterar outras configurações. Não apague a pasta do usuário nem todos os caches como tentativa inicial.

## 12. Checklist antes da próxima aula

- [ ] Instalei o Oracle **JDK 17 x64** pelo instalador MSI.
- [ ] `JAVA_HOME` aponta para a pasta correta do JDK 17.
- [ ] `java -version` e `javac -version` mostram **17** em um terminal novo.
- [ ] Os primeiros resultados de `where.exe java` e `where.exe javac` são do JDK 17 escolhido.
- [ ] O projeto está em `C:\dev\projetos\CaixaDeSugestoes`, fora do perfil `Cesário` e do OneDrive.
- [ ] `GRADLE_USER_HOME` e o Gradle user home da IDE apontam para `C:\dev\gradle-cache`.
- [ ] Instalei o IntelliJ Community ou o IntelliJ atual com os recursos gratuitos.
- [ ] Project SDK, Module SDK, Gradle JVM e runtime da execução usam Java 17.
- [ ] O IntelliJ usa o **Gradle Wrapper** do projeto.
- [ ] `.\gradlew.bat compileJava` terminou com **BUILD SUCCESSFUL**.

Com esse checklist concluído, o ambiente está preparado para a próxima etapa: construção da API de Caixa de Sugestões.
