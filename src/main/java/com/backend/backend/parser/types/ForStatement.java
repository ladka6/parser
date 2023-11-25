package com.backend.backend.parser.types;

public class ForStatement implements Expression {
    private TypeEnum type;
    private Expression init;
    private Expression test;
    private Expression update;
    private Expression body;

    public ForStatement(TypeEnum type, Expression init, Expression test, Expression update, Expression body) {
        this.type = type;
        this.init = init;
        this.test = test;
        this.update = update;
        this.body = body;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Expression getInit() {
        return this.init;
    }

    public void setInit(Expression init) {
        this.init = init;
    }

    public Expression getTest() {
        return this.test;
    }

    public void setTest(Expression test) {
        this.test = test;
    }

    public Expression getUpdate() {
        return this.update;
    }

    public void setUpdate(Expression update) {
        this.update = update;
    }

    public Expression getBody() {
        return this.body;
    }

    public void setBody(Expression body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                ", init='" + getInit() + "'" +
                ", test='" + getTest() + "'" +
                ", update='" + getUpdate() + "'" +
                ", body='" + getBody() + "'" +
                "}";
    }

}
