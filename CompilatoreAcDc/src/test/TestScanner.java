package test;

import org.junit.jupiter.api.Test;
import scanner.LexicalException;
import scanner.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestScanner {
    String testPath = "CompilatoreAcDc"+ File.separator +"src"+ File.separator +"test"+ File.separator +"data"+ File.separator +"testScanner"+ File.separator;

    @Test
    void testFileEOF() throws IOException, LexicalException {
        Scanner s = new Scanner(testPath +"testEOF.txt");
        assertEquals("<EOF,r:5>", s.nextToken().toString());
    }

    @Test
    void testFileKeyWords() throws IOException, LexicalException {
        Scanner s = new Scanner(testPath +"testIdKeyWords.txt");

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
        Scanner s = new Scanner(testPath +"erroriID");
        s.nextToken();
        s.nextToken();

        LexicalException ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: nome1, at row: 3, unexpected character: 1", ex.getMessage());

        s.nextToken();

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: v1, at row: 5, unexpected character: 1", ex.getMessage());

        assertEquals("<ID,r:5,r>",s.nextToken().toString());
    }

    @Test
    void testOperatori() throws IOException, LexicalException {
        Scanner s = new Scanner(testPath +"testOperators.txt");

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
        Scanner s = new Scanner(testPath +"testFLOAT.txt");

        assertEquals("<FLOAT,r:2,098.8095>", s.nextToken().toString());
        assertEquals("<FLOAT,r:3,0.>", s.nextToken().toString());
        assertEquals("<FLOAT,r:3,00.>", s.nextToken().toString());
        assertEquals("<FLOAT,r:4,98.>", s.nextToken().toString());
        assertEquals("<FLOAT,r:6,89.99999>", s.nextToken().toString());
    }

    @Test
    void testInt() throws IOException, LexicalException {
        Scanner s = new Scanner(testPath +"testINT.txt");

        assertEquals("<INT,r:2,698>", s.nextToken().toString());
        assertEquals("<INT,r:4,560099>", s.nextToken().toString());
        assertEquals("<INT,r:5,1234>", s.nextToken().toString());
        assertEquals("<INT,r:7,0>", s.nextToken().toString());
    }

    @Test
    void testIntErrori() throws IOException, LexicalException {
        Scanner s = new Scanner(testPath +"erroriINT.txt");
        LexicalException ex;

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 123r, at row: 3, unexpected character: r", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 12s, at row: 4, unexpected character: s", ex.getMessage());
        s.nextToken();

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 7y, at row: 5, unexpected character: y", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 00, at row: 7", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 00p, at row: 7, unexpected character: p", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 00p, at row: 8, unexpected character: p", ex.getMessage());
        s.nextToken();

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 070, at row: 9", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 07p, at row: 10, unexpected character: p", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 000, at row: 11", ex.getMessage());
    }

    @Test
    void testErroriNumbers() throws FileNotFoundException {
        Scanner s = new Scanner(testPath +"erroriNumbers.txt");

        LexicalException ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 00, at row: 1", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 123a, at row: 2, unexpected character: a", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 12.a, at row: 3, unexpected character: a", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 123.121212, at row: 4, unexpected character: 2", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 11.., at row: 5, unexpected character: .", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 22.2., at row: 6, unexpected character: .", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: 22e, at row: 7, unexpected character: e", ex.getMessage());
    }

    @Test
    void testGenerale() throws IOException, LexicalException {
        Scanner s = new Scanner(testPath +"testGenerale.txt");

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

    @Test
    void testAlfabeto() throws IOException, LexicalException {
        Scanner s = new Scanner(testPath +"testAlphabet.txt");
        LexicalException ex;

        assertEquals("<INT,r:1,74>", s.nextToken().toString());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: (, at row: 1, unexpected character: (", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: ?, at row: 3, unexpected character: ?", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: %, at row: 3, unexpected character: %", ex.getMessage());

        assertEquals("<OP_ASS,r:3,=>", s.nextToken().toString());

        s.nextToken();

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: [, at row: 4, unexpected character: [", ex.getMessage());


        assertEquals("<FLOAT,r:6,12.>", s.nextToken().toString());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: _, at row: 6, unexpected character: _", ex.getMessage());

        assertEquals("<FLOAT,r:7,12.44>", s.nextToken().toString());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: #, at row: 7, unexpected character: #", ex.getMessage());

        s.nextToken();
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: |, at row: 8, unexpected character: |", ex.getMessage());

        s.nextToken();
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: &, at row: 8, unexpected character: &", ex.getMessage());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: ., at row: 8, unexpected character: .", ex.getMessage());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: ., at row: 8, unexpected character: .", ex.getMessage());

        assertEquals("<PLUS,r:10>", s.nextToken().toString());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: ^, at row: 10, unexpected character: ^", ex.getMessage());

        s.nextToken(); //var

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: ù, at row: 10, unexpected character: ù", ex.getMessage());
        assertEquals("<PLUS,r:10>", s.nextToken().toString());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: (, at row: 12, unexpected character: (", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("Unexpected character in Token: ), at row: 12, unexpected character: )", ex.getMessage());
    }

    @Test
    void testPeekToken() throws IOException, LexicalException {
        Scanner s = new Scanner(testPath +"testFLOAT.txt");

        assertEquals("<FLOAT,r:2,098.8095>", s.peekToken().toString());
        assertEquals("<FLOAT,r:2,098.8095>", s.nextToken().toString());

        assertEquals("<FLOAT,r:3,0.>", s.nextToken().toString());

        assertEquals("<FLOAT,r:3,00.>", s.peekToken().toString());
        assertEquals("<FLOAT,r:3,00.>", s.peekToken().toString());
        assertEquals("<FLOAT,r:3,00.>", s.nextToken().toString());

        assertEquals("<FLOAT,r:4,98.>", s.nextToken().toString());
        assertEquals("<FLOAT,r:6,89.99999>", s.nextToken().toString());

        assertEquals("<EOF,r:6>", s.peekToken().toString());
        assertEquals("<EOF,r:6>", s.peekToken().toString());
        assertEquals("<EOF,r:6>", s.nextToken().toString());
    }
}
