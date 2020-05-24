package com.osadchuk.worktimerserver.service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface for converting Model into DTO
 *
 * @param <T> model type
 * @param <D> DTO type
 */
public interface DataTransferObjectService<T, D> {

	D convertIntoDTO(T model);

	default List<D> convertIntoDTO(List<T> models) {
		return models != null
				? models.stream().map(this::convertIntoDTO).collect(Collectors.toList())
				: null;
	}
}
