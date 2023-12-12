package test;

import org.junit.jupiter.api.Test;
import scanner.LexicalException;
import scanner.Scanner;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestException {
    String testPath = "CompilatoreAcDc"+ File.separator +"src"+ File.separator +"test"+ File.separator +"data"+ File.separator +"testScanner"+ File.separator;
    @Test
    void test() throws IOException, LexicalException {
        Scanner s = new Scanner(testPath + "erroriID");

        s.nextToken();
        s.nextToken();

        LexicalException ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("nome1,r:3,c:1", ex.getMessage());

        s.nextToken();

        ex = assertThrows(LexicalException.class, s::nextToken);
        assertEquals("v1,r:5,c:1", ex.getMessage());

    }
}
