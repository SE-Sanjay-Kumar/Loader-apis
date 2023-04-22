package com.tms.loader.configs;

import org.modelmapper.ModelMapper;

import com.tms.loader.entities.vehicle.Freight;
import com.tms.loader.payloads.vehicle.FreightDto;

public class ModelMapperConfig {
	public static void mapBasedOnFreightId(ModelMapper mapper) {
		
		mapper.typeMap(Freight.class, FreightDto.class)
		  .addMapping(src -> src.getVtype(), FreightDto::setType);
	}
}
