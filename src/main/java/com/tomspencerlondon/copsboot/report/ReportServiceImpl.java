package com.tomspencerlondon.copsboot.report;

import com.tomspencerlondon.copsboot.user.UserId;
import com.tomspencerlondon.copsboot.user.UserService;
import com.tomspencerlondon.copsboot.user.web.UserNotFoundException;
import java.time.ZonedDateTime;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
  private final ReportRepository repository;
  private final UserService userService;

  public ReportServiceImpl(ReportRepository repository, UserService userService) {
    this.repository = repository;
    this.userService = userService;
  }

  @Override
  public Report createReport(UserId reporterId, ZonedDateTime dateTime, String description) {
    return repository.save(new Report(repository.nextId(),
        userService.getUser(reporterId)
            .orElseThrow(() -> new UserNotFoundException(reporterId)),
        dateTime,
        description));
  }

  @Override
  public Optional<Report> findReportById(ReportId reportId) {
    return repository.findById(reportId);
  }
}