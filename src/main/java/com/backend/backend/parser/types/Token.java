package com.backend.backend.parser.types;

import org.json.JSONObject;

public class Token {
    private TypeEnum type;
    private String value;


    public Token(TypeEnum type, String value) {
        this.type = type;
        this.value = value;
    }


    public TypeEnum getType() {
        if(this.type == null) {return null;}
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("value", getValue());

        return json.toString();
    }

}
