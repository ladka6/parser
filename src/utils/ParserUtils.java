package utils;

import errors.SyntaxError;
import tokenizer.Identifier;

public class ParserUtils{

    public Boolean isAssignmentOperator(String tokenType) {
        return tokenType == "SIMPLE_ASSIGN" || tokenType == "COMPLEX_ASSIGN";
    }

    public Object _checkValidAssigmentTarget(Object node) throws SyntaxError {
        if (node instanceof Identifier) {
            Identifier typedNode = (Identifier) node;
            if ("Identifier".equals(typedNode.getType())) {
                return typedNode;
            }
        }
        throw new SyntaxError("Invalid left-hand side in assignment expression");
    }

    public Boolean _isLiteral(String tokenType) {
        return tokenType == "NUMBER" || tokenType == "STRING";
    }
}
