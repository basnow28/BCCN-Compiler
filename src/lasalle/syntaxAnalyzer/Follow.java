package lasalle.syntaxAnalyzer;

import java.util.ArrayList;

public class Follow {
    private String value;
    private ArrayList<String> setOfFollow;

    public Follow(String value, ArrayList<String> setOfFollow) {
        this.value = value;
        this.setOfFollow = setOfFollow;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<String> getSetOfFollow() {
        return setOfFollow;
    }

    public void setSetOfFollow(ArrayList<String> setOfFollow) {
        this.setOfFollow = setOfFollow;
    }
}
