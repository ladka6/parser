package com.backend.backend.parser.types;
import java.util.List;

import org.json.JSONObject;

public class CallExpression implements Expression {
    private TypeEnum type;
    private Expression calle;
    private List<Expression> arguments;

    public CallExpression(TypeEnum type, Expression calle, List<Expression> arguments) {
        this.type = type;
        this.calle = calle;
        this.arguments = arguments;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Expression getCalle() {
        return this.calle;
    }

    public void setCalle(Expression calle) {
        this.calle = calle;
    }

    public List<Expression> getArguments() {
        return this.arguments;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }

        @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("calle", getCalle());
        json.put("arguments", getArguments());

        return json.toString();
    }

}
