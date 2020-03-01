package com.osadchuk.worktimerserver.validator.phoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Phone number validator implementation
 */
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

	private static final String PHONE_NUMBER_PATTERN = "^\\+380\\d{9}$";

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
		Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}
}
