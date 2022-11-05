package com.tomspencerlondon.copsboot.report;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportNotFoundException extends RuntimeException {

  public ReportNotFoundException(ReportId id) {
    super(String.format("Report for id %s not found", id));
  }
}
