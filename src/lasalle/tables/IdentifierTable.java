package lasalle.tables;

import java.util.*;

public class IdentifierTable {

    private static Map<String, String> indentifiers = new LinkedHashMap<String, String>();

    public static Map<String, String> getValues() {
        return indentifiers;
    }

    public static void setIdentifer(Map<String, String> values) {
        IdentifierTable.indentifiers = indentifiers;
    }

    private static int getValuesSize(){
        return indentifiers.size() + 1;
    }

    public static String getNewIdentifier(){
        return "id"+ getValuesSize();
    }

    private static String checkIfVariableIsDeclared(String variable){

        for (Map.Entry<String, String> entry : indentifiers.entrySet()) {
            if (entry.getValue().equals(variable.replace("$", ""))) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String addNewIdentifier(String value){
        String key;
        if(value.contains("$")){
            key = checkIfVariableIsDeclared(value);
            if(key != null){
                return key;
            }
        }

        key = getNewIdentifier();
        indentifiers.put(key, value);
        return key;
    }
}