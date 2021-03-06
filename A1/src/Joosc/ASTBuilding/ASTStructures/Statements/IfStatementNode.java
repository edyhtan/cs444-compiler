package Joosc.ASTBuilding.ASTStructures.Statements;

import Joosc.ASTBuilding.ASTStructures.Expressions.ExpressionNode;
import Joosc.Exceptions.ASTException;
import Joosc.Exceptions.WeedingFailureException;
import Joosc.Parser.LRGrammar.ParseTree;

import java.util.List;


public class IfStatementNode extends StatementNode {

    ExpressionNode expression;
    StatementNode thenClause;
    StatementNode elseClause = null;

    IfStatementNode(ParseTree tree) throws ASTException {
        List<ParseTree> children = tree.getChildren();

        expression = ExpressionNode.resolveExpressionNode(children.get(2));

        thenClause = StatementNode.resolveStatementNode(children.get(4));
        if (children.size() > 5) {
            elseClause = StatementNode.resolveStatementNode(children.get(6));
        }
    }

    @Override
    public void weed() throws WeedingFailureException {
        //expression.weed();
        thenClause.weed();
        if (elseClause != null) {
            elseClause.weed();
        }
    }

    @Override
    public void printInfo(int level) {
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public StatementNode getElseClause() {
        return elseClause;
    }

    public StatementNode getThenClause() {
        return thenClause;
    }
}
