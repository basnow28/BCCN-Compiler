package lasalle.tables;

import java.util.LinkedHashMap;
import java.util.Map;

public class IntegerTable {

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
        return "int"+ getValuesSize();
    }


    public static String addNewInteger(String value){
        String key;

        key = getNewInteger();
        integers.put(key, value);
        return key;
    }
}
