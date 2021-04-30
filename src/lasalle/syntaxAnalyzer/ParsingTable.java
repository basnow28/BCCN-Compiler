package lasalle.syntaxAnalyzer;

import java.util.*;

public class ParsingTable {
    private static Map<MapKey, String> parsingTable;

    public ParsingTable(){
        //populate hardcoded parsing table for main function
        /*
        * start
        * {
	    * int n = 5;
        * }end
        * */

        //<identifier>, intValue, floatValue, booleanValue can be found in their respectives tables

        parsingTable = new HashMap<MapKey, String>();

       /* parsingTable.put(new MapKey("program", "<program>"), " program identifier ; <main> ");
        parsingTable.put(new MapKey("start", "<main>"), " start { <declarations> } end ");
        parsingTable.put(new MapKey("int", "<declarations>"), " <declaration> <declarations> ");
        parsingTable.put(new MapKey("float", "<declarations>"), " <declaration> <declarations> ");
        parsingTable.put(new MapKey("char", "<declarations>" ), " <declaration> <declarations> ");
        parsingTable.put(new MapKey("boolean", "<declarations>"), " <declaration> <declarations> ");
        parsingTable.put(new MapKey("}", "<declarations>"), "");
                parsingTable.put(new MapKey("int", "<declaration>"), " <IntDeclaration> ");
        parsingTable.put(new MapKey("float", "<declaration>"), " <FloatDeclaration> ");
        parsingTable.put(new MapKey("char", "<declaration>"), " <CharDeclaration> ");
        parsingTable.put(new MapKey("boolean", "<declaration>"), " <BooleanDeclaration> ");
        parsingTable.put(new MapKey( "int","<IntDeclaration>" ), " int identifier = intValue ; ");
        parsingTable.put(new MapKey( "float","<FloatDeclaration>" ), " float identifier = floatValue ; ");
        parsingTable.put(new MapKey( "char","<CharDeclaration>" ), " char identifier = charValue ; ");
        parsingTable.put(new MapKey( "boolean","<BooleanDeclaration>" ), " boolean identifier = boolValue ; ");*/

    }

    public void populateParsingTable(Map<String, FirstAndFollowMaps> firstAndFollow){

        for(Map.Entry<String, FirstAndFollowMaps> nonTerminalEntry : firstAndFollow.entrySet()){
            for(Map.Entry<String, String> terminalEntry : nonTerminalEntry.getValue().getFirstMap().entrySet()){
                parsingTable.put(new MapKey(terminalEntry.getKey(), nonTerminalEntry.getKey()), terminalEntry.getValue());
            }
        }

        System.out.println(parsingTable);

        replaceEmptyTerminalWithANonEmpty();
        removeEmptyTerminalEntries();

        parsingTable.putAll(TemporaryParsingTableOfEmptyRules.temporaryParsingTable);
        TemporaryParsingTableOfEmptyRules.temporaryParsingTable.clear();
    }

    private void replaceEmptyTerminalWithANonEmpty(){
        //Check occurence of every empty terminal in the existing grammar rules
        //For each empty terminal, do as follows:
        /*
           For each case verify a rule empty with this first and non terminal don't exist already

            First case -> nonTerminal is in middle of the grammar
                -> take the first of the following element
                        -> nonTerminal (look on the first rules existing for it and take the first)
                        -> terminal create a rule with it


           Second case -> nonTerminal is in the end of the grammar
                        -> nonTerminal (look on the first rules existing for it and take the first)
                        -> terminal create a rule with it
         */

        for(Map.Entry<MapKey, String> parsingTableEntry : parsingTable.entrySet()) {
            if(parsingTableEntry.getKey().getTerminal().equals("\"\"")){
                //If the terminal is empty, search for occurence of the corresponding nonTerminal in the grammar rules
                replaceEmptyTerminalEntry(parsingTableEntry.getKey().getNonTerminal(), parsingTableEntry.getKey().getNonTerminal());
            }
        }
    }

    private void replaceEmptyTerminalEntry(String initialNonTerminal, String upperLevelNonTerminal) {

        for (Map.Entry<MapKey, String> grammarEntry : parsingTable.entrySet()) {
            //First Case
            if (grammarEntry.getValue().contains(upperLevelNonTerminal)) {

                if (grammarEntry.getValue().endsWith(upperLevelNonTerminal+" ")) {
                    //needed for <declaration> <declarations> which could be infinite
                    if (!initialNonTerminal.equals(grammarEntry.getKey().getNonTerminal())) {
                        replaceEmptyTerminalEntry(initialNonTerminal, grammarEntry.getKey().getNonTerminal());
                    }
                } else {
                    ///Find the following element
                    //Loop through all the elements in MapKey that has the upperLevelNonTerminal key
                    //create the rule taking the terminal
                    //Create a Scanner

                    Scanner grammarScanner = new Scanner(grammarEntry.getValue());
                    boolean isNonTerminalEmpty = true;
                    boolean isScannerAfterUpperLevelNonTerminal = false;
                    boolean isUpperLevelNonTerminal = false;

                    while (grammarScanner.hasNext() && isNonTerminalEmpty) {
                        String nextToken = grammarScanner.next();
                        isUpperLevelNonTerminal = false;

                        if (nextToken.equals(upperLevelNonTerminal)) {
                            isScannerAfterUpperLevelNonTerminal = true;
                            isUpperLevelNonTerminal = true;
                        }

                        if (isScannerAfterUpperLevelNonTerminal && !isUpperLevelNonTerminal) {
                            if (!FirstAndFollow.isTokenANonTerminal(nextToken)) {
                                TemporaryParsingTableOfEmptyRules.temporaryParsingTable.put(new MapKey(nextToken, initialNonTerminal), "");
                                isNonTerminalEmpty = false;
                            } else {
                                //If the next token is a nonTerminal
                                //Check the first rules for that nonTerminal
                                //For each first rule of that nonTerminal, create a new rule

                                for (Map.Entry<String, FirstAndFollowMaps> nonTerminalEntry : FirstAndFollow.firstAndFollow.entrySet()) {
                                    if (nonTerminalEntry.getKey().equals(nextToken)) {
                                        boolean isAnyOfTheFirstRulesEmpty = false;
                                        for (Map.Entry<String, String> firstEntry : nonTerminalEntry.getValue().getFirstMap().entrySet()) {
                                            if (firstEntry.getKey().equals("\"\"")) {
                                                isAnyOfTheFirstRulesEmpty = true;
                                            }else {
                                                TemporaryParsingTableOfEmptyRules.temporaryParsingTable.put(new MapKey(firstEntry.getKey(), initialNonTerminal), "");
                                            }
                                        }
                                        isNonTerminalEmpty = isAnyOfTheFirstRulesEmpty;
                                    }
                                }
                                //parsingTable.put(new MapKey(grammarEntry.getKey().getTerminal(), initialNonT

                                //parsingTable.put(new MapKey(grammarEntry.getKey().getTerminal(), initialNonTerminal), "");

                                //<elemnt><declarations><lol><loooll>
                                //element-> could be empty
                                //would be looking for the nonterminal of <declarations>
                                //  ->pass throught the map key-> for every mapkey where the non terminal = <declaration>
                                //                              -> taking the terminal
                                //                              ->creating a new rule with this terminal
                            }
                        }
                    }

                    if(isNonTerminalEmpty){
                        replaceEmptyTerminalEntry(initialNonTerminal, grammarEntry.getKey().getNonTerminal());
                    }
                }
            }
        }
    }

    private void removeEmptyTerminalEntries(){
        ArrayList<MapKey> entryKeys = new ArrayList<>();
        for(Map.Entry<MapKey, String> parsingTableEntry : parsingTable.entrySet()) {
            if(parsingTableEntry.getKey().getTerminal().equals("\"\"")){
                //If the terminal is empty, search for occurence of the corresponding nonTerminal in the grammar rule
                entryKeys.add(parsingTableEntry.getKey());
            }
        }

        for(MapKey mapKey : entryKeys) {
            parsingTable.remove(mapKey);
        }
    }


    public String getValue(String stackToken, String inputToken) {
        for (Map.Entry<MapKey, String> entry : parsingTable.entrySet()) {


            //to check if a token has don't have an invisible caracter
            /*final String SEPARATEUR = "";
                String conte = inputToken;

                String mots[] = conte.split(SEPARATEUR);

                for (int i = 0; i < mots.length; i++) {
                    System.out.println(mots[i] + i);
                }
            */

            /*System.out.println(entry.getKey().getNonTerminal() + " : " + stackToken);
            System.out.println(entry.getKey().getNonTerminal().equals(stackToken));
            System.out.println(entry.getKey().getTerminal() + " : " + inputToken);
            System.out.println(entry.getKey().getTerminal().equals(inputToken));
            System.out.println(inputToken.trim().length());
            System.out.println(entry.getKey().getTerminal().length());
            System.out.println(inputToken.compareTo(entry.getKey().getTerminal()));*/

            if(entry.getKey().getNonTerminal().equals(stackToken) && entry.getKey().getTerminal().equals(inputToken)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        for (Map.Entry<MapKey, String> entry : parsingTable.entrySet()) {
            stb.append(entry.getKey()).append("; grammarRule: ").append(entry.getValue()).append("\n");
        }
        return stb.toString();
    }
}