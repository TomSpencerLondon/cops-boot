package com.tomspencerlondon.copsboot.report;

import com.tomspencerlondon.copsboot.user.UserId;
import java.time.ZonedDateTime;

public interface ReportService {
  Report createReport(UserId reporterId, ZonedDateTime dateTime, String description);
}
