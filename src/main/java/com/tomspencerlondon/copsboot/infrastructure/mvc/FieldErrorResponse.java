package com.tomspencerlondon.copsboot.infrastructure.mvc;

import lombok.Value;

@Value
public class FieldErrorResponse {
  private String fieldName;
  private String errorMessage;
}
