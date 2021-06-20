package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl;

import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.commons.PageableData;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.AuditLogRepository;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogSearchRequest;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogVerifyRequest;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.ApproveAuditLogRepositoryHelper;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.RejectAuditLogRepositoryHelper;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.SearchAuditLogRepositoryHelper;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.ViewDetailsAuditLogRepositoryHelper;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.validators.auditlogs.SearchAuditLogValidator;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.validators.auditlogs.VerifyAuditLogValidator;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

/** @author sunil */
@Named
@Dependent
public class AuditLogRepositoryImpl implements AuditLogRepository {

  private static final Logger LOGGER = Logger.getLogger(AuditLogRepositoryImpl.class.getName());

  private SearchAuditLogValidator searchAuditLogValidator;
  private SearchAuditLogRepositoryHelper searchAuditLogRepositoryHelper;

  private ViewDetailsAuditLogRepositoryHelper viewDetailsAuditLogRepositoryHelper;

  private VerifyAuditLogValidator verifyAuditLogValidator;
  private ApproveAuditLogRepositoryHelper approveAuditLogRepositoryHelper;
  private RejectAuditLogRepositoryHelper rejectAuditLogRepositoryHelper;

  @Inject
  public AuditLogRepositoryImpl(
          SearchAuditLogValidator searchAuditLogValidator,
          SearchAuditLogRepositoryHelper searchAuditLogRepositoryHelper,
          ViewDetailsAuditLogRepositoryHelper viewDetailsAuditLogRepositoryHelper,
          VerifyAuditLogValidator verifyAuditLogValidator,
          ApproveAuditLogRepositoryHelper approveAuditLogRepositoryHelper,
          RejectAuditLogRepositoryHelper rejectAuditLogRepositoryHelper) {

    this.searchAuditLogValidator = searchAuditLogValidator;
    this.searchAuditLogRepositoryHelper = searchAuditLogRepositoryHelper;
    this.viewDetailsAuditLogRepositoryHelper = viewDetailsAuditLogRepositoryHelper;
    this.verifyAuditLogValidator = verifyAuditLogValidator;
    this.approveAuditLogRepositoryHelper = approveAuditLogRepositoryHelper;
    this.rejectAuditLogRepositoryHelper = rejectAuditLogRepositoryHelper;
  }

  @Override
  public PageableData<AuditLogInfo> searchAuditLog(AuditLogSearchRequest auditLogSearchRequest) {

    LOGGER.info("REQUEST TO SEARCH AUDIT LOGS:" + Jsons.toJsonObj(auditLogSearchRequest));
    this.searchAuditLogValidator.validate(auditLogSearchRequest);
    return this.searchAuditLogRepositoryHelper.execute(auditLogSearchRequest);
  }

  @Override
  public AuditLogInfo findOne(AuditLogSearchRequest auditLogSearchRequest) {

    LOGGER.info("REQUEST TO VIEW DETAILS OF AUDIT LOGS:" + Jsons.toJsonObj(auditLogSearchRequest));
    return this.viewDetailsAuditLogRepositoryHelper.execute(auditLogSearchRequest);
  }

  @Override
  public AuditLogInfo approve(AuditLogVerifyRequest auditLogVerifyRequest) {

    LOGGER.info("REQUEST TO SEARCH AUDIT LOGS:" + Jsons.toJsonObj(auditLogVerifyRequest));
    this.verifyAuditLogValidator.validate(auditLogVerifyRequest);
    return this.approveAuditLogRepositoryHelper.execute(auditLogVerifyRequest);
  }

  @Override
  public AuditLogInfo reject(AuditLogVerifyRequest auditLogVerifyRequest) {

    LOGGER.info("REQUEST TO SEARCH AUDIT LOGS:" + Jsons.toJsonObj(auditLogVerifyRequest));
    this.verifyAuditLogValidator.validate(auditLogVerifyRequest);
    return this.rejectAuditLogRepositoryHelper.execute(auditLogVerifyRequest);
  }
}
