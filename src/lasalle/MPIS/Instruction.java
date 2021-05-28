package lasalle.MPIS;

import java.util.ArrayList;
import java.util.HashMap;

public interface Instruction {
   ArrayList<String> createAssemblyInstruction(ArrayList<String>tac, HashMap<String, String> s_reg);
}
