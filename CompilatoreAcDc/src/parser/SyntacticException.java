package parser;

import token.TokenType;

public class SyntacticException extends Throwable {

    public SyntacticException(TokenType expectedType, int riga, TokenType foundType) {
        super("Expected type: {" + expectedType + "} at row " + riga + " but got "+ foundType);
    }

    public SyntacticException(String expectedType, int riga, TokenType foundType) {
        super("Expected type: {" + expectedType + "} at row " + riga + " but got "+ foundType);
    }

    public SyntacticException(String stringaErrore, Throwable cause) {
        super(stringaErrore, cause);
    }
}
