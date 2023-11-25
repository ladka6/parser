package com.backend.backend.parser.types;

import java.util.List;

public class ImportStatement implements Expression {
    private TypeEnum type;
    private List<String> path;

    public ImportStatement(List<String> path) {
        this.type = TypeEnum.IMPORT_STATEMENT;
        this.path = path;
    }

    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public List<String> getPath() {
        return this.path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "{" +
                " type='" + getType() + "'" +
                ", path='" + getPath() + "'" +
                "}";
    }

}
