import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Errors.SyntaxError;
import enums.HtmlEnums;

public class Tokenizer {
	private String _string;
	private int cursor;
	
	public void init(String string) {
		// TODO Auto-generated constructor stub
		this.cursor = 0;
		this._string = string;
	}
	
	private boolean isEOF() {
		return this.cursor == this._string.length();
	}
	
	private boolean hasMoreTokens() {
		return this.cursor < this._string.length();
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
		
		// Basic <div>Test</div> For div
		String string = this._string.substring(this.cursor);
		
		String regex = "<div.*?>(.*?)</div>";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		
		if(matcher.find()) {
			String matched = matcher.group(1);
			cursor += matcher.group(0).length();
			return new Token(HtmlEnums.ELEMENT, "div", matched);
		}
		
		return null;
		
//		String string = this.string.substring(this.cursor);
//		
//		Pattern pattern = Pattern.compile("^\\d+");
//		Matcher matcher = pattern.matcher(string);
//		
//		if(matcher.find()) {
//			String matched = matcher.group(0);
//	        cursor += matched.length();
//	        return new Token("NUMBER",Integer.parseInt(matched));
//		}else {
//			throw new SyntaxError("");
//		}
		
	}
}
