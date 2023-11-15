package com.backend.backend.parser.types;

public enum OperatorEnum {
    PLUS_SIGN("+"),
    MINUS_SIGN("-");

    private final String representation;

    OperatorEnum(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
