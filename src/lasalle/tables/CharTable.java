package lasalle.tables;

import java.util.LinkedHashMap;
import java.util.Map;

public class CharTable {
    private static Map<String, String> chars = new LinkedHashMap<String, String>();

    public static Map<String, String> getValues() {
        return chars;
    }

    public static void setFloats(Map<String, String> values) {
        CharTable.chars = chars;
    }

    private static int getValuesSize(){
        return chars.size() + 1;
    }

    public static String getNewChar(){
        return "char"+ getValuesSize();
    }


    public static String addNewChar(String value){
        String key;

        key = getNewChar();
        chars.put(key, value);
        return key;
    }

}
