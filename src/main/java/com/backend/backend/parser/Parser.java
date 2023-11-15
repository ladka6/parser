package com.backend.backend.parser;

import com.backend.backend.exception.SyntaxError;
import java.util.ArrayList;
import java.util.List;
import com.backend.backend.parser.types.*;
import com.backend.backend.parser.types.literals.*;

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
     * StatementList
     * ;
     */
    private Program program() throws Throwable {
        return new Program(TypeEnum.PROGRAM, this.statementList(null));
    }

    /**
     * StatementList
     * : Statement
     * | StatementList Statement
     * 
     * @return
     * @throws Exception
     */
    private List<Expression> statementList(TypeEnum stopLookahead) throws Throwable {
        List<Expression> statementList = new ArrayList<>();
        statementList.add(this.statement());

        while (this._lookahead != null && this._lookahead.getType() != stopLookahead) {
            statementList.add(this.statement());
        }

        return statementList;
    }

    /**
     * Statement
     * : ExpressionStatement
     * | BlockStatement
     * | EmptyStatement
     * | VariableStatement
     * | IfStatement
     * | IterationStatement
     * | FunctionDeclaration
     * | ReturnStatement
     * | ClassDeclaration
     * ;
     * 
     * @return
     * @throws Exception
     */
    private Expression statement() throws Throwable {
        switch (this._lookahead.getType()) {
            case SEMICOLON:
                return emptyStatement();
            case IF:
                return ifStatement();
            case LEFT_BRACE:
                return blockStatement();
            case IDENTIFIER:
                return this.variableStatement();
            case DEF:
                return this.functionDeclaration();
            case CLASS:
                return this.classDeclaration();
            case RETURN:
                return this.returnStatement();
            case WHILE:
            case DO:
            case FOR:
                return this.iterationStatement();
            default:
                return expressionStatement();
        }
    }

    /**
     * ClassDeclaration
     * : 'class' Identifier OptClassExtends BlockStatement
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private ClassDeclaration classDeclaration() throws Throwable {
        this._eat(TypeEnum.CLASS);

        Identifier id = this.identifier();

        Identifier superClass = this._lookahead.getType().equals(TypeEnum.EXTENDS)
                ? this.classExtends()
                : null;

        BlockStatement body = this.blockStatement();

        return new ClassDeclaration(TypeEnum.CLASS_DECLARATION, id, superClass, body);
    }

    /**
     * ClassExtends
     * : 'extends' Identifier
     * 
     * @return
     * @throws Throwable
     */
    private Identifier classExtends() throws Throwable {
        this._eat(TypeEnum.IDENTIFIER);
        return this.identifier();
    }

    /**
     * FunctionDeclaration
     * : 'def' Identifier '(' OptFormalParameterList ')' BlockStatement
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private FunctionDeclaration functionDeclaration() throws Throwable {
        this._eat(TypeEnum.DEF);
        Expression name = this.identifier();

        this._eat(TypeEnum.LEFT_PARANTHESIS);

        List<Expression> params;

        if (this._lookahead.getType() != TypeEnum.RIGTH_PARANTHESIS) {
            params = this.formalParameterList();
        } else {
            params = new ArrayList<>();
        }

        this._eat(TypeEnum.RIGTH_PARANTHESIS);

        BlockStatement body = this.blockStatement();

        return new FunctionDeclaration(TypeEnum.FUNCTION_DECLARATION, name, params, body);
    }

    /**
     * FormalParameterList
     * : Identifier
     * | FormalParaterList ',' Identifier
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private List<Expression> formalParameterList() throws Throwable {
        List<Expression> params = new ArrayList<>();

        do {
            params.add(this.identifier());
        } while (this._lookahead.getType().equals(TypeEnum.COMMA) && this._eat(TypeEnum.COMMA) != null);

        return params;
    }

    /**
     * ReturnStatement
     * | 'return' OptExpression
     * 
     * @return
     * @throws Throwable
     */

    private ReturnStatement returnStatement() throws Throwable {
        this._eat(TypeEnum.RETURN);

        Expression arguments = this._lookahead.getType() != TypeEnum.SEMICOLON ? this.expression() : null;
        this._eat(TypeEnum.SEMICOLON);

        return new ReturnStatement(TypeEnum.RETURN_STATEMENT, arguments);
    }

    /**
     * IterationStatement
     * : WhileStatement
     * | DoWhileStatement
     * | ForStatement
     * 
     * @return
     */
    private Expression iterationStatement() throws Throwable {
        switch (this._lookahead.getType()) {
            case WHILE:
                return this.whileStatement();
            case DO:
                return this.doWhileStatement();
            case FOR:
                return this.forStatement();
        }
        throw new SyntaxError("Error");
    }

    /**
     * WhileStatement
     * : 'while' '(' Expression ')' Statement
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private WhileStatement whileStatement() throws Throwable {
        this._eat(TypeEnum.WHILE);
        this._eat(TypeEnum.LEFT_PARANTHESIS);

        Expression test = this.expression();

        this._eat(TypeEnum.RIGTH_PARANTHESIS);

        Expression body = this.statement();

        return new WhileStatement(TypeEnum.WHILE_STATEMENT, test, body);
    }

    /**
     * DoWhileStatement
     * : 'do' Statement 'while' '{' Expression '}' ';'
     * 
     * @return
     * @throws Throwable
     */
    private DoWhileStatement doWhileStatement() throws Throwable {
        this._eat(TypeEnum.DO);

        Expression body = this.statement();

        this._eat(TypeEnum.WHILE);
        this._eat(TypeEnum.LEFT_PARANTHESIS);

        Expression test = this.expression();

        this._eat(TypeEnum.RIGTH_PARANTHESIS);
        this._eat(TypeEnum.SEMICOLON);

        return new DoWhileStatement(TypeEnum.DO_WHILE_STATEMENT, body, test);
    }

    /**
     * ForStatement
     * : 'for' '{' OptStatementInit ';' OptExpression ';' OptExpression ')'
     * Statement
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private ForStatement forStatement() throws Throwable {
        this._eat(TypeEnum.FOR);
        this._eat(TypeEnum.LEFT_PARANTHESIS);

        Expression init = this._lookahead.getType() != TypeEnum.SEMICOLON
                ? this.forStatementInit()
                : null;
        this._eat(TypeEnum.SEMICOLON);

        Expression test = this._lookahead.getType() != TypeEnum.SEMICOLON
                ? this.expression()
                : null;
        this._eat(TypeEnum.SEMICOLON);

        Expression update = this._lookahead.getType() != TypeEnum.RIGTH_PARANTHESIS
                ? this.expression()
                : null;

        this._eat(TypeEnum.RIGTH_PARANTHESIS);

        Expression body = this.statement();

        return new ForStatement(TypeEnum.FOR_STATEMENT, init, test, update, body);
    }

    /**
     * ForStatementInit
     * : VariableStatementInit
     * | Expression
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private Expression forStatementInit() throws Throwable {
        if (this._lookahead.getType().equals(TypeEnum.LET)) {
            return this.variableStatementInit();
        }
        return this.expression();
    }

    /**
     * IfStatement
     * : 'if' '(' Expression ')' Statement
     * | 'if' '(' Expression ')' Statement 'else' Statement
     * ;
     * 
     * @return
     * @throws Exception
     */
    private IfStatement ifStatement() throws Throwable {
        this._eat(TypeEnum.IF);
        this._eat(TypeEnum.LEFT_PARANTHESIS);

        Expression test = this.expression();

        this._eat(TypeEnum.RIGTH_PARANTHESIS);

        Expression consequent = this.statement();

        Expression alternate;
        if (this._lookahead != null && this._lookahead.getType().equals(TypeEnum.ELSE)) {
            this._eat(TypeEnum.ELSE);
            alternate = this.statement();
        } else {
            alternate = null;
        }

        return new IfStatement(TypeEnum.IF_STATEMENT, test, consequent, alternate);
    }

    /**
     * VariableStatementInit
     * : 'let' VariableDeclarationList
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private VariableStatement variableStatementInit() throws Throwable {
        String type = this._eat(TypeEnum.IDENTIFIER).getValue();
        List<VariableDeclaration> declarations = this.variableDeclarationList();
        return new VariableStatement(TypeEnum.VARIABLE_STATEMENT, type, declarations);
    }

    /**
     * VariableStatement
     * : VariableStatementInit ';'
     * ;
     * 
     * @return
     * @throws Exception
     */
    private VariableStatement variableStatement() throws Throwable {
        VariableStatement variableStatement = this.variableStatementInit();
        this._eat(TypeEnum.SEMICOLON);
        return variableStatement;
    }

    /**
     * VariableDeclarationList
     * : VariableDeclaration
     * | VariableDeclarationList ',' VariableDeclaration
     */
    private List<VariableDeclaration> variableDeclarationList() throws Throwable {
        List<VariableDeclaration> declarations = new ArrayList<>();

        do {
            declarations.add(this.variableDeclaration());
        } while (this._lookahead.getType().equals(TypeEnum.COMMA) && this._eat(TypeEnum.COMMA) != null);

        return declarations;
    }

    /**
     * VariableDeclaration
     * : Identifier OptVariableInitializer
     * ;
     */
    private VariableDeclaration variableDeclaration() throws Throwable {
        Expression id = this.identifier();

        Expression init = this._lookahead.getType() != TypeEnum.SEMICOLON
                && this._lookahead.getType() != TypeEnum.COMMA
                        ? this.variableInitializer()
                        : null;

        return new VariableDeclaration(TypeEnum.VARIABLE_DECLARATION, id, init);
    }

    /**
     * variableInitializer
     * : SIMPLE_ASSIGN AssignementExpression
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
     * 
     * @return
     * @throws Exception
     */
    private EmptyStatement emptyStatement() throws Throwable {
        this._eat(TypeEnum.SEMICOLON);
        return new EmptyStatement(TypeEnum.EMPTYSTATEMENT);
    }

    /**
     * BlockStatement
     * : '{' OptStatementList '}'
     * ;
     * 
     * @return
     * @throws Exception
     */
    private BlockStatement blockStatement() throws Throwable {
        this._eat(TypeEnum.LEFT_BRACE);

        List<Expression> body = this._lookahead.getType() != TypeEnum.RIGHT_BRACE
                ? this.statementList(TypeEnum.RIGHT_BRACE)
                : new ArrayList<>();

        this._eat(TypeEnum.RIGHT_BRACE);

        return new BlockStatement(TypeEnum.BLOCK_STATEMENT, body);
    }

    /**
     * ExpressionStatement
     * : Expression ";"
     * ;
     * 
     * @return
     * @throws Exception
     */
    private ExpressionStatement expressionStatement() throws Throwable {
        Expression expression = this.expression();
        this._eat(TypeEnum.SEMICOLON);
        return new ExpressionStatement(TypeEnum.EXPRESSION_STATEMENT, expression);
    }

    // /^\w+/
    /**
     * Expression
     * : AdditiveExpression
     * ;
     * 
     * @return
     * @throws Exception
     */
    private Expression expression() throws Throwable {
        return this.assignmentExpression();
    }

    /**
     * AssignmentExpression
     * : RelationalExpression
     * | LeftHandSide AssingmentOperator AssignmentExpression
     * 
     * @return
     * @throws Exception
     */
    private Expression assignmentExpression() throws Throwable {
        Expression left = this.logicalORExpression();
        if (!this.isAssignmentOperator(this._lookahead.getType())) {
            return left;
        }

        return new AssignmentExpression(TypeEnum.ASSIGNMENT_EXPRESSION, this.assignmentOperator(),
                this.checkValidAssignment(left), this.assignmentExpression());
    }

    /**
     * Identifier
     * : IDENTIFIER
     * ;
     * 
     * @return
     */
    private Identifier identifier() throws Exception {
        String name = this._eat(TypeEnum.IDENTIFIER).getValue();
        return new Identifier(TypeEnum.IDENTIFIER, name);
    }

    private Expression checkValidAssignment(Expression node) throws Exception {
        if (node.getType().equals(TypeEnum.IDENTIFIER) || node.getType().equals(TypeEnum.MEMBER_EXPRESSION)) {
            return node;
        }
        throw new Exception("Invalid left-hand side in assignment expression");
    }

    /**
     * Whether the token is an assignment operator
     * 
     * @param tokenType
     * @return
     */
    private boolean isAssignmentOperator(TypeEnum tokenType) {
        return tokenType == TypeEnum.SIMPLE_ASSIGN || tokenType == TypeEnum.COMPLEX_ASSIGN;
    }

    /**
     * AssignmentOperator
     * : SIMPLE_ASSIGN
     * | COMPLEX_ASSIGN
     * ;
     * 
     * @return
     * @throws Exception
     */
    private Token assignmentOperator() throws Exception {
        if (this._lookahead.getType().equals(TypeEnum.SIMPLE_ASSIGN)) {
            return this._eat(TypeEnum.SIMPLE_ASSIGN);
        }
        return this._eat(TypeEnum.COMPLEX_ASSIGN);
    }

    /**
     * Logical AND expression
     * x && y
     * 
     * LogicalANDExpression
     * : EqualityExpression LOGICAL_AND LogicalANDExpression
     * | EqualityExpression
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private Expression logicalANDExpression() throws Throwable {
        Expression left = equalityExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.LOGICAL_AND) {
            Token operator = this._eat(TypeEnum.LOGICAL_AND);
            Expression right = this.equalityExpression();
            LogicalExpression logicalExpression = new LogicalExpression(TypeEnum.BINARY_EXPRESSION, operator, left,
                    right);
            left = logicalExpression;
        }

        return left;
        // return this.logicalExpression('EqualityExpression',TypeEnum.LOGICAL_AND);
    }

    /**
     * Logical OR expression
     * x || y
     * 
     * LogicalANDExpression
     * : EqualityExpression LOGICAL_AND LogicalANDExpression
     * | EqualityExpression
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private Expression logicalORExpression() throws Throwable {
        // return this.logicalExpression('LogicalANDExpression',TypeEnum.LOGICAL_OR);
        Expression left = logicalANDExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.LOGICAL_OR) {
            Token operator = this._eat(TypeEnum.LOGICAL_OR);
            Expression right = this.logicalANDExpression();
            LogicalExpression logicalExpression = new LogicalExpression(TypeEnum.BINARY_EXPRESSION, operator, left,
                    right);
            left = logicalExpression;
        }

        return left;
    }

    /**
     * EQUALITY_OPERATOR ==, !=
     * x == y
     * x != y
     * 
     * EqualityExpression
     * : RelationalExpression EQUALITY_OPERATOR EqualityExpression
     * | RelationalExpression
     * 
     * @return
     * @throws Throwable
     */
    private Expression equalityExpression() throws Throwable {
        Expression left = relationalExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.EQUALITY_OPERATOR) {
            Token operator = this._eat(TypeEnum.EQUALITY_OPERATOR);
            Expression right = this.relationalExpression();
            BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION, operator, left, right);
            left = binaryExpression;
        }

        return left;
    }

    /**
     * RELATIONAL_OPERATOR: <, <=, >, >=
     * 
     * RelationalExpression
     * : AdditiveExpression
     * | AdditiveExpression RELATIONAL_OPERATOR RelationalExpression
     * 
     * @return
     * @throws Throwable
     */
    private Expression relationalExpression() throws Throwable {
        Expression left = additiveExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.RELATIONAL_OPERATOR) {
            Token operator = this._eat(TypeEnum.RELATIONAL_OPERATOR);
            Expression right = this.additiveExpression();
            BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION, operator, left, right);
            left = binaryExpression;
        }

        return left;
    }

    /**
     * AdditiveExpression
     * : Literal
     * | AdditiveExpression ADDITIVE_OPERATOR Literal
     * 
     * @return
     * @throws Exception
     */
    private Expression additiveExpression() throws Throwable {
        // return this.binaryExpression("multiplicativeExpression",
        // TypeEnum.ADDITIVE_OPERATOR);
        Expression left = multiplicativeExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.ADDITIVE_OPERATOR) {
            Token operator = this._eat(TypeEnum.ADDITIVE_OPERATOR);
            Expression right = this.multiplicativeExpression();
            BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION, operator, left, right);
            left = binaryExpression;
        }

        return left;
    }

    private Expression multiplicativeExpression() throws Throwable {
        Expression left = unaryExpression();

        while (this._lookahead != null && this._lookahead.getType() == TypeEnum.MULTIPLICATIVE_OPERATOR) {
            Token operator = this._eat(TypeEnum.MULTIPLICATIVE_OPERATOR);
            Expression right = this.unaryExpression();
            BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION, operator, left, right);
            left = binaryExpression;
        }

        return left;
    }

    /**
     * UnaryExpression
     * : LeftHandSideExpression
     * | ADDITIVE_OPERATOR UnaryExpression
     * | LOGCAL_NOT UnaryExpression
     * 
     * @return
     */
    private Expression unaryExpression() throws Throwable {
        Token operator = null;
        switch (this._lookahead.getType()) {
            case ADDITIVE_OPERATOR:
                operator = this._eat(TypeEnum.ADDITIVE_OPERATOR);
                break;
            case LOGICAL_NOT:
                System.out.println("ASDASD");
                operator = this._eat(TypeEnum.LOGICAL_NOT);
                break;

        }

        if (operator != null) {
            return new UnaryExpression(TypeEnum.UNARY_EXPRESSION, operator, this.unaryExpression());
        }

        return this.leftHandSidExpression();
    }

    /**
     * LeftHandSideExpression
     * : CallMemberExpression
     * ;
     * 
     * @return
     */
    private Expression leftHandSidExpression() throws Throwable {
        return this.callMemberExpression();
    }

    /**
     * CallMemberExpression
     * : MemberExpression
     * | CallExpression
     * 
     * @return
     * @throws Throwable
     */
    private Expression callMemberExpression() throws Throwable {
        if (this._lookahead.getType().equals(TypeEnum.SUPER)) {
            return this.callExpression(this.superExpression());
        }

        Expression member = this.memberExpression();

        if (this._lookahead.getType().equals(TypeEnum.LEFT_PARANTHESIS)) {
            return this.callExpression(member);
        }

        return member;
    }

    /**
     * Generic call expression helper
     * 
     * CallExpression
     * : Calle Arguments
     * ;
     * Callee
     * : MemberExpression
     * | CallExpression
     * 
     * @param callee
     * @return
     * @throws Throwable
     */
    private Expression callExpression(Expression callee) throws Throwable {
        Expression callExpression = new CallExpression(TypeEnum.CALL_EXPRESSION, callee, this.arguments());

        if (this._lookahead.getType().equals(TypeEnum.LEFT_PARANTHESIS)) {
            callExpression = this.callExpression(callExpression);
        }
        return callExpression;
    }

    /**
     * Arguments
     * : '(' OptArgumentList ')'
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private List<Expression> arguments() throws Throwable {
        this._eat(TypeEnum.LEFT_PARANTHESIS);

        List<Expression> argumentList = this._lookahead.getType() != TypeEnum.RIGTH_PARANTHESIS
                ? this.argumentList()
                : new ArrayList<>();

        this._eat(TypeEnum.RIGTH_PARANTHESIS);

        return argumentList;
    }

    /**
     * ArgumentList
     * : AssignmentExpression
     * | ArgumentList ',' AssignmentExpression
     * 
     * @return
     * @throws Throwable
     */
    private List<Expression> argumentList() throws Throwable {
        List<Expression> argumentList = new ArrayList<>();

        do {
            argumentList.add(this.assignmentExpression());
        } while (this._lookahead.getType().equals(TypeEnum.COMMA) && this._eat(TypeEnum.COMMA) != null);

        return argumentList;
    }

    /**
     * MemberExpression
     * : PrimaryExpression
     * | MemberExpression '.' Identifier
     * | MemberExpression '[' Expression ']'
     * 
     * @return
     * @throws Throwable
     */
    private Expression memberExpression() throws Throwable {
        Expression object = this.primaryExpression();

        while (this._lookahead.getType().equals(TypeEnum.DOT)
                || this._lookahead.getType().equals(TypeEnum.LEFT_SQUARED_BRACE)) {
            // MemberExpression '.' Identifier
            if (this._lookahead.getType().equals(TypeEnum.DOT)) {
                this._eat(TypeEnum.DOT);
                Expression property = this.identifier();
                object = new MemberExpression(TypeEnum.MEMBER_EXPRESSION, false, object, property);
            }
            // MemberExpression '[' Identifier ']'
            if (this._lookahead.getType().equals(TypeEnum.LEFT_SQUARED_BRACE)) {
                this._eat(TypeEnum.LEFT_SQUARED_BRACE);
                Expression property = this.expression();
                this._eat(TypeEnum.RIGHT_SQUARED_BRACE);
                object = new MemberExpression(TypeEnum.MEMBER_EXPRESSION, true, object, property);
            }
        }
        return object;
    }

    /**
     * PrimaryExpression
     * : Literal
     * | ParanthesizedExpression
     * | Identifier
     * | ThisExpression
     * | NewExpression
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private Expression primaryExpression() throws Throwable {
        if (this.isLiteral(this._lookahead.getType())) {
            return this.literal();
        }
        switch (this._lookahead.getType()) {
            case LEFT_PARANTHESIS:
                return this.paranthesizedExpression();
            case IDENTIFIER:
                return this.identifier();
            case THIS:
                return this.thisExpression();
            case NEW:
                return this.newExpression();
            default:
                return this.leftHandSidExpression();
        }
    }

    /**
     * NewExpression
     * : 'new' MemberExpression Arguments
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private NewExpression newExpression() throws Throwable {
        this._eat(TypeEnum.NEW);
        return new NewExpression(TypeEnum.NEW_EXPRESSION, this.memberExpression(), this.arguments());
    }

    /**
     * ThisExpression
     * : 'this'
     * ;
     * 
     * @return
     * @throws Throwable
     */
    private ThisExpression thisExpression() throws Throwable {
        this._eat(TypeEnum.THIS);
        return new ThisExpression(TypeEnum.THIS_EXPRESSION);
    }

    private SuperExpression superExpression() throws Throwable {
        this._eat(TypeEnum.SUPER);
        return new SuperExpression(TypeEnum.SUPER_EXPRESSION);
    }

    private boolean isLiteral(TypeEnum tokenType) {
        return tokenType.equals(TypeEnum.NUMBER)
                || tokenType.equals(TypeEnum.STRING)
                || tokenType.equals(TypeEnum.TRUE)
                || tokenType.equals(TypeEnum.FALSE)
                || tokenType.equals(TypeEnum.NULL);
    }

    private Expression paranthesizedExpression() throws Throwable {
        this._eat(TypeEnum.LEFT_PARANTHESIS);
        Expression expression = this.expression();
        this._eat(TypeEnum.RIGTH_PARANTHESIS);
        return expression;
    }

    /**
     * Literal
     * : StringLiteral
     * | NumericLiteral
     * | BooleanLiteral
     * | NullLitearl
     * @return(Literal)
     */
    private Literal literal() throws Throwable {
        switch (this._lookahead.getType()) {
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

    private BooleanLiteral booleanLiteral(boolean value) throws Throwable {
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

    private NumericLiteral numericLitearl() throws Exception {
        Token token = this._eat(TypeEnum.NUMBER);

        return new NumericLiteral(TypeEnum.NUMBER, token.getValue());
    }

    private Token _eat(TypeEnum tokenType) throws Exception {
        Token token = this._lookahead;

        if (token == null) {
            throw new Exception(
                    "Unexpected end of input, expected:" + tokenType);
        }

        if (!token.getType().equals(tokenType)) {
            throw new Exception(
                    "Unexpected token type " + token + " , getting " + tokenType);
        }

        this._lookahead = this._tokenizer.getNextToken();
        // System.out.println(this._lookahead);
        return token;
    }

}
