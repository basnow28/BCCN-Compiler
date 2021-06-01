package lasalle.tac;

import lasalle.trees.ParsingTree;
import lasalle.trees.ParsingTreeNode;
import lasalle.trees.ParsingTreeNodeIterator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class SmallTrees {
    Map<String, ParsingTreeNode<String>> smallTreesMap;
    String keywords[] = {"<ifs>", "<while>", "<assignment>", "<declaration>"};
    Stack<ParsingTreeNode<String>> stack;
    int labelNumber = 1;

    public SmallTrees(){
        smallTreesMap = new LinkedHashMap<String, ParsingTreeNode<String>>();
        stack = new Stack<>();
    }

    public void createSmallTrees(){
        //Iterate through the parsingTree
        ParsingTreeNode<String> parsingTree = ParsingTree.getParsingTree();
        Iterator<ParsingTreeNode<String>> parsingTreeNodeIterator = parsingTree.iterator();
        Iterator<ParsingTreeNode<String>> stackNodeIterator = null;

        //For each node, check if it is in the keyword array
        //If the node does have children, create a small tree
        //Save a new tree in the tree stack
        //Now we know that the next children of the node in the stack will
        //be the same ones as in the parsingTreeNodeIterator
        //Continue iterating
        // -> if the stack node doesn't have more children, remove it from the stack
        // -> if it has more children, and the child is a keyword, change the value of the current child to the next label
        // Create a new tree and add it to stack
        // Update the node-stack iterator to point to the children of newly created small tree

        //Find the fist occurrence of the keyword
        while(parsingTreeNodeIterator.hasNext()) {
            ParsingTreeNode<String> node = parsingTreeNodeIterator.next();
            ParsingTreeNode<String> stackNode = null;
            //Move the iterator of the stackNodeIter
            if(stackNodeIterator != null){
                if(stackNodeIterator.hasNext()) {
                    stackNode = stackNodeIterator.next();
                }
            }else{
                stackNode = null;
            }

            //Add nodes to the small Tree if it is not a keyword
            if (node.data.equals(keywords[0]) || node.data.equals(keywords[1]) || node.data.equals(keywords[2]) || node.data.equals(keywords[3])) {
                if (!node.children.isEmpty()) {
                    ParsingTreeNode<String> newSmallTree = new ParsingTreeNode<>(node.data);
                    //For each child of the node, add them to the newSmallTree
                    Iterator<ParsingTreeNode<String>> childIter = node.children.iterator();
                    while(childIter.hasNext()){
                        newSmallTree.addChild(childIter.next().data);
                    }
                    //Add the node to small tree map with a label
                    String label = "L"+labelNumber;
                    smallTreesMap.put(label, newSmallTree);
                    //Push the small tree to the stack
                    stack.add(newSmallTree);
                    //Update the iterator of the current stack iterator
                    stackNodeIterator = newSmallTree.childrenIterator;

                    //Check if there is a parent tree
                    if(stackNode != null){
                        stackNode.updateValue(label);
                    }
                    labelNumber++;
                }
            }
        }
    }

    /*private void createSmallTreesRecursively(ParsingTreeNodeIterator<String> parsingTreeNodeIterator, ParsingTreeNode<String> smallTree) {
        while(parsingTreeNodeIterator.hasNext()){
            ParsingTreeNode<String> node = parsingTreeNodeIterator.next();

            //Add nodes to the small Tree if it is not a keyword
            if(node.data.equals(keywords[0]) || node.data.equals(keywords[1]) || node.data.equals(keywords[2])){
                if(!node.children.isEmpty()) {
                    ParsingTreeNode<String> newSmallTree = new ParsingTreeNode<>(node.data);
                    ParsingTreeNode<String> sibling = findSiblingOfANode(node);
                    stack.push(sibling);
                    labelNumber++;
                    createSmallTreesRecursively(parsingTreeNodeIterator, newSmallTree);
                }
            }else{
                //Populate the smallTree until you find the next node which is on the top of the stack
                //That is a sibling of the current smallTree
                if(!stack.isEmpty()) {
                    if (!node.data.equals(stack.peek().data)) {
                        smallTree.addChild(node.data);
                    } else {
                        //Add the tree to the map
                        smallTreesMap.put("L" + labelNumber, smallTree);
                        stack.pop();
                        //parsingTreeNodeIterator.goBack();
                    }
                }
            }
        }
    }

    private ParsingTreeNode<String> findSiblingOfANode(ParsingTreeNode<String> node){
        //Get children iterator of a parent
        if(!node.isRoot()){
            Iterator<ParsingTreeNode<String>> parentIterator = node.parent.childrenIterator;
            while(parentIterator.hasNext()){
                if(parentIterator.next().data.equals(node.data)){
                    if(parentIterator.hasNext()){
                        return parentIterator.next();
                    }
                }
            }
        }
        return null;
    }*/
}
