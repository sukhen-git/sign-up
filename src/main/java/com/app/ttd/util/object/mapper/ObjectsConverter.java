package com.app.ttd.util.object.mapper;


import org.springframework.beans.BeanUtils;

public class ObjectsConverter {

	public static <T> Object convertDtoObjToEntity(T dtoObj, T entity) {	
		BeanUtils.copyProperties(dtoObj, entity);
		return entity;
	}
	
	public static <T> Object convertEntityToDtoObj(T entity, T dtoObj) {
			BeanUtils.copyProperties(entity, dtoObj);
			return dtoObj;
		}
}
