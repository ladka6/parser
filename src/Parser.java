import java.util.ArrayList;
import java.util.List;
import types.*;
import types.literals.BooleanLiteral;
import types.literals.Literal;
import types.literals.NullLiteral;
import types.literals.NumericLiteral;
import types.literals.StringLiteral;







public class Parser {
    private String _string;
    private Tokenizer _tokenizer; 
    private Token _lookahead;

    public Parser() {
        this._tokenizer = new Tokenizer();
    }

    public Program parse(String string) throws Throwable {
        this._string = string;
        this._tokenizer.init(string);

        this._lookahead = this._tokenizer.getNextToken();

        return this.program();
    }
 
    /*
    * Program
    *  StatementList
    *  ;
    */
    public Program program() throws Throwable {
        return new Program(TypeEnum.PROGRAM, this.statementList(null));
    }

    /**
     * StatementList
     *  : Statement
     *  | StatementList Statement
     * @return
     * @throws Exception
     */
    private List<Expression> statementList(TypeEnum stopLookahead) throws Throwable{
        List<Expression> statementList = new ArrayList<>();
        statementList.add(this.statement());
        
        while(this._lookahead != null && this._lookahead.getType() != stopLookahead) {
            statementList.add(this.statement());
        }
        
        return statementList;
    }

    /**
     * Statement
     *  : ExpressionStatement
     *  | BlockStatement
     *  | EmptyStatement
     *  | VariableStatement
     *  | IfStatement
     *  ;
     * @return
     * @throws Exception
     */
    private Expression statement() throws Throwable{
        switch(this._lookahead.getType()) {
            case SEMICOLON:
                return emptyStatement();
            case IF:    
                return ifStatement();
            case LEFT_BRACE:
                return blockStatement();
            case LET:
                return this.variableStatement();
            default:
                return expressionStatement();
        }
    }

    /**
     * IfStatement
     *  : 'if' '(' Expression ')' Statement
     *  | 'if' '(' Expression ')' Statement 'else' Statement
     *  ;
     * @return
     * @throws Exception
     */
    private IfStatement ifStatement() throws Throwable {
        this._eat(TypeEnum.IF);
        this._eat(TypeEnum.LEFT_PARANTHESIS);

        Expression test = this.expression();

        this._eat(TypeEnum.RIGTH_PARANTHESIS);

        Expression consequent = this.statement();

        Expression alternate ;
        if(this._lookahead != null && this._lookahead.getType().equals(TypeEnum.ELSE)) {
            this._eat(TypeEnum.ELSE);
            alternate = this.statement();
        }else {
            alternate = null;
        }
        // = this._lookahead != null && this._lookahead.getType().equals(TypeEnum.ELSE) 
        //             ? {this._eat(TypeEnum.ELSE) ; this.statement()}
        //             : null;

                    // Alternate alternate;
                    // if (this._lookahead != null && this._lookahead.getType().equals(TypeEnum.ELSE)) {
                    //     this._eat(TypeEnum.ELSE);
                    //     this.statement();
                    //     alternate = this._lookahead;
                    // } else {
                    //     alternate = null;
                    // }
                    
        return new IfStatement(TypeEnum.IF_STATEMENT, test, consequent, alternate);
    }

    /**
     * VariableStatement
     *  : 'let' VariableDeclarationList ';'
     * ;
     * @return
     * @throws Exception
     */
    private VariableStatement variableStatement() throws Throwable{
        this._eat(TypeEnum.LET);
        List<VariableDeclaration> declarations = this.variableDeclarationList();
        this._eat(TypeEnum.SEMICOLON);
        return new VariableStatement(TypeEnum.VARIABLE_STATEMENT, declarations);
    }

    /**
     * VariableDeclarationList
     *  : VariableDeclaration
     *  | VariableDeclarationList ',' VariableDeclaration
     */
    private List<VariableDeclaration> variableDeclarationList() throws Throwable {
        List<VariableDeclaration> declarations = new ArrayList<>();
        
        do {
            declarations.add(this.variableDeclaration());
            // this._eat(TypeEnum.COMMA);
        } while (this._lookahead.getType().equals(TypeEnum.COMMA) && this._eat(TypeEnum.COMMA) != null);

        return declarations;
    }

    /**
     * VariableDeclaration
     *  : Identifier OptVariableInitializer
     *  ;
     */
    private VariableDeclaration variableDeclaration() throws Throwable {
        Expression id = this.identifier(); 
    
        Expression init = this._lookahead.getType() != TypeEnum.SEMICOLON 
            && this._lookahead.getType() != TypeEnum.COMMA 
            ? this.variableInitializer() : null; 
        
        return new VariableDeclaration(TypeEnum.VARIABLE_DECLARATION,id,init);
    }

    /**
     * variableInitializer
     *  : SIMPLE_ASSIGN AssignementExpression
     *  
     * @return
     * @throws Exception
     */

    private Expression variableInitializer() throws Throwable {
        this._eat(TypeEnum.SIMPLE_ASSIGN);
        return this.assignmentExpression();
     }

    /**
     * EmptyStatement
     * @return
     * @throws Exception
     */
    private EmptyStatement emptyStatement() throws Throwable{
        this._eat(TypeEnum.SEMICOLON);
        return new EmptyStatement(TypeEnum.EMPTYSTATEMENT);
    }

    /**
     * BlockStatement
     *  : '{' OptStatementList '}'
     *  ;
     * @return
     * @throws Exception
     */
    private BlockStatement blockStatement() throws Throwable {
        this._eat(TypeEnum.LEFT_BRACE);
        
        List<Expression> body = this._lookahead.getType() != TypeEnum.RIGHT_BRACE ? this.statementList(TypeEnum.RIGHT_BRACE) : new ArrayList<>();

        this._eat(TypeEnum.RIGHT_BRACE);
        
        return new BlockStatement(TypeEnum.BLOCK_STATEMENT, body);
    }

    /**
     * ExpressionStatement
     *  : Expression ";"
     *  ;
     * @return
     * @throws Exception
     */
    private ExpressionStatement expressionStatement() throws Throwable {
        Expression expression =  this.expression();
        this._eat(TypeEnum.SEMICOLON);
        return new ExpressionStatement(TypeEnum.EXPRESSION_STATEMENT, expression);
    }
    // /^\w+/
    /**
     * Expression
     *  : AdditiveExpression
     *  ;
     * @return
     * @throws Exception
     */
    private Expression expression() throws Throwable {
        return this.assignmentExpression();
    }

    /**
     * AssignmentExpression
     *  : RelationalExpression
     *  | LeftHandSide AssingmentOperator AssignmentExpression
     * @return
     * @throws Exception
     */
    private Expression assignmentExpression() throws Throwable {
        Expression left = this.logicalORExpression();
        if(!this.isAssignmentOperator(this._lookahead.getType())){
            return left;
        }
        
        return new AssignmentExpression(TypeEnum.ASSIGNMENT_EXPRESSION, this.assignmentOperator(), this.checkValidAssignment(left), this.assignmentExpression());
    }

    /**
     * LeftHandSideExpression
     *  : Identifier
     *  ;
     * @return
     */
    private Expression leftHandSidExpression() throws Exception {
        return this.identifier();
    }

    /**
     * Identifier
     *  : IDENTIFIER
     *  ;
     * @return
     */
    private Expression identifier() throws Exception {
        String name = this._eat(TypeEnum.IDENTIFIER).getValue();
        return new Identifier(TypeEnum.IDENTIFIER, name);
    }

    private Expression checkValidAssignment(Expression node) throws Exception {
        if(node.getType().equals(TypeEnum.IDENTIFIER)) {
            return node;
        }
        throw new Exception("Invalid left-hand side in assignment expression");
    }

    /**
     * Whether the token is an assignment operator
     * @param tokenType
     * @return
     */
    private boolean isAssignmentOperator(TypeEnum tokenType) {
        return tokenType == TypeEnum.SIMPLE_ASSIGN || tokenType == TypeEnum.COMPLEX_ASSIGN;
    }

    /**
     * AssignmentOperator
     *  : SIMPLE_ASSIGN
     *  | COMPLEX_ASSIGN
     *  ;
     * @return
     * @throws Exception
     */
    private Token assignmentOperator() throws Exception {
        if(this._lookahead.getType().equals(TypeEnum.SIMPLE_ASSIGN)) {
            return this._eat(TypeEnum.SIMPLE_ASSIGN);
        }
        return this._eat(TypeEnum.COMPLEX_ASSIGN);
    }

    /**
     * Logical AND expression
     *  x && y
     *  
     * LogicalANDExpression
     *  : EqualityExpression LOGICAL_AND LogicalANDExpression
     *  | EqualityExpression
     *  ;
     * @return
     * @throws Throwable
     */
    private Expression logicalANDExpression() throws Throwable {
        Expression left = equalityExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.LOGICAL_AND) {
            Token operator = this._eat(TypeEnum.LOGICAL_AND);
            Expression right = this.equalityExpression();
            LogicalExpression logicalExpression = new LogicalExpression(TypeEnum.BINARY_EXPRESSION,operator,left,right);
            left = logicalExpression;
        }
        
        return left;
        // return this.logicalExpression('EqualityExpression',TypeEnum.LOGICAL_AND);
    }

    /**
     * Logical OR expression
     *  x || y
     *  
     * LogicalANDExpression
     *  : EqualityExpression LOGICAL_AND LogicalANDExpression
     *  | EqualityExpression
     *  ;
     * @return
     * @throws Throwable
     */
    private Expression logicalORExpression() throws Throwable {
        // return this.logicalExpression('LogicalANDExpression',TypeEnum.LOGICAL_OR);
        Expression left = logicalANDExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.LOGICAL_OR) {
            Token operator = this._eat(TypeEnum.LOGICAL_OR);
            Expression right = this.logicalANDExpression();
            LogicalExpression logicalExpression = new LogicalExpression(TypeEnum.BINARY_EXPRESSION,operator,left,right);
            left = logicalExpression;
        }
        
        return left;
    }


    /**
     * EQUALITY_OPERATOR ==, !=
     *  x == y
     *  x != y
     *  
     * EqualityExpression
     *  : RelationalExpression EQUALITY_OPERATOR EqualityExpression
     *  | RelationalExpression 
     * @return
     * @throws Throwable
     */
    private Expression equalityExpression() throws Throwable{
        Expression left = relationalExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.EQUALITY_OPERATOR) {
            Token operator = this._eat(TypeEnum.EQUALITY_OPERATOR);
            Expression right = this.relationalExpression();
            BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION,operator,left,right);
            left = binaryExpression;
        }
        
        return left;
    }

    /**
     * RELATIONAL_OPERATOR: <, <=, >, >=
     * 
     * RelationalExpression
     *  : AdditiveExpression
     *  | AdditiveExpression RELATIONAL_OPERATOR RelationalExpression
     * @return
     * @throws Throwable
     */
    private Expression relationalExpression() throws Throwable {
        Expression left = additiveExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.RELATIONAL_OPERATOR) {
            Token operator = this._eat(TypeEnum.RELATIONAL_OPERATOR);
            Expression right = this.additiveExpression();
            BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION,operator,left,right);
            left = binaryExpression;
        }
        
        return left;
    } 

    /**
     * AdditiveExpression
     *  : Literal
     *  | AdditiveExpression ADDITIVE_OPERATOR Literal
     * @return
     * @throws Exception
     */
    public Expression additiveExpression() throws Throwable {
        // return this.binaryExpression("multiplicativeExpression", TypeEnum.ADDITIVE_OPERATOR);
        Expression left = multiplicativeExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.ADDITIVE_OPERATOR) {
            Token operator = this._eat(TypeEnum.ADDITIVE_OPERATOR);
            Expression right = this.multiplicativeExpression();
            BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION,operator,left,right);
            left = binaryExpression;
        }

        return left;
    }

    public Expression multiplicativeExpression() throws Throwable {
        // return this.binaryExpression("primaryExpression", TypeEnum.MULTIPLICATIVE_OPERATOR);

        Expression left = primaryExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.MULTIPLICATIVE_OPERATOR) {
            Token operator = this._eat(TypeEnum.MULTIPLICATIVE_OPERATOR);
            Expression right = this.primaryExpression();
            BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION,operator,left,right);
            left = binaryExpression;
        }

        return left;
    }

    public Expression primaryExpression() throws Throwable{
        if(this.isLiteral(this._lookahead.getType())) {
            return this.literal();
        }
        switch(this._lookahead.getType()) {
            case LEFT_PARANTHESIS:
                return this.paranthesizedExpression();
            default:
                return this.leftHandSidExpression();
        }
    }

    private boolean isLiteral(TypeEnum tokenType) {
        return tokenType.equals(TypeEnum.NUMBER) 
                || tokenType.equals(TypeEnum.STRING)
                || tokenType.equals(TypeEnum.TRUE)
                || tokenType.equals(TypeEnum.FALSE)
                || tokenType.equals(TypeEnum.NULL);
    }

    private Expression paranthesizedExpression() throws Throwable{
        this._eat(TypeEnum.LEFT_PARANTHESIS);
        Expression expression = this.expression();
        this._eat(TypeEnum.RIGTH_PARANTHESIS);
        return expression;
    }

    /**
     * Literal
     *  : StringLiteral
     *  | NumericLiteral
     *  | BooleanLiteral
     *  | NullLitearl
     * @return(Literal)
     */
    public Literal literal() throws Throwable {
        switch(this._lookahead.getType()) {
            case STRING:
                return this.stringLiteral();
            case NUMBER:
                return this.numericLitearl();
            case TRUE:
                return this.booleanLiteral(true);
            case FALSE:
                return this.booleanLiteral(false);
            case NULL:
                return this.nullLiteral();
            default:
                return null;
        }
    }

    private BooleanLiteral booleanLiteral(boolean value) throws Throwable{
        this._eat(value ? TypeEnum.TRUE : TypeEnum.FALSE);
        return new BooleanLiteral(TypeEnum.BOOLEAN_LITERAL, String.valueOf(value));
    }

    private NullLiteral nullLiteral() throws Throwable {
        this._eat(TypeEnum.NULL);
        return new NullLiteral(TypeEnum.NULL_LITERAL, null);
    }

    private StringLiteral stringLiteral() throws Exception {
        Token token = this._eat(TypeEnum.STRING);
        
        return new StringLiteral(TypeEnum.STRING, token.getValue());
    }

    private NumericLiteral numericLitearl() throws Exception{
        Token token = this._eat(TypeEnum.NUMBER);
        
        return new NumericLiteral(TypeEnum.NUMBER, token.getValue()); 
    }

    
    private Token _eat(TypeEnum tokenType) throws Exception {
        Token token = this._lookahead;

        if (token == null) {
            throw new Exception(
                "Unexpected end of input, expected:" + tokenType
            );
        }

        if (!token.getType().equals(tokenType)) {
            throw new Exception(
                "Unexpected token type " + token.getType() + " , getting " + tokenType);
        }

        this._lookahead = this._tokenizer.getNextToken();
        // System.out.println(this._lookahead);
        return token;
    }

}
