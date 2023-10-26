package tokenizer;

public class ExpressionStatement extends Statement{
    
    private String type;
    private Expression expression;
    
    public ExpressionStatement(String type,Expression expression) {
        super(type);
        this.expression = expression;
    }
   
    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public Expression getExpression() {
        return this.expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

}
