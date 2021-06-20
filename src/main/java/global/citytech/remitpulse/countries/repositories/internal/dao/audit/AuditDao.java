package global.citytech.remitpulse.countries.repositories.internal.dao.audit;

import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogIdentifier;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditReportFilterCriteria;

import java.util.List;

/**
 * @author bipin on 6/5/19 2:13 PM.
 */
public interface AuditDao extends AbstractRepository<AuditLogEntity, AuditLogIdentifier> {
  long countByDynamicFilter(AuditReportFilterCriteria auditReportFilterCriteria);

  List<AuditLogEntity> findWithDynamicFilter(AuditReportFilterCriteria auditReportFilterCriteria);
}
