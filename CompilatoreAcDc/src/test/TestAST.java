package test;

import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAST {
    String testPath = "CompilatoreAcDc"+ File.separator +"src"+ File.separator +"test"+ File.separator +"data"+ File.separator +"testAST"+ File.separator;
    @Test
    void testPrint() throws FileNotFoundException, SyntacticException {
        var s = new Scanner( testPath + "testPrint.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getdecStm().iterator();

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
        var iter = program.getdecStm().iterator();


        // int a;
        // float b;

        assertEquals(2, program.getdecStm().size());
        assertEquals("<DECL: INT <ID: a> null>", iter.next().toString());
        assertEquals("<DECL: FLOAT <ID: b> null>", iter.next().toString());

    }

    @Test
    void testDeclAss() throws FileNotFoundException, SyntacticException {
        var s = new Scanner( testPath + "testAss1.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getdecStm().iterator();

        assertEquals(4, program.getdecStm().size());

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
        var iter = program.getdecStm().iterator();

        assertEquals(5, program.getdecStm().size());

        //int a = 1 + 2;
        //float x = a + 7.25;
        //float y = 5.0 + k;
        assertEquals("<DECL: INT <ID: a> <BINOP: <CONST: INT 1> PLUS <CONST: INT 2>>>", iter.next().toString());
        assertEquals("<DECL: FLOAT <ID: x> <BINOP: <ID: a> PLUS <CONST: FLOAT 7.25>>>", iter.next().toString());
        assertEquals("<DECL: FLOAT <ID: y> <BINOP: <CONST: FLOAT 5.0> PLUS <ID: k>>>", iter.next().toString());

        //int v = 1 - 2
        //int v = 1 - k
        assertEquals("<DECL: INT <ID: v> <BINOP: <CONST: INT 1> MINUS <CONST: INT 2>>>", iter.next().toString());
        assertEquals("<DECL: INT <ID: v> <BINOP: <CONST: INT 1> MINUS <ID: k>>>", iter.next().toString());

    }

    @Test
    void testExpr() throws SyntacticException, FileNotFoundException {
        var s = new Scanner( testPath + "testExpr.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getdecStm().iterator();

        assertEquals(4, program.getdecStm().size());

        //System.out.println(program);

        //a = b + 6 - c;
        //var = 6 / 8.2;
        //x = a / b + 7;
        //c = 2 / 3 + 7 * 0.
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: b> PLUS <BINOP: <CONST: INT 6> MINUS <ID: c>>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: var> <BINOP: <CONST: INT 6> DIVISION <CONST: FLOAT 8.2>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: x> <BINOP: <BINOP: <ID: a> DIVISION <ID: b>> PLUS <CONST: INT 7>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: c> <BINOP: <BINOP: <CONST: INT 2> DIVISION <CONST: INT 3>> PLUS <BINOP: <CONST: INT 7> MULTIP <CONST: FLOAT 0.>>>>", iter.next().toString());
    }

    @Test
    void testDeclExpr() throws FileNotFoundException, SyntacticException {
        var s = new Scanner( testPath + "testDecl2.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getdecStm().iterator();

        assertEquals(2, program.getdecStm().size());

        assertEquals("<DECL: FLOAT <ID: k> <BINOP: <BINOP: <CONST: INT 7> DIVISION <ID: a>> PLUS <ID: b>>>", iter.next().toString());
        assertEquals("<DECL: FLOAT <ID: k> <BINOP: <BINOP: <CONST: FLOAT 7.25> DIVISION <ID: a>> PLUS <BINOP: <ID: b> PLUS <BINOP: <ID: c> MULTIP <CONST: INT 9>>>>>", iter.next().toString());

    }

    @Test
    void testOpAss() throws FileNotFoundException, SyntacticException {
        var s = new Scanner(testPath + "testOpAss.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getdecStm().iterator();

        assertEquals(5, program.getdecStm().size());


        // a = a + 9;
        // a += 9;
        // a -= 9;
        // a *= 9;
        // a /= 9;
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> PLUS <CONST: INT 9>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> PLUS <CONST: INT 9>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> MINUS <CONST: INT 9>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> MULTIP <CONST: INT 9>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: a> <BINOP: <ID: a> DIVISION <CONST: INT 9>>>", iter.next().toString());
    }

    @Test
    void testGeneral1() throws FileNotFoundException, SyntacticException {
        var s = new Scanner(testPath + "testGenerale1.txt");
        var p = new Parser(s);

        var program = p.parse();
        var iter = program.getdecStm().iterator();

        assertEquals(11, program.getdecStm().size());

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
        assertEquals("<ASSIGN: <ID: id> <BINOP: <ID: id> DIVISION <CONST: INT 5>>>", iter.next().toString());
        assertEquals("<ASSIGN: <ID: num> <BINOP: <ID: id> MULTIP <ID: id>>>", iter.next().toString());
        assertEquals("id = (id + (5 - (8 * (6.0 * 2))))", iter.next().toStringConcise());
        assertEquals("num = ((id * 5) - ((8.0 * 6) + 2))", iter.next().toStringConcise());
        assertEquals("<PRINT: <ID: num>>", iter.next().toString());
        assertEquals("<PRINT: <ID: id>>", iter.next().toString());
    }
}
