package com.tms.loader.utils;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.tms.loader.exceptions.CJpaSystemException;
import com.tms.loader.exceptions.ExceptionEnd;


@Component
public class CRUDClass {
	@Autowired
	ModelMapper mapper;
	public <T, D> T saveDTOToRepository(D dto, JpaRepository<T, ?> repository, Class<T> entityClass) throws CJpaSystemException, ExceptionEnd {
        T entity = mapper.map(dto, entityClass);
        repository.save(entity);
        return entity;
    }



}
