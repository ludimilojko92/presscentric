package com.presscentric.userprovider.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailField, String> {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean isValid(String emailString, ConstraintValidatorContext context) {
		if (emailString != null && !emailString.isEmpty()) {
			final Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailString);
			return matcher.matches();
		}
	    return true;
	}
}
