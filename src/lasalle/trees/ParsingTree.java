package lasalle.trees;

import java.util.StringTokenizer;
import java.util.Iterator;

public class ParsingTree {
    private static ParsingTreeNode<String> parsingTree = new ParsingTreeNode<>("<program>");
    //private static Iterator<ParsingTreeNode<String>> parsingTreeNodeIterator = parsingTree.iterator();
    private static ParsingTreeNodePopulateIterator<String> parsingTreeNodePopulateIterator = parsingTree.getPopulateIterator();

    public static void populateParsingTree(String parseEntry){
        //Tokenize the input string and populate the stack
        StringTokenizer stringTokenizer = new StringTokenizer(parseEntry);

        if(parsingTreeNodePopulateIterator.hasNext()) {
            ParsingTreeNode<String> node = parsingTreeNodePopulateIterator.next();

            while (stringTokenizer.hasMoreTokens()) {
                //Populating Parsing Tree
                //adding the following tags to the stack of the nextNode to be read
                String token = stringTokenizer.nextToken();
                node.addChild(token);
            }
            //After populating, add children to the iterator array
            if(!node.children.isEmpty()){
                parsingTreeNodePopulateIterator.addChildrenToStack(node);
            }
        }
    }

    public static void updateTreeNodeValueWhenMatch(String stackToken, String inputToken){
        if(!stackToken.equals(inputToken)) {
            if (parsingTreeNodePopulateIterator.hasNext()) {
                parsingTreeNodePopulateIterator.next().updateValue(inputToken);
            }
        }else{
            parsingTreeNodePopulateIterator.hasNext();
            parsingTreeNodePopulateIterator.next();
        }
    }

    public static ParsingTreeNode<String> getParsingTree(){
        return parsingTree;
    }
}
