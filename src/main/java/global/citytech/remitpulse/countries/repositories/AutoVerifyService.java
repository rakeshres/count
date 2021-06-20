package global.citytech.remitpulse.countries.repositories;

import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.Entity;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;

/** @author bipin on 7/11/19 1:37 PM. */
public interface AutoVerifyService {
  boolean verifyAndApprove(
      AuditLogEntity auditLogEntity,
      AuditDao auditDao,
      Entity entity,
      AbstractRepository dao,
      RabbitRequestContext rabbitRequestContext);
}
