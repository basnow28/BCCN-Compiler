package lasalle.trees;

import lasalle.syntaxAnalyzer.FirstAndFollow;

import java.util.Iterator;
import java.util.Stack;

public class ParsingTreeNodeIterator<T> implements Iterator<ParsingTreeNode<T>> {
    enum ProcessStages {
        ProcessParent, ProcessChildCurrentNode, ProcessChildSubNode
    }

    private ParsingTreeNode<T> parsingTreeNode;
    private ProcessStages nextProcessStage;
    private ParsingTreeNode<T> nextTreeNode;

    private static Stack<ParsingTreeNode<String>> nodeStack = new Stack<>();

    //Iterators for the children to implement hasNext function
    private Iterator<ParsingTreeNode<T>> childrenCurrentNodeIterator;
    private Iterator<ParsingTreeNode<T>> childrenSubNodeIterator;

    public ParsingTreeNodeIterator(ParsingTreeNode parsingTreeNode){
        this.parsingTreeNode = parsingTreeNode;
        this.nextProcessStage = ProcessStages.ProcessParent;
        //Getting the iterator of children list
        // of a new parsing tree node iterator
        this.childrenCurrentNodeIterator = parsingTreeNode.children.iterator();
    }

    public void updateChildrenIterator(ParsingTreeNode<T> parsingTreeNode){
        this.childrenCurrentNodeIterator = parsingTreeNode.children.iterator();
        this.nextProcessStage = ProcessStages.ProcessChildCurrentNode;
    }


    @Override
    public boolean hasNext() {
        //Implement hasNext() for the tree method
        //Check if the nextProcessStage is the parent
        //If so, it means there is a next node which is a parent
        if(this.nextProcessStage == ProcessStages.ProcessParent){
            this.nextProcessStage = ProcessStages.ProcessChildCurrentNode;
            this.nextTreeNode = this.parsingTreeNode;
            return true;
        }

        if(this.nextProcessStage == ProcessStages.ProcessChildCurrentNode) {
            if (childrenCurrentNodeIterator.hasNext()) {
                ParsingTreeNode<T> childDirect = childrenCurrentNodeIterator.next();

                //Check if the childDirect has a sibling
                //Move on to the children of the childDirect
                //If the sibling exists, add it to the stack
                if(childrenCurrentNodeIterator.hasNext()){
                    nodeStack.add((ParsingTreeNode<String>) childrenCurrentNodeIterator.next());
                }

                // Move on to the children of the childDirect
                this.childrenCurrentNodeIterator = childDirect.childrenIterator;
                this.nextProcessStage = ProcessStages.ProcessChildCurrentNode;
                this.nextTreeNode = childDirect;
                return true;
            } else {
                //Check if there is something in the stack
                System.out.println("Next tree node: " + this.nextTreeNode);
                System.out.println("Node stack: " + this.nodeStack.toString());
                if (!nodeStack.isEmpty()) {
                    //The next tree node should be the sibling of the node from the stack
                    //which is the next child of it's parent node
                    ParsingTreeNode<T> stackNode = (ParsingTreeNode<T>) nodeStack.pop();
                    this.nextTreeNode = stackNode;
                    this.nextProcessStage = ProcessStages.ProcessChildCurrentNode;
                    return true;
                } else {
                    if(this.nextTreeNode.parent.childrenIterator.hasNext()){
                        this.nextTreeNode = this.nextTreeNode.parent.childrenIterator.next();
                        this.nextProcessStage = ProcessStages.ProcessChildCurrentNode;
                        this.childrenCurrentNodeIterator = this.nextTreeNode.childrenIterator;
                        return true;
                    }
                    this.nextProcessStage = null;
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public ParsingTreeNode<T> next() {
        return this.nextTreeNode;
    }
}
