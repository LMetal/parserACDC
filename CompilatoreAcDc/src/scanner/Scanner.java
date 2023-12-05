package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.css.CSSFontFaceRule;
import token.*;

public class Scanner {
	final char EOF = (char) -1; 
	private int riga;
	private final PushbackReader buffer;
	private String log;

	// skpChars: insieme caratteri di skip (include EOF) e inizializzazione
	// letters: insieme lettere e inizializzazione
	// digits: cifre e inizializzazione
	ArrayList<Character> skpChars = new ArrayList<>();
	ArrayList<Character> letters = new ArrayList<>();
	List<Character> alphabet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');


	ArrayList<Character> digits = new ArrayList<>();
	List<Character> numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

	// char_type_Map: mapping fra caratteri '+', '-', '*', '/', ';', '=', ';' e il
	// TokenType corrispondente
	HashMap<Character, TokenType> charTypeMap = new HashMap<>();

	// keyWordsMap: mapping fra le stringhe "print", "float", "int" e il
	// TokenType  corrispondente
	HashMap<String, TokenType> keyWordsMap = new HashMap<>();

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

	public Token nextToken() throws IOException, LexicalException {

		// nextChar contiene il prossimo carattere dell'input (non consumato).
		char nextChar = peekChar(); //Catturate l'eccezione IOException e 
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
		consumeAllAndException(new StringBuilder());

		return null;
	}

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
				if(!skpChars.contains(nextChar)){
					bufferNumber.append(readChar());
					throw new LexicalException(bufferNumber.toString(), riga, nextChar);
				}

				throw new LexicalException(bufferNumber.toString(), riga);
			}
			return scanFloat(bufferNumber);

		} else {
			while (digits.contains(nextChar)){
				nextChar = consumeAdd(bufferNumber);
			}

			if(notInAlphabet(nextChar) || letters.contains(nextChar)){
				consumeAllAndException(bufferNumber);
			}

			if(nextChar != '.'){
				return new Token(TokenType.INT, riga, bufferNumber.toString());
			}
			return scanFloat(bufferNumber);
		}
	}

	private Token scanFloat(StringBuilder bufferNumber) throws IOException, LexicalException {
		char nextChar = consumeAdd(bufferNumber);
		int numDecimali = 0;

		while (digits.contains(nextChar)) {
			if(numDecimali >= 5) consumeAllAndException(bufferNumber);

			nextChar = consumeAdd(bufferNumber);
			numDecimali++;
		}
		if(notInAlphabet(nextChar) || letters.contains(nextChar) || nextChar == '.') consumeAllAndException(bufferNumber);

		return new Token(TokenType.FLOAT, riga, bufferNumber.toString());
	}


	private Token scanId() throws IOException, LexicalException {
		StringBuilder bufferLetters = new StringBuilder();
		char nextChar = peekChar();

		while(letters.contains(nextChar)){
			nextChar = consumeAdd(bufferLetters);
		}

		if(digits.contains(nextChar)){
			bufferLetters.append(readChar());
			throw new LexicalException(bufferLetters.toString(),riga,nextChar);
		}

		if(keyWordsMap.containsKey(bufferLetters.toString())) return new Token(keyWordsMap.get(bufferLetters.toString()), riga);
		else return new Token(TokenType.ID, riga, bufferLetters.toString());
	}

	private Token scanOp() throws IOException {
		char nextChar = peekChar();
		char saveOp;

		if(nextChar == '=')	{
			readChar();
			return new Token(TokenType.OP_ASS, riga, "=");
		}
		if(nextChar == ';'){
			readChar();
			return new Token(TokenType.SEMI, riga);
		}

		saveOp = readChar();
		if(peekChar() == '='){
			return new Token(TokenType.OP_ASS, riga, saveOp + String.valueOf(readChar()));
		}

		return new Token(charTypeMap.get(saveOp), riga);
	}

	private void consumeAllAndException(StringBuilder buffer) throws IOException, LexicalException {
		char nextChar = peekChar();
		char errore;

		if(!(skpChars.contains(nextChar) || charTypeMap.containsKey(nextChar))) errore = nextChar;
		else errore = '_';

		while (!(charTypeMap.containsKey(nextChar) || skpChars.contains(nextChar))){
			nextChar = consumeAdd(buffer);
		}
		throw new LexicalException(buffer.toString(), riga, errore);
	}

	private char consumeAdd(StringBuilder s) throws IOException {
		s.append(readChar());
		return peekChar();
	}

	private boolean notInAlphabet(char c){
		return !letters.contains(c) && !numbers.contains(c) && !charTypeMap.containsKey(c) && !skpChars.contains(c) && c != '.';
	}

	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}
