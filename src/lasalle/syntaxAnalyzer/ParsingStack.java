package lasalle.syntaxAnalyzer;

import java.util.Stack;

public class ParsingStack {
    private Stack<String> parsingStack;

    public ParsingStack(){
        this.parsingStack = new Stack();
        //Initializing the parsing Stack
        parsingStack.push("$");
        parsingStack.push("<program>");
    }

    public Stack<String> getParsingStack() {
        return parsingStack;
    }

    public void setParsingStack(Stack<String> parsingStack) {
        this.parsingStack = parsingStack;
    }
}
