package global.citytech.remitpulse.countries.repositories.impl.helpers.impl;

import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.crudapi.AutoVerifier;
import global.citytech.remitpulse.countries.repositories.converters.CountryDynamicFormConverter;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.impl.helpers.CountryDynamicFormAddRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** @author bipin on 2020-03-04 15:28 */
@Named
@Dependent
public class CountryDynamicFormAddRepositoryHelperImpl implements CountryDynamicFormAddRepositoryHelper {
  private AuditDao auditLogDao;
  private AutoVerifier autoVerifier;
  private RabbitRequestContext rabbitRequestContext;
  private CountryDynamicFormConverter countryDynamicFormConverter;

  @Inject
  public CountryDynamicFormAddRepositoryHelperImpl(
      AuditDao auditLogDao,
      AutoVerifier autoVerifier,
      RabbitRequestContext rabbitRequestContext,
      CountryDynamicFormConverter countryDynamicFormConverter) {
    this.auditLogDao = auditLogDao;
    this.autoVerifier = autoVerifier;
    this.rabbitRequestContext = rabbitRequestContext;
    this.countryDynamicFormConverter = countryDynamicFormConverter;
  }

  @Override
  public AuditDao getAuditLogDao() {
    return this.auditLogDao;
  }

  @Override
  public AutoVerifier getAutoVerifier() {
    return this.autoVerifier;
  }

  @Override
  public AuditableEntityConverter<CountryDynamicFormEntity, CountryDynamicFormInfo> getConverter() {
    return this.countryDynamicFormConverter;
  }

  @Override
  public String getEntityName() {
    return EntityName.COUNTRY_DYNAMIC_FORM.name();
  }

  @Override
  public RabbitRequestContext getRabbitRequestContext() {
    return this.rabbitRequestContext;
  }

  @Override
  public void setPrimaryKey(CountryDynamicFormEntity auditableEntity) {
    auditableEntity.setId(HelperUtils.generateUUID());
  }

  @Override
  public String getPrimaryKey(CountryDynamicFormEntity auditableEntity) {
    return auditableEntity.getId();
  }

  @Override
  public void updateResponse(CountryDynamicFormInfo info, boolean autoVerifyEnable) {
    info.setAutoApproveEnabled(autoVerifyEnable);
  }
}
