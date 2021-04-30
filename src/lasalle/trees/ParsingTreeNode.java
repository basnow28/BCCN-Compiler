package lasalle.trees;

import java.util.*;

public class ParsingTreeNode<T> implements Iterable<ParsingTreeNode<T>> {

    public T data;
    public ParsingTreeNode<T> parent;
    public List<ParsingTreeNode<T>> children;

    public ParsingTreeNode(T data){
        this.data = data;
        this.children = new LinkedList<ParsingTreeNode<T>>();

    }

    public ParsingTreeNode<T> addChild(T treeChild){
        ParsingTreeNode<T> childParsingTreeNode = new ParsingTreeNode<>(treeChild);
        childParsingTreeNode.parent = this;
        this.children.add(childParsingTreeNode);
        return childParsingTreeNode;
    }

    public boolean isRoot(){
        return parent == null;
    }

    public boolean isLeaf(){
        return children.size() == 0;
    }

    public int getLevel(){
        if(this.isRoot()){
            return 0;
        }else {
            return parent.getLevel() + 1;
        }
    }

    @Override
    public String toString(){
        return data != null ? data.toString() : "[data null]";
    }

    @Override
    public Iterator<ParsingTreeNode<T>> iterator() {
        ParsingTreeNodeIterator<T> iterator = new ParsingTreeNodeIterator<>(this);
        return iterator;
    }

}
