# Instruments and instructions

Code written on Java. For building and testing project used Maven.

Github link: https://github.com/ilshatus/cc_assignment2

### Maven dependencies
- ```junit``` for unit tests
### Maven plugins
- ```maven-compiler-plugin``` for compilation
- ```org.codehaus.mojo.exec-maven-plugin``` for execution
- ```maven-surefire-plugin``` for testing
### Instruction
- install maven
- unzip archive with project
- go to directory where you unzipped project in console
- ```mvn clean compile exec:java``` run this command to build and execute (input will be read from in.txt, result will be printed in out.txt)
- ```mvn test``` run this command to run unit tests (result of testing will be shown on console)
### Instruction (alternative) 
- open ```pom.xml``` as project in Intellij Idea
- run ```src/main/java/Main``` to build and run
- run ```src/test/java/UnitTests``` to run test



### Tokens

- Delimetr token '''DelimiterToken.java''' token which accepts all delimetrs ";" "." "," "\n" 

- Keyword token '''KeywordToken.java''' token which accepts all special words in language "abstract", "do", "finally", "import", "null", "protected", "throw", "val", "case", "else", "for", "lazy", "object", "return", "trait", "var", "catch",  "extends", "forSome", "macro", "override", "sealed", "try", "while", "class", "if" and etc.
     
- MultilineComment token '''MultilineCommentToken.java''' token which accepts multiline comments in format /* text */

- ParenthesesToken token '''ParenthesesToken.java''' token which accepts parenthesess "(" ")" "\[" "\]" "{" "}"


