package com.backend.backend.parser.types;

import java.util.List;

public class VariableStatement implements Expression{
    private TypeEnum type;
    private List<VariableDeclaration> declarations;

    public VariableStatement(TypeEnum type, List<VariableDeclaration> declarations) {
        this.type = type;
        this.declarations = declarations;
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
            ", declarations='" + getDeclarations() + "'" +
            "}";
    }
    
}
