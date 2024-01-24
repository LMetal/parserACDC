package typeDescriptor;

/**
 * Rappresenta un oggetto utilizzato per memorizzare informazioni sul tipo durante l'analisi semantica.
 */
public class TypeDescriptor {
    /**
     * Il tipo associato
     */
    private final TypeTD type;

    /**
     * Eventuale messaggio di errore associato
     */
    private String errore = null;

    /**
     * Numero di riga a cui ci si riferisce
     */
    private int row;

    /**
     * Costruisce un nuovo descrittore di tipo con il tipo fornito
     *
     * @param t Il tipo associato al descrittore.
     */
    public TypeDescriptor(TypeTD t) {
        this.type = t;
    }

    /**
     * Costruisce un nuovo descrittore di tipo con il tipo e il messaggio di errore
     *
     * @param t Il tipo associato al descrittore
     * @param errore Il messaggio di errore associato al descrittore
     */
    public TypeDescriptor(TypeTD t, String errore) {
        this.type = t;
        this.errore = errore;
    }

    /**
     * Imposta il numero di riga
     *
     * @param row Il numero della riga
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Ritorna il tipo
     *
     * @return Il tipo
     */
    public TypeTD getTypeTD() {
        return type;
    }

    /**
     * Ritorna il messaggio di errore associato incluso il numero di riga.
     *
     * @return Una stringa nel formato "r:row errore" se presente, altrimenti una stringa vuota.
     */
    public String getMessaggioErrore() {
        return (errore != null) ? "r:" + row + " " + errore : "";
    }
}
