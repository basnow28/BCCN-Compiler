package lasalle.tables;

import java.util.*;

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
        Iterator it = values.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(variable.replace("$", ""));
            if(pair.getValue().toString().equals(variable.replace("$", ""))){
                return pair.getKey().toString();
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