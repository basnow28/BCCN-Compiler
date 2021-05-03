package lasalle.syntaxAnalyzer;

import lasalle.lexicalAnalyser.LexicalArray;
import lasalle.trees.ParsingTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SyntaxAnalyzer {
    private ParsingStack parsingStack;

    public SyntaxAnalyzer(){
        parsingStack = new ParsingStack();
    }

    public void validateCode(LexicalArray lexicalArray, ParsingTable parsingTable) throws Exception{
        //Use the ParsingStack to analyze the code
        int line = 0;
        int token_id = 0;
        int count=0;
        while(line < lexicalArray.getLexicalArray().size()){
            ArrayList<String> tokensArray = lexicalArray.getLexicalArray().get(line);
            while( token_id < tokensArray.size()){
                System.out.println(tokensArray.get(token_id));
                String stackToken = parsingStack.getParsingStack().peek();
                String inputToken =  tokensArray.get(token_id);

                // Deleting an extra character from the first value in the tokenArray
                // Because of JAVA adding an extra character when reading the file
                // Problem with 'program' token
                /*if (count==0 || count==1){
                    inputToken= inputToken.substring(1);
                    count++;
                }*/

                if(this.compare(stackToken, inputToken)){
                    System.out.println("Matches");
                    //If the compare method returns true, remove the values from the stack and the input
                    parsingStack.getParsingStack().pop();
                    ParsingTree.updateTreeNodeValueWhenMatch(stackToken, inputToken);
                    System.out.println(parsingStack.getParsingStack());
                    tokensArray.remove(token_id);
                }else {
                    //If it is not the same
                    //Check if the stack key and token key have an entry in the Parsing Table
                    // If not -> throw an error
                    String parseEntry = parsingTable.getValue(stackToken, inputToken);
                    if(parseEntry == null){
                        throw new Exception("There has been an error: " + stackToken + inputToken + " on line: " + line);
                    }else{
                        //If the entry is found, replace the stack
                        parsingStack.getParsingStack().pop();
                        //for each token in the parseEntry, pop it separately to the stack
                        this.populateStack(parseEntry);
                        ParsingTree.populateParsingTree(parseEntry);
                        //System.out.println(this.parsingStack.getParsingStack());
                    }
                }

            }
            line++;
        }
        System.out.println(lexicalArray);
        System.out.println(this.parsingStack.getParsingStack());

    }

    public static boolean compare(String stackToken, String inputToken){
        //Check if the stackToken and inputToken are equals
        //If not, check if the stackToken is an identifier,
        //if so, check if the inputToken is an identifier or intValue, booleanValue, charValue, floatValue - using regex for each identifier
        // if not, return false


        if(stackToken.equals(inputToken)) {
            return true;
        }
        else if(stackToken.equals("identifier")){
            if(inputToken.matches( "^id[0-9]+")){
                return true;
            }
        }
        else if(stackToken.equals("intValue") && inputToken.matches("^intValue[0-9]+")){
            return true;
        }
        else if(stackToken.equals("charValue") && inputToken.matches("^charValue[0-9]+")){
            return true;
        }
        else if(stackToken.equals("boolValue") && inputToken.matches("^boolValue[0-9]+")){
            return true;
        }
        else if(stackToken.equals("floatValue") && inputToken.matches("^floatValue[0-9]+")){
            return true;
        }
        return false;
    }

    private void populateStack(String parseEntry){
        //Tokenize the input string and populate the stack
        StringTokenizer st = new StringTokenizer(parseEntry);

        String sReversed = "";

        while (st.hasMoreTokens()) {
            sReversed = st.nextToken() + " " + sReversed;
        }

        StringTokenizer stringTokenizer = new StringTokenizer(sReversed);

        while(stringTokenizer.hasMoreTokens()){
            String token = stringTokenizer.nextToken();
            //Swap the tokens
            this.parsingStack.getParsingStack().push(token);

            //Populating Parsing Tree
            //adding the following tags to the stack of the nextNode to be read

        }
    }
}
