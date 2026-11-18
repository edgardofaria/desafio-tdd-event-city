package com.devsuperior.bds02.dto;

import java.time.LocalDate;

public record EventRequest(String name, LocalDate date, String url, Long cityId) {}
