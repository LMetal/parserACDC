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
		assertEquals("<INT,r:1,3>", t1.toString());

		Token t2 = new Token(PRINT, 3);
		assertEquals("<PRINT,r:3>", t2.toString());
	}

	@Test
	void testFileEOF() throws IOException, LexicalException {
		Scanner s = new Scanner("CompilatoreAcDc/src/test/data/testScanner/testEOF.txt");
		assertEquals("<EOF,r:5>", s.nextToken().toString());
	}

	@Test
	void testFileKeyWords() throws IOException, LexicalException {
		Scanner s = new Scanner("CompilatoreAcDc/src/test/data/testScanner/testIdKeyWords.txt");

		assertEquals("<TYPE_INT,r:1>", s.nextToken().toString());

		assertEquals("<ID,r:1,inta>", s.nextToken().toString());

		assertEquals("<TYPE_FLOAT,r:2>", s.nextToken().toString());

		assertEquals("<PRINT,r:3>", s.nextToken().toString());

		assertEquals("<ID,r:4,nome>", s.nextToken().toString());

		assertEquals("<ID,r:5,intnome>", s.nextToken().toString());

		assertEquals("<TYPE_INT,r:6>", s.nextToken().toString());

		assertEquals("<ID,r:6,nome>", s.nextToken().toString());

		assertEquals("<EOF,r:6>", s.nextToken().toString());
	}

	@Test
	void testErroriID() throws IOException, LexicalException {
		Scanner s = new Scanner("CompilatoreAcDc/src/test/data/testScanner/erroriID");
		s.nextToken();
		s.nextToken();

		LexicalException ex = assertThrows(LexicalException.class, s::nextToken);
		assertEquals("nome1,r:3,c:1", ex.getMessage());

		s.nextToken();

		ex = assertThrows(LexicalException.class, s::nextToken);
		assertEquals("v1,r:5,c:1", ex.getMessage());

		assertEquals("<ID,r:5,r>",s.nextToken().toString());
	}

	@Test
	void testOperatori() throws IOException, LexicalException {
		Scanner s = new Scanner("CompilatoreAcDc/src/test/data/testScanner/testOperators.txt");

		assertEquals("<PLUS,r:1>", s.nextToken().toString());

		assertEquals("<OP_ASS,r:1,/=>", s.nextToken().toString());

		assertEquals("<MINUS,r:2>", s.nextToken().toString());

		assertEquals("<MULTIP,r:2>", s.nextToken().toString());

		assertEquals("<DIVISION,r:3>", s.nextToken().toString());

		assertEquals("<OP_ASS,r:5,+=>", s.nextToken().toString());

		assertEquals("<OP_ASS,r:6,=>", s.nextToken().toString());

		assertEquals("<OP_ASS,r:6,-=>", s.nextToken().toString());

		assertEquals("<MINUS,r:8>", s.nextToken().toString());

		assertEquals("<OP_ASS,r:8,=>", s.nextToken().toString());

		assertEquals("<OP_ASS,r:8,*=>", s.nextToken().toString());

		assertEquals("<SEMI,r:10>", s.nextToken().toString());

		assertEquals("<SEMI,r:11>", s.nextToken().toString());

		assertEquals("<OP_ASS,r:11,=>", s.nextToken().toString());

		assertEquals("<EOF,r:11>", s.nextToken().toString());
	}


	@Test
	void testFLOAT() throws IOException, LexicalException {
		Scanner s = new Scanner("CompilatoreAcDc/src/test/data/testScanner/testFLOAT.txt");

		assertEquals("<FLOAT,r:2,098.8095>", s.nextToken().toString());
		assertEquals("<FLOAT,r:3,0.>", s.nextToken().toString());
		assertEquals("<FLOAT,r:3,00.>", s.nextToken().toString());
		assertEquals("<FLOAT,r:4,98.>", s.nextToken().toString());
		assertEquals("<FLOAT,r:6,89.99999>", s.nextToken().toString());
	}

	@Test
	void testInt() throws IOException, LexicalException {
		Scanner s = new Scanner("CompilatoreAcDc/src/test/data/testScanner/testINT.txt");

		assertEquals("<INT,r:2,698>", s.nextToken().toString());
		assertEquals("<INT,r:4,560099>", s.nextToken().toString());
		assertEquals("<INT,r:5,1234>", s.nextToken().toString());
		assertEquals("<INT,r:7,0>", s.nextToken().toString());
	}

	@Test
	void testIntErrori() throws FileNotFoundException {
		Scanner s = new Scanner("CompilatoreAcDc/src/test/data/testScanner/erroriINT.txt");

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
		Scanner s = new Scanner("CompilatoreAcDc/src/test/data/testScanner/erroriNumbers.txt");

		LexicalException ex = assertThrows(LexicalException.class, s::nextToken);
		assertEquals("00,r:1", ex.getMessage());

		ex = assertThrows(LexicalException.class, s::nextToken);
		assertEquals("123a,r:2,c:a", ex.getMessage());

		ex = assertThrows(LexicalException.class, s::nextToken);
		assertEquals("12.a,r:3,c:a", ex.getMessage());

		ex = assertThrows(LexicalException.class, s::nextToken);
		assertEquals("123.121212,r:4,c:2", ex.getMessage());

		ex = assertThrows(LexicalException.class, s::nextToken);
		assertEquals("11..,r:5,c:.", ex.getMessage());

		ex = assertThrows(LexicalException.class, s::nextToken);
		assertEquals("22.2.,r:6,c:.", ex.getMessage());
	}

	@Test
	void testGenerale() throws IOException, LexicalException {
		Scanner s = new Scanner("CompilatoreAcDc/src/test/data/testScanner/testGenerale.txt");

		/*
		int temp;
			temp += 5.;

		float b;
			b = temp + 3.2;
			print b;

		 */


		assertEquals("<TYPE_INT,r:1>", s.nextToken().toString());
		assertEquals("<ID,r:1,temp>", s.nextToken().toString());
		assertEquals("<SEMI,r:1>", s.nextToken().toString());

		assertEquals("<ID,r:2,temp>", s.nextToken().toString());
		assertEquals("<OP_ASS,r:2,+=>", s.nextToken().toString());
		assertEquals("<FLOAT,r:2,5.>", s.nextToken().toString());
		assertEquals("<SEMI,r:2>", s.nextToken().toString());

		assertEquals("<TYPE_FLOAT,r:4>", s.nextToken().toString());
		assertEquals("<ID,r:4,b>", s.nextToken().toString());
		assertEquals("<SEMI,r:4>", s.nextToken().toString());

		assertEquals("<ID,r:5,b>", s.nextToken().toString());
		assertEquals("<OP_ASS,r:5,=>", s.nextToken().toString());
		assertEquals("<ID,r:5,temp>", s.nextToken().toString());
		assertEquals("<PLUS,r:5>", s.nextToken().toString());
		assertEquals("<FLOAT,r:5,3.2>", s.nextToken().toString());
		assertEquals("<SEMI,r:5>", s.nextToken().toString());

		assertEquals("<PRINT,r:6>", s.nextToken().toString());
		assertEquals("<ID,r:6,b>", s.nextToken().toString());
		assertEquals("<SEMI,r:6>", s.nextToken().toString());

		assertEquals("<EOF,r:7>", s.nextToken().toString());

	}

	//@Test
	void testAlfabeto() throws IOException, LexicalException {
		Scanner s = new Scanner("CompilatoreAcDc/src/test/data/testScanner/testAlphabet.txt");

		testException(s, "74(", 1, '(');

		testException(s, "?", 3, '?');

		testException(s, "%", 3, '%');

		assertEquals("<OP_ASS,r:3,=>", s.nextToken().toString());

		testException(s, "var[", 4, '[');

		testException(s, "12._", 6, '_');

		testException(s, "12.44#", 7, '#');

		testException(s, "0.|", 8, '|');
		testException(s, "6&..", 8, '&');

		assertEquals("<PLUS,r:10>", s.nextToken().toString());
		testException(s, "^var^", 10, '^');
		assertEquals("<PLUS,r:10>", s.nextToken().toString());

		testException(s, "()", 12, '(');
	}
}
