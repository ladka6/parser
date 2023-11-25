package com.backend.backend.parser.types;

public class ExpressionStatement implements Expression {
    private TypeEnum type;
    private BinaryExpression binaryExpression;

    public ExpressionStatement(TypeEnum type, BinaryExpression binaryExpression) {
        this.type = type;
        this.binaryExpression = binaryExpression;
    }

    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public BinaryExpression getBinaryExpression() {
        return this.binaryExpression;
    }

    public void setBinaryExpression(BinaryExpression binaryExpression) {
        this.binaryExpression = binaryExpression;
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                ", binaryExpression='" + getBinaryExpression() + "'" +
                "}";
    }

}
