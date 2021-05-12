package lasalle.syntaxAnalyzer;

import java.util.*;

public class ParsingTable {
    private static Map<MapKey, String> parsingTable;
    //This Map is used as a temporary storage when creating new rules when the terminal is empty and there is an existing grammar rule
    private static Map<MapKey, String> tempTableEmptyRules;
    public static Set<String> nonTerminalsList = new LinkedHashSet<>();
    public static Map<MapKey, String> temporaryParsingTable = new LinkedHashMap<>();

    public ParsingTable(){
        //populate hardcoded parsing table for main function
        /*
        * start
        * {
	    * int n = 5;
        * }end
        * */

        //<identifier>, intValue, floatValue, booleanValue can be found in their respectives tables

        parsingTable = new LinkedHashMap<MapKey, String>();
        tempTableEmptyRules = new LinkedHashMap<>();

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

        replaceEmptyTerminalWithANonEmpty();
        //removeEmptyTerminalAndGrammarEntries();

        //putAllTempEntries(temporaryParsingTable);

        //replaceEmptyTerminalWhenThereIsAGrammarRule();
        //putAllTempEntries(tempTableEmptyRules);
        //removeEmptyTerminalEntries();

        /*System.out.println("Temporary Parsing Table");
        for(Map.Entry<MapKey, String> tempEntry : temporaryParsingTable.entrySet()){
            System.out.print("Non Terminal: " + tempEntry.getKey().getNonTerminal());
            System.out.print(" Terminal: " + tempEntry.getKey().getTerminal());
            System.out.println(" Value: " + tempEntry.getValue());
        }

        System.out.println("Temp Parsing Table");
        for(Map.Entry<MapKey, String> tempEntry : tempTableEmptyRules.entrySet()){
            System.out.print("Non Terminal: " + tempEntry.getKey().getNonTerminal());
            System.out.print(" Terminal: " + tempEntry.getKey().getTerminal());
            System.out.println(" Value: " + tempEntry.getValue());
        }*/

        //temporaryParsingTable.clear();
        //tempTableEmptyRules.clear();

        creatingMissingParsingRules();
    }
        /**
        * loop in array of nonterminal
        -> method loooking for this first
        ->push temporary table inside hasmap
        ->clear temporary table
        end l
        *
        * */
    private void creatingMissingParsingRules(){
        for(String nonTerminal : nonTerminalsList){
            createParsingRule(nonTerminal);
            putAllTempEntries(temporaryParsingTable);
            temporaryParsingTable.clear();
        }
    }

        /**
         method
         -> check if first elements in rules is a non terminal or not
         ->passing throught the parsing table to find the first of the non terminal passed
         -> register in temporary table if new entries
         **/
    private void createParsingRule(String nonTerminal){
        for(Map.Entry<MapKey, String> parsingTableEntrySet: parsingTable.entrySet()){
            if(parsingTableEntrySet.getKey().getNonTerminal().equals(nonTerminal)){
                addRuleInTemporaryTable(nonTerminal, parsingTableEntrySet.getValue());
            }
        }
    }

    private void addRuleInTemporaryTable(String initialNonTerminal, String grammarRule){
        Scanner grammarScanner = new Scanner(grammarRule);
        if(grammarScanner.hasNext()){
            String token = grammarScanner.next();
            for(Map.Entry<MapKey, String> tempEntry : parsingTable.entrySet()){
                if(tempEntry.getKey().getNonTerminal().equals(token)){
                    temporaryParsingTable.put(new MapKey(tempEntry.getKey().getTerminal(), initialNonTerminal), grammarRule);
                }
            }
        }
    }

    private void replaceEmptyTerminalWhenThereIsAGrammarRule() {
        for(Map.Entry<MapKey, String> parsingTableEntry : parsingTable.entrySet()) {
            if (parsingTableEntry.getKey().getTerminal().equals("\"\"") && !parsingTableEntry.getValue().equals("\"\" ")) {
                //If the terminal is empty, search for occurence of the corresponding nonTerminal in the grammar rules
                replaceEmptyTerminalWithTemporaryParsingTableEntry(
                        parsingTableEntry.getKey().getNonTerminal(),
                        parsingTableEntry.getValue());
            }
        }
    }

    private void replaceEmptyTerminalWithTemporaryParsingTableEntry(String initialNonTerminal, String grammarRule){
        Scanner grammarScanner = new Scanner(grammarRule);
        if(grammarScanner.hasNext()){
            String token = grammarScanner.next();
            for(Map.Entry<MapKey, String> tempEntry : temporaryParsingTable.entrySet()){
                if(tempEntry.getKey().getNonTerminal().equals(token)){
                    tempTableEmptyRules.put(new MapKey(tempEntry.getKey().getTerminal(), initialNonTerminal),grammarRule);
                }
            }
        }
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
            if(parsingTableEntry.getKey().getTerminal().equals("\"\"") && parsingTableEntry.getValue().equals("\"\" ")){
                //If the terminal is empty, search for occurence of the corresponding nonTerminal in the grammar rules
                replaceEmptyTerminalEntry(parsingTableEntry.getKey().getNonTerminal(), parsingTableEntry.getKey().getNonTerminal()) ;
            }
        }
    }

    private void replaceEmptyTerminalEntry(String initialNonTerminal, String upperLevelNonTerminal) {

        for (Map.Entry<MapKey, String> grammarEntry : parsingTable.entrySet()) {
            //First Case

            if (grammarEntry.getValue().contains(upperLevelNonTerminal)) {

                if (grammarEntry.getValue().endsWith(upperLevelNonTerminal+" ")) {
                    /*System.out.println("PASSED");
                    System.out.println(initialNonTerminal);
                    System.out.println(upperLevelNonTerminal);
                    System.out.println(grammarEntry.getValue());
                    System.out.println(grammarEntry.getKey().getNonTerminal());*/
                    //needed for <declaration> <declarations> which could be infinite
                    if (!upperLevelNonTerminal.equals(grammarEntry.getKey().getNonTerminal()) ) {
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
                                temporaryParsingTable.put(new MapKey(nextToken, initialNonTerminal), "");
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
                                                temporaryParsingTable.put(new MapKey(firstEntry.getKey(), initialNonTerminal), "");
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

    private void removeEmptyTerminalAndGrammarEntries(){
        ArrayList<MapKey> entryKeys = new ArrayList<>();
        for(Map.Entry<MapKey, String> parsingTableEntry : parsingTable.entrySet()) {
            if(parsingTableEntry.getKey().getTerminal().equals("\"\"") && parsingTableEntry.getValue().equals("\"\" ")){
                //If the terminal is empty, search for occurence of the corresponding nonTerminal in the grammar rule
                entryKeys.add(parsingTableEntry.getKey());
            }
        }

        for(MapKey mapKey : entryKeys) {
            parsingTable.remove(mapKey);
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

            if(entry.getKey().getNonTerminal().equals(stackToken) && SyntaxAnalyzer.compare(entry.getKey().getTerminal(), inputToken)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private void putAllTempEntries(Map<MapKey, String> tableToCopy){
        for(Map.Entry<MapKey, String> tempEntry : tableToCopy.entrySet()){
            if(!parsingTable.containsKey(tempEntry.getKey())){
                parsingTable.put(tempEntry.getKey(), tempEntry.getValue());
            }else{
                if(parsingTable.get(tempEntry.getKey()).equals("")){
                    parsingTable.put(tempEntry.getKey(), tempEntry.getValue());
                }
            }
        }
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