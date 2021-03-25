package lasalle.tables;

import java.util.ArrayList;
import java.util.Arrays;

public class OperatorTable {

    private static final ArrayList<String> operators = new ArrayList<String>(Arrays.asList(
            ">", "<", "AND", "OR",  "EQUALS", "NOTEQUALS", "+", "-", "%", "*", "/"));

    public static String identifyOperator(String token){
        for ( String op: operators  ) {
            if (op.equals(token)) {
                return op;
            }
        }
        return null;
    }
}
