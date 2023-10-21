
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
	
	private Object Literal() throws Exception {
		switch(this._lookahead.getType()) {
			case "NUMBER": 
				return this.NumericLiteral();
			default: 
				throw new SyntaxError("Literal: unexpecred literal production");
		}
	}
	
	private Object NumericLiteral() throws SyntaxError {
		Token token = this._eat("NUMBER");
		
		return new Token("NumericLiteral",token.getValue());
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

class Token {
	private String type;
	private int value;
	
	public Token(String type,int value) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.value = value;
	}
	
	public int getValue() { 
		return this.value;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
    public String toString() {
        return "Token{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
class Program {
	private String type;
	private Object body;
	
	public Program(String type, Object body) {
		this.type = type;
		this.body = body;
	}
	
	@Override
	public String toString() {
        return "Program{" +
                "type='" + type + '\'' +
                ", body=" + body +
                '}';
    }
}

class SyntaxError extends Exception {
	public SyntaxError(String str) {
		super(str);
	}
}
