package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.User;
import com.osadchuk.worktimerserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public User findById(long id) {
		return userRepository.findById(id).orElse(null);
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
}
