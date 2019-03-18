package Joosc.ASTModel.Statements;

import Joosc.ASTBuilding.ASTStructures.Statements.ForStatementNode;
import Joosc.ASTModel.Expressions.ConstantExpression;
import Joosc.ASTModel.Expressions.Expression;
import Joosc.Environment.Env;
import Joosc.Environment.LocalEnv;
import Joosc.Exceptions.NamingResolveException;
import Joosc.Exceptions.TypeCheckException;
import Joosc.Exceptions.UnreachableStatementException;

import java.util.ArrayList;

public class ForStatement extends HasScope implements Statement, HasExpression {
    private Statement forInit = null;
    private Expression expression = null;
    private Statement forUpdate = null;
    private Statement statement;
    public boolean in,out;

    public ForStatement(ForStatementNode node) {
        forInit = Statement.convertStatementNode(node.getForInit());
        forUpdate = Statement.convertStatementNode(node.getForUpdate());
        expression = Expression.convertExpressionNode(node.getExpression());
        statement = Statement.convertStatementNode(node.getStatement());
    }

    public Statement getStatement() {
        return statement;
    }

    public Expression getExpression() {
        return expression;
    }

    public Statement getForInit() {
        return forInit;
    }

    public Statement getForUpdate() {
        return forUpdate;
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
    public void checkExpression(Env env) throws NamingResolveException {

    }

    @Override
    public void checkType() throws TypeCheckException {
        if(forInit instanceof HasExpression) ((HasExpression) forInit).checkType();
        if(forUpdate instanceof HasExpression) ((HasExpression) forUpdate).checkType();
        if(statement instanceof HasExpression) ((HasExpression) statement).checkType();
        expression.getType();
    }

    @Override
    public void reachabilityAnalysis(boolean input) throws UnreachableStatementException {
        in = input;
        if (!in) {
            throw new UnreachableStatementException("Unreachable statement");
        }
        if (expression == null) {
            statement.reachabilityAnalysis(in);
            out = false;
        } else {    // Cond exp is not null
            if (expression.isConstantExpression()) {
                ConstantExpression constantExpression = (ConstantExpression) expression;
                // Type checked. It must be a boolean exp. true or false
                if(constantExpression.evaluateConstant().toBoolean()) {
                    statement.reachabilityAnalysis(in);
                    out = false;
                } else {
                    statement.reachabilityAnalysis(false);
                    out = in;
                }
            } else {
                statement.reachabilityAnalysis(in);
                out = in;
            }
        }
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
