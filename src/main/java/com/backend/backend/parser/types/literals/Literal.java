package com.backend.backend.parser.types.literals;

import org.json.JSONObject;

import com.backend.backend.parser.types.Expression;
import com.backend.backend.parser.types.TypeEnum;

public class Literal  implements Expression {
    private TypeEnum type;
    private String value;


    public Literal(TypeEnum type, String value) {
        this.type = type;
        this.value = value;
    }


    @Override
    public TypeEnum getType() {
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
