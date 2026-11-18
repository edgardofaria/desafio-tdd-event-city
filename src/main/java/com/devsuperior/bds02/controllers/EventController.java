package com.devsuperior.bds02.controllers;

import com.devsuperior.bds02.dto.EventRequest;
import com.devsuperior.bds02.dto.EventResponse;
import com.devsuperior.bds02.services.EventService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/events")
public class EventController {

  private final EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping
  public ResponseEntity<Page<EventResponse>> findAll(
      @RequestParam(name = "name", required = false) String name,
      @PageableDefault(sort = {"name", "id"}) Pageable pageable) {
    return ResponseEntity.ok(eventService.findAll(name, pageable));
  }

  @PostMapping
  public ResponseEntity<EventResponse> create(@Validated @RequestBody EventRequest request) {
    EventResponse response = eventService.create(request);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(response.id())
        .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EventResponse> update(
      @PathVariable("id") Long eventId, @Validated @RequestBody EventRequest request) {
    return ResponseEntity.ok(eventService.update(eventId, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long eventId) {
    eventService.delete(eventId);
    return ResponseEntity.noContent().build();
  }
}
