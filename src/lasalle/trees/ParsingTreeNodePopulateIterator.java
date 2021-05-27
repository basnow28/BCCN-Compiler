package lasalle.trees;

import java.util.Iterator;
import java.util.Stack;

public class ParsingTreeNodePopulateIterator<T> implements Iterator<ParsingTreeNode<T>> {
    private Stack<ParsingTreeNode<T>> nodeStack;
    private ParsingTreeNode<T> nextTreeNode;

    public ParsingTreeNodePopulateIterator(ParsingTreeNode<T> parentNode){
        this.nodeStack = new Stack<>();
        this.nodeStack.push(parentNode);
    }

    public void addChildrenToStack(ParsingTreeNode<T> parent){
        //Will be saving the children to a temp stack
        //Because finally, we need to populate the original stack in reversed order
        Stack<ParsingTreeNode<T>> tempStack = new Stack<>();
        //Get children iterator of a parent
        Iterator<ParsingTreeNode<T>> childrenIterator = parent.children.iterator();

        int tempStackSize = 0;
        while(childrenIterator.hasNext()){
            tempStack.push(childrenIterator.next());
            tempStackSize++;
        }

        //Move the nodes from tempStack to the original stack
        for(int i=0; i<tempStackSize; i++){
            nodeStack.push(tempStack.pop());
        }
        tempStack.empty();
    }

    @Override
    public boolean hasNext() {
        boolean hasNext = false;
        if( !nodeStack.empty()){
            hasNext = true;
            nextTreeNode = nodeStack.pop();
        }else{
            nextTreeNode = null;
        }
        return hasNext;
    }

    @Override
    public ParsingTreeNode<T> next() {
        return nextTreeNode;
    }
}
