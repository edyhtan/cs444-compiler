package Joosc.ASTModel.Statements;

import Joosc.ASTModel.Expressions.Expression;
import Joosc.ASTBuilding.ASTStructures.Statements.IfStatementNode;
import Joosc.Environment.LocalEnv;

import java.util.ArrayList;


public class IfStatement extends HasScope implements Statement {
    private Expression expression;
    private Statement thenClause;
    private ElseBlock elseClause = null;
    private LocalEnv localEnv= null;

    public IfStatement(IfStatementNode node) {
        expression = Expression.convertExpressionNode(node.getExpression());
        thenClause = Statement.convertStatementNode(node.getThenClause());
        elseClause = new ElseBlock(Statement.convertStatementNode(node.getElseClause()));
    }

    public Expression getExpression() {
        return expression;
    }

    public ElseBlock getElseClause() {
        return elseClause;
    }

    public Statement getThenClause() {
        return thenClause;
    }

    @Override
    public ArrayList<Statement> getBlock() {
        if (thenClause instanceof Block) {
            return ((Block) thenClause).getBlock();
        } else {
            ArrayList<Statement> statements = new ArrayList<>();
            statements.add(thenClause);
            return statements;
        }
    }
}
