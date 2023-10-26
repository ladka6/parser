package tokenizer;

public abstract class Expression {
    private String type;
    private String operation;
    private Identifier left;
    private Literal right;


    public Expression(String type, String operation, Identifier left, Literal right) {
        this.type = type;
        this.operation = operation;
        this.left = left;
        this.right = right;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Identifier getLeft() {
        return this.left;
    }

    public void setLeft(Identifier left) {
        this.left = left;
    }

    public Literal getRight() {
        return this.right;
    }

    public void setRight(Literal right) {
        this.right = right;
    }

}
