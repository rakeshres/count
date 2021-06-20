package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs;


import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.remitpulse.countries.crudapi.impl.helper.RepositoryHelper;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogVerifyRequest;

/** @author sunil */
public interface ApproveAuditLogRepositoryHelper
    extends RepositoryHelper<AuditLogVerifyRequest, AuditLogInfo> {}
