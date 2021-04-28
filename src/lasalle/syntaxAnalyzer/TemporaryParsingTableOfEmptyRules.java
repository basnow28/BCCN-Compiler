package lasalle.syntaxAnalyzer;

import java.util.HashMap;
import java.util.Map;

public class TemporaryParsingTableOfEmptyRules {

    public static Map<MapKey, String> temporaryParsingTable = new HashMap<>();

    public static Map<MapKey, String> getTemporaryParsingTable() {
        return temporaryParsingTable;
    }

    public static void addNewTemporaryEntry(MapKey mapKey, String rule){
        temporaryParsingTable.put(mapKey, rule);
    }
}
