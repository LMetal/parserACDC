package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static token.TokenType.*;

import org.junit.jupiter.api.Test;

import token.Token;


class TestToken {

	@Test
	void testToken() {
		Token t1 = new Token(INT, 1, "3");
		assertEquals("<INT,r:1,3>", t1.toString());
		assertEquals(INT, t1.getTipo());
		assertEquals(1, t1.getRiga());
		assertEquals("3", t1.getVal());

		Token t2 = new Token(PRINT, 3);
		assertEquals("<PRINT,r:3>", t2.toString());
		assertEquals(PRINT, t2.getTipo());
		assertEquals(3, t2.getRiga());
		assertNull(t2.getVal());
	}


}
