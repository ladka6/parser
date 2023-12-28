package com.backend.backend.parser;

import java.util.ArrayList;
import java.util.List;

import com.backend.backend.exception.parserException.AdditiveExpressionException;
import com.backend.backend.exception.parserException.ArgumentListException;
import com.backend.backend.exception.parserException.ArgumentsException;
import com.backend.backend.exception.parserException.AssignmentExpressionException;
import com.backend.backend.exception.parserException.AssignmentOperatorExpressionException;
import com.backend.backend.exception.parserException.BlockStatementException;
import com.backend.backend.exception.parserException.BooleanLiteralException;
import com.backend.backend.exception.parserException.CallExpressionException;
import com.backend.backend.exception.parserException.CallMemberExpressionException;
import com.backend.backend.exception.parserException.ClassDecarationException;
import com.backend.backend.exception.parserException.DoWhileStatementException;
import com.backend.backend.exception.parserException.EmptyStatementException;
import com.backend.backend.exception.parserException.EqualityExpressionException;
import com.backend.backend.exception.parserException.ExpressionException;
import com.backend.backend.exception.parserException.ExpressionStatementException;
import com.backend.backend.exception.parserException.ForStatementException;
import com.backend.backend.exception.parserException.ForStatementInitException;
import com.backend.backend.exception.parserException.FormalParameterListException;
import com.backend.backend.exception.parserException.FunctionDeclarationException;
import com.backend.backend.exception.parserException.FunctionOrVariableException;
import com.backend.backend.exception.parserException.IdentifierException;
import com.backend.backend.exception.parserException.IfStatementException;
import com.backend.backend.exception.parserException.ImportStatementException;
import com.backend.backend.exception.parserException.IterationStatementException;
import com.backend.backend.exception.parserException.LeftHandSideExpressionException;
import com.backend.backend.exception.parserException.LiteralExpressionException;
import com.backend.backend.exception.parserException.LogicalAndExpressionException;
import com.backend.backend.exception.parserException.LogicalOrExpressionException;
import com.backend.backend.exception.parserException.MemberExpressionException;
import com.backend.backend.exception.parserException.MultiplicativeExpressionException;
import com.backend.backend.exception.parserException.NewExpressionException;
import com.backend.backend.exception.parserException.NullLiteralException;
import com.backend.backend.exception.parserException.NumericLiteralException;
import com.backend.backend.exception.parserException.ParanthesizedExpressionException;
import com.backend.backend.exception.parserException.PrimaryExpressionException;
import com.backend.backend.exception.parserException.ProgramException;
import com.backend.backend.exception.parserException.RelationalExpressionException;
import com.backend.backend.exception.parserException.ReturnStatementException;
import com.backend.backend.exception.parserException.StatementException;
import com.backend.backend.exception.parserException.StatementListException;
import com.backend.backend.exception.parserException.StringLiteralException;
import com.backend.backend.exception.parserException.SuperExpressionException;
import com.backend.backend.exception.parserException.SyntaxError;
import com.backend.backend.exception.parserException.ThisExpressionException;
import com.backend.backend.exception.parserException.UnaryExpressionException;
import com.backend.backend.exception.parserException.ValidAssignmentException;
import com.backend.backend.exception.parserException.VariableDeclarationException;
import com.backend.backend.exception.parserException.VariableDeclarationListException;
import com.backend.backend.exception.parserException.VariableInitializerException;
import com.backend.backend.exception.parserException.VariableStatementException;
import com.backend.backend.exception.parserException.VariableStatementInitException;
import com.backend.backend.exception.parserException.WhileStatementException;
import com.backend.backend.parser.types.AssignmentExpression;
import com.backend.backend.parser.types.BinaryExpression;
import com.backend.backend.parser.types.BlockStatement;
import com.backend.backend.parser.types.CallExpression;
import com.backend.backend.parser.types.ClassDeclaration;
import com.backend.backend.parser.types.DoWhileStatement;
import com.backend.backend.parser.types.EmptyStatement;
import com.backend.backend.parser.types.Expression;
import com.backend.backend.parser.types.ExpressionStatement;
import com.backend.backend.parser.types.ForStatement;
import com.backend.backend.parser.types.FunctionDeclaration;
import com.backend.backend.parser.types.FunctionParams;
import com.backend.backend.parser.types.Identifier;
import com.backend.backend.parser.types.IfStatement;
import com.backend.backend.parser.types.ImportStatement;
import com.backend.backend.parser.types.LogicalExpression;
import com.backend.backend.parser.types.MemberExpression;
import com.backend.backend.parser.types.NewExpression;
import com.backend.backend.parser.types.Program;
import com.backend.backend.parser.types.ReturnStatement;
import com.backend.backend.parser.types.SuperExpression;
import com.backend.backend.parser.types.ThisExpression;
import com.backend.backend.parser.types.Token;
import com.backend.backend.parser.types.TypeEnum;
import com.backend.backend.parser.types.UnaryExpression;
import com.backend.backend.parser.types.VariableDeclaration;
import com.backend.backend.parser.types.VariableStatement;
import com.backend.backend.parser.types.WhileStatement;
import com.backend.backend.parser.types.literals.BooleanLiteral;
import com.backend.backend.parser.types.literals.Literal;
import com.backend.backend.parser.types.literals.NullLiteral;
import com.backend.backend.parser.types.literals.NumericLiteral;
import com.backend.backend.parser.types.literals.StringLiteral;

public class Parser {
    private String _string;
    private Tokenizer _tokenizer;
    private Token _lookahead;

    private List<String> paths = new ArrayList<>() {
        {
            add("String");
            add("int");
            add("boolean");
            add("float");
            add("double");
        }
    };

    private List<Expression> realList = new ArrayList<>();

    public Parser() {
        this._tokenizer = new Tokenizer();
    }

    private void setPaths(String path) {
        this.paths.add(path);
    }

    private List<String> getPaths() {
        return this.paths;
    }

    public List<Expression> getStatementList() {
        return this.realList;
    }

    public void setStatementList(Expression statement) {
        this.realList.add(statement);
    }

    private void resetStatementList() {
        this.realList.clear();
    }

    public Program parse(String string) throws ProgramException {
        try {
            this.resetStatementList();

            this._string = string;
            this._tokenizer.init(string);

            this._lookahead = this._tokenizer.getNextToken();

            return this.program();
        } catch (Exception e) {
            throw new ProgramException("Unexpected program");
        }
    }

    /*
     * Program
     * StatementList
     * ;
     */
    private Program program() throws ProgramException {
        try {
            return new Program(TypeEnum.PROGRAM, this.statementList(null));
        } catch (Exception e) {
            throw new ProgramException("Unexpected program");
        }
    }

    /**
     * StatementList
     * : Statement
     * | StatementList Statement
     * 
     * @return
     * @throws Exception
     */
    private List<Expression> statementList(TypeEnum stopLookahead) throws StatementListException {
        try {
            List<Expression> statementList = new ArrayList<>();
            Expression firstStatement = this.statement();
            setStatementList(firstStatement);
            statementList.add(firstStatement);

            while (this._lookahead != null && this._lookahead.getType() != stopLookahead) {
                Expression statement = this.statement();
                setStatementList(statement);
                statementList.add(statement);

            }

            return statementList;
        } catch (Exception e) {
            throw new StatementListException("Unexpected statement list");
        }
    }

    /**
     * Statement
     * : ExpressionStatement
     * | ImportStatement
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
    private Expression statement() throws StatementException {
        try {
            switch (this._lookahead.getType()) {
                case IMPORT_STATEMENT:
                    return this.importStatement();
                case SEMICOLON:
                    return emptyStatement();
                case IF:
                    return ifStatement();
                case LEFT_BRACE:
                    return blockStatement();
                case IDENTIFIER:
                    return this.functionOrVariable();
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
        } catch (Exception e) {
            throw new StatementException("Unexpected statement");
        }
    }

    private ImportStatement importStatement() throws ImportStatementException {
        try {
            List<String> imports = new ArrayList<>();

            while (this._lookahead != null && this._lookahead.getType().equals(TypeEnum.IMPORT_STATEMENT)) {
                String path = this._eat(TypeEnum.IMPORT_STATEMENT).getValue();
                String[] splitted = path.split(" ");
                String[] importStatementSplitted = splitted[1].split("\\.");

                this._eat(TypeEnum.SEMICOLON);
                String current = importStatementSplitted[importStatementSplitted.length - 1];
                imports.add(current);
                this.setPaths(current);
            }

            return new ImportStatement(imports);
        } catch (Exception e) {
            throw new ImportStatementException("Unexpected import statement");
        }
    }

    /**
     * ClassDeclaration
     * : 'class' Identifier OptClassExtends BlockStatement
     * ;
     * 
     * @return
     */
    private ClassDeclaration classDeclaration() throws ClassDecarationException {
        try {
            this._eat(TypeEnum.CLASS);

            Identifier id = this.identifier();

            Identifier superClass = this._lookahead.getType().equals(TypeEnum.EXTENDS)
                    ? this.classExtends()
                    : null;

            BlockStatement body = this.blockStatement();

            return new ClassDeclaration(TypeEnum.CLASS_DECLARATION, id, superClass, body);
        } catch (Exception e) {
            throw new ClassDecarationException("Unexpected class declaration");
        }
    }

    /**
     * ClassExtends
     * : 'extends' Identifier
     * 
     * @return
     */
    private Identifier classExtends() throws ClassCastException {
        try {
            this._eat(TypeEnum.IDENTIFIER);
            return this.identifier();
        } catch (Exception e) {
            throw new ClassCastException("Unexpected class extends");
        }
    }

    private Expression functionOrVariable() throws FunctionOrVariableException {
        try {
            if (this._tokenizer.lookTwoAhead().getType().equals(TypeEnum.LEFT_PARANTHESIS)) {
                return this.functionDeclaration(false);
            } else if (this._tokenizer.lookOneAhead().getType().equals(TypeEnum.LEFT_PARANTHESIS)) {
                return this.functionDeclaration(true);
            } else {
                return this.variableStatement();
            }
        } catch (Exception e) {
            throw new FunctionOrVariableException("Unexpected function or variable");
        }
    }

    /**
     * FunctionDeclaration
     * : 'def' Identifier '(' OptFormalParameterList ')' BlockStatement
     * ;
     * 
     * @return
     */
    private FunctionDeclaration functionDeclaration(boolean isConstructor) throws FunctionDeclarationException {
        try {
            String type = null;

            if (!isConstructor) {
                type = this._eat(TypeEnum.IDENTIFIER).getValue();

                String[] checkMemberAccessArray = type.split(" ");
                if (checkMemberAccessArray.length > 1) {
                    type = checkMemberAccessArray[1];
                }

                if (!this.getPaths().contains(type)) {
                    throw new SyntaxError("Unspported function return type");
                }

            }

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

            return new FunctionDeclaration(TypeEnum.FUNCTION_DECLARATION, type, name, params, body);
        } catch (Exception e) {
            throw new FunctionDeclarationException("Unexpected function declaration");
        }
    }

    /**
     * FormalParameterList
     * : Identifier
     * | FormalParaterList ',' Identifier
     * ;
     * 
     * @return
     */
    private List<Expression> formalParameterList() throws FormalParameterListException {
        try {
            List<Expression> params = new ArrayList<>();

            do {
                String variableType = this._eat(TypeEnum.IDENTIFIER).getValue();
                String variableName = this._eat(TypeEnum.IDENTIFIER).getValue();
                FunctionParams functionParams = new FunctionParams(TypeEnum.IDENTIFIER, variableName, variableType);
                params.add(functionParams);
            } while (this._lookahead.getType().equals(TypeEnum.COMMA) && this._eat(TypeEnum.COMMA) != null);

            return params;
        } catch (Exception e) {
            throw new FormalParameterListException("Unexpected formal parameter list");
        }
    }

    /**
     * ReturnStatement
     * | 'return' OptExpression
     * 
     * @return
     */

    private ReturnStatement returnStatement() throws ReturnStatementException {
        try {
            this._eat(TypeEnum.RETURN);

            Expression arguments = this._lookahead.getType() != TypeEnum.SEMICOLON ? this.expression() : null;
            this._eat(TypeEnum.SEMICOLON);

            return new ReturnStatement(TypeEnum.RETURN_STATEMENT, arguments);
        } catch (Exception e) {
            throw new ReturnStatementException("Unexpected return statement");
        }
    }

    /**
     * IterationStatement
     * : WhileStatement
     * | DoWhileStatement
     * | ForStatement
     * 
     * @return
     */
    private Expression iterationStatement() throws IterationStatementException {
        try {
            switch (this._lookahead.getType()) {
                case WHILE:
                    return this.whileStatement();
                case DO:
                    return this.doWhileStatement();
                case FOR:
                    return this.forStatement();
                default:
                    break;
            }
            throw new SyntaxError("Error");
        } catch (Exception e) {
            throw new IterationStatementException("Unexpected iteration statement");
        }
    }

    // TODO implement types in loops

    /**
     * WhileStatement
     * : 'while' '(' Expression ')' Statement
     * ;
     * 
     * @return
     */
    private WhileStatement whileStatement() throws WhileStatementException {
        try {
            this._eat(TypeEnum.WHILE);
            this._eat(TypeEnum.LEFT_PARANTHESIS);

            Expression test = this.expression();

            this._eat(TypeEnum.RIGTH_PARANTHESIS);

            Expression body = this.statement();

            return new WhileStatement(TypeEnum.WHILE_STATEMENT, test, body);
        } catch (Exception e) {
            throw new WhileStatementException("Unexpected while statement");
        }
    }

    /**
     * DoWhileStatement
     * : 'do' Statement 'while' '{' Expression '}' ';'
     * 
     * @return
     */
    private DoWhileStatement doWhileStatement() throws DoWhileStatementException {
        try {
            this._eat(TypeEnum.DO);

            Expression body = this.statement();

            this._eat(TypeEnum.WHILE);
            this._eat(TypeEnum.LEFT_PARANTHESIS);

            Expression test = this.expression();

            this._eat(TypeEnum.RIGTH_PARANTHESIS);
            this._eat(TypeEnum.SEMICOLON);

            return new DoWhileStatement(TypeEnum.DO_WHILE_STATEMENT, body, test);
        } catch (Exception e) {
            throw new DoWhileStatementException("Unexpected do while statement");
        }
    }

    /**
     * ForStatement
     * : 'for' '{' OptStatementInit ';' OptExpression ';' OptExpression ')'
     * Statement
     * ;
     * 
     * @return
     */
    private ForStatement forStatement() throws ForStatementException {
        try {
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
            ;
            this._eat(TypeEnum.RIGTH_PARANTHESIS);

            Expression body = this.statement();

            return new ForStatement(TypeEnum.FOR_STATEMENT, init, test, update, body);
        } catch (Exception e) {
            throw new ForStatementException("Unexpected for statement");
        }
    }

    /**
     * ForStatementInit
     * : VariableStatementInit
     * | Expression
     * ;
     * 
     * @return
     */
    private Expression forStatementInit() throws ForStatementInitException {
        try {
            if (this._lookahead.getType().equals(TypeEnum.IDENTIFIER)) {
                return this.variableStatementInit();
            }
            return this.expression();
        } catch (Exception e) {
            throw new ForStatementInitException("Unexpected for statement init");
        }
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
    private IfStatement ifStatement() throws IfStatementException {
        try {
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
        } catch (Exception e) {
            throw new IfStatementException("Unexpected if statement");
        }
    }

    /**
     * VariableStatementInit
     * : Type VariableDeclarationList
     * : VariableDeclarationList
     * ;
     * 
     * @return
     */
    private VariableStatement variableStatementInit() throws VariableStatementInitException {
        try {
            List<VariableDeclaration> declarations = new ArrayList<>();
            String type = null;

            if (!this._tokenizer.lookOneAhead().getType().equals(TypeEnum.IDENTIFIER)) {
                VariableDeclaration declaration = this.variableDeclaration();
                declarations.add(declaration);
            } else {
                type = this._eat(TypeEnum.IDENTIFIER).getValue();

                String[] checkMemberAccessArray = type.split(" ");
                if (checkMemberAccessArray.length > 1) {
                    type = checkMemberAccessArray[1];
                }

                if (!this.getPaths().contains(type)) {
                    throw new SyntaxError("Unsupported variable type");
                }
                declarations = this.variableDeclarationList();
            }

            return new VariableStatement(TypeEnum.VARIABLE_STATEMENT, type, declarations);
        } catch (Exception e) {
            throw new VariableStatementInitException("Unexpected variable statement init");
        }
    }

    /**
     * VariableStatement
     * : VariableStatementInit ';'
     * ;
     * 
     * @return
     * @throws Exception
     */
    private VariableStatement variableStatement() throws VariableStatementException {
        try {
            VariableStatement variableStatement = this.variableStatementInit();
            this._eat(TypeEnum.SEMICOLON);
            return variableStatement;
        } catch (Exception e) {
            throw new VariableStatementException("Unexpected variable statement");
        }
    }

    /**
     * VariableDeclarationList
     * : VariableDeclaration
     * | VariableDeclarationList ',' VariableDeclaration
     */
    private List<VariableDeclaration> variableDeclarationList() throws VariableDeclarationListException {
        try {
            List<VariableDeclaration> declarations = new ArrayList<>();

            do {
                declarations.add(this.variableDeclaration());
            } while (this._lookahead.getType().equals(TypeEnum.COMMA) && this._eat(TypeEnum.COMMA) != null);

            return declarations;
        } catch (Exception e) {
            throw new VariableDeclarationListException("Unexpected variable declaration list");
        }
    }

    /**
     * VariableDeclaration
     * : Identifier OptVariableInitializer
     * ;
     */
    private VariableDeclaration variableDeclaration() throws VariableDeclarationException {
        try {
            Expression id = this.identifier();

            Expression init = this._lookahead.getType() != TypeEnum.SEMICOLON
                    && this._lookahead.getType() != TypeEnum.COMMA
                            ? this.variableInitializer()
                            : null;

            return new VariableDeclaration(TypeEnum.VARIABLE_DECLARATION, id, init);
        } catch (Exception e) {
            throw new VariableDeclarationException("Unexpected variable declaration");
        }
    }

    /**
     * variableInitializer
     * : SIMPLE_ASSIGN AssignementExpression
     * 
     * @return
     * @throws Exception
     */

    private Expression variableInitializer() throws VariableInitializerException {
        try {
            this._eat(TypeEnum.SIMPLE_ASSIGN);
            return this.assignmentExpression();
        } catch (Exception e) {
            throw new VariableInitializerException("Unexpected variable initializer");
        }
    }

    /**
     * EmptyStatement
     * 
     * @return
     * @throws Exception
     */
    private EmptyStatement emptyStatement() throws EmptyStatementException {
        try {
            this._eat(TypeEnum.SEMICOLON);
            return new EmptyStatement(TypeEnum.EMPTYSTATEMENT);
        } catch (Exception e) {
            throw new EmptyStatementException("Unexpected empty statement");
        }
    }

    /**
     * BlockStatement
     * : '{' OptStatementList '}'
     * ;
     * 
     * @return
     * @throws Exception
     */
    private BlockStatement blockStatement() throws BlockStatementException {
        try {
            this._eat(TypeEnum.LEFT_BRACE);

            List<Expression> body = this._lookahead.getType() != TypeEnum.RIGHT_BRACE
                    ? this.statementList(TypeEnum.RIGHT_BRACE)
                    : new ArrayList<>();

            this._eat(TypeEnum.RIGHT_BRACE);

            return new BlockStatement(TypeEnum.BLOCK_STATEMENT, body);
        } catch (Exception e) {
            throw new BlockStatementException("Unexpected block statement");
        }
    }

    /**
     * ExpressionStatement
     * : Expression ";"
     * ;
     * 
     * @return
     * @throws Exception
     */
    private ExpressionStatement expressionStatement() throws ExpressionStatementException {
        try {
            Expression expression = this.expression();
            this._eat(TypeEnum.SEMICOLON);
            return new ExpressionStatement(TypeEnum.EXPRESSION_STATEMENT, expression);
        } catch (Exception e) {
            throw new ExpressionStatementException("Unexpected expression statement");
        }
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
    private Expression expression() throws ExpressionException {
        try {
            return this.assignmentExpression();
        } catch (Exception e) {
            throw new ExpressionException("Unexpected expression");
        }
    }

    /**
     * AssignmentExpression
     * : RelationalExpression
     * | LeftHandSide AssingmentOperator AssignmentExpression
     * 
     * @return
     * @throws Exception
     */
    private Expression assignmentExpression() throws AssignmentExpressionException {
        try {
            Expression left = this.logicalORExpression();
            if (!this.isAssignmentOperator(this._lookahead.getType())) {
                return left;
            }

            return new AssignmentExpression(TypeEnum.ASSIGNMENT_EXPRESSION, this.assignmentOperator(),
                    this.checkValidAssignment(left), this.assignmentExpression());
        } catch (Exception e) {
            throw new AssignmentExpressionException("Unexpected assignment expression");
        }
    }

    /**
     * Identifier
     * : IDENTIFIER
     * ;
     * 
     * @return
     */
    private Identifier identifier() throws IdentifierException {
        try {
            String name = this._eat(TypeEnum.IDENTIFIER).getValue();
            return new Identifier(TypeEnum.IDENTIFIER, name);
        } catch (Exception e) {
            throw new IdentifierException("Unexpected identifier");
        }
    }

    private Expression checkValidAssignment(Expression node) throws ValidAssignmentException {
        if (node.getType().equals(TypeEnum.IDENTIFIER) || node.getType().equals(TypeEnum.MEMBER_EXPRESSION)) {
            return node;
        }
        throw new ValidAssignmentException("Invalid left-hand side in assignment expression");
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
    private Token assignmentOperator() throws AssignmentOperatorExpressionException {
        try {
            if (this._lookahead.getType().equals(TypeEnum.SIMPLE_ASSIGN)) {
                return this._eat(TypeEnum.SIMPLE_ASSIGN);
            }
            return this._eat(TypeEnum.COMPLEX_ASSIGN);
        } catch (Exception e) {
            throw new AssignmentOperatorExpressionException("Unexpected assignment operator expression");
        }
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
     */
    private Expression logicalANDExpression() throws LogicalAndExpressionException {
        try {
            Expression left = equalityExpression();

            while (this._lookahead != null && this._lookahead.getType() == TypeEnum.LOGICAL_AND) {
                Token operator = this._eat(TypeEnum.LOGICAL_AND);
                Expression right = this.equalityExpression();
                LogicalExpression logicalExpression = new LogicalExpression(TypeEnum.BINARY_EXPRESSION, operator, left,
                        right);
                left = logicalExpression;
            }

            return left;
        } catch (Exception e) {
            throw new LogicalAndExpressionException("Unexpected logical and expression");
        }
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
     */
    private Expression logicalORExpression() throws LogicalOrExpressionException {
        try {
            Expression left = logicalANDExpression();

            while (this._lookahead != null && this._lookahead.getType() == TypeEnum.LOGICAL_OR) {
                Token operator = this._eat(TypeEnum.LOGICAL_OR);
                Expression right = this.logicalANDExpression();
                LogicalExpression logicalExpression = new LogicalExpression(TypeEnum.BINARY_EXPRESSION, operator, left,
                        right);
                left = logicalExpression;
            }

            return left;
        } catch (Exception e) {
            throw new LogicalOrExpressionException("Unexpected logical or expression");
        }
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
     */
    private Expression equalityExpression() throws EqualityExpressionException {
        try {
            Expression left = relationalExpression();

            while (this._lookahead != null && this._lookahead.getType() == TypeEnum.EQUALITY_OPERATOR) {
                Token operator = this._eat(TypeEnum.EQUALITY_OPERATOR);
                Expression right = this.relationalExpression();
                BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION, operator, left,
                        right);
                left = binaryExpression;
            }

            return left;
        } catch (Exception e) {
            throw new EqualityExpressionException("Unexpected equality expression");
        }
    }

    /**
     * RELATIONAL_OPERATOR: <, <=, >, >=
     * 
     * RelationalExpression
     * : AdditiveExpression
     * | AdditiveExpression RELATIONAL_OPERATOR RelationalExpression
     * 
     * @return
     */
    private Expression relationalExpression() throws RelationalExpressionException {
        try {
            Expression left = additiveExpression();

            while (this._lookahead != null && this._lookahead.getType() == TypeEnum.RELATIONAL_OPERATOR) {
                Token operator = this._eat(TypeEnum.RELATIONAL_OPERATOR);
                Expression right = this.additiveExpression();
                BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION, operator, left,
                        right);
                left = binaryExpression;
            }

            return left;
        } catch (Exception e) {
            throw new RelationalExpressionException("Unexpected relational expression");
        }
    }

    /**
     * AdditiveExpression
     * : Literal
     * | AdditiveExpression ADDITIVE_OPERATOR Literal
     * 
     * @return
     * @throws Exception
     */
    private Expression additiveExpression() throws AdditiveExpressionException {
        try {
            Expression left = multiplicativeExpression();

            while (this._lookahead != null && this._lookahead.getType() == TypeEnum.ADDITIVE_OPERATOR) {
                Token operator = this._eat(TypeEnum.ADDITIVE_OPERATOR);
                Expression right = this.multiplicativeExpression();
                BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION, operator, left,
                        right);
                left = binaryExpression;
            }

            return left;
        } catch (Exception e) {
            throw new AdditiveExpressionException("Unexpected additive expression");
        }
    }

    private Expression multiplicativeExpression() throws MultiplicativeExpressionException {
        try {
            Expression left = unaryExpression();

            while (this._lookahead != null && this._lookahead.getType() == TypeEnum.MULTIPLICATIVE_OPERATOR) {
                Token operator = this._eat(TypeEnum.MULTIPLICATIVE_OPERATOR);
                Expression right = this.unaryExpression();
                BinaryExpression binaryExpression = new BinaryExpression(TypeEnum.BINARY_EXPRESSION, operator, left,
                        right);
                left = binaryExpression;
            }

            return left;
        } catch (Exception e) {
            throw new MultiplicativeExpressionException("Unexpected multiplicative expression");
        }
    }

    /**
     * UnaryExpression
     * : LeftHandSideExpression
     * | ADDITIVE_OPERATOR UnaryExpression
     * | LOGCAL_NOT UnaryExpression
     * 
     * @return
     */
    private Expression unaryExpression() throws UnaryExpressionException {
        try {
            Token operator = null;
            switch (this._lookahead.getType()) {
                case ADDITIVE_OPERATOR:
                    operator = this._eat(TypeEnum.ADDITIVE_OPERATOR);
                    break;
                case LOGICAL_NOT:
                    operator = this._eat(TypeEnum.LOGICAL_NOT);
                    break;

            }

            if (operator != null) {
                return new UnaryExpression(TypeEnum.UNARY_EXPRESSION, operator, this.unaryExpression());
            }

            return this.leftHandSidExpression();
        } catch (Exception e) {
            throw new UnaryExpressionException("Unexpected unary expression");
        }
    }

    /**
     * LeftHandSideExpression
     * : CallMemberExpression
     * ;
     * 
     * @return
     */
    private Expression leftHandSidExpression() throws LeftHandSideExpressionException {
        try {
            return this.callMemberExpression();
        } catch (Exception e) {
            throw new LeftHandSideExpressionException("Unexpected left hand side expression");
        }
    }

    /**
     * CallMemberExpression
     * : MemberExpression
     * | CallExpression
     * 
     * @return
     */
    private Expression callMemberExpression() throws CallMemberExpressionException {
        try {
            if (this._lookahead.getType().equals(TypeEnum.SUPER)) {
                return this.callExpression(this.superExpression());
            }

            Expression member = this.memberExpression();

            if (this._lookahead.getType().equals(TypeEnum.LEFT_PARANTHESIS)) {
                return this.callExpression(member);
            }

            return member;
        } catch (Exception e) {
            throw new CallMemberExpressionException("Unexpected call member expression");
        }
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
     */
    private Expression callExpression(Expression callee) throws CallExpressionException {
        try {
            Expression callExpression = new CallExpression(TypeEnum.CALL_EXPRESSION, callee, this.arguments());

            if (this._lookahead.getType().equals(TypeEnum.LEFT_PARANTHESIS)) {
                callExpression = this.callExpression(callExpression);
            }
            return callExpression;
        } catch (Exception e) {
            throw new CallExpressionException("Unexpected call expression");
        }
    }

    /**
     * Arguments
     * : '(' OptArgumentList ')'
     * ;
     * 
     * @return
     */
    private List<Expression> arguments() throws ArgumentsException {
        try {
            this._eat(TypeEnum.LEFT_PARANTHESIS);

            List<Expression> argumentList = this._lookahead.getType() != TypeEnum.RIGTH_PARANTHESIS
                    ? this.argumentList()
                    : new ArrayList<>();

            this._eat(TypeEnum.RIGTH_PARANTHESIS);

            return argumentList;
        } catch (Exception e) {
            throw new ArgumentsException("Unexpected argument list");
        }
    }

    /**
     * ArgumentList
     * : AssignmentExpression
     * | ArgumentList ',' AssignmentExpression
     * 
     * @return
     */
    private List<Expression> argumentList() throws ArgumentListException {
        try {
            List<Expression> argumentList = new ArrayList<>();

            do {
                argumentList.add(this.assignmentExpression());
            } while (this._lookahead.getType().equals(TypeEnum.COMMA) && this._eat(TypeEnum.COMMA) != null);

            return argumentList;
        } catch (Exception e) {
            throw new ArgumentListException("Unexpected argument list");
        }
    }

    /**
     * MemberExpression
     * : PrimaryExpression
     * | MemberExpression '.' Identifier
     * | MemberExpression '[' Expression ']'
     * 
     * @return
     */
    private Expression memberExpression() throws MemberExpressionException {
        try {
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
        } catch (Exception e) {
            throw new MemberExpressionException("Unexpected member expression");
        }
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
     */
    private Expression primaryExpression() throws PrimaryExpressionException {
        try {
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
        } catch (Exception e) {
            throw new PrimaryExpressionException("Unexpected primary expression");
        }
    }

    /**
     * NewExpression
     * : 'new' MemberExpression Arguments
     * ;
     * 
     * @return
     */
    private NewExpression newExpression() throws NewExpressionException {
        try {
            this._eat(TypeEnum.NEW);
            return new NewExpression(TypeEnum.NEW_EXPRESSION, this.memberExpression(), this.arguments());
        } catch (Exception e) {
            throw new NewExpressionException("Unexpected new expression");
        }
    }

    /**
     * ThisExpression
     * : 'this'
     * ;
     * 
     * @return
     */
    private ThisExpression thisExpression() throws ThisExpressionException {
        try {
            this._eat(TypeEnum.THIS);
            return new ThisExpression(TypeEnum.THIS_EXPRESSION);
        } catch (Exception e) {
            throw new ThisExpressionException("Unexpected this expression");
        }
    }

    private SuperExpression superExpression() throws SuperExpressionException {
        try {
            this._eat(TypeEnum.SUPER);
            return new SuperExpression(TypeEnum.SUPER_EXPRESSION);
        } catch (Exception e) {
            throw new SuperExpressionException("Unexpected super expression");
        }
    }

    private boolean isLiteral(TypeEnum tokenType) {
        return tokenType.equals(TypeEnum.NUMBER)
                || tokenType.equals(TypeEnum.STRING)
                || tokenType.equals(TypeEnum.TRUE)
                || tokenType.equals(TypeEnum.FALSE)
                || tokenType.equals(TypeEnum.NULL);
    }

    private Expression paranthesizedExpression() throws ParanthesizedExpressionException {
        try {
            this._eat(TypeEnum.LEFT_PARANTHESIS);
            Expression expression = this.expression();
            this._eat(TypeEnum.RIGTH_PARANTHESIS);
            return expression;
        } catch (Exception e) {
            throw new ParanthesizedExpressionException("Unexpected paranthesized expression");
        }

    }

    /**
     * Literal
     * : StringLiteral
     * | NumericLiteral
     * | BooleanLiteral
     * | NullLitearl
     * @return(Literal)
     */
    private Literal literal() throws LiteralExpressionException {
        try {
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
        } catch (Exception e) {
            throw new LiteralExpressionException("Unexpected literal expression");
        }
    }

    private BooleanLiteral booleanLiteral(boolean value) throws BooleanLiteralException {
        try {
            this._eat(value ? TypeEnum.TRUE : TypeEnum.FALSE);
            return new BooleanLiteral(TypeEnum.BOOLEAN_LITERAL, String.valueOf(value));
        } catch (Exception e) {
            throw new BooleanLiteralException("Unexpected boolean literal");
        }
    }

    private NullLiteral nullLiteral() throws NullLiteralException {
        try {
            this._eat(TypeEnum.NULL);
            return new NullLiteral(TypeEnum.NULL_LITERAL, null);
        } catch (Exception e) {
            throw new NullLiteralException("Unexpected null literal");
        }
    }

    private StringLiteral stringLiteral() throws StringLiteralException {
        try {
            Token token = this._eat(TypeEnum.STRING);
            return new StringLiteral(TypeEnum.STRING, token.getValue());
        } catch (Exception e) {
            throw new StringLiteralException("Unexpected string literal");
        }
    }

    private NumericLiteral numericLitearl() throws NumericLiteralException {
        try {
            Token token = this._eat(TypeEnum.NUMBER);
            return new NumericLiteral(TypeEnum.NUMBER, token.getValue());
        } catch (Exception e) {
            throw new NumericLiteralException("Unexpected numeric literal");
        }
    }

    private Token _eat(TypeEnum tokenType) throws SyntaxError {
        Token token = this._lookahead;

        if (token == null) {
            throw new SyntaxError(
                    "Unexpected end of input, expected:" + tokenType);
        }

        if (!token.getType().equals(tokenType)) {
            throw new SyntaxError(
                    "Unexpected token type " + token + " , getting " + tokenType);
        }

        this._lookahead = this._tokenizer.getNextToken();
        return token;
    }

}
