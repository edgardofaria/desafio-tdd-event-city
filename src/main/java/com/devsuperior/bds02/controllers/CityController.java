package com.devsuperior.bds02.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds02.dto.CityRequest;
import com.devsuperior.bds02.dto.CityResponse;
import com.devsuperior.bds02.services.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<Page<CityResponse>> findAll(
        @PageableDefault(sort = {"name", "id"}) Pageable pageable,
        @RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.ok(cityService.findAll(name, pageable));
    }

    @PostMapping
    public ResponseEntity<CityResponse> create(@Validated @RequestBody CityRequest cityRequest) {
        CityResponse cityResponse = cityService.create(cityRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(cityResponse.id())
            .toUri();
        return ResponseEntity.created(location).body(cityResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Validated @PathVariable Long id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
