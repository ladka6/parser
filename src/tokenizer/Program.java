package tokenizer;
import java.util.List;

public class Program {
	private String type;
	private List<Statement> body;
	
	public Program(String type, List<Statement> body) {
		this.type = type;
		this.body = body;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Statement> getBody() {
		return this.body;
	}

	public void setBody(List<Statement> body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
        return "{" +
                " type = '" + type + '\'' +
                ", body = " + body +
                " }";
    }
}

