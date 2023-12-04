package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static token.TokenType.*;

import org.junit.jupiter.api.Test;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

import java.io.FileNotFoundException;
import java.io.IOException;

class TestToken {
	void test(Token t, int riga, TokenType type, String val) {
		assertEquals("<" + type + ",r:" +riga+ ","+ val + ">", t.toString());
		assertEquals(riga, t.getRiga());
		assertEquals(type, t.getTipo());
		assertEquals(val, t.getVal());
	}
	void test(Token t, int riga, TokenType type) {
		assertEquals("<" + type + ",r:" +riga+ ">", t.toString());
		assertEquals(riga, t.getRiga());
		assertEquals(type, t.getTipo());
		assertNull(t.getVal());
	}

	void testNext(Scanner s, int riga, TokenType type, String val) throws LexicalException, IOException {
		test(s.nextToken(), riga, type, val);
	}
	void testNext(Scanner s, int riga, TokenType type) throws LexicalException, IOException {
		test(s.nextToken(), riga, type);
	}

	void testException(Scanner s, String val, int riga, char errore) {
		LexicalException ex = assertThrows(LexicalException.class, s::nextToken);
		assertEquals(val + ",r:" + riga + ",c:" + errore, ex.getMessage());
		assertEquals(val, ex.getValue());
		assertEquals(riga, ex.getRiga());
		assertEquals(errore, ex.getErrore());
	}

	@Test
	void testToken() {
		Token t1 = new Token(INT, 1, "3");
		test(t1, 1, INT, "3");

		Token t2 = new Token(PRINT, 3);
		test(t2, 3, PRINT);
	}

	@Test
	void testFileEOF() throws IOException, LexicalException {
		Scanner s = new Scanner("test/data/testScanner/testEOF.txt");
		testNext(s, 5, EOF);
	}

	@Test
	void testFileKeyWords() throws IOException, LexicalException {
		Scanner s = new Scanner("test/data/testScanner/testIdKeyWords.txt");
		Token t = s.nextToken();
		test(t, 1, TYPE_INT);

		t = s.nextToken();
		test(t, 1, ID, "inta");

		t = s.nextToken();
		test(t, 2, TYPE_FLOAT);

		t = s.nextToken();
		test(t, 3, PRINT);

		t = s.nextToken();
		test(t, 4, ID, "nome");

		t = s.nextToken();
		test(t, 5, ID, "intnome");

		t = s.nextToken();
		test(t, 6, TYPE_INT);

		t = s.nextToken();
		test(t, 6, ID, "nome");

		t = s.nextToken();
		test(t, 6, EOF);
	}

	@Test
	void testErroriID() throws IOException, LexicalException {
		Scanner s = new Scanner("test/data/testScanner/erroriID");
		s.nextToken();
		s.nextToken();

		testException(s, "nome1", 3, '1');

		s.nextToken();

		testException(s, "v1r", 5, '1');
	}

	@Test
	void testOperatori() throws IOException, LexicalException {
		Scanner s = new Scanner("test/data/testScanner/testOperators.txt");

		testNext(s, 1, PLUS);

		testNext(s, 1, OP_ASS, "/=");

		testNext(s, 2, MINUS);

		testNext(s, 2, MULTIP);

		testNext(s, 3, DIVISION);

		testNext(s, 5, OP_ASS, "+=");

		testNext(s, 6, OP_ASS, "=");

		testNext(s, 6, OP_ASS, "-=");

		testNext(s, 8, MINUS);

		testNext(s, 8, OP_ASS, "=");

		testNext(s, 8, OP_ASS, "*=");

		testNext(s, 10, SEMI);

		testNext(s, 11, SEMI);

		testNext(s, 11, OP_ASS, "=");

		testNext(s, 11, EOF);
	}


	@Test
	void testFLOAT() throws IOException, LexicalException {
		Scanner s = new Scanner("test/data/testScanner/testFLOAT.txt");

		testNext(s, 2, FLOAT, "098.8095");
		testNext(s, 3, FLOAT, "0.");
		testNext(s, 3, FLOAT, "00.");
		testNext(s, 4, FLOAT, "98.");
		testNext(s, 6, FLOAT, "89.99999");
	}

	@Test
	void testInt() throws IOException, LexicalException {
		Scanner s = new Scanner("test/data/testScanner/testINT.txt");

		testNext(s, 2, INT, "698");

		testNext(s, 4, INT, "560099");

		testNext(s, 5, INT, "1234");

		testNext(s, 7, INT, "0");
	}

	@Test
	void testIntErrori() throws FileNotFoundException {
		Scanner s = new Scanner("test/data/testScanner/erroriINT.txt");

		testException(s, "123r", 3, 'r');

		testException(s, "12s3", 4, 's');

		testException(s, "7y", 5, 'y');

		testException(s, "00", 7, '_');

		testException(s, "00p", 7, 'p');

		testException(s, "00p0", 8, 'p');

		testException(s, "070", 9, '_');

		testException(s, "07p", 10, 'p');

		testException(s, "000", 11, '_');
	}

	@Test
	void testErroriNumbers() throws FileNotFoundException {
		Scanner s = new Scanner("test/data/testScanner/erroriNumbers.txt");

		testException(s, "00", 1, '_');

		testException(s, "123a", 2, 'a');

		testException(s, "12.a", 3, 'a');

		testException(s, "123.121212", 4, '2');

		testException(s, "11..", 5, '.');

		testException(s, "22.2.", 6, '.');
	}

	@Test
	void testGenerale() throws IOException, LexicalException {
		Scanner s = new Scanner("test/data/testScanner/testGenerale.txt");

		/*
		int temp;
			temp += 5.;

		float b;
			b = temp + 3.2;
			print b;

		 */


		testNext(s, 1, TYPE_INT);
		testNext(s, 1, ID, "temp");
		testNext(s, 1, SEMI);

		testNext(s, 2, ID, "temp");
		testNext(s, 2, OP_ASS, "+=");
		testNext(s, 2, FLOAT, "5.");
		testNext(s, 2, SEMI);

		testNext(s, 4, TYPE_FLOAT);
		testNext(s, 4, ID, "b");
		testNext(s, 4, SEMI);

		testNext(s, 5, ID, "b");
		testNext(s, 5, OP_ASS, "=");
		testNext(s, 5, ID, "temp");
		testNext(s, 5, PLUS);
		testNext(s, 5, FLOAT, "3.2");
		testNext(s, 5, SEMI);

		testNext(s, 6, PRINT);
		testNext(s, 6, ID, "b");
		testNext(s, 6, SEMI);

		testNext(s, 7, EOF);

	}

	@Test
	void testAlfabeto() throws IOException, LexicalException {
		Scanner s = new Scanner("test/data/testScanner/testAlphabet.txt");

		testException(s, "74(", 1, '(');

		testException(s, "?", 3, '?');

		testException(s, "%", 3, '%');

		testNext(s, 3, OP_ASS, "=");

		testException(s, "var[", 4, '[');

		testException(s, "12._", 6, '_');

		testException(s, "12.44#", 7, '#');

		testException(s, "0.|", 8, '|');
		testException(s, "6&..", 8, '&');

		testNext(s, 10, PLUS);
		testException(s, "^var^", 10, '^');
		testNext(s, 10, PLUS);

		testException(s, "()", 12, '(');
	}
}
