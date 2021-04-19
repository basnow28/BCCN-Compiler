package lasalle.syntaxAnalyzer;

import java.util.ArrayList;

public class First {
    private String value;
    private ArrayList<String> setOfFirst;

    public First(String value, ArrayList<String> setOfFirst) {
        this.value = value;
        this.setOfFirst = setOfFirst;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<String> getSetOfFirst() {
        return setOfFirst;
    }

    public void setSetOfFirst(ArrayList<String> setOfFirst) {
        this.setOfFirst = setOfFirst;
    }
}
