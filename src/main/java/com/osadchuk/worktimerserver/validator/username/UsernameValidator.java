package com.osadchuk.worktimerserver.validator.username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Username validator implementation
 */
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

	private static final String USERNAME_PATTERN = "[A-z\\d_]+";

	@Override
	public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);
		Matcher matcher = pattern.matcher(username);
		return matcher.matches();
	}
}
