package com.backend.backend.parser.types;

import org.json.JSONObject;

public class EmptyStatement implements Expression{
    private TypeEnum type;

    public EmptyStatement(TypeEnum type) {
        this.type = type;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

        @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());

        return json.toString();
    }

}
