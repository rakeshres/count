package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.impl;

import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.remitpulse.countries.crudapi.AutoVerifier;
import global.citytech.remitpulse.countries.crudapi.impl.exceptions.ExceptionManager;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogLetter;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogVerifyRequest;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.BaseAuditLogRepositoryHelper;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.RejectAuditLogRepositoryHelper;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

/** @author sunil */
@Named
@Dependent
public class RejectAuditLogRepositoryHelperImpl implements RejectAuditLogRepositoryHelper {

  private static final Logger LOGGER =
      Logger.getLogger(RejectAuditLogRepositoryHelperImpl.class.getName());

  private AutoVerifier autoVerifier;
  private AuditLogLetter auditLogLetter;
  private BaseAuditLogRepositoryHelper baseAuditLogRepositoryHelper;

  @Inject
  public RejectAuditLogRepositoryHelperImpl(
      AutoVerifier autoVerifier,
      AuditLogLetter auditLogLetter,
      BaseAuditLogRepositoryHelper baseAuditLogRepositoryHelper) {

    this.autoVerifier = autoVerifier;
    this.auditLogLetter = auditLogLetter;
    this.baseAuditLogRepositoryHelper = baseAuditLogRepositoryHelper;
  }

  @Override
  public AuditLogInfo execute(AuditLogVerifyRequest auditLogVerifyRequest) {

    LOGGER.info("Approving the audit log with id : " + auditLogVerifyRequest.getId());

    AuditLogEntity auditLogEntity = auditLogLetter.getAuditLogEntity();
    if (auditLogEntity == null) {
      ExceptionManager.throwErrorOnCommunicationBetweenValidatorAndHelperClass();
    }

    auditLogEntity.setResponseRemarks(auditLogVerifyRequest.getRemarks());
    autoVerifier.reject(auditLogEntity, false);

    return baseAuditLogRepositoryHelper.toServiceObject(auditLogEntity);
  }
}
