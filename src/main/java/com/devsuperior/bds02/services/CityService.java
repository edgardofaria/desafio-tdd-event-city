package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityRequest;
import com.devsuperior.bds02.dto.CityResponse;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.CityNotFoundException;
import com.devsuperior.bds02.services.exceptions.EventInUseException;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityService {

  private final CityRepository cityRepository;

  public CityService(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }

  @Transactional(readOnly = true)
  public List<CityResponse> findAll() {
    return cityRepository.findAll(Sort.by("name").ascending()).stream()
        .map(CityResponse::from)
        .toList();
  }

  @Transactional(readOnly = true)
  public Page<CityResponse> findAll(String name, Pageable pageable) {
    String searchName = name == null ? "" : name;

    return cityRepository.findByNameContainingIgnoreCase(searchName, pageable)
        .map(CityResponse::from);
  }

  @Transactional(readOnly = true)
  public CityResponse findById(Long cityId) {
    return cityRepository.findById(cityId)
        .map(CityResponse::from)
        .orElseThrow(() -> new CityNotFoundException(cityId));
  }

  @Transactional
  public CityResponse create(CityRequest request) {
    City city = new City(request.name());
    City savedCity = cityRepository.save(city);
    return CityResponse.from(savedCity);
  }

  @Transactional
  public void delete(Long cityId) {
    City city = cityRepository.findById(cityId)
        .orElseThrow(() -> new CityNotFoundException(cityId));

    try {
      cityRepository.delete(city);
      cityRepository.flush();
    } catch (DataIntegrityViolationException exception) {
      throw new EventInUseException(cityId, exception);
    }
  }
}
