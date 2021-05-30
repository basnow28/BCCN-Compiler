package lasalle.MPIS;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpressionInstruction implements Instruction{
    private final int size = 5;
    private ArrayList<String> mips;

    public ExpressionInstruction() {
        this.mips = new ArrayList<>();
    }


    public ArrayList<String> getMips() {
        return mips;
    }

    public void setMips(ArrayList<String> mips) {
        this.mips = mips;
    }



    @Override
    public ArrayList<String> createAssemblyInstruction(ArrayList<String> tac, HashMap<String, String> s_reg) {
        String s = findOp(tac.get(3));
        switch (s){
            case "ADD":
                createAdd(tac, s_reg);
                break;
            case "SUB":
                createSub(tac, s_reg);
                break;
            case "MULT":
                createMult(tac, s_reg);
                break;
            case "DIV":
                createDiv(tac,s_reg);
                break;
        }

        return mips;
    }

    public void createAdd(ArrayList<String> tac, HashMap<String, String> s_reg){
        if(isNumber(tac.get(2)) && isNumber(tac.get(4))) {
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0, " + tac.get(2));
            mips.add("li $t1, " + tac.get(4));
            mips.add("addu " + s + ", $t0, $t1");
        }
        else if(s_reg.containsKey(tac.get(2)) &&  s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("add " + s + ", " + s_reg.get(tac.get(2))  + ", " + s_reg.get(tac.get(4)));
        }
        else if(!s_reg.containsKey(tac.get(2)) &&  s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0, " + tac.get(2));
            //mips.add("add " + s + ", " + s_reg.get(tac.get(4)) + ", $0");
            mips.add("addu " + s + ", $t0, " + s_reg.get(tac.get(4)));
        }
        else if(s_reg.containsKey(tac.get(2)) &&  !s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0, " + tac.get(4));
            //mips.add("add " + s + ", " + s_reg.get(tac.get(2)) + ", $0");
            mips.add("addu " + s + ", $t0, " +  s_reg.get(tac.get(2)));
        }

    }
    public void createSub(ArrayList<String> tac, HashMap<String, String> s_reg){
        int  t_count = 0;
        if(isNumber(tac.get(2)) && isNumber(tac.get(4))) {
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0" + ","+tac.get(2));
            mips.add("li $t1" + "," + s_reg.get(tac.get(4)));
            mips.add("sub " + s_reg.get(tac.get(0)) + ", $t0, $t1");
        }
        else if(s_reg.containsKey(tac.get(2)) &&  s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("sub " + s + ", " + s_reg.get(tac.get(2)) + ", " + s_reg.get(tac.get(4)));
        }
        else if(!s_reg.containsKey(tac.get(2)) &&  s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0" + ","+tac.get(2));
            mips.add("sub " + s_reg.get(tac.get(0)) +", $t0," + s_reg.get(tac.get(4)));
        }
        else if(s_reg.containsKey(tac.get(2)) &&  !s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0" + ","+tac.get(4));
            mips.add("sub  "+ s_reg.get(tac.get(0)) + ","  + s_reg.get(tac.get(2)) + ", $t0");
        }


    }
    public void createDiv(ArrayList<String> tac, HashMap<String, String> s_reg){
        if(isNumber(tac.get(2)) && isNumber(tac.get(4))) {
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0" + ","+tac.get(2));
            mips.add("li $t1" + ","+tac.get(4));
            mips.add("div " + s + ", $t0, $t1");
        }
        else if(s_reg.containsKey(tac.get(2)) &&  s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("div " + s + ", " + s_reg.get(tac.get(2))  + ", " + s_reg.get(tac.get(4)));
        }
        else if(!s_reg.containsKey(tac.get(2)) &&  s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0" + ","  + tac.get(2));
            mips.add("div " + s + ", $t0, " + s_reg.get(tac.get(4)));

        }
        else if(s_reg.containsKey(tac.get(2)) &&  !s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0" + ","  + tac.get(4));
            mips.add("div " + s  + ", $t0, " + s_reg.get(tac.get(2)));
        }

    }
    public void createMult(ArrayList<String> tac, HashMap<String, String> s_reg){
        if(isNumber(tac.get(2)) && isNumber(tac.get(4))) {
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0" + ","+tac.get(2));
            mips.add("li $t1" + ","+tac.get(4));
            mips.add("mul " + s + ", $t0, $t1");
        }
        else if(s_reg.containsKey(tac.get(2)) &&  s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("mul " + s + ", " + s_reg.get(tac.get(2))  + ", " + s_reg.get(tac.get(4)));
        }
        else if(!s_reg.containsKey(tac.get(2)) &&  s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0" + ","  + tac.get(2));
            mips.add("mul " + s + ", $t0, " + s_reg.get(tac.get(4)));

        }
        else if(s_reg.containsKey(tac.get(2)) &&  !s_reg.containsKey(tac.get(4))){
            String s = s_reg.get(tac.get(0));
            mips.add("li $t0, "  + tac.get(4));
            mips.add("mul " + s + ", $t0, " + s_reg.get(tac.get(2)));
        }

    }
    public String findOp(String op){
        String s = "";
        switch (op) {
            case "+":
                s = "ADD";
                break;
            case "-":
                s = "SUB";
                break;
            case "*":
                s = "MULT";
                break;
            case "/":
                s = "DIV";
                break;
        }
        return s;

    }
    public boolean isNumber(String s){
        String regex = "[0-9]+";
        return s.matches(regex);
    }
    public boolean variableExist(String tac , HashMap<String, String> s_reg) {
        return !isRegisterEmpty(s_reg) && s_reg.containsKey(tac);
    }

    public boolean isRegisterEmpty(HashMap<String, String> s_reg) {
        return s_reg.size() != 0;
    }
}

