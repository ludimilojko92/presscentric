package com.presscentric.userprovider.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidatorTest {

	@Test
	void emailValidatorTest() {
		EmailValidator validator = new EmailValidator();

		boolean isValidEmail = validator.isValid("abcdefg.com", null);
		assertFalse(isValidEmail);

		isValidEmail = validator.isValid("myname@gmail.com", null);
		assertTrue(isValidEmail);

		isValidEmail = validator.isValid(null, null);
		assertTrue(isValidEmail);
	}
}
