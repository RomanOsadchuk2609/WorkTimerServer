package com.osadchuk.worktimerserver.validator.password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Password validator implementation
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

	private static final String PASSWORD_PATTERN = "[A-z\\d@#$%!*_]{8,}";

	@Override
	public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
}
