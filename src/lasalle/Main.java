package lasalle;

import lasalle.lexicalAnalyser.LexicalAnalyser;
import lasalle.lexicalAnalyser.LexicalArray;
import lasalle.reader.CodeReader;
import lasalle.syntaxAnalyzer.ParsingTable;
import lasalle.syntaxAnalyzer.SyntaxAnalyzer;
import lasalle.tables.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        LexicalArray lexicalArray;
        try {
            ArrayList<ArrayList<String>> code = CodeReader.readTheFile("file.txt");
            lexicalArray = new LexicalArray(LexicalAnalyser.readTheFile(code));
            System.out.println(lexicalArray.getLexicalArray());

            //Syntax Analysis of the Code

            SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
            syntaxAnalyzer.validateCode(lexicalArray, new ParsingTable());



            /*System.out.println(IdentifierTable.getValues());
            System.out.println(IntegerTable.getValues());
            System.out.println(FloatTable.getValues());
            System.out.println(CharTable.getValues());
            System.out.println(BooleanTable.getValues());*/
        }
        catch (IOException e){
            System.out.print(e.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
