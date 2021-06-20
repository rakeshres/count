package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.validators.auditlogs.impl;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.remitpulse.countries.crudapi.impl.exceptions.ExceptionManager;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogLetter;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogVerifyRequest;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.validators.auditlogs.VerifyAuditLogValidator;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogFilter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;
import java.util.logging.Logger;

/** @author sunil */
@Named
@Dependent
public class VerifyAuditLogValidatorImpl implements VerifyAuditLogValidator {

  private static final Logger LOGGER =
      Logger.getLogger(VerifyAuditLogValidatorImpl.class.getName());

  private AuditLogLetter auditLogLetter;
  private AuditDao auditLogDao;

  @Inject
  public VerifyAuditLogValidatorImpl(AuditLogLetter auditLogLetter, AuditDao auditLogDao) {
    this.auditLogLetter = auditLogLetter;
    this.auditLogDao = auditLogDao;
  }

  @Override
  public void validate(AuditLogVerifyRequest auditLogVerifyRequest) {

    String auditLogId = validateAndGetAuditLogId(auditLogVerifyRequest);
    validateRemark(auditLogVerifyRequest);
    AuditLogEntity logEntity = validateAndGetAuditLogEntity(auditLogId);
    validatePendingStatus(logEntity);
    setAuditLogEntityToLetter(logEntity);
  }

  private void validateRemark(AuditLogVerifyRequest auditLogVerifyRequest) {
    if (AuditLogVerifyRequest.ActionToPerform.REJECT.equals(
            auditLogVerifyRequest.getActionToPerform())
        && Strings.isNullOrEmpty(auditLogVerifyRequest.getRemarks())) {
      ExceptionManager.throwAuditLogRemarkIsBlank();
    }
  }

  private void setAuditLogEntityToLetter(AuditLogEntity logEntity) {
    auditLogLetter.setAuditLogEntity(logEntity);
  }

  private String validateAndGetAuditLogId(AuditLogVerifyRequest auditLogVerifyRequest) {
    String auditLogId = auditLogVerifyRequest.getId();
    if (Strings.isNullOrEmpty(auditLogId)) {
      ExceptionManager.throwAuditLogIdIsBlank();
    }
    return auditLogId;
  }

  private void validatePendingStatus(AuditLogEntity logEntity) {
    if (AuditStatus.PENDING != logEntity.getStatus()) {
      ExceptionManager.throwInvalidAuditLogStatusToVerify();
    }
  }

  private AuditLogEntity validateAndGetAuditLogEntity(String auditLogId) {

    LOGGER.info("Fetching audit-log details for id: " + auditLogId);
    AuditLogFilter filterCriteria = this.prepareAuditLogFilterCriteria(auditLogId);
    Optional<AuditLogEntity> auditLogEntity = auditLogDao.find(filterCriteria);
    if (auditLogEntity.isEmpty()) {
      ExceptionManager.throwAuditLogNotFound();
    }

    return auditLogEntity.get();
  }

  private AuditLogFilter prepareAuditLogFilterCriteria(String id) {

    AuditLogFilter filterCriteria = new AuditLogFilter();
    filterCriteria.setId(id);
    return filterCriteria;
  }
}
