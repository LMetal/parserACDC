package test;

import AST.NodeProgram;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import typeDescriptor.TypeTD;
import visitor.TypeCheckingVisitor;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTypeCheck {
    String testPath = "CompilatoreAcDc"+ File.separator +"src"+ File.separator +"test"+ File.separator +"data"+ File.separator +"testTypeChecking"+ File.separator;


    @Test
    void testDoubleDecl() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "1_dicRipetute.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        var error = resIter.next();
        assertEquals(TypeTD.ERROR, error.getTypeTD());
        assertEquals("a gia' dichiarato", error.getMessaggio());
    }

    @Test
    void testNotDeclared() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "2_idNonDec.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());

        var error = resIter.next();
        assertEquals(TypeTD.ERROR, error.getTypeTD());
        assertEquals("b non dichiarato", error.getMessaggio());
    }

    @Test
    void testNotDecl() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "3_idNonDec.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());

        var error = resIter.next();
        assertEquals(TypeTD.ERROR, error.getTypeTD());
        assertEquals("c non dichiarato", error.getMessaggio());

    }

    @Test
    void testNonCompatibile() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "4_tipoNonCompatibile.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());

        var error = resIter.next();
        assertEquals(TypeTD.ERROR, error.getTypeTD());
        assertEquals("Impossibile assegnare FLOAT a id INT", error.getMessaggio());
    }

    @Test
    void testOK1() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "5_corretto.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
    }

    @Test
    void testOK2() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "6_corretto.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
    }

    @Test
    void testOK3() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "7_corretto.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
    }
}
