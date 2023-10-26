package tokenizer;
import java.util.List;

public class BlockStatement extends Statement{

    private List<Statement> body;

    public BlockStatement(String type,List<Statement> body) {
        super(type);
        this.body = body;
    }

    public List<Statement> getBody() {
        return this.body;
    }

    public void setBody(List<Statement> body) {
        this.body = body;
    }

}
