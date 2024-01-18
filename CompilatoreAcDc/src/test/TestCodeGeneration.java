package test;

import AST.NodeProgram;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCodeGeneration {
    String testPath = "CompilatoreAcDc"+ File.separator +"src"+ File.separator +"test"+ File.separator +"data"+ File.separator +"testCodeGen"+ File.separator;

    @Test
    void testDich1() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "dich1.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var genVisit = new CodeGeneratorVisitor();
        nP.accept(genVisit);

        var iter = genVisit.getDcCode();
        assertEquals("9 sa", iter.next());
        assertEquals("", iter.next());
        assertEquals("1 sc", iter.next());
    }

    @Test
    void testDich2() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "dich2.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var genVisit = new CodeGeneratorVisitor();
        nP.accept(genVisit);

        var iter = genVisit.getDcCode();
        assertEquals("8 10 + sa", iter.next());
        assertEquals("la sb", iter.next());
        assertEquals("8.2 sc", iter.next());
        assertEquals("0.1 lc + sd", iter.next());
    }

    @Test
    void testPrint() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "print.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var genVisit = new CodeGeneratorVisitor();
        nP.accept(genVisit);

        var iter = genVisit.getDcCode();
        assertEquals("5 sa", iter.next());
        assertEquals("la p P", iter.next());
    }

    @Test
    void testAssign1() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "assign1.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var genVisit = new CodeGeneratorVisitor();
        nP.accept(genVisit);

        var iter = genVisit.getDcCode();
        assertEquals("", iter.next());
        assertEquals("", iter.next());
        assertEquals("6 7 + sa", iter.next());
        assertEquals("la 9 * sb", iter.next());
    }

    @Test
    void testConvert1() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "convert1.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var genVisit = new CodeGeneratorVisitor();
        nP.accept(genVisit);

        var iter = genVisit.getDcCode();
        assertEquals("5 k 5 2. / sa 0 k", iter.next());
        assertEquals("5 11 - sa", iter.next());
    }

    @Test
    void testGenerale1() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "generale1.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var genVisit = new CodeGeneratorVisitor();
        nP.accept(genVisit);

        var iter = genVisit.getDcCode();
        assertEquals("10 sa", iter.next());
        assertEquals("0.5 sb", iter.next());
        assertEquals("5 k la lb * sb 0 k", iter.next());
        assertEquals("lb p P", iter.next());
    }

    @Test
    void testGenerale2() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "generale2.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var genVisit = new CodeGeneratorVisitor();
        nP.accept(genVisit);

        var iter = genVisit.getDcCode();
        assertEquals("2.5 sa", iter.next());
        assertEquals("la 1.3 + sb", iter.next());
        assertEquals("lb p P", iter.next());
    }

    @Test
    void testGenerale3() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "generale3.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var genVisit = new CodeGeneratorVisitor();
        nP.accept(genVisit);

        var iter = genVisit.getDcCode();
        assertEquals("8.5 5 k 1 + sa 0 k", iter.next());
        assertEquals("", iter.next());
        assertEquals("10 3 / sb", iter.next());
        assertEquals("la lb - sa", iter.next());
        assertEquals("la p P", iter.next());

    }
}
