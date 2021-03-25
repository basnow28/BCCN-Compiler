package lasalle.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CodeReader {

    public static ArrayList<ArrayList<String>> readTheFile(String fileName )throws IOException {
        ArrayList<ArrayList<String>> linesOfCode = new ArrayList<ArrayList<String>>();
        BufferedReader bufferedReader= new BufferedReader(new FileReader(fileName));
        String line = "";
        Scanner scanner= null;

        while((line = bufferedReader.readLine()) != null){
            linesOfCode.add(new ArrayList<String>());
            int lineNumber=linesOfCode.size() -1;
            scanner = new Scanner(line);
            while(scanner.hasNext()){
                linesOfCode.get(lineNumber).add(scanner.next());
            }
        }
        return linesOfCode;
    }

}
