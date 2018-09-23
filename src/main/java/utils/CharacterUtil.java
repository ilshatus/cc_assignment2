package utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CharacterUtil {
    private enum UnicodeLetterCategory {
        Lu(1),
        Ll(2),
        Lt(3),
        Lo(5),
        Nl(10);

        int typeCode;

        UnicodeLetterCategory(int typeCode) {
            this.typeCode = typeCode;
        }

        int getTypeCode() {
            return typeCode;
        }
    }

    private enum UnicodeSymbolCategory {
        Sm(25),
        So(28);

        int typeCode;

        UnicodeSymbolCategory(int typeCode) {
            this.typeCode = typeCode;
        }

        int getTypeCode() {
            return typeCode;
        }
    }

    private static final Character[] parentheses = {'(', ')', '[', ']', '{', '}'};
    private static final HashSet<Character> parenthesesSet = new HashSet<>(Arrays.asList(parentheses));
    private static final Character[] delimiters = {'`', '\'', '\"', '.', ';', ','};
    private static final HashSet<Character> delimitersSet = new HashSet<>(Arrays.asList(delimiters));
    private static final Character[] whiteSpaces = { '\u0020', '\u0009' };
    private static final List<Character> whiteSpacesList = Arrays.asList(whiteSpaces);

    public static boolean isLetter(char ch) {
        int code = Character.getType(ch);
        UnicodeLetterCategory[] categories = UnicodeLetterCategory.values();
        for (UnicodeLetterCategory category : categories) {
            if (category.getTypeCode() == code) {
                return true;
            }
        }
        return ch == '_' || ch == '$';
    }

    public static boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }

    public static boolean isParentheses(char ch) {
        return parenthesesSet.contains(ch);
    }

    public static boolean isDelimiterChar(char ch) {
        return delimitersSet.contains(ch);
    }

    public static boolean isOperatorChar(char ch) {
        int code = Character.getType(ch);
        UnicodeSymbolCategory[] categories = UnicodeSymbolCategory.values();
        for (UnicodeSymbolCategory category : categories) {
            if (category.getTypeCode() == code) {
                return true;
            }
        }
        if (ch > '\u0020' && ch <= '\u007e' &&
                !isLetter(ch) && !isDigit(ch) &&
                !isParentheses(ch) && !isDelimiterChar(ch)) {
            return true;
        }
        return false;
    }

    public static boolean isWhiteSpace(char ch) {
        return whiteSpacesList.contains(ch);
    }
}
