package lasalle.tables;

import java.util.LinkedHashMap;
import java.util.Map;

public class FloatTable {
    public static String regex = "[-+]?[0-9]*\\.{1}[0-9]*";

    private static Map<String, String> floats = new LinkedHashMap<String, String>();

    public static Map<String, String> getValues() {
        return floats;
    }

    public static void setFloats(Map<String, String> values) {
        FloatTable.floats = floats;
    }

    private static int getValuesSize(){
        return floats.size() + 1;
    }

    public static String getNewFloat(){
        return "floatValue"+ getValuesSize();
    }


    public static String addNewFloat(String value){
        String key;

        key = getNewFloat();
        floats.put(key, value);
        return key;
    }
}
