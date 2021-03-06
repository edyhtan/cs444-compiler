package Joosc.ASTModel.Expressions;

import Joosc.ASTBuilding.ASTStructures.Expressions.ExpressionTypeNode;
import Joosc.AsmWriter.AsmWriter;
import Joosc.Environment.Env;
import Joosc.Exceptions.NamingResolveException;
import Joosc.Exceptions.TypeCheckException;
import Joosc.Exceptions.UnreachableStatementException;
import Joosc.TypeSystem.ArrayType;
import Joosc.TypeSystem.JoosType;

import java.util.ArrayList;
import java.util.HashSet;

public class ExpressionType extends Expression {
    ArrayList<String> typeName;
    boolean isArrayType;

    ExpressionType(ExpressionTypeNode node) {
        typeName = new ArrayList<>(node.getTypeName());
        isArrayType = node.isArray();
    }

    @Override
    public void addEnv(Env env) {
        super.addEnv(env);
    }

    @Override
    public Env validate() throws NamingResolveException {
        joosType = getEnv().typeResolve(typeName);
        return null;
    }

    @Override
    public JoosType getType() throws TypeCheckException {
        if (isArrayType) {
            joosType = new ArrayType(joosType);
        }
        return joosType;
    }

    @Override
    public boolean isConstantExpression() {
        return false;
    }

    @Override
    public void forwardDeclaration(String fieldname, HashSet<String> initializedName) throws TypeCheckException { }

    @Override
    public void localVarSelfReference(String id) throws UnreachableStatementException { }

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
