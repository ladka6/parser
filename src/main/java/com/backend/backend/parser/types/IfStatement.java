package com.backend.backend.parser.types;

import org.json.JSONObject;

public class IfStatement implements Expression {
    private TypeEnum type;
    private Expression test;
    private Expression consequent;
    private Expression alternate;

    public IfStatement(TypeEnum type, Expression test, Expression consequent, Expression alternate) {
        this.type = type;
        this.test = test;
        this.consequent = consequent;
        this.alternate = alternate;
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

    public Expression getConsequent() {
        return this.consequent;
    }

    public void setConsequent(Expression consequent) {
        this.consequent = consequent;
    }

    public Expression getAlternate() {
        return this.alternate;
    }

    public void setAlternate(Expression alternate) {
        this.alternate = alternate;
    }

        @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("test", getTest());
        json.put("consequent", getConsequent());
        json.put("alternate", getAlternate());

        return json.toString();
    }

}
