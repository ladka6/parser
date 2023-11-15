package com.backend.backend.parser.types;

public class IfStatement implements Expression {
    private TypeEnum type;
    private Expression test;
    private Expression consequent;
    private Expression alternate;

    public IfStatement(TypeEnum type, Expression test, Expression consequent, Expression alternate) {
        this.type = type;
        this.test = test;
        this.consequent = consequent;
        this.alternate = alternate;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Expression getTest() {
        return this.test;
    }

    public void setTest(Expression test) {
        this.test = test;
    }

    public Expression getConsequent() {
        return this.consequent;
    }

    public void setConsequent(Expression consequent) {
        this.consequent = consequent;
    }

    public Expression getAlternate() {
        return this.alternate;
    }

    public void setAlternate(Expression alternate) {
        this.alternate = alternate;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", test='" + getTest() + "'" +
            ", consequent='" + getConsequent() + "'" +
            ", alternate='" + getAlternate() + "'" +
            "}";
    }

}
