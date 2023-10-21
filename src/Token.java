import java.util.ArrayList;
import java.util.List;

import enums.HtmlEnums;


public class Token {

	private String tag;
	private HtmlEnums type;
	private String content;
	private List<Token> children;
	
	public Token(HtmlEnums type,String tag,String content) {
		this.type = type;
		this.tag = tag;
		this.content = content;
		this.children = new ArrayList<>();
	}
	
	
	public HtmlEnums getType() {
		return (HtmlEnums) this.type;
	}
	
	public void setType(HtmlEnums type) {
		this.type = type;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Token> getChildren() {
		return children;
	}

	public void setChildren(List<Token> children) {
		this.children = children;
	}
	
	@Override
	public String toString() {
        return "Token{" +
                "type=" + type +
                ", tag=" + tag +
                ", content=" + content +
                ", children=" + children +
                '}';
    }
}