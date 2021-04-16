package lasalle.lexicalAnalyser;

import java.util.ArrayList;

public class LexicalArray {
    private ArrayList<ArrayList<String>> lexicalArray;

    public LexicalArray(ArrayList<ArrayList<String>> lexicalArray){
        this.lexicalArray = lexicalArray;
    }

    public ArrayList<ArrayList<String>> getLexicalArray() {
        return lexicalArray;
    }

    public void setLexicalArray(ArrayList<ArrayList<String>> lexicalArray) {
        this.lexicalArray = lexicalArray;
    }
}
