package com.backend.backend.parser.types;
import java.util.List;

import org.json.JSONObject;

public class FunctionDeclaration implements Expression{
    private TypeEnum type;
    private Expression name;
    private List<Expression> params;
    private BlockStatement body;

    public FunctionDeclaration(TypeEnum type, Expression name, List<Expression> params, BlockStatement body) {
        this.type = type;
        this.name = name;
        this.params = params;
        this.body = body;
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
        JSONObject json = new JSONObject();
        json.put("type", getType());
        json.put("name", getName());
        json.put("params", getParams());
        json.put("body", getBody());

        return json.toString();
    }

    
}
