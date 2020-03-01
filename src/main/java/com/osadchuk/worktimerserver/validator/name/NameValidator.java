package com.osadchuk.worktimerserver.validator.name;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Name validator implementation
 */
public class NameValidator implements ConstraintValidator<ValidName, String> {

	private static final String NAME_PATTERN = "^[A-z]([a-z])+$";

	@Override
	public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
		Pattern pattern = Pattern.compile(NAME_PATTERN);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}
}
