package com.backend.backend.parser.types;

import org.json.JSONObject;

public class DoWhileStatement implements Expression{
    private TypeEnum type;
    private Expression body;
    private Expression test;

    public DoWhileStatement(TypeEnum type, Expression body, Expression test) {
        this.type = type;
        this.body = body;
        this.test = test;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Expression getBody() {
        return this.body;
    }

    public void setBody(Expression body) {
        this.body = body;
    }

    public Expression getTest() {
        return this.test;
    }

    public void setTest(Expression test) {
        this.test = test;
    }

        @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("body", getBody());
        json.put("test", getTest());

        return json.toString();
    }

}
