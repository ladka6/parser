package com.backend.backend.parser.types;

public class ReturnStatement implements Expression {
    private TypeEnum type;
    private Expression argument;

    public ReturnStatement(TypeEnum type, Expression argument) {
        this.type = type;
        this.argument = argument;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Expression getArgument() {
        return this.argument;
    }

    public void setArgument(Expression argument) {
        this.argument = argument;
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                ", argument='" + getArgument() + "'" +
                "}";
    }

}
