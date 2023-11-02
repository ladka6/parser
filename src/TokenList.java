import java.util.List;
import types.Token;


public class TokenList {
    private String type;
    private List<Token> tokenList;


    public TokenList(String type, List<Token> tokenList) {
        this.type = type;
        this.tokenList = tokenList;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Token> getTokenList() {
        return this.tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }


}
