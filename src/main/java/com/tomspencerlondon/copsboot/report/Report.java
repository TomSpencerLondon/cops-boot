package com.tomspencerlondon.copsboot.report;

import com.tomspencerlondon.copsboot.user.User;
import com.tomspencerlondon.orm.jpa.AbstractEntity;
import com.tomspencerlondon.util.ArtifactForFramework;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Report extends AbstractEntity<ReportId> {
  @ManyToOne
  private User reporter;
  private ZonedDateTime dateTime;
  private String description;

  @ArtifactForFramework
  protected Report() {
  }

  public Report(ReportId id, User reporter, ZonedDateTime dateTime, String description) {
    super(id);
    this.reporter = reporter;
    this.dateTime = dateTime;
    this.description = description;
  }

  public User getReporter() {
    return reporter;
  }

  public ZonedDateTime getDateTime() {
    return dateTime;
  }

  public String getDescription() {
    return description;
  }
}
