

import java.util.ArrayList;
import java.util.List;

import errors.SyntaxError;

public class Parser {
	private String _string;
	private Token _lookahead;
	private Tokenizer _tokenizer;
	public Parser() {
		_tokenizer = new Tokenizer();
	}
	
	public Object parse(String string) throws Exception {
		this._string = string;	
		this._tokenizer.init(string);
		
		this._lookahead = this._tokenizer.getNextToken();
		
		return this.Program();
	}
	
	private Object Program() throws Exception {
		return Literal();
	}
	
	private Token Literal() throws Exception {
		switch(this._lookahead.getType()) {
			case "STRING": 
				return this.StringLiteral();
			case "NUMBER":
				return this.NumericLiteral();
			default: 
				throw new SyntaxError("Literal: unexpecred literal production");
		}
	}
	
	private Token NumericLiteral() throws SyntaxError {
		Token token = this._eat("NUMBER");
		
		return new Token("NumericLiteral",token.getValue());
	}
	
	private Token StringLiteral() throws SyntaxError {
		Token token = this._eat("STRING");
		return new Token("StringLiteral",token.getValue());
	}
	
	
	private Token _eat(String tokenType) throws SyntaxError {
		Token token = this._lookahead;

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

	    this._lookahead = this._tokenizer.getNextToken();

	    return token;
	}
	
}

