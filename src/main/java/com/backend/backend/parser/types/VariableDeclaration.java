package com.backend.backend.parser.types;

import org.json.JSONObject;

public class VariableDeclaration implements Expression{
    private TypeEnum type;
    private Expression id;
    private Expression init;

    public VariableDeclaration(TypeEnum type, Expression id, Expression init) {
        this.type = type;
        this.id = id;
        this.init = init;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Expression getId() {
        return this.id;
    }

    public void setId(Expression id) {
        this.id = id;
    }

    public Expression getInit() {
        return this.init;
    }

    public void setInit(Expression init) {
        this.init = init;
    }

        @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("id", getId());
        json.put("init", getInit());

        return json.toString();
    }

}
