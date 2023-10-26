package tokenizer;

import java.util.List;

public class StatementList {
    private List<Statement> statementList;


    public StatementList(List<Statement> statementList) {
        this.statementList = statementList;
    }


    public List<Statement> getStatementList() {
        return this.statementList;
    }

    public void setStatementList(List<Statement> statementList) {
        this.statementList = statementList;
    }

}
