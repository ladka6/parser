package tokenizer;

public class Identifier {
    private String type;
    private String name;


    public Identifier(String type, String name) {
        this.type = type;
        this.name = name;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
