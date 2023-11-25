package com.backend.backend.parser.types;

public class DoWhileStatement implements Expression {
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
        return "{" +
                " type='" + getType() + "'" +
                ", body='" + getBody() + "'" +
                ", test='" + getTest() + "'" +
                "}";
    }

}
