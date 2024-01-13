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
        assertEquals(TypeTD.ERROR, tcVisit.getResType().getTypeTD());
    }

    @Test
    void testNotDeclared() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner(testPath + "2_idNonDec.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        for(var lineRes: tcVisit.getLinesRes()){
            System.out.println(lineRes.getTypeTD());
        }

        assertEquals(TypeTD.ERROR, tcVisit.getResType().getTypeTD());
    }
}
