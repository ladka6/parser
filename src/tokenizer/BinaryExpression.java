package tokenizer;

public class BinaryExpression extends Expression{

    public BinaryExpression(String type, String operation, Identifier left, Literal right) {
        super(type, operation, left, right);
    }
    
}
