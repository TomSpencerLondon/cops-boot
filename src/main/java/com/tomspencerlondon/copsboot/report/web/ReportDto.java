package com.tomspencerlondon.copsboot.report.web;

import com.tomspencerlondon.copsboot.report.Report;
import com.tomspencerlondon.copsboot.report.ReportId;
import java.time.ZonedDateTime;
import lombok.Value;

@Value
public class ReportDto {
  private ReportId id;
  private String reporter;
  private ZonedDateTime dateTime;
  private String description;

  public static ReportDto fromReport(Report report) {
    return new ReportDto(report.getId(),
        report.getReporter().getEmail(),
        report.getDateTime(),
        report.getDescription()
    );
  }
}
