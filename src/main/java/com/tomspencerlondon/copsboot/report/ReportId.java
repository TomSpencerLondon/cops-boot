package com.tomspencerlondon.copsboot.report;

import com.tomspencerlondon.orm.jpa.AbstractEntityId;
import com.tomspencerlondon.util.ArtifactForFramework;
import java.util.UUID;

public class ReportId extends AbstractEntityId<UUID> {
  @ArtifactForFramework
  protected ReportId() {
  }

  public ReportId(UUID id) {
    super(id);
  }

}
