package Joosc;

import Joosc.ASTBuilding.JoosAST;
import Joosc.ASTModel.Program;
import Joosc.AsmWriter.AsmWriter;
import Joosc.AsmWriter.Register;
import Joosc.Environment.GlobalEnv;
import Joosc.Exceptions.*;

import Joosc.Parser.JoosParse;
import Joosc.Parser.LRGrammar.ParseTree;
import Joosc.Scanner.JoosScan;
import Joosc.Token.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Joosc {
    public static boolean IDE_FLAG = true;
    public static boolean RUN_SUITE_FLAG = false;

    private static String getFileName(String path) {
        String[] temp = path.split("/");
        return temp[temp.length - 1];
    }

    public static int exitOnCode(int code) {
        if (!RUN_SUITE_FLAG) {
            System.exit(code);
        }
        return code;
    }

    private static JoosAST process(String filename) throws Exception {
        JoosScan scan = new JoosScan(new File(filename));
        String file = getFileName(filename);
        scan.scan();
        ArrayList<Token> tokens = scan.getOutput();

        // Parsing
        JoosParse parse = new JoosParse();
        parse.parse(tokens);
        ParseTree tree = parse.getTree();

        JoosAST ast = new JoosAST(tree);
        ast.checkFileName(file);
        ast.weed();

        return ast;
    }

    public static int run(String args[]) {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(args));
        IDE_FLAG = !argList.contains("-t");
        RUN_SUITE_FLAG = argList.contains("-full");
        argList.remove("-t");
        argList.remove("-full");

        try {
            ArrayList<JoosAST> astList = new ArrayList<>();

            for (String filename : argList) {
                astList.add(process(filename));
            }

            ArrayList<Program> asts = astList.stream().map(x -> new Program(x.getRoot()))
                    .collect(Collectors.toCollection(ArrayList::new));

            GlobalEnv globalEnvironment = new GlobalEnv(asts);
            globalEnvironment.semanticAnalysis();

            AsmWriter.initTable();
            for (Program ast : asts) {
                Program.globalCount = 0;
                ast.staticAnalysis();
                ast.codeGen(0);
            }

            AsmWriter asm = new AsmWriter(System.out);
            asm.outputInit();

        } catch (UnreachableStatementException e) {
            System.err.printf("ERROR: Static analysis error: %s\n", e.getLocalizedMessage());
            e.printStackTrace();
            return exitOnCode(42);
        } catch (TypeCheckException e) {
            System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
            e.printStackTrace();
            return exitOnCode(42);
        } catch (NamingResolveException e) {
            System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
            e.printStackTrace();
            return exitOnCode(42);
        } catch (FileNotFoundException e) {
            System.err.printf("ERROR: file not found: %s\n", e.getLocalizedMessage());
            return exitOnCode(2);
        } catch (InvalidCharacterException e) {
            System.err.printf("ERROR: invalid characters %s\n", e.getInvalidChar());
            return exitOnCode(42);
        } catch (InvalidTokenException e) {
            System.err.printf("ERROR: invalid lexeme: {%s} (%c, %d)\n", e.getInvalidLexeme(), e.getCurChar(), (int) e.getCurChar());
            e.printExistingTokens();
            return exitOnCode(42);
        } catch (InvalidSyntaxException e) {
            System.err.printf("ERROR: invalid syntax at %d, on state %d, with input %s\n", e.getLocation(), e.getState(), e.getInput());
            return exitOnCode(42);
        } catch (InvalidParseTreeStructureException e) {
            e.printStackTrace();
            System.err.printf("ERROR: %s", e.getLocalizedMessage());
            return exitOnCode(42);
        } catch (InvalidParseTreeException e) {
            System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
            return exitOnCode(42);
        } catch (WrongFileNameException e) {
            System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
            return exitOnCode(42);
        } catch (JoosException e) {
            e.printStackTrace();
            System.err.println("Something wrong with your shit...");
            System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
            return exitOnCode(42);
        } catch (WeedingFailureException e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
            return exitOnCode(42);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return exitOnCode(4);
        }
        catch (Exception e) {
            e.printStackTrace();
            return exitOnCode(3);
        }
        return exitOnCode(0);
    }

    public static void main(String args[]) {
        run(args);
    }
}