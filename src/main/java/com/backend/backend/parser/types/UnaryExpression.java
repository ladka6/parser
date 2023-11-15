package com.backend.backend.parser.types;

import org.json.JSONObject;

public class UnaryExpression implements Expression{
    private TypeEnum type;
    private Token operator;
    private Expression argument;

    public UnaryExpression(TypeEnum type, Token operator, Expression argument) {
        this.type = type;
        this.operator = operator;
        this.argument = argument;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Token getOperator() {
        return this.operator;
    }

    public void setOperator(Token operator) {
        this.operator = operator;
    }

    public Expression getArgument() {
        return this.argument;
    }

    public void setArgument(Expression argument) {
        this.argument = argument;
    }

        @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("operator", getOperator());
        json.put("argument", getArgument());

        return json.toString();
    }

}
