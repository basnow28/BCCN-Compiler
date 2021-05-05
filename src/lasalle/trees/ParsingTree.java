package lasalle.trees;

import java.util.StringTokenizer;
import java.util.Iterator;

public class ParsingTree {
    private static ParsingTreeNode<String> parsingTree = new ParsingTreeNode<>("<program>");
    private static Iterator<ParsingTreeNode<String>> parsingTreeNodeIterator = parsingTree.iterator();

    public static void populateParsingTree(String parseEntry){
        //Tokenize the input string and populate the stack
        StringTokenizer stringTokenizer = new StringTokenizer(parseEntry);

        if(parsingTreeNodeIterator.hasNext()) {
            ParsingTreeNode<String> node = parsingTreeNodeIterator.next();

            while (stringTokenizer.hasMoreTokens()) {
                //Populating Parsing Tree
                //adding the following tags to the stack of the nextNode to be read
                String token = stringTokenizer.nextToken();
                System.out.print("Children:  ");
                System.out.print(token);
                node.addChild(token, (ParsingTreeNodeIterator<String>) parsingTreeNodeIterator);
            }
        }
    }

    public static void updateTreeNodeValueWhenMatch(String stackToken, String inputToken){
        if(!stackToken.equals(inputToken)) {
            if (parsingTreeNodeIterator.hasNext()) {
                parsingTreeNodeIterator.next().updateValue(inputToken);
            }
        }else{
            parsingTreeNodeIterator.hasNext();
            parsingTreeNodeIterator.next();
        }
    }

    public static ParsingTreeNode<String> getParsingTree(){
        return parsingTree;
    }
}
