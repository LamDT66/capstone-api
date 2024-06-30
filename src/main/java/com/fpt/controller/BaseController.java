package com.fpt.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class BaseController {

	@Autowired
	protected ModelMapper modelMapper;

	public <ENTITY, DTO> DTO convertEntityToDto(final ENTITY entity, Class<DTO> dtoClass) {
		return modelMapper.map(entity, dtoClass);
	}

	public <ENTITY, DTO> List<DTO> convertListEntityToListDto(final List<ENTITY> entities, Class<DTO> dtoClass) {
		return entities.stream()
				.map(entity -> convertEntityToDto(entity, dtoClass))
				.collect(Collectors.toList());
	}

	protected <ENTITY, DTO> Page<DTO> convertEntityPageToDtoPage(Page<ENTITY> entityPage, Pageable pageable, Class<DTO> dtoClass) {
		// get list entity
		List<ENTITY> entities = entityPage.getContent();

		// convert list entities to dtos
		List<DTO> dtos = convertListEntityToListDto(entities, dtoClass);

		// return page dto
		return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
	}
}
