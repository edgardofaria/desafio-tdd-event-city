package com.devsuperior.bds02.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.bds02.entities.City;

public interface CityRepository extends JpaRepository<City, Long> {

    Page<City> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
