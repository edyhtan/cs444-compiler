package Joosc.Environment;

import Joosc.ASTBuilding.Constants.Symbol;

import java.util.ArrayList;

public class FieldsVarInfo {
    String name;
    TypeInfo typeInfo;
    ArrayList<Symbol> modifiers;
    int offset;

    public FieldsVarInfo(String name, TypeInfo typeInfo, ArrayList<Symbol> modifiers) {
        this.name = name;
        this.typeInfo = typeInfo;
        this.modifiers = modifiers;
    }

    public String getFieldName() {
        return name;
    }

    public String getFullTypeName() {
        return String.join(".",typeInfo.fullName);
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public boolean isTypeArray() {
        return typeInfo.isArray;
    }

    public ArrayList<Symbol> getModifiers() {
        return modifiers;
    }

    public boolean isTypePrimitive() {
        return typeInfo.isPrimitive();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
