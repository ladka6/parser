package tokenizer;

public abstract class Statement {
    private String type;


    public Statement(String type) {
        this.type = type;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
