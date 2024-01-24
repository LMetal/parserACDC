package scanner;

/**
 * Rappresenta una eccezione dell'analisi lessicale
 */
public class LexicalException extends Exception {

    /**
     * Costruttore con stringa errore, riga errore e carattere che ha creato l'errore
     *
     * @param stringaErrore Stringa che descrive l'errore
     * @param riga Numero riga dell'errore
     * @param errore Carattere che ha generato l'errore
     */
    public LexicalException(String stringaErrore, int riga, char errore){
        super("Unexpected character in Token: "+stringaErrore + ", at row: " + riga + ", unexpected character: " + errore);

    }

    /**
     * Costruttore con stringa errore, riga errore
     *
     * @param stringaErrore Stringa che descrive l'errore
     * @param riga Numero riga dell'errore
     */
    public LexicalException(String stringaErrore, int riga){
        super("Unexpected character in Token: "+stringaErrore + ", at row: " + riga);

    }

    /**
     * Costruttore con stringa errore, riga errore e carattere che ha creato l'errore
     *
     * @param stringaErrore Stringa che descrive l'errore
     * @param cause Eccezione causa errore
     */
    public LexicalException(String stringaErrore, Throwable cause) {
        super(stringaErrore, cause);
    }


}
