package com.tomspencerlondon.copsboot.report.web;

import com.tomspencerlondon.copsboot.infrastructure.security.ApplicationUserDetails;
import com.tomspencerlondon.copsboot.report.ReportService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {
  private final ReportService service;

  public ReportRestController(ReportService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReportDto createReport(@AuthenticationPrincipal ApplicationUserDetails userDetails,
      @Valid CreateReportParameters parameters) {
    return ReportDto.fromReport(service.createReport(userDetails.getUserId(),
        parameters.getDateTime(),
        parameters.getDescription()));
  }

//  Add two endpoints:
//  1. Get request /api/reports/id [path variable]
//  2. Get request /api/reports/id/image [path variable] - image from database and return way for http
//  stack overflow return image from controller
}
