package com.tomspencerlondon.copsboot.report;

import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, ReportId>, ReportRepositoryCustom {
}
