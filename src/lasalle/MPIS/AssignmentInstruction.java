package lasalle.MPIS;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentInstruction implements Instruction {
    private ArrayList<String> mips;

    public AssignmentInstruction() {
        this.mips = new ArrayList<>();
    }


    public ArrayList<String> getMips() {
        return mips;
    }

    public void setMips(ArrayList<String> mips) {
        this.mips = mips;
    }

    //todo create li
    @Override
    public ArrayList<String> createAssemblyInstruction(ArrayList<String> tac, HashMap<String, String> s_reg) {
        //a = 1
        //create assignment instruction
        mips = new ArrayList<>();

        if (!isNumber(tac.get(2))) {
            createLu(tac, s_reg);
        }
        else {
            createLi(tac, s_reg);
        }
        return getMips();
    }

    public boolean isNumber(String s){
        String regex = "[0-9]+";
        if(s.matches(regex)){
            return true;
        }
        return false;
    }

    public void createLi(ArrayList<String> tac, HashMap<String, String> s_reg) {
        //if a is a key in the map we find it and use it
        if (s_reg.size() != 0 && s_reg.containsKey(tac.get(0))) {
            String s = s_reg.get(tac.get(0));
            mips.add("li " + s + ", " + tac.get(2));
        }
        //if doesnt exist we put a new value in the hashmap
        else {
            //if theres stuff in it
            if (s_reg.size() != 0) {
                int var = s_reg.size();
                String hash_value = "$s" + var;
                s_reg.put(tac.get(0), hash_value);
                mips.add("li " + hash_value + ", " + tac.get(2));
            }
            else {
                s_reg.put(tac.get(0), "$s0");
                mips.add("li $s0, " + tac.get(2));

            }


        }

    }

    public void createLu(ArrayList<String> tac, HashMap<String, String> s_reg) {
        //if a is a key in the map we find it and use it
        //a = n
        if (variableExist(tac.get(0), s_reg)) {
            String s = s_reg.get(tac.get(0));
            mips.add("move " + s + ", " + tac.get(2));
        }
        //if doesnt exist we put a new value in the hashmap
        else {
            String key = tac.get(2);
            String hash_value = "$s" + s_reg.size();
            mips.add("move " + hash_value + ", " + s_reg.get(tac.get(2)));
            s_reg.put(tac.get(0), hash_value);
            s_reg.put(tac.get(2), s_reg.get(key));

        }
    }


    public boolean variableExist(String tac , HashMap<String, String> s_reg) {
        return !isRegisterEmpty(s_reg) && s_reg.containsKey(tac);
    }

    public boolean isRegisterEmpty(HashMap<String, String> s_reg) {
        if (s_reg.size() != 0) {
            return true;
        }
        return false;
    }
}

