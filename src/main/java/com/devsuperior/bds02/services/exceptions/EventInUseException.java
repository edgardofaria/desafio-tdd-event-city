package com.devsuperior.bds02.services.exceptions;

import java.io.Serial;

public class EventInUseException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public EventInUseException(Long eventId, Throwable cause) {
    super("Cannot delete event " + eventId + " because it is referenced by other resources.", cause);
  }
}
