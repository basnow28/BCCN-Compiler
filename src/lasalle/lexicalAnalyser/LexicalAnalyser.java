package lasalle.lexicalAnalyser;

import lasalle.tables.KeywordTable;
import lasalle.tables.OperatorTable;
import lasalle.tables.ValuesTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LexicalAnalyser {

    public static ArrayList<ArrayList<String>> readTheFile(ArrayList<ArrayList<String>> linesOfCode ){

        for ( ArrayList<String> line : linesOfCode) {
            for (int i = 1; i < line.size(); i++) {
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
        String identifier = ValuesTable.addNewValue(word);
        return identifier;
    }
}
