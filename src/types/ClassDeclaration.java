package types;

public class ClassDeclaration implements Expression{
    private TypeEnum type;
    private Identifier id;
    private Identifier superClass;
    private BlockStatement body;

    public ClassDeclaration(TypeEnum type, Identifier id, Identifier superClass, BlockStatement body) {
        this.type = type;
        this.id = id;
        this.superClass = superClass;
        this.body = body;
    }

    @Override
    public TypeEnum getType() {
        return this.type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public Identifier getId() {
        return this.id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public Identifier getSuperClass() {
        return this.superClass;
    }

    public void setSuperClass(Identifier superClass) {
        this.superClass = superClass;
    }

    public BlockStatement getBody() {
        return this.body;
    }

    public void setBody(BlockStatement body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", id='" + getId() + "'" +
            ", superClass='" + getSuperClass() + "'" +
            ", body='" + getBody() + "'" +
            "}";
    }

}
