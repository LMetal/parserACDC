package token;

/**
 * Rappresenta un token all'interno dell'analisi lessicale
 */
public class Token {

	/**
	 * Numero di riga in cui il token è stato trovato
	 */
	private final int riga;

	/**
	 * Tipo del token.
	 */
	private final TokenType tipo;

	/**
	 * Valore associato al token, se presente
	 */
	private final String val;

	/**
	 * Costruisce un nuovo oggetto Token con il tipo, la riga e il valore fornito
	 *
	 * @param tipo Il tipo del token
	 * @param riga Il numero di riga in cui il token è stato trovato
	 * @param val  Il valore associato al token (può essere null se il token non ha un valore)
	 */
	public Token(TokenType tipo, int riga, String val) {
		this.tipo = tipo;
		this.riga = riga;
		this.val = val;
	}

	/**
	 * Costruisce un nuovo oggetto Token con il tipo e la riga forniti
	 *
	 * @param tipo Il tipo del token.
	 * @param riga Il numero di riga in cui il token è stato trovato
	 */
	public Token(TokenType tipo, int riga) {
		this(tipo, riga, null);
	}

	/**
	 * Ritorna il numero di riga in cui il token è stato trovato
	 *
	 * @return Il numero di riga del token.
	 */
	public int getRiga() {
		return riga;
	}

	/**
	 * Ritorna il valore associato al token.
	 *
	 * @return Il valore del token (può essere null se il token non ha un valore).
	 */
	public String getVal() {
		return val;
	}

	/**
	 * Ritorna il tipo del token.
	 *
	 * @return Il tipo del token.
	 */
	public TokenType getTipo() {
		return tipo;
	}

	/**
	 * Ritorna una rappresentazione in stringa del token.
	 *
	 * @return Una stringa nel formato "&lt;tipo,r:riga,val&gt;" o "&lt;tipo,r:riga&gt;" se il token non ha un valore.
	 */
	public String toString() {
		if (val != null) return "<" + getTipo() + ",r:" + getRiga() + "," + getVal() + ">";
		else return "<" + getTipo() + ",r:" + getRiga() + ">";
	}
}
