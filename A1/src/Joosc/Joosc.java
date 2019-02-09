package Joosc;

import Joosc.Exceptions.InvalidCharacterException;
<<<<<<< HEAD
import Joosc.Exceptions.InvalidParseTreeStructureException;
=======
import Joosc.Exceptions.InvalidParseTreeException;
>>>>>>> A1
import Joosc.Exceptions.InvalidSyntaxException;
import Joosc.Exceptions.InvalidTokenException;
import Joosc.Parser.JoosParse;
import Joosc.Parser.LRGrammar.ParseTree;
import Joosc.Scanner.JoosScan;
import Joosc.Token.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Joosc {
    public static boolean IDE_FLAG = true;

    public static void main(String args[]) {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(args));
        IDE_FLAG = !argList.contains("-t");
        argList.remove("-t");

        try {
            if (argList.size() > 1) {
                throw new Exception("ERROR: incorrect number of parameter, the size should be 1.");
            }

            JoosScan scan = new JoosScan(new File(argList.get(0)));
            scan.scan();
            ArrayList<Token> tokens = scan.getOutput();

            int i = 1;
            for (Token token : tokens) {
                System.out.printf("%d. %-30s :  %15s\n", i, token.getLexeme(), token.getKind());
                i++;
            }
            System.out.println();

            // TODO: add parsing
            JoosParse parse = new JoosParse();
            parse.parse(tokens);
            ParseTree tree = parse.getTree();
        } catch (FileNotFoundException e) {
            System.err.printf("ERROR: file not found: %s\n", e.getLocalizedMessage());
            System.exit(2);
        } catch (InvalidCharacterException e) {
            System.err.printf("ERROR: invalid characters %s\n", e.getInvalidChar());
            System.exit(42);
        } catch (InvalidTokenException e) {
            System.err.printf("ERROR: invalid lexeme: {%s} (%c, %d)\n", e.getInvalidLexeme(), e.getCurChar(), (int) e.getCurChar());
            e.printExistingTokens();
            System.exit(42);
        } catch (InvalidSyntaxException e) {
            System.err.printf("ERROR: invalid syntax at %d, on state %d, with input %s\n", e.getLocation(), e.getState(), e.getInput());
            e.printParseTree();
            System.exit(42);
        } catch (InvalidParseTreeStructureException e) {
            System.err.printf("ERROR: %s", e.getLocalizedMessage());
            System.exit(42);
        } catch (InvalidParseTreeException e) {
           System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
           System.exit(42);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}