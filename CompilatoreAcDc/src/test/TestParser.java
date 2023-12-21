package test;

import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestParser {
    String testPath = "CompilatoreAcDc"+ File.separator +"src"+ File.separator +"test"+ File.separator +"data"+ File.separator +"testParser"+ File.separator;

    @Test
    void testDichiarazioniPrint() throws IOException, SyntacticException {
        Scanner s = new Scanner( testPath + "testSoloDichPrint1.txt");
        Parser p = new Parser(s);

        p.parse();

    }

    @Test
    void testEcc0() throws IOException {
        Scanner s = new Scanner(testPath + "testParserEcc_0.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {OP_ASS} at row 1 but got SEMI", ex.getMessage());
    }

    @Test
    void testEcc1() throws FileNotFoundException {
        Scanner s = new Scanner(testPath + "testParserEcc_1.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {ID, FLOAT, INT} at row 2 but got MULTIP", ex.getMessage());
    }

    @Test
    void testEcc2() throws FileNotFoundException {
        Scanner s = new Scanner(testPath + "testParserEcc_2.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {TYPE_INT, TYPE_FLOAT, ID, PRINT, EOF} at row 3 but got INT", ex.getMessage());
    }

    @Test
    void testEcc3() throws FileNotFoundException {
        Scanner s = new Scanner(testPath + "testParserEcc_3.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {OP_ASS} at row 2 but got PLUS", ex.getMessage());
    }

    @Test
    void testEcc4() throws FileNotFoundException {
        Scanner s = new Scanner(testPath + "testParserEcc_4.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {ID} at row 2 but got INT", ex.getMessage());
    }

    @Test
    void testEcc5() throws FileNotFoundException {
        Scanner s = new Scanner(testPath + "testParserEcc_5.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {ID} at row 3 but got INT", ex.getMessage());
    }

    @Test
    void testEcc6() throws FileNotFoundException {
        Scanner s = new Scanner(testPath + "testParserEcc_6.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {ID} at row 4 but got TYPE_FLOAT", ex.getMessage());
    }

    @Test
    void testEcc7() throws FileNotFoundException {
        Scanner s = new Scanner(testPath + "testParserEcc_7.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {ID} at row 2 but got OP_ASS", ex.getMessage());
    }

    @Test
    void testCorretto1() throws IOException, SyntacticException {
        Scanner s = new Scanner(testPath + "testParserCorretto1.txt");
        Parser p = new Parser(s);

        p.parse();
    }

    @Test
    void testCorretto2() throws IOException, SyntacticException {
        Scanner s = new Scanner(testPath + "testParserCorretto2.txt");
        Parser p = new Parser(s);

        p.parse();
    }

    @Test
    void testMoreOK1() throws IOException, SyntacticException {
        Scanner s = new Scanner(testPath + "moreOK1.txt");
        Parser p = new Parser(s);

        p.parse();
    }

    @Test
    void testMoreOK2() throws IOException, SyntacticException {
        Scanner s = new Scanner(testPath + "moreOK2.txt");
        Parser p = new Parser(s);

        p.parse();
    }

    @Test
    void testMoreOK3() throws IOException, SyntacticException {
        Scanner s = new Scanner(testPath + "moreOK3.txt");
        Parser p = new Parser(s);

        p.parse();
    }

    @Test
    void testMoreEcc1() throws IOException {
        Scanner s = new Scanner(testPath + "moreEcc1.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {OP_ASS} at row 1 but got PLUS", ex.getMessage());
    }

    @Test
    void testMoreEcc2() throws IOException {
        Scanner s = new Scanner(testPath + "moreEcc2.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {ID} at row 1 but got INT", ex.getMessage());
    }

    @Test
    void testMoreEcc3() throws IOException {
        Scanner s = new Scanner(testPath + "moreEcc3.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Unexpected character in Token: var1, at row: 5, unexpected character: 1", ex.getMessage());
    }

    @Test
    void testMoreEcc4() throws IOException {
        Scanner s = new Scanner(testPath + "moreEcc4.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Unexpected character in Token: 9c, at row: 1, unexpected character: c", ex.getMessage());
    }

    @Test
    void testMoreEcc5() throws IOException {
        Scanner s = new Scanner(testPath + "moreEcc5.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {MULTIP, DIVISION, PLUS, MINUS, SEMI} at row 2 but got ID", ex.getMessage());
    }

    @Test
    void testMoreEcc6() throws IOException {
        Scanner s = new Scanner(testPath + "moreEcc6.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected type: {TYPE_INT, TYPE_FLOAT, ID, PRINT, EOF} at row 1 but got SEMI", ex.getMessage());
    }

    @Test
    void testMoreEcc7() throws IOException {
        Scanner s = new Scanner(testPath + "moreEcc7.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Unexpected character in Token: ', at row: 1, unexpected character: '", ex.getMessage());
    }

    @Test
    void testMoreEcc8() throws IOException {
        Scanner s = new Scanner(testPath + "moreEcc8.txt");
        Parser p = new Parser(s);

        SyntacticException ex = assertThrows(SyntacticException.class, p::parse);
        assertEquals("Expected string: {=} at row 1 but got +=", ex.getMessage());
    }

}
