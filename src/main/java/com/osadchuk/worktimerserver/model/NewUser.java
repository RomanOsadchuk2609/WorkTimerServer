package com.osadchuk.worktimerserver.model;

import com.osadchuk.worktimerserver.validator.name.ValidName;
import com.osadchuk.worktimerserver.validator.phoneNumber.ValidPhoneNumber;
import com.osadchuk.worktimerserver.validator.username.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewUser {
	@ValidUsername
	private String username;

	@ValidName
	private String firstName;

	@ValidName
	private String lastName;

	@ValidPhoneNumber
	private String phoneNumber;

	private boolean isAdmin;

	private String token;

	private String error;
}
