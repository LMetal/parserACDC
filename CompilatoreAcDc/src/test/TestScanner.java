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
        assertEquals("nome1,r:3,c:1", ex.getMessage());

        s.nextToken();

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("v1,r:5,c:1", ex.getMessage());

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
        assertEquals("123r,r:3,c:r", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("12s,r:4,c:s", ex.getMessage());
        s.nextToken();

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("7y,r:5,c:y", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("00,r:7", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("00p,r:7,c:p", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("00p,r:8,c:p", ex.getMessage());
        s.nextToken();

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("070,r:9", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("07p,r:10,c:p", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("000,r:11", ex.getMessage());
    }

    @Test
    void testErroriNumbers() throws FileNotFoundException {
        Scanner s = new Scanner(testPath +"erroriNumbers.txt");

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

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("22e,r:7,c:e", ex.getMessage());
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
        assertEquals("(,r:1,c:(", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("?,r:3,c:?", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("%,r:3,c:%", ex.getMessage());

        assertEquals("<OP_ASS,r:3,=>", s.nextToken().toString());

        s.nextToken();

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("[,r:4,c:[", ex.getMessage());


        assertEquals("<FLOAT,r:6,12.>", s.nextToken().toString());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("_,r:6,c:_", ex.getMessage());

        assertEquals("<FLOAT,r:7,12.44>", s.nextToken().toString());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("#,r:7,c:#", ex.getMessage());

        s.nextToken();
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("|,r:8,c:|", ex.getMessage());

        s.nextToken();
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("&,r:8,c:&", ex.getMessage());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals(".,r:8,c:.", ex.getMessage());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals(".,r:8,c:.", ex.getMessage());

        assertEquals("<PLUS,r:10>", s.nextToken().toString());
        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("^,r:10,c:^", ex.getMessage());

        s.nextToken(); //var

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("ù,r:10,c:ù", ex.getMessage());
        assertEquals("<PLUS,r:10>", s.nextToken().toString());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("(,r:12,c:(", ex.getMessage());

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("),r:12,c:)", ex.getMessage());
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
