package token;

public class Token {

	private final int riga;
	private final TokenType tipo;
	private final String val;
	
	public Token(TokenType tipo, int riga, String val) {
		this.tipo = tipo;
		this.riga = riga;
		this.val  = val;
	}
	
	public Token(TokenType tipo, int riga) {
		this(tipo, riga, null);
	}

	public int getRiga() {
		return riga;
	}

	public String getVal() {
		return val;
	}

	public TokenType getTipo() {
		return tipo;
	}

	public String toString() {
		if(val != null) return "<" + getTipo() + ",r:" + getRiga() + "," + getVal()  + ">";
		else return "<" + getTipo() + ",r:" + getRiga() + ">";
	}
}
