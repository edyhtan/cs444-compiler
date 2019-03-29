package Joosc.ASTModel.Expressions;

import Joosc.ASTBuilding.ASTStructures.Expressions.ExpressionBinaryNode;
import Joosc.ASTBuilding.Constants.Symbol;
import Joosc.Environment.Env;
import Joosc.Exceptions.NamingResolveException;
import Joosc.Exceptions.TypeCheckException;
import Joosc.TypeSystem.JoosType;

import java.util.ArrayList;
import java.util.Arrays;

public class ExpressionBinary extends Expression {
    private Symbol kind, operator;
    private Expression LHS, RHS;

    public ExpressionBinary(ExpressionBinaryNode node) {
        kind = node.getKind();
        operator = node.getOperator();
        LHS = Expression.convertExpressionNode(node.getLHS());
        RHS = Expression.convertExpressionNode(node.getRHS());
    }

    public Symbol getOperator() {
        return operator;
    }

    public Symbol getKind() {
        return kind;
    }

    public Expression getLHS() {
        return LHS;
    }

    public Expression getRHS() {
        return RHS;
    }

    @Override
    public void addEnv(Env env) {
        super.addEnv(env);
        LHS.addEnv(env);
        RHS.addEnv(env);
    }

    @Override
    public Env validate() throws NamingResolveException {
        LHS.validate();
        RHS.validate();
        return null;
    }

    @Override
    public JoosType getType() throws TypeCheckException {
        ArrayList<String> string = new ArrayList<>(Arrays.asList("java", "lang", "String"));
        JoosType stringType = JoosType.getJoosType(string);

        JoosType lhsType = LHS.getType();
        JoosType rhsType = RHS.getType();

        switch (operator) {
            case Plus:
                if ((lhsType.getTypeName().equals(string) && !rhsType.equals("void"))
                        || (rhsType.getTypeName().equals(string) && !lhsType.equals("void"))) {
                    joosType = stringType;
                } else if (JoosType.isNumber(lhsType) && JoosType.isNumber(rhsType)) {
                    joosType = JoosType.getJoosType("int");
                } else {
                    throw new TypeCheckException(String.format("+ operator type incompatible: %s, %s",
                            lhsType.getTypeName() , rhsType.getTypeName()));
                }
                break;
            // assignability
            case Equal:
                if (lhsType.assignable(rhsType) && !LHS.isFinal()) {
                    joosType = lhsType;
                } else {
                    throw new TypeCheckException(String.format("Assignment type incompatible: %s, %s",
                                lhsType.getTypeName() , rhsType.getTypeName()));
                }
                break;
            // arithmetic operations
            case Minus:
            case Star:
            case Slash:
            case Percent:
                if (JoosType.isNumber(lhsType) && JoosType.isNumber(rhsType)) {
                    joosType = JoosType.getJoosType("int");
                } else {
                    throw new TypeCheckException(String.format("Arithmetic operations type incompatible: %s, %s",
                            lhsType.getTypeName(), rhsType.getTypeName()));
                }
                break;
            // comparison
            case EQ:
            case NE:
                if (lhsType.equals(JoosType.VOID) || rhsType.equals(JoosType.VOID)) {
                    throw new TypeCheckException("Void return value cannot be used in equality");
                }

                if ((JoosType.isNumber(lhsType) && JoosType.isNumber(rhsType))
                        || (lhsType.isA(rhsType) || rhsType.isA(lhsType))) {
                    joosType = JoosType.getJoosType("boolean");
                } else {
                    throw new TypeCheckException("Cannot compare type " + lhsType.getTypeName()
                            + " with " + rhsType.getTypeName());
                }
                break;
            case GE:
            case GT:
            case LT:
            case LE:
                if (lhsType.equals(JoosType.VOID) || rhsType.equals(JoosType.VOID)) {
                    throw new TypeCheckException("Void return value cannot be used in equality");
                }
                if (JoosType.isNumber(lhsType) && JoosType.isNumber(rhsType)) {
                    joosType = JoosType.getJoosType("boolean");
                } else {
                    throw new TypeCheckException(String.format("Comparison type incompatible: %s, %s",
                            lhsType.getTypeName(), rhsType.getTypeName()));
                }
                break;
            case Instanceof:
                if (lhsType.isA(rhsType) || rhsType.isA(lhsType)) {
                    joosType = JoosType.getJoosType("boolean");
                } else {
                    throw new TypeCheckException("Cannot check instanceof between type "
                            + lhsType.getTypeName() + " and " + rhsType.getTypeName());
                }
                break;
            // logical operations
            case And:
            case Or:
            case Cap:
            case Bar:
            case Amp:
                if (lhsType.equals("boolean") || rhsType.equals("boolean")) {
                    joosType = JoosType.getJoosType("boolean");
                } else {
                    throw new TypeCheckException(String.format("Logical operation type incompatible: %s, %s",
                            lhsType.getTypeName(), rhsType.getTypeName()));
                }
                break;
        }

        return joosType;
    }
}
