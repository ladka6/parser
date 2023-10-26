package tokenizer;

public class ParenthesizedExpression extends Expression {

    public ParenthesizedExpression(String type, String operation, Identifier left, Literal right) {
        super(type, operation, left, right);
    }
    
}