import java.util.regex.Pattern;

public class TokenSpec {
	public final Pattern pattern;
	public final String type;

	public TokenSpec(String regex, String type) {
		this.pattern = Pattern.compile(regex);
		this.type = type;
	}

}
