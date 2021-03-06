package Joosc.ASTModel.Expressions;

import Joosc.ASTBuilding.ASTStructures.Expressions.ExpressionBinaryNode;
import Joosc.ASTBuilding.Constants.Symbol;
import Joosc.AsmWriter.AsmWriter;
import Joosc.Environment.ClassEnv;
import Joosc.Environment.Env;
import Joosc.Exceptions.NamingResolveException;
import Joosc.Exceptions.TypeCheckException;
import Joosc.Exceptions.UnreachableStatementException;
import Joosc.TypeSystem.JoosType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ExpressionBinary extends Expression implements ConstantExpression {
    private Symbol kind, operator;
    private Expression LHS, RHS;
    private ConstantLiteral constantLiteral = null;

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

        LHS.setParentIsStatic(this.parentIsStatic);
        RHS.setParentIsStatic(this.parentIsStatic);

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
                            lhsType.getTypeName(), rhsType.getTypeName()));
                }
                break;
            // assignability
            case Equal:
                if (lhsType.assignable(rhsType) && !LHS.isFinal()) {
                    joosType = lhsType;
                } else {
                    throw new TypeCheckException(String.format("Assignment type incompatible: %s, %s",
                            lhsType.getTypeName(), rhsType.getTypeName()));
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
                // TODO: JLS 15.22 15.23 says both side must be boolean. We should use && here.
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

    @Override
    public boolean isConstantExpression() {
        if (constantLiteral != null) {
            return true;
        }

        if (LHS.isConstantExpression() && RHS.isConstantExpression()) {
            ConstantLiteral LHS = ((ConstantExpression) this.LHS).evaluateConstant();
            ConstantLiteral RHS = ((ConstantExpression) this.RHS).evaluateConstant();
            switch (operator) {
                case Plus:
                    // TODO: Questionable type, looks like byte + byte is int? ↑
                    if (LHS.type.isString() || RHS.type.isString()) {
                        constantLiteral = new ConstantLiteral(LHS.literal + RHS.literal, JoosType.getJoosType(new ArrayList<>(Arrays.asList("java", "lang", "String"))));
                    } else {
                        constantLiteral = new ConstantLiteral(LHS.toInt() + RHS.toInt(), JoosType.getJoosType("int"));
                    }
                    return true;
                case Equal:
                    return false;
                case Minus:
                    constantLiteral = new ConstantLiteral(LHS.toInt() - RHS.toInt(), JoosType.getJoosType("int"));
                    return true;
                case Star:
                    constantLiteral = new ConstantLiteral(LHS.toInt() * RHS.toInt(), JoosType.getJoosType("int"));
                    return true;
                case Slash:
                    constantLiteral = new ConstantLiteral(LHS.toInt() / RHS.toInt(), JoosType.getJoosType("int"));
                    return true;
                case Percent:
                    constantLiteral = new ConstantLiteral(LHS.toInt() % RHS.toInt(), JoosType.getJoosType("int"));
                    return true;
                case EQ:
                    if (LHS.type.isString() || RHS.type.isString()) {
                        constantLiteral = new ConstantLiteral(LHS.literal == RHS.literal, JoosType.getJoosType("boolean"));
                    } else {
                        constantLiteral = new ConstantLiteral(LHS.literal.equals(RHS.literal), JoosType.getJoosType("boolean"));
                    }
                    return true;
                case NE:
                    if (LHS.type.isString() || RHS.type.isString()) {
                        constantLiteral = new ConstantLiteral(LHS.literal != RHS.literal, JoosType.getJoosType("boolean"));
                    } else {
                        constantLiteral = new ConstantLiteral(!LHS.literal.equals(RHS.literal), JoosType.getJoosType("boolean"));
                    }
                    return true;
                case GE:
                    constantLiteral = new ConstantLiteral(LHS.toInt() >= RHS.toInt(), JoosType.getJoosType("boolean"));
                    return true;
                case GT:
                    constantLiteral = new ConstantLiteral(LHS.toInt() > RHS.toInt(), JoosType.getJoosType("boolean"));
                    return true;
                case LT:
                    constantLiteral = new ConstantLiteral(LHS.toInt() < RHS.toInt(), JoosType.getJoosType("boolean"));
                    return true;
                case LE:
                    constantLiteral = new ConstantLiteral(LHS.toInt() <= RHS.toInt(), JoosType.getJoosType("boolean"));
                    return true;
                case Instanceof:
                    return false;
                case And:
                    constantLiteral = new ConstantLiteral(LHS.toBoolean() && RHS.toBoolean(), JoosType.getJoosType("boolean"));
                    return true;
                case Or:
                    constantLiteral = new ConstantLiteral(LHS.toBoolean() || RHS.toBoolean(), JoosType.getJoosType("boolean"));
                    return true;
                case Cap:
                    constantLiteral = new ConstantLiteral(LHS.toBoolean() ^ RHS.toBoolean(), JoosType.getJoosType("boolean"));
                    return true;
                case Bar:
                    constantLiteral = new ConstantLiteral(LHS.toBoolean() | RHS.toBoolean(), JoosType.getJoosType("boolean"));
                    return true;
                case Amp:
                    constantLiteral = new ConstantLiteral(LHS.toBoolean() & RHS.toBoolean(), JoosType.getJoosType("boolean"));
                    return true;
            }
        }
        return false;
    }

    @Override
    public ConstantLiteral evaluateConstant() {
        return constantLiteral;
    }

    public void forwardDeclaration(String fieldname, HashSet<String> initializedName) throws TypeCheckException {
        switch (operator) {
            case Equal:
                LHS.setIsLHS(true);
            default:
                LHS.forwardDeclaration(fieldname, initializedName);
                RHS.forwardDeclaration(fieldname, initializedName);
        }
    }

    @Override
    public void localVarSelfReference(String id) throws UnreachableStatementException {
        LHS.localVarSelfReference(id);
        RHS.localVarSelfReference(id);
    }

    //Code Gen
    AsmWriter asmWriter;

    @Override
    public void codeGen(int indent) {

    }

    @Override
    public void addWriter(AsmWriter writer) {
        asmWriter = writer;
    }
}
