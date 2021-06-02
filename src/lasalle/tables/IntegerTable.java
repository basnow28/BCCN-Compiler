package lasalle.tables;

import java.util.LinkedHashMap;
import java.util.Map;

public class IntegerTable {
    public static String regex = "[-+]?[0-9]";

    private static Map<String, String> integers = new LinkedHashMap<String, String>();

    public static Map<String, String> getValues() {
        return integers;
    }

    public static void setIntegers(Map<String, String> values) {
        IntegerTable.integers = integers;
    }

    private static int getValuesSize(){
        return integers.size() + 1;
    }

    public static String getNewInteger(){
        return "intValue"+ getValuesSize();
    }


    public static String addNewInteger(String value){
        String key;

        key = getNewInteger();
        integers.put(key, value);
        return key;
    }

    public static Map<String, String> getIntegers() {
        return integers;
    }
}
