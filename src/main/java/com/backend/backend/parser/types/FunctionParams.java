package com.backend.backend.parser.types;

public class FunctionParams implements Expression {
    private TypeEnum type;
    private String name;
    private String variableType;

    public FunctionParams(TypeEnum type, String name, String variableType) {
        this.type = type;
        this.name = name;
        this.variableType = variableType;
    }

    @Override
    public TypeEnum getType() {
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
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

    public String getVariableType() {
        return this.variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                ", name='" + getName() + "'" +
                ", variableType='" + getVariableType() + "'" +
                "}";
    }

}
