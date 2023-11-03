import errors.SyntaxError;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import types.Token;
import types.TypeEnum;


public class Tokenizer {
	private String _string;
	private int cursor;

	public static List<TokenSpec> getTokenSpec() {
		List<TokenSpec> tokenSpecs = new ArrayList<>();
		//Keywords:
		tokenSpecs.add(new TokenSpec("^\\blet\\b", TypeEnum.LET));
		tokenSpecs.add(new TokenSpec("^\\bif\\b", TypeEnum.IF));
		tokenSpecs.add(new TokenSpec("^\\belse\\b", TypeEnum.ELSE));
		tokenSpecs.add(new TokenSpec("^\\bture\\b", TypeEnum.TRUE));
		tokenSpecs.add(new TokenSpec("^\\bfalse\\b", TypeEnum.FALSE));
		tokenSpecs.add(new TokenSpec("^\\bnull\\b", TypeEnum.NULL));
		tokenSpecs.add(new TokenSpec("^\\bwhile\\b", TypeEnum.WHILE));
		tokenSpecs.add(new TokenSpec("^\\bdo\\b", TypeEnum.DO));
		tokenSpecs.add(new TokenSpec("^\\bfor\\b", TypeEnum.FOR));
		tokenSpecs.add(new TokenSpec("^\\bdef\\b", TypeEnum.DEF));
		tokenSpecs.add(new TokenSpec("^\\breturn\\b", TypeEnum.RETURN));

		// Whitespace
		tokenSpecs.add(new TokenSpec("^\\s+", null));
		
		// Skip single-line comments
		tokenSpecs.add(new TokenSpec("^//.*", null));
		
		// MultiLine /* */
		tokenSpecs.add(new TokenSpec("^/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/", null));
		
		// Symbol delimiters
		tokenSpecs.add(new TokenSpec("^;", TypeEnum.SEMICOLON));
		tokenSpecs.add(new TokenSpec("^\\{", TypeEnum.LEFT_BRACE));
		tokenSpecs.add(new TokenSpec("^\\}", TypeEnum.RIGHT_BRACE));
		tokenSpecs.add(new TokenSpec("^\\(", TypeEnum.LEFT_PARANTHESIS));
		tokenSpecs.add(new TokenSpec("^\\)", TypeEnum.RIGTH_PARANTHESIS));
		tokenSpecs.add(new TokenSpec("^\\,", TypeEnum.COMMA));
		tokenSpecs.add(new TokenSpec("^\\.", TypeEnum.DOT));
		tokenSpecs.add(new TokenSpec("^\\[", TypeEnum.LEFT_SQUARED_BRACE));
		tokenSpecs.add(new TokenSpec("^\\]", TypeEnum.RIGHT_SQUARED_BRACE));

		
		
		// Number
		tokenSpecs.add(new TokenSpec("^\\d+", TypeEnum.NUMBER));
		
		// Identifiers
		tokenSpecs.add(new TokenSpec("^\\w+", TypeEnum.IDENTIFIER));

		//Equality 
		tokenSpecs.add((new TokenSpec("^[=!]=", TypeEnum.EQUALITY_OPERATOR)));

		// Assignment Operators: *= /= += -=
		tokenSpecs.add(new TokenSpec("^=", TypeEnum.SIMPLE_ASSIGN));
		tokenSpecs.add(new TokenSpec("^[*\\/\\+\\-]=", TypeEnum.COMPLEX_ASSIGN));

		// Math Operators (+,-,*)
		tokenSpecs.add(new TokenSpec("^[+\\-]", TypeEnum.ADDITIVE_OPERATOR));
		tokenSpecs.add(new TokenSpec("^[*\\/]", TypeEnum.MULTIPLICATIVE_OPERATOR));

		// Relational operators
		tokenSpecs.add(new TokenSpec("^[><]=?", TypeEnum.RELATIONAL_OPERATOR));

		//Logical operators
		tokenSpecs.add(new TokenSpec("^&&", TypeEnum.LOGICAL_AND));
		tokenSpecs.add(new TokenSpec("^\\|\\|", TypeEnum.LOGICAL_OR));
		tokenSpecs.add(new TokenSpec("^!", TypeEnum.LOGICAL_NOT));

		// String
		tokenSpecs.add(new TokenSpec("\"[^\"]*\"", TypeEnum.STRING));
		tokenSpecs.add(new TokenSpec("^'[^']*'", TypeEnum.STRING));

		return tokenSpecs;
	}
	

	public void init(String string) {
		this.cursor = 0;
		this._string = string;
	}

	private boolean hasMoreTokens() {
		return this.cursor < this._string.length();
	}

	private String _match(String regex, String string) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);

		if (matcher.find()) {
			String matched = matcher.group(0);
			cursor += matched.length();
			return matched;
		} else {
			return null;
		}
	}
	
	public Token getNextToken() throws SyntaxError {
		if (!this.hasMoreTokens()) {			
			return null;
		}
		
		String string = this._string.substring(this.cursor);
		
		for (TokenSpec tokenSpec : getTokenSpec()) {
			String regex = tokenSpec.pattern.pattern();
			TypeEnum tokenType = tokenSpec.type;
			
			String tokenValue = this._match(regex, string);
			
			if(tokenValue == null) {
				continue;
			}
			
			if (tokenType == null) {
                return this.getNextToken();
            }
			
			return new Token(tokenType,tokenValue);
		}

		return null;

	}
}
