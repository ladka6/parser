package com.backend.backend.parser.types;
import java.util.List;

public class NewExpression implements Expression{
    private TypeEnum type;
    private Expression callee;
    private List<Expression> arguments;

    public NewExpression(TypeEnum type, Expression callee, List<Expression> arguments) {
        this.type = type;
        this.callee = callee;
        this.arguments = arguments;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Expression getCallee() {
        return this.callee;
    }

    public void setCallee(Expression callee) {
        this.callee = callee;
    }

    public List<Expression> getArguments() {
        return this.arguments;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", callee='" + getCallee() + "'" +
            ", arguments='" + getArguments() + "'" +
            "}";
    }

}
