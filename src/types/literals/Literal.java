package types.literals;

import types.Expression;
import types.TypeEnum;

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
        return "{" +
            " type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }

}
