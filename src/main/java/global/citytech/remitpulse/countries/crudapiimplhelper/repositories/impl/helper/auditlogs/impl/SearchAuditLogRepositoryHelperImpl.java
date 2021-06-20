package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.impl;

import global.citytech.rabbit.core.commons.EntityConverter;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.SearchAuditLogRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.converters.CountryDynamicFormConverter;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** @author sunil */
@Named
@Dependent
public class SearchAuditLogRepositoryHelperImpl implements SearchAuditLogRepositoryHelper {

  private AuditDao auditLogDao;
  private CountryDynamicFormConverter countryDynamicFormConverter;



  @Inject
  public SearchAuditLogRepositoryHelperImpl(AuditDao auditLogDao, CountryDynamicFormConverter countryDynamicFormConverter) {
    this.auditLogDao = auditLogDao;
    this.countryDynamicFormConverter = countryDynamicFormConverter;
  }

  @Override
  public AuditDao getAuditLogDao() {
    return this.auditLogDao;
  }

  @Override
  public Class getClassByEntityType(String entity) {
    if (EntityName.COUNTRY_DYNAMIC_FORM.name().equals(entity)) {
      return CountryDynamicFormEntity.class;
    } else {
      ExceptionManager.throwInvalidAuditLogEntity();
    }
    return null;
  }

  @Override
  public EntityConverter getConverterByEntityType(String entity) {
    if (EntityName.COUNTRY_DYNAMIC_FORM.name().equalsIgnoreCase(entity)) {
      return countryDynamicFormConverter;
    } else {
      ExceptionManager.throwInvalidAuditLogEntity();
      return null;
    }
  }
}
