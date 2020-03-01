package com.osadchuk.worktimerserver.validator.password;

import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * New Password validator implementation
 */
public class NewPasswordValidator implements ConstraintValidator<ValidNewPassword, String> {

	private static final String PASSWORD_PATTERN = "[A-z\\d@#$%!*_]{8,}";

	@Override
	public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
		if (Strings.isBlank(password)) {
			return true;
		} else {
			Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
			Matcher matcher = pattern.matcher(password);
			return matcher.matches();
		}
	}
}
