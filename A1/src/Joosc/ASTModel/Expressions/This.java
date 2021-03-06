package Joosc.ASTModel.Expressions;

import Joosc.AsmWriter.AsmWriter;
import Joosc.Environment.Env;
import Joosc.Exceptions.NamingResolveException;
import Joosc.Exceptions.TypeCheckException;
import Joosc.Exceptions.UnreachableStatementException;
import Joosc.TypeSystem.JoosType;

import java.util.ArrayList;
import java.util.HashSet;

public class This extends ExpressionContent {
    @Override
    public Env validate() throws NamingResolveException {
        joosType = getEnv().getJoosType();
        return null;
    }

    @Override
    public JoosType getType() throws TypeCheckException {
        ArrayList<String> currentClassFullName = getEnv().getCurrentClass().getCanonicalName();
        joosType = JoosType.getJoosType(currentClassFullName);
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
