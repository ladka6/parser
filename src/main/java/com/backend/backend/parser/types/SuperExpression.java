package com.backend.backend.parser.types;

public class SuperExpression implements Expression {
    private TypeEnum type;

    public SuperExpression(TypeEnum type) {
        this.type = type;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                "}";
    }

}
