package lasalle.syntaxAnalyzer;

import java.util.*;

public class ParsingTable {
    private LinkedHashMap<MapKey, String> parsingTable;

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

        parsingTable.put(new MapKey("program", "<program>"), " program identifier ; <main> ");
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
        parsingTable.put(new MapKey( "boolean","<BooleanDeclaration>" ), " boolean identifier = boolValue ; ");

    }

    public void populateParsingTable(String terminal, String nonTerminal, String value){
        MapKey mapKey = new MapKey(terminal, nonTerminal);

        parsingTable.put(mapKey, value);
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
}