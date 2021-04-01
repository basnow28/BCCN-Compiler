package lasalle.tables;

import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.*;
import java.util.Collections.*;
import java.util.stream.Collectors;

public class ValuesTable {

    private static Map<String, String> values = new LinkedHashMap<String, String>();

    public static Map<String, String> getValues() {
        return values;
    }

    public static void setValues(Map<String, String> values) {
        ValuesTable.values = values;
    }

    private static int getValuesSize(){
        return values.size() + 1;
    }

    public static String getNewIdentifier(){
        return "id"+ getValuesSize();
    }

    private static String checkIfVariableIsDeclared(String variable){

        for (Map.Entry<String, String> entry : values.entrySet()) {
            if (entry.getValue().equals(variable.replace("$", ""))) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String addNewValue(String value){
        String key;
        if(value.contains("$")){
            key = checkIfVariableIsDeclared(value);
            if(key != null){
                return key;
            }
        }

        key = getNewIdentifier();
        values.put(key, value);
        return key;
    }
}