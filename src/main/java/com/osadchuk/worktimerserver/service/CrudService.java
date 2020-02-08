package com.osadchuk.worktimerserver.service;

import java.util.List;

/**
 * Service Interface with general CRUD operations
 *
 * @param <T> - type of Entity
 */
public interface CrudService<T> {
	List<T> findAll();

	T findById(long id);

	T save(T entity);

	void delete(T entity);

	void deleteById(long id);
}
