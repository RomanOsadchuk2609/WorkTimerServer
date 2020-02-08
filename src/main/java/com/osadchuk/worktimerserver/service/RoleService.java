package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.Role;
import com.osadchuk.worktimerserver.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for operations with {@link Role} entity
 */
@Service
@Slf4j
public class RoleService implements CrudService<Role> {
	private final RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> findById(long id) {
		return roleRepository.findById(id);
	}

	@Override
	public Role save(Role entity) {
		return roleRepository.save(entity);
	}

	@Override
	public void delete(Role entity) {
		roleRepository.delete(entity);
	}

	@Override
	public void deleteById(long id) {
		roleRepository.deleteById(id);
	}
}
