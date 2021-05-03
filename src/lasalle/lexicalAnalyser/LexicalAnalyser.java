package lasalle.lexicalAnalyser;

import lasalle.tables.KeywordTable;
import lasalle.tables.OperatorTable;
import lasalle.tables.IdentifierTable;
import lasalle.tables.IntegerTable;
import lasalle.tables.CharTable;
import lasalle.tables.FloatTable;
import lasalle.tables.BooleanTable;

import java.util.ArrayList;

public class LexicalAnalyser {

    public static ArrayList<ArrayList<String>> readTheFile(ArrayList<ArrayList<String>> linesOfCode ){

        for ( ArrayList<String> line : linesOfCode) {
            for (int i = 0; i < line.size(); i++) {
                String identifier = identifyToken(line.get(i));
                line.set(i, identifier);
            }
        }
        return linesOfCode;
    }

    private static String identifyToken(String word){
        String token = null;

        if ((  token = KeywordTable.identifyKeyword(word)) != null){
            return token;
        }

        if ((  token = OperatorTable.identifyOperator(word)) != null){
            return token;
        }

        if (word.matches(CharTable.regex)){
            String caracter = CharTable.addNewChar(word);
            return caracter;
        }

        if (word.matches(FloatTable.regex) ){
            String floatt = FloatTable.addNewFloat(word);
            return floatt;
        }

        if (word.matches(IntegerTable.regex) ){
            String integer = IntegerTable.addNewInteger(word);
            return integer;
        }

        if (word.matches(BooleanTable.regex) ){
            String bool = BooleanTable.addNewBoolean(word);
            return bool;
        }

        String identifier = IdentifierTable.addNewIdentifier(word);
        return identifier;
    }
}
