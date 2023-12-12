package scanner;

import java.io.IOException;

public class LexicalException extends Exception {


	// Costruttori
    public LexicalException(String stringaErrore, int riga, char errore){
        super(stringaErrore + ",r:" + riga + ",c:" + errore);

    }
    public LexicalException(String stringaErrore, int riga){
        super(stringaErrore + ",r:" + riga);

    }

    public LexicalException(String stringaErrore, Throwable cause) {
        super(stringaErrore, cause);

    }


}
