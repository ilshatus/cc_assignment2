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


# CODE STRUCTURE
All source files stored in src folder.
### Main 
Main file "Main.java" runs the project. It reads the file with inputs from "in.txt" and call LexicalAnalyzers to proccess it. It also prints the output to file "out.txt". Both files "in.txt" and "out.txt" stored in the main folder of the project.

### Lexical Analyzer

It gets the whole program code as String input. And the returning tokens by calling function "getNextToken()". In each moment it producing only one next token.  

## Tokens

Each token has output forman to string as "T_nameOfToken(string_value)" 

- Token token '''Token.java''' parent class of all tokens

- PlainIdentifierToken token '''PlainIdentifierToken.java''' token which accepts all names of variables, functions, classes and etc.
Output format "T_identifier(%s)"

- Delimetr token '''DelimiterToken.java''' token which accepts all delimetrs ";" "." "," "\n" 
Output format "T_delimiter(%s)"

- Keyword token '''KeywordToken.java''' token which accepts all special words in language "abstract", "do", "finally", "import", "null", "protected", "throw", "val", "case", "else", "for", "lazy", "object", "return", "trait", "var", "catch",  "extends", "forSome", "macro", "override", "sealed", "try", "while", "class", "if" and etc. 
Output format "T_keyword(%s)"
     
- Multiline Comment token '''MultilineCommentToken.java''' token which accepts multiline comments in format /* text */
Output format "T_comment(%s)" 

- Simple Comment token '''SimpleCommentToken.java''' token which accepts single line comment in format // text 
Output format "T_comment(%s)"

- ParenthesesToken token '''ParenthesesToken.java''' token which accepts parenthesess "(" ")" "\[" "\]" "{" "}" 
Output format "T_parentheses(%s)" 

- End of file  token '''EndOfFileToken.java''' token which returing token of ending the file. It has only one function.
Output format "T_end_of_file()" 

- Error  token '''ErrorToken.java''' token which returing token of error. It stores in the text reason(if it can say), position and first letters. 
Output format "T_error(message=\"%s\", line=%d)"

- Back Quote Identifier  token '''BackQuoteIdentifierToken.java''' token which accepts string in quote `string`
Output format "T_identifier(%s)"

- XML  token '''XmlToken.java''' token which accepts XML text. 
Output format "T_xml(%s)"

### Literals tokens

- Character Literal Token '''CharacterLiteralToken.java''' token which accepts all version of characters 'a', '\u1357'
Output format "T_literal_character(%s)"
- Logical Literal Token '''LogicalLiteralToken.java''' token which accepts logical values "true" "false"
Output format "T_literal_logical(%s)"
- Multiline String Literal Token '''MultilineStringLiteralToken.java''' token which accepts multilines strings """asdasa
asdasda""" 
Output format  "T_literal_multiline_string(%s)"
- Numeric Literal Token '''NumericLiteralToken.java''' token which accepts numeric values like "135", "-5.8" and etc
Output format "T_literal_%s(%s)"

- String Literal Token '''StringLiteralToken.java''' token which accepts string values like "asdasd"
Output format "T_literal_string(%s)"

- Symbol Literal Token '''SymbolLiteralToken.java''' token which accepts string const values like 'asdasd 
Output format "T_literal_symbol(%s)"





