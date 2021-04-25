package lasalle.syntaxAnalyzer;

import java.util.Map;

public class MapKey {
    private String terminal;
    private String nonTerminal;

    public MapKey(String terminal, String nonTerminal) {
        this.terminal = terminal;
        this.nonTerminal = nonTerminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public void setNonTerminal(String non_terminal) {
        this.nonTerminal = non_terminal;
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();

            stb.append("NonTerminal: ").append(MapKey.this.nonTerminal)
                    .append("; Terminal: ").append(MapKey.this.terminal);
        return stb.toString();
    }
}
