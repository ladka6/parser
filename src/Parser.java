import errors.SyntaxError;
import java.util.ArrayList;
import java.util.List;
import tokenizer.AssignmentExpression;
import tokenizer.BinaryExpression;
import tokenizer.BlockStatement;
import tokenizer.EmptyStatement;
import tokenizer.Expression;
import tokenizer.ExpressionStatement;
import tokenizer.Identifier;
import tokenizer.Literal;
import tokenizer.NumericalLiteral;
import tokenizer.ParenthesizedExpression;
import tokenizer.Program;
import tokenizer.Statement;
import tokenizer.StringLiteral;
import utils.ParserUtils;





public class Parser {
	private String _string;
	private Literal _lookahead;
	private Tokenizer _tokenizer;
	private ParserUtils parserUtils = new ParserUtils();

	public Parser() {
		_tokenizer = new Tokenizer();
	}
	
	public Object parse(String string) throws Exception {
		this._string = string;	
		this._tokenizer.init(string);
		
		this._lookahead = this._tokenizer.getNextToken();
		
		return this.Program();
	}
	
	private Program Program() throws Exception{
		return new Program("Program", this.StatementList(null));
	}

	private List<Statement> StatementList(String stopLookahead) throws Exception{

		List<Statement> statementList = new ArrayList<>();
		statementList.add((tokenizer.Statement) this.Statement());
		
		while (this._lookahead != null && !this._lookahead.getType().equals(stopLookahead)) {
			statementList.add((tokenizer.Statement) this.Statement());
		}
		
		return statementList;
	}

	private Object Statement() throws Exception {
		switch(this._lookahead.getType()) {
			case ";":	
				return this.EmptyStatement();
			case "{":
				return this.BlockStatement();
			default:	
				return this.ExpressionStatement();
		}
	}

	private EmptyStatement EmptyStatement() throws  Exception {
		this._eat(";");
        return new EmptyStatement("EmptyStatement");
	}

	private BlockStatement BlockStatement() throws Exception {
		this._eat("{");

		List<Statement> body = this._lookahead.getType() != "}" ? this.StatementList(null) : new ArrayList<>();

		this._eat("}");

		return new BlockStatement("BlockStatement", body);
	}

	private ExpressionStatement ExpressionStatement() throws Exception{
		Expression expression = this.Expression();
		
		this._eat(";");

		return new ExpressionStatement("ExpressionStatement",expression);
	}

	private Expression Expression() throws Exception {
		return (tokenizer.Expression) this.AssignemntExpression();
	}

	private Object AssignemntExpression() throws Exception{
		BinaryExpression left = this.AdditiveExpression();

		if(!this.parserUtils.isAssignmentOperator(this._lookahead.getType())) {
			return left;
		}

		return new AssignmentExpression(
			"AssignmentExpression", 
			this.AssignmentOperator().getValue(),
			(Identifier) this.parserUtils._checkValidAssigmentTarget(left),
			(Literal) this.AssignemntExpression()
		);
	}

	private Identifier LeftHandSideExpression() throws Exception{
		return this.Identifier();
	}

	private Identifier Identifier() throws Exception {
		String name = this._eat("IDENTIFIER").getValue();
		return new Identifier("Identifier", name);
	}


	private Literal AssignmentOperator() throws Exception {
        if (this._lookahead.getType() == "SIMPLE_ASSIGN") {
            return this._eat("SIMPLE_ASSIGN");
        }
        return this._eat("COMPLEX_ASSIGN");
    }

	private BinaryExpression AdditiveExpression() throws Exception {
        return (BinaryExpression) this._BinaryExpression(
            "MultiplicativeExpression",
            "ADDITIVE_OPERATOR"
        );
    }

	private BinaryExpression MultiplicativeExpression() throws Exception {
        return (BinaryExpression) this._BinaryExpression(
            "PrimaryExpression",
            "MULTIPLICATIVE_OPERATOR"
        );
    }

	private Object _BinaryExpression(String builderName, String operatorToken) throws Exception {
        Identifier left = (tokenizer.Identifier) this._callBuilderMethod(builderName);
		BinaryExpression binaryExpression = null;
        while (this._lookahead.getType() == operatorToken) {
            String operator = this._eat(operatorToken).getValue();

            Literal right = (tokenizer.Literal) this._callBuilderMethod(builderName);

            binaryExpression = new BinaryExpression("BinaryExpression" ,operator, left, right);
			
        }

        return binaryExpression != null ? binaryExpression : left;
    }

	private Object PrimaryExpression() throws Exception {
        if (parserUtils._isLiteral(this._lookahead.getType())) {
            return this.Literal();
        }
        switch (this._lookahead.getType()) {
            case "(":
                return this.ParenthesizedExpression();
            default:
                return this.Literal();
        }
    }

	private ParenthesizedExpression ParenthesizedExpression() throws Exception{
        this._eat("(");
        Expression expression = this.Expression();
        this._eat(")");
		ParenthesizedExpression parenthesizedExpression = new ParenthesizedExpression(expression.getType(), expression.getOperation(), expression.getLeft(),expression.getRight());
        return parenthesizedExpression;
    }
	
	private Literal Literal() throws Exception {
		switch(this._lookahead.getType()) {
			case "STRING": 
				return this.StringLiteral();
			case "NUMBER":
				return this.NumericLiteral();
			default: 
				throw new SyntaxError("Literal: unexpecred literal production");
		}
	}
	
	private NumericalLiteral NumericLiteral() throws SyntaxError {
		Literal token = this._eat("NUMBER");
		
		return new NumericalLiteral("NumericLiteral",token.getValue());
	}
	
	private StringLiteral StringLiteral() throws SyntaxError {
		Literal token = this._eat("STRING");
		return new StringLiteral("StringLiteral",token.getValue());
	}
	
	
	private Literal _eat(String tokenType) throws SyntaxError {
		Literal token = this._lookahead;

	    if (token == null) {
	      throw new SyntaxError(
	        "Unexpected end of input, expected: " + tokenType
	      );
	    }

	    if (token.getType() != tokenType) {
	      throw new SyntaxError(
	        "Unexpected token "+ token.getType() + ", expected: " + tokenType
	      );
	    }

	    this._lookahead = (Literal)this._tokenizer.getNextToken();

	    return token;
	}

	public Object _callBuilderMethod(String methodName)throws Exception {
        if(methodName.equals("PrmiaryExpression")) {
			return PrimaryExpression();
		}
		else {
			return MultiplicativeExpression();
		}
    }
	
}

