package com.backend.backend.parser.types;

public class ThisExpression implements Expression{
    private TypeEnum type;

    public ThisExpression(TypeEnum type) {
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
