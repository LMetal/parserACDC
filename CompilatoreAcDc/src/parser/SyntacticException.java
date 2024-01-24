package parser;

import token.TokenType;

/**
 * Rappresenta una eccezione dell'analisi sintattica
 */
public class SyntacticException extends Throwable {

    /**
     * Costruisce un'eccezione con tipo atteso, riga dell'errore, tipo trovato
     *
     * @param expectedType Tipo atteso
     * @param riga Numero riga dell'errore
     * @param foundType Tipo trovato
     */
    public SyntacticException(TokenType expectedType, int riga, TokenType foundType) {
        super("Expected type: {" + expectedType + "} at row " + riga + " but got "+ foundType);
    }

    /**
     * Costruisce un'eccezione con tipi attesi, riga dell'errore, tipo trovato
     *
     * @param expectedType Tipi attesi
     * @param riga Numero riga dell'errore
     * @param foundType Tipo trovato
     */
    public SyntacticException(String expectedType, int riga, TokenType foundType) {
        super("Expected type: {" + expectedType + "} at row " + riga + " but got "+ foundType);
    }


    /**
     * Costruisce un'eccezione con la stringa attesa, eccezione
     *
     * @param stringaErrore Stringa trovata
     * @param cause Eccezione
     */
    public SyntacticException(String stringaErrore, Throwable cause) {
        super(stringaErrore, cause);
    }


    /**
     * Costruisce un'eccezione con la stringa attesa, riga dell'errore, stringa trovata
     *
     * @param expectedString Stringa attesa
     * @param riga Numero riga
     * @param foundString Stringa trovata
     */
    public SyntacticException(String expectedString, int riga, String foundString) {
        super("Expected string: {" + expectedString + "} at row " + riga + " but got "+ foundString);
    }
}
