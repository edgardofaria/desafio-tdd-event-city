package com.devsuperior.bds02.services.exceptions;

import java.io.Serial;

public class CityNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public CityNotFoundException(Long cityId) {
    super("City not found: " + cityId);
  }
}
