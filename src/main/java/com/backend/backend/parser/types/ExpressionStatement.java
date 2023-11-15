package com.backend.backend.parser.types;

import org.json.JSONObject;

public class ExpressionStatement implements Expression{
    private TypeEnum type;
    private Expression binaryExpression;

    public ExpressionStatement(TypeEnum type, Expression binaryExpression) {
        this.type = type;
        this.binaryExpression = binaryExpression;
    }


    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Expression getLiteral() {
        return this.binaryExpression;
    }

    public void setLiteral(Expression binaryExpression) {
        this.binaryExpression = binaryExpression;
    }


    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("literal", getLiteral());

        return json.toString();
    }
}
