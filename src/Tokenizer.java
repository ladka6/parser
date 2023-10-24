import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import errors.SyntaxError;

public class Tokenizer {
	private String _string;
	private int cursor;

	public static List<TokenSpec> getTokenSpec() {
		List<TokenSpec> tokenSpecs = new ArrayList<>();

		// Whitespace
		tokenSpecs.add(new TokenSpec("^\\s+", null));

		// Skip single-line comments
		tokenSpecs.add(new TokenSpec("^//.*", null));

		// MultiLine /* */
		tokenSpecs.add(new TokenSpec("^/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/", null));

		// Symbol delimiters
		tokenSpecs.add(new TokenSpec("^;", ";"));
		tokenSpecs.add(new TokenSpec("^\\{", "{"));
		tokenSpecs.add(new TokenSpec("^\\}", "}"));
		tokenSpecs.add(new TokenSpec("^\\(", "("));
		tokenSpecs.add(new TokenSpec("^\\)", ")"));
		// Number
		tokenSpecs.add(new TokenSpec("^\\d+", "NUMBER"));

		// Identifiers
		tokenSpecs.add(new TokenSpec("^\\w+", "IDENTIFIER"));

		// Assignment Operators: *= /= += -=
		tokenSpecs.add(new TokenSpec("^=", "SIMPLE_ASSIGN"));
		tokenSpecs.add(new TokenSpec("^[*\\/\\+\\-]=", "COMPLEX_ASSIGN"));

		// Math Operators (+,-,*)
		tokenSpecs.add(new TokenSpec("^[+\\-]", "ADDITIVE_OPERATOR"));
		tokenSpecs.add(new TokenSpec("^[*\\/]", "MULTIPLICATIVE_OPERATOR"));

		// String
		tokenSpecs.add(new TokenSpec("\"[^\"]*\"", "STRING"));
		tokenSpecs.add(new TokenSpec("^'[^']*'", "STRING"));

		return tokenSpecs;
	}
	

	public void init(String string) {
		this.cursor = 0;
		this._string = string;
	}

	private boolean isEOF() {
		return this.cursor == this._string.length();
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
			String tokenType = tokenSpec.type;
			
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
