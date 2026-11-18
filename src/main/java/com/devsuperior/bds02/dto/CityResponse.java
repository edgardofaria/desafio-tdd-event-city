package com.devsuperior.bds02.dto;

import com.devsuperior.bds02.entities.City;

public record CityResponse(Long id, String name)
{

	public static CityResponse from(City city) {
		return new CityResponse(city.getId(), city.getName());
	}
}
