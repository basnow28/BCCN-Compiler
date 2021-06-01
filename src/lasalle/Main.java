package lasalle;

import lasalle.lexicalAnalyser.LexicalAnalyser;
import lasalle.lexicalAnalyser.LexicalArray;
import lasalle.reader.CodeReader;
import lasalle.syntaxAnalyzer.FirstAndFollow;
import lasalle.syntaxAnalyzer.MapKey;
import lasalle.syntaxAnalyzer.ParsingTable;
import lasalle.syntaxAnalyzer.SyntaxAnalyzer;
import lasalle.tables.*;
import lasalle.tac.SmallTrees;
import lasalle.tac.TacResultsArray;
import lasalle.trees.ParsingTree;
import lasalle.trees.ParsingTreeNode;
import lasalle.trees.ParsingTreeNodeIterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        LexicalArray lexicalArray;
        try {
            /*;


            System.out.println(lexicalArray.getLexicalArray());
            System.out.println(IntegerTable.getValues());

            //Syntax Analysis of the Code*/

            ArrayList<ArrayList<String>> code = CodeReader.readTheFile("file.txt");
            lexicalArray = new LexicalArray(LexicalAnalyser.readTheFile(code));
            SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();

            FirstAndFollow.populateFirstAndFollowFromGrammarFile();
            System.out.println("NonTerminal List: " + ParsingTable.nonTerminalsList);
            ParsingTable parsingTable = new ParsingTable();
            parsingTable.populateParsingTable(FirstAndFollow.firstAndFollow);
            System.out.println("First and Follow" + FirstAndFollow.firstAndFollow);
            System.out.println(parsingTable);
            for(Map.Entry<MapKey, String> tempEntry :  ParsingTable.temporaryParsingTable.entrySet()){
                System.out.print("Non Terminal: " + tempEntry.getKey().getNonTerminal());
                System.out.print(" Terminal: " + tempEntry.getKey().getTerminal());
                System.out.println(" Value: " + tempEntry.getValue());
            }

            syntaxAnalyzer.validateCode(lexicalArray, parsingTable);

            /*System.out.println(IdentifierTable.getValues());
            System.out.println(IntegerTable.getValues());
            System.out.println(FloatTable.getValues());
            System.out.println(CharTable.getValues());
            System.out.println(BooleanTable.getValues());*/

            Iterator<ParsingTreeNode<String>> iterator = ParsingTree.getParsingTree().iterator();
            ParsingTreeNode<String> node = ParsingTree.getParsingTree();
        /*    System.out.println("Parsing Tree");
            while(iterator.hasNext()){
                System.out.println(iterator.next());
            }*/

            TacResultsArray tagResultsArray = new TacResultsArray();
            SmallTrees smallTrees = new SmallTrees();
            smallTrees.createSmallTrees();

        }
        catch (IOException e){
            System.out.print(e.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
