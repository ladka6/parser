package com.backend.backend.parser.types;

import java.util.List;

public class VariableStatement implements Expression {
    private TypeEnum type;
    private String typeAnnotation;
    private List<VariableDeclaration> declarations;

    public VariableStatement(TypeEnum type, String typeAnnotation, List<VariableDeclaration> declarations) {
        this.type = type;
        this.declarations = declarations;
        this.typeAnnotation = typeAnnotation;
    }

    public String getTypeAnnotation() {
        return this.typeAnnotation;
    }

    public void setTypeAnnotation(String typeAnnotation) {
        this.typeAnnotation = typeAnnotation;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public List<VariableDeclaration> getDeclarations() {
        return this.declarations;
    }

    public void setDeclarations(List<VariableDeclaration> declarations) {
        this.declarations = declarations;
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                ", typeAnnotation='" + getTypeAnnotation() + "'" +
                ", declarations='" + getDeclarations() + "'" +
                "}";
    }

}
