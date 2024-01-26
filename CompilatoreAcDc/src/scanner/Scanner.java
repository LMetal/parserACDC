package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import token.*;

/**
 * Rappresenta un oggetto che legge caratteri da file e ritorna i token associati
 * Analisi lessicale
 */
public class Scanner {
	/**
	 * EOF char
	 */
	final char EOF = (char) -1;

	/**
	 * Numero della stringa
	 */
	private int riga;

	/**
	 * Oggetto che permette di leggere un carattere alla volta da file, permette di annullare la lettura di un carattere
	 */
	private final PushbackReader buffer;

	/**
	 * Token, salva il token appena letto
	 */
	private Token token;

	/**
	 * Insieme caratteri di skip (include EOF) e inizializzazione
	 */
	ArrayList<Character> skpChars = new ArrayList<>();

	/**
	 * Insieme lettere
	 */
	ArrayList<Character> letters = new ArrayList<>();
	/**
	 * Inizializzazione delle lettere
	 */
	List<Character> alphabet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

	/**
	 * Insieme cifre
	 */
	ArrayList<Character> digits = new ArrayList<>();
	/**
	 * Inizializza le cifre
	 */
	List<Character> numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

	/**
	 * Mapping fra caratteri '+', '-', '*', '/', ';', '=', ';' e il
	 * TokenType corrispondente
	 */
	HashMap<Character, TokenType> charTypeMap = new HashMap<>();


	/**
	 * Mapping fra le stringhe "print", "float", "int" e il
	 * TokenType  corrispondente
	 */
	HashMap<String, TokenType> keyWordsMap = new HashMap<>();

	/**
	 * Costruisce lo scanner, inizializza le liste di caratteri
	 *
	 * @param fileName Il file da leggere
	 * @throws FileNotFoundException Errore apertura file
	 */
	public Scanner(String fileName) throws FileNotFoundException {

		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;

		skpChars.add('\n');
		skpChars.add(' ');
		skpChars.add('\t');
		skpChars.add('\r');
		skpChars.add(EOF);

		letters.addAll(alphabet);
		digits.addAll(numbers);

		keyWordsMap.put("print", TokenType.PRINT);
		keyWordsMap.put("float", TokenType.TYPE_FLOAT);
		keyWordsMap.put("int", TokenType.TYPE_INT);

		charTypeMap.put('+', TokenType.PLUS);
		charTypeMap.put('-', TokenType.MINUS);
		charTypeMap.put('*', TokenType.MULTIP);
		charTypeMap.put('/', TokenType.DIVISION);
		charTypeMap.put(';', TokenType.SEMI);
		charTypeMap.put('=', TokenType.OP_ASS);

	}

	/**
	 * Scorre il file e ritorna il Token associato alla prossima stringa del file.
	 * Se è presente un token in Token lo ritorna
	 * Consuma il token
	 *
	 * @return Il prossimo token
	 * @throws IOException Se avviene un errore durante la lettura da file
	 * @throws LexicalException Se viene trovata una stringa non appartenente al linguaggio
	 */
	public Token nextToken() throws IOException, LexicalException {
		char nextChar;

		if(token != null) {
			Token toReturn = token;
			token = null;
			return toReturn;
		}

		// nextChar contiene il prossimo carattere dell'input (non consumato).
		try{
			nextChar = peekChar(); //Catturate l'eccezione IOException e
		}catch (IOException e){
			throw new LexicalException("Errore IO alla riga: " + riga, e);
		}
		       // ritornate una LexicalException che la contiene

		// Avanza nel buffer leggendo i carattere in skipChars
		// incrementando riga se leggi '\n'.
		// Se raggiungi la fine del file ritorna il Token EOF
		while(skpChars.contains(nextChar)){
			readChar();
			if(nextChar == '\n') riga++;
			if(nextChar == EOF) return new Token(TokenType.EOF, riga);
			nextChar = peekChar();
		}



		// Se nextChar e' in letters
		// return scanId()
		// che legge tutte le lettere minuscole e ritorna un Token ID o
		// il Token associato Parola Chiave (per generare i Token per le
		// parole chiave usate l'HaskMap di corrispondenza
		if(letters.contains(nextChar)) return scanId();



		// Se nextChar e' o in operators oppure 
		// ritorna il Token associato con l'operatore o il delimitatore
		if(charTypeMap.containsKey(nextChar)) return scanOp();

		// Se nextChar e' in numbers
		// return scanNumber()
		// che legge sia un intero che un float e ritorna il Token INUM o FNUM
		// i caratteri che leggete devono essere accumulati in una stringa
		// che verra' assegnata al campo valore del Token
		if(digits.contains(nextChar)) return scanNumber();


		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
		// eccezione lessicale dicendo la riga e il carattere che la hanno
		// provocata.
		throw new LexicalException(String.valueOf(nextChar), riga, readChar());
	}

	/**
	 * Ritorna il prossimo token e lo salva per una peekToken o nextToken futura.
	 *
	 * @return Il prossimo token
	 * @throws LexicalException Se viene trovata una stringa non appartenente al linguaggio
	 */
	public Token peekToken() throws LexicalException {
		try{
			if(token == null)token = nextToken();
		} catch (IOException e){
			throw new LexicalException("Errore IO alla riga: " + riga, e);
		}
		return token;
	}

	/**
	 * Scorre il file basandosi sull'automa a stati finiti
	 *
	 * @return Token di un numero
	 * @throws IOException Se avviene errore nel file
	 * @throws LexicalException Se viene trovata una stringa non appartenente al linguaggio
	 */
	private Token scanNumber() throws IOException, LexicalException {
		StringBuilder bufferNumber = new StringBuilder();
		char nextChar = peekChar();

		if(nextChar == '0'){
			nextChar = consumeAdd(bufferNumber);

			if(notInAlphabet(nextChar) || !digits.contains(nextChar) && nextChar != '.') return new Token(TokenType.INT, riga, bufferNumber.toString());

			while(digits.contains(nextChar)){
				nextChar = consumeAdd(bufferNumber);
			}

			if(nextChar != '.'){
				if(letters.contains(nextChar)){
					bufferNumber.append(nextChar);
					throw new LexicalException(bufferNumber.toString(), riga, readChar());
				}

				throw new LexicalException(bufferNumber.toString(), riga);
			}

		} else {
			while (digits.contains(nextChar)){
				nextChar = consumeAdd(bufferNumber);
			}

			if(letters.contains(nextChar)){
				bufferNumber.append(nextChar);
				throw new LexicalException(bufferNumber.toString(), riga, readChar());
			}

			if(nextChar != '.'){
				return new Token(TokenType.INT, riga, bufferNumber.toString());
			}
		}
		return scanFloat(bufferNumber);
	}

	/**
	 * Scorre il file basandosi sull'automa a stati finiti
	 *
	 * @return Token di un numero float
	 * @throws IOException Se avviene errore nel file
	 * @throws LexicalException Se viene trovata una stringa non appartenente al linguaggio
	 */
	private Token scanFloat(StringBuilder bufferNumber) throws IOException, LexicalException {
		char nextChar = consumeAdd(bufferNumber);
		int numDecimali = 0;
		char error = '_';

		while (digits.contains(nextChar)) {
			numDecimali++;
			if(numDecimali == 6) {
				error = nextChar;
			}

			nextChar = consumeAdd(bufferNumber);
		}
		if(numDecimali > 5) throw new LexicalException(bufferNumber.toString(), riga, error);


		if(letters.contains(nextChar) || nextChar == '.') {
			bufferNumber.append(nextChar);
			throw new LexicalException(bufferNumber.toString(), riga, readChar());
		}

		return new Token(TokenType.FLOAT, riga, bufferNumber.toString());
	}

	/**
	 * Scorre il file basandosi sull'automa a stati finiti
	 * Stati 0 -> 3 -> 8
	 *
	 * @return Token di un id
	 * @throws IOException Se avviene errore nel file
	 * @throws LexicalException Se viene trovata una stringa non appartenente al linguaggio
	 */
	private Token scanId() throws IOException, LexicalException {
		StringBuilder bufferLetters = new StringBuilder();
		char nextChar = peekChar();

		while(letters.contains(nextChar)){
			//stato 3
			nextChar = consumeAdd(bufferLetters);
		}

		if(digits.contains(nextChar)){
			//stato 8
			bufferLetters.append(readChar());
			throw new LexicalException(bufferLetters.toString(),riga,nextChar);
		}

		if(keyWordsMap.containsKey(bufferLetters.toString())) return new Token(keyWordsMap.get(bufferLetters.toString()), riga);
		else return new Token(TokenType.ID, riga, bufferLetters.toString());
	}


	/**
	 * Scorre il file basandosi sull'automa a stati finiti
	 * Stati 0 -> 4 -> 10
	 * 		 0 -> 9
	 *
	 * @return Token di un operazione
	 * @throws IOException Se avviene errore nel file
	 */
	private Token scanOp() throws IOException {
		// stato 0
		char nextChar = peekChar();
		char saveOp;

		if(nextChar == '=')	{
			// stato 9
			readChar();
			return new Token(TokenType.OP_ASS, riga, "=");
		}
		if(nextChar == ';'){
			// stato 9
			readChar();
			return new Token(TokenType.SEMI, riga);
		}

		// stato 4
		saveOp = readChar();
		if(peekChar() == '='){
			// stato 10
			return new Token(TokenType.OP_ASS, riga, saveOp + String.valueOf(readChar()));
		}

		return new Token(charTypeMap.get(saveOp), riga);
	}


	/**
	 * Permette di scrivere codice piu' pulito
	 * Aggiunge il prossimo carattere alla stringa, lo consuma e ritorna il successivo
	 *
	 * @param s Builder della stringa
	 * @return Il prossimo carattere
	 * @throws IOException Se ci sono errori col file
	 */
	private char consumeAdd(StringBuilder s) throws IOException {
		s.append(readChar());
		return peekChar();
	}

	/**
	 * @param c Carattere da controllare
	 * @return True se non appartiene all'alfabeto, altrimenti False
	 */
	private boolean notInAlphabet(char c){
		return !letters.contains(c) && !numbers.contains(c) && !charTypeMap.containsKey(c) && !skpChars.contains(c) && c != '.';
	}

	/**
	 * Legge e consuma un carattere dal file, lo ritorna
	 *
	 * @return Carattere letto dal file
	 * @throws IOException Se ci sono errori col file
	 */
	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	/**
	 * Legge ma non consuma un carattere dal file, lo ritorna
	 *
	 * @return Carattere letto dal file
	 * @throws IOException Se ci sono errori col file
	 */
	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}
