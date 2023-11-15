package com.backend.backend.parser.types;

import org.json.JSONObject;

public class Identifier implements Expression{
    private TypeEnum type;
    private String name;

    public Identifier(TypeEnum type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

        @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("name", getName());

        return json.toString();
    }

}
