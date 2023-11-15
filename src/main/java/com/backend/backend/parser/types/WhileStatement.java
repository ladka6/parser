package com.backend.backend.parser.types;

import org.json.JSONObject;

public class WhileStatement implements Expression{
    private TypeEnum type;
    private Expression test;
    private Expression body; 

    public WhileStatement(TypeEnum type, Expression test, Expression body) {
        this.type = type;
        this.test = test;
        this.body = body;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Expression getTest() {
        return this.test;
    }

    public void setTest(Expression test) {
        this.test = test;
    }

    public Expression getBody() {
        return this.body;
    }

    public void setBody(Expression body) {
        this.body = body;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("test", getTest());
        json.put("body", getBody());

        return json.toString();
    }

}
