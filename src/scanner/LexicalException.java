package scanner;

public class LexicalException extends Exception {
	private final int riga;
    private final String stringa;
    //private final char errore;
	// Costruttori
    public LexicalException(String stringaErrore, int riga){
        super(stringaErrore + ",r:" + riga);
        this.riga = riga;
        this.stringa = stringaErrore;
    }

    public String getValue(){
        return this.stringa;
    }

    public int getRiga(){
        return this.riga;
    }

}
