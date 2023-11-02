package types;

public enum TypeEnum {
    LEFT_BRACE("{"),
    RIGHT_BRACE("}"),
    LEFT_PARANTHESIS("("),
    RIGTH_PARANTHESIS(")"),
    SEMICOLON(";"),
    NULL("null"),
    NUMBER("NUMBER"),
    IDENTIFIER("IDENTIFIER"),
    SIMPLE_ASSIGN("SIMPLE_ASSIGN"),
    COMPLEX_ASSIGN("COMPLEX_ASSIGN"),
    ADDITIVE_OPERATOR("ADDITIVE_OPERATOR"),
    MULTIPLICATIVE_OPERATOR("MULTIPLICATIVE_OPERATOR"),
    STRING("STRING"),
    EMPTYSTATEMENT("EMPTYSTATEMENT"),
    BINARY_EXPRESSION("BINARY_EXPEESSION"),
    ASSIGNMENT_EXPRESSION("ASSIGNMENT_EXPRESSION"),
    LET("LET"),
    IF("IF"),
    ELSE("ELSE"),
    COMMA("COMMA"),
    VARIABLE_DECLARATION("VARIABLE_DECLARATION"),
    VARIABLE_STATEMENT("VARIABLE_STATEMENT"),
    BLOCK_STATEMENT("BLOCK_STATEMENT"),
    EXPRESSION_STATEMENT("EXPRESSION_STATEMENT"),
    IF_STATEMENT("IF_STATEMENT"),
    RELATIONAL_OPERATOR("RELATIONAL_OPERATOR"),
    PROGRAM("PROGRAM");


    private final String representation;

    TypeEnum(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}
