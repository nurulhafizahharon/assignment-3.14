package com.skillsunion.shoppingcartapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * Note that you should not accidentally import JUnit v4. All codes are using JUnit v5.
 */

@SpringBootTest
public class TestServiceForTest {

	@Autowired
	ServiceForTest service;

	// Add code here
	@Test
	public void passwordLengthTest() {

		// String password = "12345678";
		// int expectedLength = 8;
		// String password = "123456789";
		// int expectedLength = 9;
		String password = "1234567";
		int expectedLength = 0;
		int passwordLength = 0;

		try {
			passwordLength = service.verifyPassword(password);
		} catch (Exception e) {
			assertEquals("Password must be at least 8 characters", e.getMessage());
		}

		assertEquals(expectedLength, passwordLength, "password length should be 9");
	}

	// @Test
	// public void passwordExceptionTest() {

	// String password = "1234567";
	// try {
	// int passwordLength = service.verifyPassword(password);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// assertEquals("Password must be at least 8 characters", e.getMessage());
	// }

	// // Exception ex = assertThrows(ServiceForTest.class, () ->
	// // service.verifyPassword(password));
	// // assertEquals("Password must be at least 8 characters", ex.getMessage());
	// }

}
