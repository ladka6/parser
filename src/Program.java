
public class Program {
	private String type;
	private Object body;
	
	public Program(String type, Object body) {
		this.type = type;
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

