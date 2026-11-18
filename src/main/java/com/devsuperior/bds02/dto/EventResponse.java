package com.devsuperior.bds02.dto;

import com.devsuperior.bds02.entities.Event;
import java.time.LocalDate;

public record EventResponse(Long id, String name, LocalDate date, String url, Long cityId) {

  public static EventResponse from(Event event) {
    return new EventResponse(
        event.getId(),
        event.getName(),
        event.getDate(),
        event.getUrl(),
        event.getCity().getId());
  }
}
