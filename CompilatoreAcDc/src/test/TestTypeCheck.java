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

/**
 * Test type checking
 */
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
        assertEquals("r:1 a gia' dichiarato", error.getMessaggioErrore());
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
        assertEquals("r:2 b non dichiarato", error.getMessaggioErrore());
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
        assertEquals("r:2 c non dichiarato", error.getMessaggioErrore());

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
        assertEquals("r:1 Impossibile assegnare FLOAT a id INT", error.getMessaggioErrore());
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

    @Test
    void testDeclNoCast() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "more_declNoCasts.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals("FLOAT a = 9; INT b = 0; FLOAT c = b; FLOAT d = (b + 7)", nP.toStringConcise());
    }


    @Test
    void testAssignNoCasts() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "more_assignNoCasts.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());

        assertEquals("INT b = 0; FLOAT a = null; a = 9; a = b; a = (1 + 1)", nP.toStringConcise());
    }

    @Test
    void testBinOpCasts() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "more_binOpCasts.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());

        assertEquals("INT a = 0; FLOAT b = ((float)(a) + 1.); b = ((float)(a) + 0.); b = ((float)(a) * ((float)(a) / 1.))", nP.toStringConcise());
    }

    @Test
    void testMoreErr1() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "moreErr1.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        var res = resIter.next();
        assertEquals(TypeTD.ERROR, res.getTypeTD());
        assertEquals("r:0 impossibile inizializzare INT a FLOAT", res.getMessaggioErrore());
    }

    @Test
    void testMoreNotLatRowError() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "more_errorNotLastRow.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);

        var resIter = tcVisit.getLinesRes();
        //assertEquals(TypeTD.ERROR, resIter.next().getTypeTD());
        assertEquals("r:0 impossibile inizializzare INT a FLOAT", resIter.next().getMessaggioErrore());
        assertEquals(TypeTD.OK, resIter.next().getTypeTD());
        assertEquals("r:2 b non dichiarato", resIter.next().getMessaggioErrore());
    }
}
