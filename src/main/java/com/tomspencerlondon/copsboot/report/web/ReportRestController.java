package com.tomspencerlondon.copsboot.report.web;

import com.tomspencerlondon.copsboot.infrastructure.security.ApplicationUserDetails;
import com.tomspencerlondon.copsboot.report.Report;
import com.tomspencerlondon.copsboot.report.ReportId;
import com.tomspencerlondon.copsboot.report.ReportService;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @GetMapping("/{reportId}")
  public ReportDto reportById(@PathVariable String reportId) {
    return service.findReportById(new ReportId(UUID.fromString(reportId)))
        .map(ReportDto::fromReport).orElse(null);
  }


//  2. Get request /api/reports/id/image [path variable] - image from database and return way for http
//  stack overflow return image from controller
}
