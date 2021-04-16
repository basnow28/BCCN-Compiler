package lasalle.syntaxAnalyzer;

import lasalle.lexicalAnalyser.LexicalArray;

import java.io.BufferedReader;
import java.io.FileReader;
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

        for(int line = 0; line < lexicalArray.getLexicalArray().size() + 1; line++){
            ArrayList<String> tokensArray = lexicalArray.getLexicalArray().get(line);
            for(int token_id = 0; token_id < tokensArray.size() + 1; token_id++){
                System.out.println(tokensArray.get(token_id));
                String stackToken = parsingStack.getParsingStack().peek();
                String inputToken =  tokensArray.get(token_id);

                if(this.compare(stackToken, inputToken)){
                    //If the compare method returns true, remove the values from the stack and the input
                    parsingStack.getParsingStack().pop();
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
                    }
                }

            }
        }
        System.out.println(lexicalArray);
        System.out.println(this.parsingStack.getParsingStack());

    }

    private boolean compare(String stackToken, String inputToken){
        //Check if the stackToken and inputToken are equals
        //If not, check if the stackToken is an identifier,
        //if so, check if the inputToken is an identifier or intValue, booleanValue, charValue, floatValue - using regex for each identifier
        // if not, return false

        if(stackToken.equals(inputToken)){
            return true;
        }
        else if(stackToken == "<identifier>" && inputToken.matches( "^id[0-9]+")){
            return true;
        }
        else if(stackToken == "intValue" && inputToken.matches("^intValue[0-9]+")){
            return true;
        }
        else if(stackToken == "charValue" && inputToken.matches("^charValue[0-9]+")){
            return true;
        }
        else if(stackToken == "boolValue" && inputToken.matches("^boolValue[0-9]+")){
            return true;
        }
        else if(stackToken == "floatValue" && inputToken.matches("^floatValue[0-9]+")){
            return true;
        }
        return false;
    }

    private void populateStack(String parseEntry){
        //Tokenize the input string and populate the stack
        StringTokenizer stringTokenizer = new StringTokenizer(parseEntry);
        while(stringTokenizer.hasMoreTokens()){
            String token = stringTokenizer.nextToken();
            //Swap the tokens
            System.out.println("String token: " + token);
            this.parsingStack.getParsingStack().push(token);
        }
    }
}
