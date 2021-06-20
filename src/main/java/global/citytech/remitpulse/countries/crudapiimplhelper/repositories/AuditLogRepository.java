package global.citytech.remitpulse.countries.crudapiimplhelper.repositories;

import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.commons.PageableData;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogSearchRequest;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogVerifyRequest;

/** @author sunil */
public interface AuditLogRepository {

  PageableData<AuditLogInfo> searchAuditLog(AuditLogSearchRequest auditLogSearchRequest);

  AuditLogInfo findOne(AuditLogSearchRequest auditLogSearchRequest);

  AuditLogInfo approve(AuditLogVerifyRequest auditLogVerifyRequest);

  AuditLogInfo reject(AuditLogVerifyRequest auditLogVerifyRequest);
}
