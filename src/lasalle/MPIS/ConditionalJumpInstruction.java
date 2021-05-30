package lasalle.MPIS;

import java.util.ArrayList;
import java.util.HashMap;

public class ConditionalJumpInstruction implements Instruction{
    //if x  = y goto label into "beq $result, $"
    private String result;
    private String op;
    private ArrayList<String> mips;
    public ConditionalJumpInstruction() {
        this.mips = new ArrayList<>();
    }


    public ArrayList<String> getMips() {
        return mips;
    }

    public void setMips(ArrayList<String> mips) {
        this.mips = mips;
    }

    public void createBeq(String  label){
        String s = "beq $t0, $t1, " + label;
        mips.add(s);
    }
    public void createBlt(String  label){
        String s = "blt $t0, $t1, "+ label;
        mips.add(s);
    }
    public void createBle(String  label){
        String s = "ble $t0, $t1, " + label;
        mips.add(s);
    }
    public void createBgt(String  label){
        String s = "bgt $t0, $t1, " + label;
        mips.add(s);
    }
    public void createBge(String  label){
        String s = "bge $t0, $t1, " + label;
        mips.add(s);
    }
    public void createBne(String  label){
        String s = "bne $t0, $t1, " + label;
        mips.add(s);
    }

    @Override
    public ArrayList<String> createAssemblyInstruction(ArrayList<String> tac, HashMap<String, String> s_reg) {
        if(s_reg.containsKey(tac.get(3))){
            String s = "move $t0, " + s_reg.get(tac.get(1));
            String s1 = "move $t1, " + s_reg.get(tac.get(3));
            mips.add(s);
            mips.add(s1);

        }
        else{
            String s = "move $t0, " + s_reg.get(tac.get(1));
            String s1 = "li $t1, " + tac.get(3);
            mips.add(s);
            mips.add(s1);
        }
        String s = tac.get(2);
        String  label = tac.get(5);
        switch(s){
            case "=":
                createBeq(label);
                break;
            case "<":
                createBlt(label);
                break;
            case "<=":
                createBle(label);
                break;
            case ">":
                createBgt(label);
                break;
            case ">=":
                createBge(label);
                break;
            case "!=":
                createBne(label);
                break;

        }
        return mips;
    }
}
