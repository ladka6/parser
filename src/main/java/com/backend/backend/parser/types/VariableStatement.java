package com.backend.backend.parser.types;

import java.util.List;

import org.json.JSONObject;

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
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("declarations", getDeclarations());

        return json.toString();
    }
    
}
