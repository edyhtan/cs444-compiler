package Joosc.ASTModel.Expressions;

import Joosc.ASTBuilding.ASTStructures.Expressions.ExpressionMethodInvocationNode;
import Joosc.ASTBuilding.Constants.Symbol;
import Joosc.AsmWriter.AsmWriter;
import Joosc.Environment.Env;
import Joosc.Environment.MethodInfo;
import Joosc.Exceptions.NamingResolveException;
import Joosc.Exceptions.TypeCheckException;
import Joosc.Exceptions.UnreachableStatementException;
import Joosc.TypeSystem.JoosType;
import Joosc.util.Tri;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ExpressionMethodInvocation extends ExpressionPrimary {
    private ArrayList<String> methodName;
    private ArrayList<Expression> argList;
    private Expression methodParentExpression;
    private String methodIdentifier;
    private boolean isStatic = false;

    private MethodInfo matchingMethod;


    public ExpressionMethodInvocation(ExpressionMethodInvocationNode node) {
        methodName = node.getMethodName();
        argList = node.getArgList().stream().map(Expression::convertExpressionNode)
                .collect(Collectors.toCollection(ArrayList::new));
        methodParentExpression = Expression.convertExpressionNode(node.getMethodParentExpression());
        methodIdentifier = node.getMethodIdentifier();
    }

    public String getMethodIdentifier() {
        return methodIdentifier;
    }

    public ArrayList<String> getMethodName() {
        return methodName;
    }

    public ArrayList<Expression> getArgList() {
        return argList;
    }

    public Expression getMethodParentExpression() {
        return methodParentExpression;
    }

    @Override
    public void addEnv(Env env) {
        super.addEnv(env);
        if (methodParentExpression != null) {
            methodParentExpression.addEnv(env);
        }
        argList.forEach(x -> x.addEnv(env));
    }

    @Override
    public Env validate() throws NamingResolveException {
        if (methodParentExpression != null) {
            methodParentExpression.validate();
        }

        for (Expression expression : argList) {
            expression.validate();
        }
        return null;
    }

    public String getMethodSimpleName() {
        if (methodName == null) {
            return methodIdentifier;
        } else {
            return methodName.get(methodName.size() - 1);
        }
    }

    @Override
    public void forwardDeclaration(String fieldname, HashSet<String> declared) throws TypeCheckException {
        if (!isLHS) {
            if (methodParentExpression != null) {
                methodParentExpression.forwardDeclaration(fieldname, declared);
            } else if (!isStatic && (methodName.get(0).equals(fieldname) || !declared.contains(methodName.get(0)))) {
                throw new TypeCheckException("field " + fieldname + " cannot be forward referenced");
            }
        }

        for (Expression arg : argList) {
            arg.forwardDeclaration(fieldname, declared);
        }
    }

    @Override
    public void localVarSelfReference(String id) throws UnreachableStatementException {
        if (methodParentExpression != null) {
            methodParentExpression.localVarSelfReference(id);
        } else if (!isStatic && (methodName.get(0).equals(id))) {
            throw new UnreachableStatementException("field " + id + " cannot be forward referenced");
        }

        for (Expression arg : argList) {
            arg.localVarSelfReference(id);
        }
    }

    @Override
    public JoosType getType() throws TypeCheckException {
        Env env;
        String simpleName;

        if (methodParentExpression != null) {
            methodParentExpression.setParentIsStatic(this.parentIsStatic);
        }

        if (methodName == null) {
            if (methodParentExpression.getType().isPrimitive()) {
                throw new TypeCheckException("Cannot invoke methods on primitive types");
            }
            env = methodParentExpression.getType().getClassEnv();
            simpleName = methodIdentifier;
        } else {
            boolean isDefaultPkg = false;
            if (getEnv().getCurrentClass().getClassEnv().getPackageDeclr() == null
                    || getEnv().getCurrentClass().getClassEnv().getPackageDeclr().isEmpty()) {
                isDefaultPkg = true;
            }


            Tri<Integer, Env, String> tri = Names.resolveAmbiguity(getEnv(), methodName, isDefaultPkg);
            env = tri.get2();
            simpleName = methodName.get(methodName.size() - 1);
            if ((tri.get1() & Names.isStatic) != 0) {
                isStatic = true;
            }
        }

        ArrayList<String> argTypeList = new ArrayList<>();
        argTypeList.add(simpleName);
        for (Expression arg : argList) {
            argTypeList.add(arg.getType().getQualifiedName());
        }

        MethodInfo matchingMethod = null;
        String callSignature = String.join(",", argTypeList);

        matchingMethod = env.getAllMethodSignature().getOrDefault(callSignature, null);

        if (matchingMethod == null) {
            throw new TypeCheckException("No matching method signature: " + callSignature);
        }
        joosType = matchingMethod.getReturnType().getJoosType();

        if (matchingMethod.getModifiers().contains(Symbol.Protected) && !getEnv().getJoosType().equals(env.getJoosType())) {
            JoosType accessType = env.getJoosType();
            if (!accessType.isA(getEnv().getJoosType()) && !getEnv().getJoosType().isA(accessType)) {
                throw new TypeCheckException("Protected Access on method " + callSignature);
            }

            if (getEnv().getJoosType().isA(accessType) && !matchingMethod.getModifiers().contains(Symbol.Static)) {
                throw new TypeCheckException("Protected Access on method " + callSignature);
            }

            if (accessType.isA(getEnv().getJoosType()) &&
                    env.getDeclaredMethodSignature().containsKey(matchingMethod.getSignatureStr())) {
                throw new TypeCheckException("Protected Access on method " + callSignature);
            }
        }

        if (matchingMethod.isStatic()) {
            if (methodName != null) {
                if (methodName.size() == 1) {
                    throw new TypeCheckException("Static method invoked without class accessor: " + callSignature);
                } else {
                    ArrayList<String> accessor = new ArrayList<>(methodName);
                    accessor.remove(accessor.size() - 1);
                    int i;
                    for (i = accessor.size() - 1; i >= 0; --i) {
                        if (getEnv().isLocalVariableDeclared(accessor.get(i))) {
                            throw new TypeCheckException("Static method accessed from instance: " + callSignature);
                        }
                    }
                }
            } else {
                if (methodParentExpression instanceof This) {
                    throw new TypeCheckException("Static method accessed from this: " + callSignature);
                }
            }
        } else { // non-static methods
            if (methodName != null) {
                if (methodName.size() > 1) {
                    ArrayList<String> accessor = new ArrayList<>(methodName);
                    accessor.remove(accessor.size() - 1);

                    boolean isDefaultPkg = false;
                    if (getEnv().getCurrentClass().getClassEnv().getPackageDeclr() == null) {
                        isDefaultPkg = true;
                    }

                    Tri<Integer, Env, String> accessorInfo = Names.resolveAmbiguity(env, accessor, isDefaultPkg);
                    ArrayList<String> accessorTypeName = accessorInfo.get2().getJoosType().getTypeName();

                    if (!getEnv().isLocalVariableDeclared(accessorInfo.get3())
                            && accessorTypeName.get(accessorTypeName.size() - 1).equals(accessorInfo.get3())) {
                        throw new TypeCheckException("Non-static method accessed from class: " + callSignature);
                    }
                } else { // size == 1
                    if (parentIsStatic)
                        throw new TypeCheckException("Implicit this access inside static method: " + callSignature);
                }
            }
            if (parentIsStatic && methodParentExpression instanceof This) {
                throw new TypeCheckException("Explicit this access inside static method: " + callSignature);
            }
        }
        this.matchingMethod = matchingMethod;

        return joosType;
    }

    @Override
    public boolean isConstantExpression() {
        return false;
    }

    //Code Gen
    AsmWriter asmWriter;

    @Override
    public void codeGen(int indent) {
        if(methodParentExpression != null) methodParentExpression.addWriter(asmWriter);



    }

    @Override
    public void addWriter(AsmWriter writer) {
        asmWriter = writer;
    }
}
