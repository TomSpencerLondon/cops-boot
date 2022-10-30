package com.tomspencerlondon.copsboot.report;

import com.tomspencerlondon.copsboot.user.User;
import com.tomspencerlondon.orm.jpa.AbstractEntity;
import com.tomspencerlondon.util.ArtifactForFramework;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Report extends AbstractEntity<ReportId> {
  @ManyToOne
  private User reporter;
  private ZonedDateTime dateTime;
  private String description;

  @Lob
  @Column(name = "imagedata", length = 1000)
  private byte[] image;

  @ArtifactForFramework
  protected Report() {
  }

  public Report(ReportId id, User reporter, ZonedDateTime dateTime, String description, byte[] image) {
    super(id);
    this.reporter = reporter;
    this.dateTime = dateTime;
    this.description = description;
    this.image = image;
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

  public byte[] getImage() {
    return image;
  }
}
