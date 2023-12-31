package com.backend.backend.parser.types;

import java.util.List;

public class FunctionDeclaration implements Expression {
    private TypeEnum type;
    private String typeAnnotation;
    private Expression name;
    private List<Expression> params;
    private BlockStatement body;

    public FunctionDeclaration(TypeEnum type, String typeAnnotation, Expression name, List<Expression> params,
            BlockStatement body) {
        this.type = type;
        this.typeAnnotation = typeAnnotation;
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public String getTypeAnnotation() {
        return this.typeAnnotation;
    }

    public void setTypeAnnotation(String typeAnnotation) {
        this.typeAnnotation = typeAnnotation;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Expression getName() {
        return this.name;
    }

    public void setName(Expression name) {
        this.name = name;
    }

    public List<Expression> getParams() {
        return this.params;
    }

    public void setParams(List<Expression> params) {
        this.params = params;
    }

    public BlockStatement getBody() {
        return this.body;
    }

    public void setBody(BlockStatement body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                ", typeAnnotation='" + getTypeAnnotation() + "'" +
                ", name='" + getName() + "'" +
                ", params='" + getParams() + "'" +
                ", body='" + getBody() + "'" +
                "}";
    }

}
