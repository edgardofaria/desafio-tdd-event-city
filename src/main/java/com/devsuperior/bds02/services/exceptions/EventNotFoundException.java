package com.devsuperior.bds02.services.exceptions;

import java.io.Serial;

public class EventNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public EventNotFoundException(Long eventId) {
    super("Event not found: " + eventId);
  }
}
