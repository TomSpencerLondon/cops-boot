package com.tomspencerlondon.copsboot.report;

import com.tomspencerlondon.orm.jpa.UniqueIdGenerator;
import java.util.UUID;

public class ReportRepositoryImpl implements ReportRepositoryCustom {
  private final UniqueIdGenerator<UUID> generator;

  public ReportRepositoryImpl(UniqueIdGenerator<UUID> generator) {
    this.generator = generator;
  }

  @Override
  public ReportId nextId() {
    return new ReportId(generator.getNextUniqueId());
  }
}
