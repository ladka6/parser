import Errors.SyntaxError;
import enums.HtmlEnums;

public class Parser {
	private String _string;
	private Token _lookahead;
	private Tokenizer _tokenizer;
	public Parser() {
		// TODO Auto-generated constructor stub
		_tokenizer = new Tokenizer();
	}
	
	public Object parse(String string) throws Exception {
		this._string = string;	
		this._tokenizer.init(string);
		
		this._lookahead = this._tokenizer.getNextToken();
		
		return this.Program();
	}
	
	private Object Program() throws Exception {
		return new Program("Program",Literal());
	}
	
	private Token Literal() throws Exception {
		switch(this._lookahead.getType()) {
			case ELEMENT: 
				return this.ElementLiteral();
			default: 
				throw new SyntaxError("Literal: unexpecred literal production");
		}
	}
		
	private Token ElementLiteral() throws SyntaxError {
		Token token = this._eat(HtmlEnums.ELEMENT);
		
		return new Token(HtmlEnums.ELEMENT,token.getTag(),token.getContent());
	}
	
//	private Token NumericLiteral() throws SyntaxError {
//		Token token = this._eat("NUMBER");
//		
//		return new Token("NumericLiteral",token.getValue());
//	}
	
	private Token _eat(HtmlEnums tokenType) throws SyntaxError {
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

