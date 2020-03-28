package com.osadchuk.worktimerserver.model.dto;

import com.osadchuk.worktimerserver.validator.name.ValidName;
import com.osadchuk.worktimerserver.validator.password.ValidNewPassword;
import com.osadchuk.worktimerserver.validator.password.ValidPassword;
import com.osadchuk.worktimerserver.validator.phoneNumber.ValidPhoneNumber;
import com.osadchuk.worktimerserver.validator.username.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object fro user entity representation with custom validation using on registration page
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
	@ValidUsername
	private String username;

	@ValidName
	private String firstName;

	@ValidName
	private String lastName;

	@ValidPhoneNumber
	private String phoneNumber;

	@ValidPassword
	private String password;

	private String confirmPassword;

	@ValidNewPassword
	private String newPassword;

	private boolean isAdmin;

	private String token;

	private String error;
}
