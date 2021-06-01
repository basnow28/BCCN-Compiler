package lasalle.tac;

import lasalle.syntaxAnalyzer.FirstAndFollow;
import lasalle.trees.ParsingTreeNode;

import java.util.*;

public class TacResults {
    private ArrayList<ArrayList<String>> tacArray = new ArrayList<ArrayList<String>>();
    private Map<String, ArrayList<String>> tempTacLabArray = new LinkedHashMap<>();
    public Map<String, String> parentChildLabels = new LinkedHashMap<>();
    private HashSet<String> goTosLabels = new HashSet<>();

    public TacResults(){
        tempTacLabArray.put("L-1", null);
    }

    public void populateTacArray(Map<String, ParsingTreeNode<String>> smallTrees){
        populateTempTacArray(smallTrees);
        addNewLinesToTac("L1");
        System.out.println("added");
    }

    private ArrayList<String> addNewLinesToTac(String label){
        ArrayList<String> labelElements = tempTacLabArray.get(label);
        ArrayList<String> newLine = new ArrayList<>();
        boolean isGoTo = false;

        for(int i=0; i<labelElements.size(); i++){
            String element = labelElements.get(i);
            if(element.matches("^L[0-9]+$") && !isGoTo){
                //newLine.addAll();
                if(newLine.size() != 0) {
                    tacArray.add(new ArrayList<>(newLine));
                    newLine.clear();
                }
                tacArray.add(addNewLinesToTac(element));
            }else{
                if(element.equals("goto")){
                    isGoTo = true;
                }else{
                    isGoTo = false;
                }
                newLine.add(element);
            }
        }
        return newLine;
    }

    private void populateTempTacArray(Map<String, ParsingTreeNode<String>> smallTrees){
        for(Map.Entry<String, ParsingTreeNode<String>> entry : smallTrees.entrySet()){
            Iterator<ParsingTreeNode<String>> entryIter = entry.getValue().iterator();
            String parent = "";
            if(entryIter.hasNext()) {
                parent = entryIter.next().data;
            }
            switch(parent){
                case "<ifs>":
                    //addIfsToTacTemp(entry.getKey(), entryIter);
                    break;
                case "<while>":
                    addWhileToTacTemp(entry.getKey(), entryIter);
                    break;
                case "<assignment>":
                    addAssignmentToTacTemp(entry.getKey(), entryIter);
                    break;
                case "<declaration>":
                    addDeclarationTacToTemp(entry.getKey(), entryIter);
                    break;
                case "<main>":
                    addStartTacToTemp(entry.getKey(), entryIter);
                    break;
                default:
                    new Exception("The parent of the small tree is not valid");
                    break;
            }
        }

        for(String gotoLabel : goTosLabels){
            for(Map.Entry<String, ArrayList<String>> entry : tempTacLabArray.entrySet()) {
                if(entry.getKey().equals(gotoLabel)){
                    ArrayList<String> items = entry.getValue();
                    ArrayList<String> newArray = new ArrayList<>();
                    newArray.add(gotoLabel + ":");
                    for (String item : items) {
                        newArray.add(item);
                    }
                    entry.setValue(newArray);
                }
            }
        }
    }

    private void addStartTacToTemp(String key, Iterator<ParsingTreeNode<String>> entryIter) {
        ArrayList<String> tempTacLine = new ArrayList<>();
        while(entryIter.hasNext()) {
            ParsingTreeNode<String> node = entryIter.next();
            if(node.data.startsWith("L")){
                tempTacLine.add(node.data);
                parentChildLabels.put(node.data, key);
            }
        }
        this.tempTacLabArray.put(key, tempTacLine);
    }

    private void addWhileToTacTemp(String key, Iterator<ParsingTreeNode<String>> entryIter) {
        ArrayList<String> tempTacLine = new ArrayList<>();
        tempTacLine.add(key+":");
        while(entryIter.hasNext()) {
            ParsingTreeNode<String> node = entryIter.next();
            if(node.children.isEmpty() && !FirstAndFollow.isTokenANonTerminal(node.data)){
                switch(node.data){
                    case "<":
                        tempTacLine.add(">");
                        break;
                    case ">":
                        tempTacLine.add("<");
                        break;
                    case "NOTEQUALS":
                        tempTacLine.add("!=");
                        break;
                    case "EQUALS":
                        tempTacLine.add("=");
                        break;
                    case "{":
                        tempTacLine.add("goto");
                        //We know there is nothing after the while in our program
                        //Normally, we have to find out what is the following element of the while
                        String goToValue = findGoTo(key);
                        if(!goToValue.equals("L-1")) {
                            goTosLabels.add(goToValue);
                        }

                        tempTacLine.add(goToValue);
                        break;
                    case "}":
                        break;
                    default:
                        if(node.data.matches("^L[0-9]+")){
                            parentChildLabels.put(node.data, key);
                        }
                        tempTacLine.add(node.data);
                        break;
                }
            }
        }
        tempTacLine.add("goto");
        tempTacLine.add(key);
        this.tempTacLabArray.put(key, tempTacLine);
    }

    private String findGoTo(String child) {
        boolean doIExist = false;
        String parent = "";
        for(Map.Entry<String, String> entry : parentChildLabels.entrySet()){
            if(entry.getKey().equals(child)){
                parent = entry.getValue();
                doIExist = true;
            }else{
                if(entry.getValue().equals(parent) && doIExist){
                    return entry.getKey();
                }else{
                    if(!parent.equals("L1") && doIExist) {
                        return findGoTo(parent);
                    }
                }
            }
        }
        if(!parent.equals("L1") && doIExist) {
            return findGoTo(parent);
        }
        return "L-1";
    }

    private void addAssignmentToTacTemp(String key, Iterator<ParsingTreeNode<String>> entryIter) {
        ArrayList<String> tempTacLine = new ArrayList<>();
        while(entryIter.hasNext()) {
            ParsingTreeNode<String> node = entryIter.next();
            if(node.children.isEmpty() && !FirstAndFollow.isTokenANonTerminal(node.data)){
                tempTacLine.add(node.data);
            }
        }
        //Removing the semicolon ;
        tempTacLine.remove(tempTacLine.size()-1);
        if(tempTacLine.size() == 5 || tempTacLine.size() == 3) {
            this.tempTacLabArray.put(key, tempTacLine);
        }else{
            //This should be a new method that will look into the tree and prioritize
            //the multiplication and keeping the order of the calculations
        }
    }

    private void addDeclarationTacToTemp(String key, Iterator<ParsingTreeNode<String>> entryIter) {
        ArrayList<String> tempTacLine = new ArrayList<>();
        while(entryIter.hasNext()){
            ParsingTreeNode<String> node = entryIter.next();
            if(node.children.isEmpty()){
                tempTacLine.add(node.data);
            }
        }
        //Remove the first and the last one
        tempTacLine.remove(0);
        tempTacLine.remove(tempTacLine.size()-1);
        this.tempTacLabArray.put(key, tempTacLine);
    }
}
