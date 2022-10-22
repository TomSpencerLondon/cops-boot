package com.tomspencerlondon.copsboot.report.web;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportParameters {
  private ZonedDateTime dateTime;

  @ValidReportDescription
  private String description;
}