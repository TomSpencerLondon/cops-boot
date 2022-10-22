package com.tomspencerlondon.copsboot.report.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.time.ZonedDateTime;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static com.tomspencerlondon.copsboot.util.test.ConstraintViolationSetAssert.assertThat;

class ReportDescriptionValidatorTest {
  @Test
  public void givenEmptyString_notValid() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(), "", null);

    Set<ConstraintViolation<CreateReportParameters>> violationSet = validator.validate(parameters);

    assertThat(violationSet).hasViolationOnPath("description");
  }
}