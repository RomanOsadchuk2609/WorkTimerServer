package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.User;
import com.osadchuk.worktimerserver.model.NewUser;
import com.osadchuk.worktimerserver.model.dto.UserDTO;
import com.osadchuk.worktimerserver.repository.RoleRepository;
import com.osadchuk.worktimerserver.repository.UserRepository;
import com.osadchuk.worktimerserver.util.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.osadchuk.worktimerserver.util.WorkTimerConstants.Role.ADMIN;
import static com.osadchuk.worktimerserver.util.WorkTimerConstants.Role.USER;

/**
 * Service layer for operations with {@link User} entity
 */
@Service
@Slf4j
public class UserService implements CrudService<User> {
	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public User save(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public List<User> findAllByContainingFilterIgnoreCase(String filter) {
		List<User> usersByUsername = userRepository.findAllByUsernameContainingIgnoreCase(filter);
		List<User> usersByFirstName = userRepository.findAllByFirstNameContainingIgnoreCase(filter);
		List<User> usersByLastName = userRepository.findAllByLastNameContainingIgnoreCase(filter);
		List<User> usersByPhoneNumber = userRepository.findAllByPhoneNumberContainingIgnoreCase(filter);
		return Stream.of(usersByUsername, usersByFirstName, usersByLastName, usersByPhoneNumber)
				.flatMap(Collection::stream)
				.distinct()
				.sorted(Comparator.comparing(User::getUsername))
				.collect(Collectors.toList());
	}

	public Optional<UserDTO> findByUsernameAsDTO(String username) {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		return optionalUser.map(user -> Optional.of(convertIntoDTO(user))).orElse(null);
	}

	public UserDTO convertIntoDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(user.getUsername());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setAdmin(user.isAdmin());
		userDTO.setToken(user.getToken());
		return userDTO;
	}

	public List<UserDTO> convertIntoDTO(List<User> users) {
		return users != null
				? users.stream().map(this::convertIntoDTO).collect(Collectors.toList())
				: null;
	}

	public User update(User user) {
		User oldUser = findByUsername(user.getUsername()).orElse(new User());
		oldUser.setFirstName(user.getFirstName());
		oldUser.setLastName(user.getLastName());
		oldUser.setPhoneNumber(user.getPhoneNumber());
		oldUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}

	public UserDTO update(UserDTO userDTO) {
		User oldUser = findByUsername(userDTO.getUsername()).orElse(new User());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (passwordEncoder.matches(userDTO.getPassword(), oldUser.getPassword())) {
			String newPassword = userDTO.getNewPassword();
			if (Strings.isBlank(newPassword)) {
				oldUser.setFirstName(userDTO.getFirstName());
				oldUser.setLastName(userDTO.getLastName());
				oldUser.setPhoneNumber(userDTO.getPhoneNumber());
				oldUser = userRepository.save(oldUser);
				return convertIntoDTO(oldUser);
			} else {
				if (Strings.isNotBlank(userDTO.getConfirmPassword()) && newPassword.equals(userDTO.getConfirmPassword())) {
					oldUser.setFirstName(userDTO.getFirstName());
					oldUser.setLastName(userDTO.getLastName());
					oldUser.setPhoneNumber(userDTO.getPhoneNumber());
					oldUser.setPassword(new BCryptPasswordEncoder().encode(userDTO.getNewPassword()));
					oldUser = userRepository.save(oldUser);
					return convertIntoDTO(oldUser);
				} else {
					userDTO.setError("Passwords does not match!");
					return userDTO;
				}
			}
		} else {
			userDTO.setError("Incorrect password!");
			return userDTO;
		}
	}

	public NewUser addUser(NewUser newUser) {
		StringBuilder errorMessageBuilder = new StringBuilder();
		if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
			errorMessageBuilder.append("User with username: ")
					.append(newUser.getUsername())
					.append(" already exists. ");
		}
		if (userRepository.findByPhoneNumber(newUser.getPhoneNumber()).isPresent()) {
			errorMessageBuilder.append("User with phone number: ")
					.append(newUser.getPhoneNumber())
					.append(" already exists. ");
		}
		if (Strings.isBlank(errorMessageBuilder.toString())) {
			User user = new User();
			user.setUsername(newUser.getUsername());
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setPhoneNumber(newUser.getPhoneNumber());
			user.setRoles(new HashSet<>());
			user.getRoles().add(roleRepository.findByName(USER));
			if (newUser.isAdmin()) {
				user.getRoles().add(roleRepository.findByName(ADMIN));
			}
			user.setToken(PasswordGenerator.generatePassword(8));
			user = userRepository.save(user);
			newUser.setToken(user.getToken());
		}
		return newUser;
	}
}
