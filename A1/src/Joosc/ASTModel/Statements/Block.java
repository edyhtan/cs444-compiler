package Joosc.ASTModel.Statements;

import Joosc.ASTBuilding.ASTStructures.Statements.BlockNode;
import Joosc.Environment.LocalEnv;
import Joosc.Exceptions.UnreachableStatementException;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Block extends HasScope implements Statement {
    private ArrayList<Statement> statements = new ArrayList<>();
    private LocalEnv env;
    public boolean in, out;
    private boolean parentIsStatic;

    public Block(Statement statement) {
        statements.add(statement);
    }

    public Block(BlockNode node) {
        if (node.getStatements() != null) {
            statements = node.getStatements().stream().map(stmt -> Statement.convertStatementNode(stmt))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    @Override
    public ArrayList<Statement> getBlock() {
        return statements;
    }

    @Override
    public void passDownScopes() {

    }

    @Override
    public void reachabilityAnalysis(boolean input) throws UnreachableStatementException {
        in = input;
        if (!in) {
            throw new UnreachableStatementException("Unreachable statement");
        }
        boolean lastOut = in;
        for (Statement stmt : statements) {
            if (!lastOut) {
                throw new UnreachableStatementException();
            }
            stmt.reachabilityAnalysis(lastOut);
            lastOut = stmt.getOut();
        }
        out = lastOut;
    }

    @Override
    public boolean getIn() {
        return in;
    }

    @Override
    public boolean getOut() {
        return out;
    }

}
