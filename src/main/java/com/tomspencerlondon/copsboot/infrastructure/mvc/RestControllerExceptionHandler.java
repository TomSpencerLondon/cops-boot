package com.tomspencerlondon.copsboot.infrastructure.mvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestControllerExceptionHandler {

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, List<FieldErrorResponse>> handle(MethodArgumentNotValidException exception) {
    return error(exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> new FieldErrorResponse(fieldError.getField(),
                                                  fieldError.getDefaultMessage()
            )).collect(Collectors.toList()));
  }

  private Map<String, List<FieldErrorResponse>> error(List<FieldErrorResponse> errors) {
    return Collections.singletonMap("errors", errors);
  }
}
