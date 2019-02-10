package Joosc.AST.ASTStructures;

import Joosc.Parser.LRGrammar.ParseTree;

public class ClassBodyNode extends ASTNode {

    public ClassBodyNode(ParseTree parseTree) {
        this.parseTree = parseTree;
    }

    @Override
    public void weed() {

    }

    @Override
    public void printInfo(int level) {
        for (int i = 0; i < level; i += 1) {
            System.out.print(TREELEVEL);
        }
        System.out.println(TREEITEM + "Class Body Node:");
        String prefix = new String(new char[level+1]).replace("\0", TREELEVEL);
    }
}
