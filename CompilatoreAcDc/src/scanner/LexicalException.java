package scanner;

public class LexicalException extends Exception {
	private final int riga;
    private final String stringa;
    private final char errore;

	// Costruttori
    public LexicalException(String stringaErrore, int riga, char errore){
        super(stringaErrore + ",r:" + riga + "c:" + errore);
        this.riga = riga;
        this.stringa = stringaErrore;
        this.errore = errore;
    }

    public String getValue(){
        return this.stringa;
    }

    public int getRiga(){
        return this.riga;
    }

    public char getErrore(){
        return this.errore;
    }

}
