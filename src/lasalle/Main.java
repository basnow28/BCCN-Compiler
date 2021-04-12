package lasalle;

import lasalle.lexicalAnalyser.LexicalAnalyser;
import lasalle.reader.CodeReader;
import lasalle.tables.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        try {
            ArrayList<ArrayList<String>> code = CodeReader.readTheFile("file.txt");
            System.out.println(code);
            System.out.println(LexicalAnalyser.readTheFile(code));
            System.out.println(IdentifierTable.getValues());
            System.out.println(IntegerTable.getValues());
            System.out.println(FloatTable.getValues());
            System.out.println(CharTable.getValues());
            System.out.println(BooleanTable.getValues());
        }
        catch (IOException e){
            System.out.print(e.getMessage());
        }
    }
}
