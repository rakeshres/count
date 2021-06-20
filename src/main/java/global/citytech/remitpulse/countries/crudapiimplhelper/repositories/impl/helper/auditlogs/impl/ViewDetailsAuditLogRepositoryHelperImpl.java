package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.impl;

import global.citytech.rabbit.core.commons.EntityConverter;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.BaseAuditLogRepositoryHelper;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.ViewDetailsAuditLogRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** @author sunil */
@Named
@Dependent
public class ViewDetailsAuditLogRepositoryHelperImpl
        implements ViewDetailsAuditLogRepositoryHelper {

  private AuditDao auditLogDao;
  private BaseAuditLogRepositoryHelper baseAuditLogRepositoryHelper;

  @Inject
  public ViewDetailsAuditLogRepositoryHelperImpl(
      AuditDao auditLogDao, BaseAuditLogRepositoryHelper baseAuditLogRepositoryHelper) {
    this.auditLogDao = auditLogDao;
    this.baseAuditLogRepositoryHelper = baseAuditLogRepositoryHelper;
  }

  @Override
  public AuditDao getAuditLogDao() {
    return this.auditLogDao;
  }

  @Override
  public Class getClassByEntityType(String entity) {
    return this.baseAuditLogRepositoryHelper.getClassByEntityType(entity);
  }

  @Override
  public EntityConverter getConverterByEntityType(String entity) {
    return this.baseAuditLogRepositoryHelper.getConverterByEntityType(entity);
  }
}
