package com.backend.backend.parser.types;

import org.json.JSONObject;

import com.backend.backend.parser.types.literals.Literal;

public class AdditiveExpression {
    private TypeEnum type;
    private Token operator;
    private Literal left;
    private Literal right;

    public AdditiveExpression(TypeEnum type, Token operator, Literal left, Literal right) {
        this.type = type;
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

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

    public Literal getLeft() {
        return this.left;
    }

    public void setLeft(Literal left) {
        this.left = left;
    }

    public Literal getRight() {
        return this.right;
    }

    public void setRight(Literal right) {
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
