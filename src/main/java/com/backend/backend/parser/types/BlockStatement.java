package com.backend.backend.parser.types;

import java.util.List;

public class BlockStatement implements Expression {
    private TypeEnum type;
    private List<Expression> body;

    public BlockStatement(TypeEnum type, List<Expression> body) {
        this.type = type;
        this.body = body;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public List<Expression> getBody() {
        return this.body;
    }

    public void setBody(List<Expression> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                ", body='" + getBody() + "'" +
                "}";
    }

}
