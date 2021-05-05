package lasalle.trees;

import java.util.Iterator;

public class ParsingTreeNodeIterator<T> implements Iterator<ParsingTreeNode<T>> {
    enum ProcessStages {
        ProcessParent, ProcessChildCurrentNode, ProcessChildSubNode
    }

    private ParsingTreeNode<T> parsingTreeNode;
    private ProcessStages nextProcessStage;
    private ParsingTreeNode<T> nextTreeNode;

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

        if(this.nextProcessStage == ProcessStages.ProcessChildCurrentNode){
            if(childrenCurrentNodeIterator.hasNext()){
                ParsingTreeNode<T> childDirect = childrenCurrentNodeIterator.next();
                childrenSubNodeIterator = childDirect.iterator();
                this.nextProcessStage = ProcessStages.ProcessChildSubNode;
                return hasNext();
            }
            else {
                this.nextProcessStage = null;
                return false;
            }
        }

        if(this.nextProcessStage == ProcessStages.ProcessChildSubNode){
            if(childrenSubNodeIterator.hasNext()){
                this.nextTreeNode = childrenSubNodeIterator.next();
                return true;
            }
            else {
                // If the sub children of the current node don't have children
                // Return to the current children list, and check if other children
                // of that node have sub children
                this.nextTreeNode = null;
                this.nextProcessStage = ProcessStages.ProcessChildCurrentNode;
                return hasNext();
            }
        }
        return false;
    }

    @Override
    public ParsingTreeNode<T> next() {
        return this.nextTreeNode;
    }
}
