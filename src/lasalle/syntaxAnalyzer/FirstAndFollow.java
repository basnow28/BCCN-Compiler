package lasalle.syntaxAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FirstAndFollow {
    public static Map<String, FirstAndFollowMaps> firstAndFollow = new HashMap<>();
    // where the key is a corresponding NonTerminal of the first and follow rules
    private static String grammarFile = "grammar.txt";

    public static void populateFirstAndFollowFromGrammarFile() throws Exception {
            BufferedReader bufferedReader= new BufferedReader(new FileReader(grammarFile));
            String line = "";
            Scanner scanner= null;

            while((line = bufferedReader.readLine()) != null){
                scanner = new Scanner(line);

                String nonTerminal = "";
                String first = "";
                String follow = "";
                String grammarRule = "";

                int word_id = 1;
                while(scanner.hasNext()){
                    if(word_id == 1){
                        nonTerminal = scanner.next();
                        if(!isTokenANonTerminal(nonTerminal)){
                            throw new Exception("All the grammar rules need to start with a non terminal");
                        }
                    }
                    else if(word_id > 2){
                        String token = scanner.next();
                        grammarRule += token;
                        grammarRule += (" ");
                    }else if(word_id == 2){
                        scanner.next();
                    }
                    word_id ++;
                }
                // Check if the nonTerminal already has it's first and follow
                // If so, add first and follow rules to the map under the nonTerminal key
                // If the non terminal doesn't exist put a new entry in the Map
                addFirst(nonTerminal, grammarRule);
            }
    }

    private static void populateFirstRules(Map.Entry<String, FirstAndFollowMaps> entry, String grammarRule) throws Exception {
        Scanner scanner = new Scanner(grammarRule);
        String token = scanner.next();

        if(isTokenANonTerminal(token)){
            boolean doesNonTerminalExist = false;
            //return the first of this non terminal
            //read the first Map of the non terminal

            for (Map.Entry<String, FirstAndFollowMaps> nonTerminalEntry : firstAndFollow.entrySet()) {
                if(nonTerminalEntry.getKey().equals(token)){

                    doesNonTerminalExist = true;
                    //Populate a new entry with the nonTerminal existing firsts and the grammar
                    //Copying all the first of the nonTerminal and creating a new rules with it and the grammar
                    for(Map.Entry<String, String> terminalEntry : nonTerminalEntry.getValue().getFirstMap().entrySet()){
                        String token2 = terminalEntry.getKey();
                        entry.getValue().addFirstRule(token2, grammarRule);
                        //System.out.println(entry.getValue().addFirstRule(token2, grammarRule));
                    }
                }
            }
            if(!doesNonTerminalExist){
                throw new Exception("The non terminal doesn't have an entry for the grammar rule: " + grammarRule + "\n Non Terminal: " + token);
            }
        }else {
            //if the first token is a terminal, create a new first rule
            entry.getValue().addFirstRule(token, grammarRule);
        }
    }

    private static boolean isTokenANonTerminal(String token){
        return token.startsWith("<") && token.endsWith(">");
    }

    private static void addFirst(String nonTerminal, String grammarRule){
        //Check if there is a nonTerminal entry in the first and follow
        //If so, add first to the corresponding first map

        if(!firstAndFollow.containsKey(nonTerminal)){
            //The nonTerminal doesn't have an entry in the first and follow
            firstAndFollow.put(nonTerminal, new FirstAndFollowMaps());
        }

        for (Map.Entry<String, FirstAndFollowMaps> entry : firstAndFollow.entrySet()){
            if(entry.getKey().equals(nonTerminal)){
                //Add new first and follow rules to the existing entry based on the grammar rules

                try {
                    populateFirstRules(entry, grammarRule);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
