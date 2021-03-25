package lasalle.tables;

import java.util.ArrayList;
import java.util.Arrays;

public class KeywordTable {

    private static final ArrayList<String> keywords = new ArrayList<>(Arrays.asList(
            "start",
            "end",
            "program",
            "function",
            "if",
            "ifelse",
            "else",
            "return",
            ";",
            "while",
            "[","]",
            "{", "}",
            "(", ")",
            ":", "=",
            "int", "float", "boolean", "char" ));

    public static String identifyKeyword(String token){
        for ( String keyword: keywords  ) {
            if (token.equals(keyword)) {
                return keyword;
            }
        }
        return null;
    }

}
