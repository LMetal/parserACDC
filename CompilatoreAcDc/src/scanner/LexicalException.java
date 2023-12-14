package scanner;

public class LexicalException extends Exception {


	// Costruttori
    public LexicalException(String stringaErrore, int riga, char errore){
        super("Unexpected character in Token: "+stringaErrore + ", at row: " + riga + ", unexpected character: " + errore);

    }
    public LexicalException(String stringaErrore, int riga){
        super("Unexpected character in Token: "+stringaErrore + ", at row: " + riga);

    }

    public LexicalException(String stringaErrore, Throwable cause) {
        super(stringaErrore, cause);
    }


}
