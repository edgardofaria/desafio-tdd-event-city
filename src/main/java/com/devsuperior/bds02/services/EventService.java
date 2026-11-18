package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.EventRequest;
import com.devsuperior.bds02.dto.EventResponse;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.CityNotFoundException;
import com.devsuperior.bds02.services.exceptions.EventInUseException;
import com.devsuperior.bds02.services.exceptions.EventNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

  private final EventRepository eventRepository;
  private final CityRepository cityRepository;

  public EventService(EventRepository eventRepository, CityRepository cityRepository) {
    this.eventRepository = eventRepository;
    this.cityRepository = cityRepository;
  }

  @Transactional(readOnly = true)
  public Page<EventResponse> findAll(String name, Pageable pageable) {
    String searchName = name == null ? "" : name;

    return eventRepository.findByNameContainingIgnoreCase(searchName, pageable)
        .map(EventResponse::from);
  }

  @Transactional(readOnly = true)
  public EventResponse findById(Long eventId) {
    return eventRepository.findById(eventId)
        .map(EventResponse::from)
        .orElseThrow(() -> new EventNotFoundException(eventId));
  }

  @Transactional
  public EventResponse create(EventRequest request) {
    Event event = new Event();
    applyRequest(event, request);

    Event savedEvent = eventRepository.save(event);
    return EventResponse.from(savedEvent);
  }

  @Transactional
  public EventResponse update(Long eventId, EventRequest request) {
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new EventNotFoundException(eventId));
    applyRequest(event, request);

    Event savedEvent = eventRepository.save(event);
    return EventResponse.from(savedEvent);
  }

  @Transactional
  public void delete(Long eventId) {
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new EventNotFoundException(eventId));

    try {
      eventRepository.delete(event);
      eventRepository.flush();
    } catch (DataIntegrityViolationException exception) {
      throw new EventInUseException(eventId, exception);
    }
  }

  private void applyRequest(Event event, EventRequest request) {
    City city = cityRepository.findById(request.cityId())
        .orElseThrow(() -> new CityNotFoundException(request.cityId()));

    event.setName(request.name());
    event.setDate(request.date());
    event.setUrl(request.url());
    event.setCity(city);
  }
}
