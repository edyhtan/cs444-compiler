package Joosc.ASTModel.Statements;

import Joosc.ASTBuilding.ASTStructures.Statements.WhileStatementNode;
import Joosc.ASTModel.Expressions.Expression;
import Joosc.Environment.LocalEnv;
import Joosc.Exceptions.NamingResolveException;

import java.util.ArrayList;

public class WhileStatement extends HasScope implements Statement, HasExpression {
    private Expression expression;
    private Statement statement;

    public WhileStatement(WhileStatementNode node) {
        expression = Expression.convertExpressionNode(node.getExpression());
        statement = Statement.convertStatementNode(node.getStatement());
    }

    public Expression getExpression() {
        return expression;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public ArrayList<Statement> getBlock() {
        if (statement instanceof Block) {
            return ((Block) statement).getBlock();
        } else {
            ArrayList<Statement> statements = new ArrayList<>();
            statements.add(statement);
            return statements;
        }
    }

    @Override
    public void passDownScopes() {

    }

    @Override
    public void checkExpression(LocalEnv env) throws NamingResolveException {

    }
}
