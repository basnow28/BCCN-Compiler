package lasalle.tables;

import java.util.LinkedHashMap;
import java.util.Map;

public class BooleanTable {

        private static Map<String, String> booleans = new LinkedHashMap<String, String>();

        public static Map<String, String> getValues() {
            return booleans;
        }

        public static void setBooleans(Map<String, String> values) {
            lasalle.tables.BooleanTable.booleans = booleans;
        }

        private static int getValuesSize(){
            return booleans.size() + 1;
        }

        public static String getNewBoolean(){
            return "bool"+ getValuesSize();
        }


        public static String addNewBoolean(String value){
            String key;

            key = getNewBoolean();
            booleans.put(key, value);
            return key;
        }
}
