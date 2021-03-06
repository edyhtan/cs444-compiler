import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class CFGGen {
    private static BufferedWriter writer;

    private static void terminalRead() throws IOException {
        Scanner terminalRead = new Scanner(new File("input/terminals.txt"));
        ArrayList<String> writingBuffer = new ArrayList<>();

        while (terminalRead.hasNextLine()) {
            String str = terminalRead.nextLine();
            if (str.length() > 0 && !str.contains("#"))
                writingBuffer.add(str);
        }
        writeAll(writingBuffer);
    }

    private static void nonterminalRead() throws IOException {
        Scanner terminalRead = new Scanner(new File("input/non-terminals.txt"));
        ArrayList<String> writingBuffer = new ArrayList<>();

        while (terminalRead.hasNextLine()) {
            String str = terminalRead.nextLine();
            if (str.length() > 0 && !str.contains("#"))
                writingBuffer.add(str);
        }
        writeAll(writingBuffer);
    }

    private static void rulesRead() throws IOException{
        Scanner terminalRead = new Scanner(new File("input/rules.txt"));
        ArrayList<String> writingBuffer = new ArrayList<>();

        while (terminalRead.hasNextLine()) {
            String str = terminalRead.nextLine();
            if (str.length() > 0) {
                str = str.replace(":", "").replaceAll("\040+", " ");
            }
            if (str.length() > 0 && !str.contains("#"))
                writingBuffer.add(str);
        }
        writeAll(writingBuffer);
    }

    private static void writeAll(ArrayList<String> writeList) throws IOException{
        writer.append(Integer.toString(writeList.size()) + "\n");
        for (String str: writeList) {
            if (str.length() > 0 && !str.contains("#"))
                writer.append(str + "\n");
        }
    }

    public static void main(String args[]) {
        try {
            writer = new BufferedWriter(new FileWriter(JLR.INPUT_PATH));
            terminalRead();
            nonterminalRead();
            writer.append("S'" + "\n");
            rulesRead();

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
