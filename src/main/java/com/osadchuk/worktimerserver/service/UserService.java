package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.User;
import com.osadchuk.worktimerserver.model.dto.UserDTO;
import com.osadchuk.worktimerserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for operations with {@link User} entity
 */
@Service
@Slf4j
public class UserService implements CrudService<User> {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
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

	public Optional<UserDTO> findByUsernameAsDTO(String username) {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		return optionalUser.map(user -> Optional.of(convertIntoDTO(user))).orElse(null);
	}

	private UserDTO convertIntoDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(user.getUsername());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		return userDTO;
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
}
