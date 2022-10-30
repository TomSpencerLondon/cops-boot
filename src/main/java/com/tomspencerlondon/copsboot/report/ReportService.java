package com.tomspencerlondon.copsboot.report;

import com.tomspencerlondon.copsboot.user.UserId;
import java.time.ZonedDateTime;
import java.util.Optional;

public interface ReportService {
  Report createReport(UserId reporterId, ZonedDateTime dateTime, String description, byte[] bytes);

  Optional<Report> findReportById(ReportId reportId);
}
