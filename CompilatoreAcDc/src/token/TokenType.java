package token;

/**
 * Enumerator con i possibili tipi dei token
 */
public enum TokenType {
	/**
	 * Numero intero
	 */
	INT,
	/**
	 * Numero floating point
	 */
	FLOAT,
	/**
	 * Id
	 */
	ID,
	/**
	 * Istruzione print
	 */
	PRINT,
	/**
	 * Tipo floating point
	 */
	TYPE_FLOAT,
	/**
	 * Tipo intero
	 */
	TYPE_INT,
	/**
	 * Operazione +
	 */
	PLUS,
	/**
	 * Operazione -
	 */
	MINUS,
	/**
	 * Operazione *
	 */
	MULTIP,
	/**
	 * Operazione /
	 */
	DIVISION,
	/**
	 * Operazione assegnamento =
	 */
	OP_ASS,
	/**
	 * Fine riga ;
	 */
	SEMI,
	/**
	 * Fine file
	 */
	EOF
}
