package com.backend.backend.parser.types;

public class MemberExpression implements Expression{
    private TypeEnum type;
    private Boolean computed;
    private Expression object;
    private Expression property;

    public MemberExpression(TypeEnum type, Boolean computed, Expression object, Expression property) {
        this.type = type;
        this.computed = computed;
        this.object = object;
        this.property = property;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Boolean isComputed() {
        return this.computed;
    }

    public Boolean getComputed() {
        return this.computed;
    }

    public void setComputed(Boolean computed) {
        this.computed = computed;
    }

    public Expression getObject() {
        return this.object;
    }

    public void setObject(Expression object) {
        this.object = object;
    }

    public Expression getProperty() {
        return this.property;
    }

    public void setProperty(Expression property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", computed='" + isComputed() + "'" +
            ", object='" + getObject() + "'" +
            ", property='" + getProperty() + "'" +
            "}";
    }

}
