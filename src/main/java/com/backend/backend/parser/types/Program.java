package com.backend.backend.parser.types;

import java.util.List;

import org.json.JSONObject;

public class Program implements Expression {
    private TypeEnum type;
    private List<Expression> body;

    public Program(TypeEnum type, List<Expression> body) {
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
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("body", getBody());

        return json.toString();
    }

}
