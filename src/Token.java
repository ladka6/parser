
public class Token {
	private String type;
	private int value;
	
	public Token(String type,int value) {
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