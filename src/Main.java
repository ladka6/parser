import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.HtmlEnums;

public class Main {
	public static void main(String[] args) throws Exception {
		Parser parser = new Parser();
//		
		String program = "<div>Efsa</div> <div>Ege</div>";
//		
		System.out.println(parser.parse(program));
		
		
		
	}
}
