package com.osadchuk.worktimerserver.util;

import java.util.Random;

public class PasswordGenerator {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

	private static final Random RANDOM = new Random();

	public static String generatePassword(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = RANDOM.nextInt(ALPHA_NUMERIC_STRING.length());
			sb.append(ALPHA_NUMERIC_STRING.charAt(index));
		}
		return sb.toString();
	}
}
