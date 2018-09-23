import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                TestBackQuoteIdentifierToken.class,
                TestDelimeterToken.class,
                TestKeywordToken.class,
                TestLexicalAnalyzer.class,
                TestLiteralTokens.class,
                TestParenthesesToken.class,
                TestPlainIdentifierToken.class,
                TestXmlToken.class
        }
)
public class UnitTests {

}
