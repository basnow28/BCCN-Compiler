package lasalle.MPIS;

import lasalle.tac.TacResultsArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class InstructionFactory {
    private static int num_v = 0;
    private ArrayList<String> mips = new ArrayList<>();
    //HashMap of <mips_register, register name>
    private static HashMap<String, String> s_reg;
    private ArrayList<ArrayList<String>> tacResultsArray;



    public InstructionFactory() {

    }

    public static HashMap<String, String> getS_reg() {
        return s_reg;
    }

    public static void setS_reg(HashMap<String, String> s_reg) {
        InstructionFactory.s_reg = s_reg;
    }

    public static int getNum_v() {
        return num_v;
    }

    public static void setNum_v(int num_v) {
        InstructionFactory.num_v = num_v;
    }

    public ArrayList<String> getMips() {
        return mips;
    }

    public void setMips(ArrayList<String> mips) {
        this.mips = mips;
    }


    public ArrayList<ArrayList<String>> getTacResultsArray() {
        return tacResultsArray;
    }

    public void setTacResultsArray(ArrayList<ArrayList<String>> tacResultsArray) {
        this.tacResultsArray = tacResultsArray;
    }

    public void configMIPS(){
        this.mips = new ArrayList<String>();
        this.mips.add(".data");
        this.mips.add("");
        this.mips.add(".text");
        this.mips.add("");
       // s_reg.put(null, "$s0");
        s_reg = new HashMap<>();

    }

    public void createMIPSInstructions() throws IOException {
        configMIPS();


        for(int i = 0; i< tacResultsArray.size(); i++){
                if(tacResultsArray.get(i).get(0).contains("L")){
                    String string = tacResultsArray.get(i).get(0);
                    mips.add(string);
                    tacResultsArray.get(i).remove(0);
                }

            Instruction instruction;
                switch(tacResultsArray.get(i).size()){
                    case 2:
                        instruction = getInstructionType("UNCONDITIONAL-JUMP");
                        break;
                    case 3:
                        instruction = getInstructionType("ASSIGNED");
                        break;
                    case 5:
                        instruction = getInstructionType("EXPRESSION");
                        break;
                    case 6:
                        instruction = getInstructionType("CONDITIONAL-JUMP");
                        break;
                    default:
                        instruction = (tac, s_reg) -> null;
                        break;

                }
            mips.addAll(instruction.createAssemblyInstruction(tacResultsArray.get(i), s_reg));
        }
        printAllMIPS();

    }

    private void printAllMIPS() throws IOException {
        FileWriter fileWriter = new FileWriter("mips.txt");
        for(int i = 0;  i< mips.size(); i++){
            fileWriter.write(mips.get(i));
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    public Instruction getInstructionType(String type){
        if(type.equals("EXPRESSION")){
            return new ExpressionInstruction();
        }
        else if(type.equals("ASSIGNED")){
            return new AssignmentInstruction();
        }
        else if(type.equals("UNCONDITIONAL-JUMP")){
            return new UnconditionalJumpInstruction();
        }
        else if(type.equals("CONDITIONAL-JUMP")){
            return new ConditionalJumpInstruction();
        }

        return null;
    }


}
