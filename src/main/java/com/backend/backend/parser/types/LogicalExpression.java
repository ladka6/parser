package com.backend.backend.parser.types;

import org.json.JSONObject;

public class LogicalExpression implements Expression {
    private TypeEnum type;
    private Token operator;
    private Expression left;
    private Expression right;

    public LogicalExpression(TypeEnum type, Token operator, Expression left, Expression right) {
        this.type = type;
        this.operator = operator;
        this.left = left;
        this.right = right;
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

    public Expression getLeft() {
        return this.left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return this.right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

        @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("operator", getOperator());
        json.put("left", getLeft());
        json.put("right", getRight());

        return json.toString();
    }

}
