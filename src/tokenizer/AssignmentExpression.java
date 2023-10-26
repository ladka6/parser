package tokenizer;

public class AssignmentExpression extends Expression{

    public AssignmentExpression(String type, String operation, Identifier left, Literal right) {
        super(type, operation, left, right);
    }
    
}
