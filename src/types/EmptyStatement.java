package types;

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
        return "{" +
            " type='" + getType() + "'" +
            "}";
    }

}
