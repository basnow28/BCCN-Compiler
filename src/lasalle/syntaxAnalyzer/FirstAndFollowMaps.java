package lasalle.syntaxAnalyzer;

import java.util.HashMap;
import java.util.Map;

public class FirstAndFollowMaps {
    private Map<String, String> firstMap; //where key is Terminal, and the value is the corresponding grammar rule
    private Map<String, String> followMap;

    public FirstAndFollowMaps(){
        firstMap = new HashMap<>();
    }
    public FirstAndFollowMaps(String first, String grammarRule){
        firstMap = new HashMap<>();
        firstMap.put(first, grammarRule);
    }

    public void addFirstRule(String first, String grammarRule){
        firstMap.put(first, grammarRule);
    }

    public Map<String, String> getFirstMap() {
        return firstMap;
    }


    public void setFirstMap(Map<String, String> firstMap) {
        this.firstMap = firstMap;
    }

    public void addNewFirstMapEntries(Map<String, String> firstMap) {
        this.firstMap.putAll(firstMap);
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        for (Map.Entry<String, String> entry : firstMap.entrySet()) {
            stb.append("terminal: ").append(entry.getKey())
                    .append("; grammarRule: ").append(entry.getValue()).append("\n");
        }
        return stb.toString();
    }
}
