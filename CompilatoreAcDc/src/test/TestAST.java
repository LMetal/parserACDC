package test;

import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test generazione AST
 */
public class TestAST {
    String testPath = "CompilatoreAcDc"+ File.separator +"src"+ File.separator +"test"+ File.separator +"data"+ File.separator +"testAST"+ File.separator;
    @Test
    void testPrint() throws FileNotFoundException, SyntacticException {
        var s = new Scanner( testPath + "testPrint.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getDecStm().iterator();

        /*
        print a;
        print b;
         */
        assertEquals("<PRINT: <ID: a>>", iter.next().toString());
        assertEquals("<PRINT: <ID: b>>", iter.next().toString());
    }

    @Test
    void testDeclNoAss() throws FileNotFoundException, SyntacticException {
        var s = new Scanner( testPath + "testDecl.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getDecStm().iterator();


        // int a;
        // float b;

        assertEquals(2, program.getDecStm().size());
        assertEquals("<DECL: INT <ID: a> null>", iter.next().toString());
        assertEquals("<DECL: FLOAT <ID: b> null>", iter.next().toString());

    }

    @Test
    void testDeclAss() throws FileNotFoundException, SyntacticException {
        var s = new Scanner( testPath + "testAss1.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getDecStm().iterator();

        assertEquals(4, program.getDecStm().size());

        //System.out.println(program);


        // var = 8;
        // int pippo = 7;
        // float pluto = 7.;
        // a = b;
        assertEquals("<ASSIGN: <ID: var> <CONST: INT 8>>", iter.next().toString());
        assertEquals("<DECL: INT <ID: pippo> <CONST: INT 7>>", iter.next().toString());
        assertEquals("<DECL: FLOAT <ID: pluto> <CONST: FLOAT 7.>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: a> <ID: b>>", iter.next().toString());

    }

    @Test
    void testAssExpr() throws FileNotFoundException, SyntacticException {
        var s = new Scanner( testPath + "testAss2.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getDecStm().iterator();

        assertEquals(5, program.getDecStm().size());

        //int a = 1 + 2;
        //float x = a + 7.25;
        //float y = 5.0 + k;
        assertEquals("<DECL: INT <ID: a> <BINOP: <CONST: INT 1> + <CONST: INT 2>>>", iter.next().toString());
        assertEquals("<DECL: FLOAT <ID: x> <BINOP: <ID: a> + <CONST: FLOAT 7.25>>>", iter.next().toString());
        assertEquals("<DECL: FLOAT <ID: y> <BINOP: <CONST: FLOAT 5.0> + <ID: k>>>", iter.next().toString());

        //int v = 1 - 2
        //int v = 1 - k
        assertEquals("<DECL: INT <ID: v> <BINOP: <CONST: INT 1> - <CONST: INT 2>>>", iter.next().toString());
        assertEquals("<DECL: INT <ID: v> <BINOP: <CONST: INT 1> - <ID: k>>>", iter.next().toString());

    }

    @Test
    void testExpr() throws SyntacticException, FileNotFoundException {
        var s = new Scanner( testPath + "testExpr.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getDecStm().iterator();

        assertEquals(4, program.getDecStm().size());

        //System.out.println(program);

        //a = b + 6 - c;
        //var = 6 / 8.2;
        //x = a / b + 7;
        //c = 2 / 3 + 7 * 0.
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: b> + <BINOP: <CONST: INT 6> - <ID: c>>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: var> <BINOP: <CONST: INT 6> / <CONST: FLOAT 8.2>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: x> <BINOP: <BINOP: <ID: a> / <ID: b>> + <CONST: INT 7>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: c> <BINOP: <BINOP: <CONST: INT 2> / <CONST: INT 3>> + <BINOP: <CONST: INT 7> * <CONST: FLOAT 0.>>>>", iter.next().toString());
    }


    @Test
    void testMultipDivis() throws SyntacticException, FileNotFoundException {
        var s = new Scanner( testPath + "testMultDivis.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getDecStm().iterator();

        assertEquals(3, program.getDecStm().size());

        //System.out.println(program);

        //a = a / 5;
        //b = 8 * a;
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> / <CONST: INT 5>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: b> <BINOP: <CONST: INT 8> * <ID: a>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: c> <BINOP: <CONST: INT 8> * <BINOP: <CONST: INT 7> / <CONST: INT 6>>>>", iter.next().toString());
    }


    @Test
    void testDeclExpr() throws FileNotFoundException, SyntacticException {
        var s = new Scanner( testPath + "testDecl2.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getDecStm().iterator();

        assertEquals(2, program.getDecStm().size());

        assertEquals("<DECL: FLOAT <ID: k> <BINOP: <BINOP: <CONST: INT 7> / <ID: a>> + <ID: b>>>", iter.next().toString());
        assertEquals("<DECL: FLOAT <ID: k> <BINOP: <BINOP: <CONST: FLOAT 7.25> / <ID: a>> + <BINOP: <ID: b> + <BINOP: <ID: c> * <CONST: INT 9>>>>>", iter.next().toString());

    }

    @Test
    void testOpAss() throws FileNotFoundException, SyntacticException {
        var s = new Scanner(testPath + "testOpAss.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getDecStm().iterator();

        assertEquals(5, program.getDecStm().size());


        // a = a + 9;
        // a += 9;
        // a -= 9;
        // a *= 9;
        // a /= 9;
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> + <CONST: INT 9>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> + <CONST: INT 9>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> - <CONST: INT 9>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> * <CONST: INT 9>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> / <CONST: INT 9>>>", iter.next().toString());
    }

    @Test
    void testGeneral1() throws FileNotFoundException, SyntacticException {
        var s = new Scanner(testPath + "testGenerale1.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getDecStm().iterator();

        assertEquals(11, program.getDecStm().size());

        //float num;
        //int id = 99/2;
        //num = 5;
        //num = id;
        //num *= id + 5.0;
        //id /= 5;
        //num = id * id;
        //id += 5 - 8 * 6.0 / 2;
        //num = id * 5 - 8.0 * 6 + 2;
        //print num;
        //print id;
        assertEquals("<DECL: FLOAT <ID: num> null>", iter.next().toString());
        assertEquals("INT id = (99 / 2)", iter.next().toStringConcise());
        assertEquals("<ASSIGN: <ID: num> <CONST: INT 5>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: num> <ID: id>>", iter.next().toString());
        assertEquals("num = (num * (id + 5.0))", iter.next().toStringConcise());
        assertEquals("<ASSIGN: <ID: id> <BINOP: <ID: id> / <CONST: INT 5>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: num> <BINOP: <ID: id> * <ID: id>>>", iter.next().toString());
        assertEquals("id = (id + (5 - (8 * (6.0 / 2))))", iter.next().toStringConcise());
        assertEquals("num = ((id * 5) - ((8.0 * 6) + 2))", iter.next().toStringConcise());
        assertEquals("<PRINT: <ID: num>>", iter.next().toString());
        assertEquals("<PRINT: <ID: id>>", iter.next().toString());
    }
}
