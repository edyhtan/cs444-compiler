package Joosc.ASTModel.Statements;

import Joosc.ASTBuilding.ASTStructures.Statements.LocalVarDeclrStatementNode;
import Joosc.ASTModel.Expressions.Expression;
import Joosc.ASTModel.Type;
import Joosc.AsmWriter.AsmWriter;
import Joosc.AsmWriter.Register;
import Joosc.Environment.Env;
import Joosc.Environment.FieldsVarInfo;
import Joosc.Exceptions.NamingResolveException;
import Joosc.Exceptions.TypeCheckException;
import Joosc.Exceptions.UnreachableStatementException;
import Joosc.TypeSystem.JoosType;

public class LocalVarDeclrStatement implements Statement, HasExpression {
    private Type type;
    private String id;
    private Expression initExpression;
    FieldsVarInfo info;
    public boolean in, out;
    private boolean parentIsStatic;

    public LocalVarDeclrStatement(LocalVarDeclrStatementNode node) {
        type = new Type(node.getType());
        id = node.getId();
        initExpression = Expression.convertExpressionNode(node.getInitExpression());
    }

    public String getId() {
        return id;
    }

    public Expression getInitExpression() {
        return initExpression;
    }

    public Type getType() {
        return type;
    }

    public void addInfo(FieldsVarInfo info) {
        this.info = info;
    }

    @Override
    public void checkExpression(Env env) throws NamingResolveException {
        if (initExpression != null) {
            initExpression.addEnv(env);
            initExpression.validate();
        }
    }

    @Override
    public void checkType() throws TypeCheckException {
        // check declared type matches initExpr type
        if (initExpression == null) {
            return;
        }

        JoosType initExprType = initExpression.getType();

        if (!info.getTypeInfo().getJoosType().assignable(initExprType)) {
            throw new TypeCheckException(String.format("Incompatible Type: %s, %s",
                    String.join(".", info.getTypeInfo().getJoosType().getTypeName()),
                    String.join(".", initExprType.getTypeName())));
        }
    }

    @Override
    public void reachabilityAnalysis(boolean input) throws UnreachableStatementException {
        in = input;
        if (!in) {
            throw new UnreachableStatementException("Unreachable statement");
        }
        if (initExpression == null) {
            throw new UnreachableStatementException("Local variable declare without initialization");
        }

        initExpression.localVarSelfReference(id);

        out = input;
    }

    @Override
    public boolean getIn() {
        return in;
    }

    @Override
    public boolean getOut() {
        return out;
    }

    public void setParentIsStatic(boolean parentIsStatic) {
        this.parentIsStatic = parentIsStatic;
    }

    //Code Gen
    AsmWriter asmWriter;

    @Override
    public void codeGen(int indent) {
        asmWriter.indent(indent);
        asmWriter.sub(Register.esp, 4);
        initExpression.addWriter(asmWriter);
        initExpression.codeGen(indent);
        asmWriter.println("");

        asmWriter.indent(indent);
        String tmp = Register.ebp + String.valueOf(info.getOffset());
        asmWriter.movToAddr(tmp, Register.eax);
    }

    @Override
    public void addWriter(AsmWriter writer) {
        asmWriter = writer;
    }
}
