package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static token.TokenType.*;

import org.junit.jupiter.api.Test;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

import java.io.FileNotFoundException;
import java.io.IOException;

class TestToken {

	@Test
	void testToken() {
		Token t1 = new Token(INT, 1, "3");
		assertEquals("<INT,r:1,3>", t1.toString());

		Token t2 = new Token(PRINT, 3);
		assertEquals("<PRINT,r:3>", t2.toString());
	}


}
