import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Errors.SyntaxError;

public class Tokenizer {
	private String string;
	private int cursor;
	
	public void init(String string) {
		// TODO Auto-generated constructor stub
		this.cursor = 0;
		this.string = string;
	}
	
	private boolean isEOF() {
		return this.cursor == this.string.length();
	}
	
	private boolean hasMoreTokens() {
		return this.cursor < this.string.length();
	}
	
	private String _match(String regex,String string) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		
		if(matcher.find()) {
			String matched = matcher.group(0);
	        cursor += matched.length();
	        return matched;
		}else {
			return null;
		}
	}
	
	public Token getNextToken() throws SyntaxError {
		if(!this.hasMoreTokens()) return null;
		
		String string = this.string.substring(this.cursor);
		
		Pattern pattern = Pattern.compile("^\\d+");
		Matcher matcher = pattern.matcher(string);
		
		if(matcher.find()) {
			String matched = matcher.group(0);
	        cursor += matched.length();
	        return new Token("NUMBER",Integer.parseInt(matched));
		}else {
			throw new SyntaxError("");
		}
		
	}
}
