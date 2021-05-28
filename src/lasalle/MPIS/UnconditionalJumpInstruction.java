package lasalle.MPIS;

import java.util.ArrayList;
import java.util.HashMap;

public class UnconditionalJumpInstruction implements Instruction{
    private ArrayList<String> mips;

    public UnconditionalJumpInstruction() {
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
        //"goto label" into label j
        String instruction = "j " + tac.get(1);
        mips.add(instruction);
        return getMips();
    }
}
