package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl;

import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.crudapi.AutoVerifier;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.BaseAuditLogRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** @author sunil */
@Named
@Dependent
public class AutoVerifierImpl implements AutoVerifier {
  private AuditDao auditLogDao;
  private RabbitRequestContext rabbitRequestContext;
  private BaseAuditLogRepositoryHelper baseAuditLogRepositoryHelper;

  @Inject
  public AutoVerifierImpl(
      AuditDao auditLogDao,
      RabbitRequestContext rabbitRequestContext,
      BaseAuditLogRepositoryHelper baseAuditLogRepositoryHelper) {
    this.auditLogDao = auditLogDao;
    this.rabbitRequestContext = rabbitRequestContext;
    this.baseAuditLogRepositoryHelper = baseAuditLogRepositoryHelper;
  }

  @Override
  public AuditDao getAuditLogDao() {
    return auditLogDao;
  }

  @Override
  public RabbitRequestContext getRabbitRequestContext() {
    return rabbitRequestContext;
  }

  @Override
  public AbstractRepository getDaoBasedOnEntityType(String entityName) {

    return baseAuditLogRepositoryHelper.getDaoBasedOnEntityType(entityName);
  }

  @Override
  public boolean isValidEntity(String entityName) {
    return baseAuditLogRepositoryHelper.isValidEntity(entityName);
  }

  @Override
  public Class getEntityClass(String entityName) {
    return this.baseAuditLogRepositoryHelper.getClassByEntityType(entityName);
  }

  @Override
  public boolean isAutoVerifyEnable(String entityName) {
    return this.baseAuditLogRepositoryHelper.isAutoVerifyEnable(entityName);
  }
}
