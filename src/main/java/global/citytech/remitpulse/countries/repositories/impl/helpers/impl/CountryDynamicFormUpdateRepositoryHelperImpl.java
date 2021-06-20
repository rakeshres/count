package global.citytech.remitpulse.countries.repositories.impl.helpers.impl;

import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.FilterCriteria;
import global.citytech.rabbit.core.commons.StringIdentifier;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.crudapi.AutoVerifier;
import global.citytech.remitpulse.countries.repositories.converters.CountryDynamicFormConverter;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.impl.helpers.CountryDynamicFormUpdateRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryDynamicFormFilter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** @author bipin on 2020-03-06 16:42 */
@Named
@Dependent
public class CountryDynamicFormUpdateRepositoryHelperImpl
    implements CountryDynamicFormUpdateRepositoryHelper {

  private CountryDynamicFormDao countryDynamicFormDao;
  private AuditDao auditLogDao;
  private AutoVerifier autoVerifier;
  private RabbitRequestContext rabbitRequestContext;
  private CountryDynamicFormConverter countryDynamicFormConverter;

  @Inject
  public CountryDynamicFormUpdateRepositoryHelperImpl(
      CountryDynamicFormDao countryDynamicFormDao,
      AuditDao auditLogDao,
      AutoVerifier autoVerifier,
      RabbitRequestContext rabbitRequestContext,
      CountryDynamicFormConverter countryDynamicFormConverter) {
    this.countryDynamicFormDao = countryDynamicFormDao;
    this.auditLogDao = auditLogDao;
    this.autoVerifier = autoVerifier;
    this.rabbitRequestContext = rabbitRequestContext;
    this.countryDynamicFormConverter = countryDynamicFormConverter;
  }

  @Override
  public AbstractRepository<CountryDynamicFormEntity, StringIdentifier> getEntityDao() {
    return countryDynamicFormDao;
  }

  @Override
  public AuditableEntityConverter<CountryDynamicFormEntity, CountryDynamicFormInfo> getConverter() {
    return countryDynamicFormConverter;
  }

  @Override
  public AuditDao getAuditLogDao() {
    return auditLogDao;
  }

  @Override
  public AutoVerifier getAutoVerifier() {
    return autoVerifier;
  }

  @Override
  public String getPrimaryKey(CountryDynamicFormInfo serviceObject) {
    return serviceObject.getId();
  }

  @Override
  public String getEntityName() {
    return EntityName.COUNTRY_DYNAMIC_FORM.name();
  }

  @Override
  public RabbitRequestContext getRabbitRequestContext() {
    return rabbitRequestContext;
  }

  @Override
  public FilterCriteria prepareFilterCriteriaWithPrimaryKey(CountryDynamicFormInfo serviceObject) {
    CountryDynamicFormFilter filter = new CountryDynamicFormFilter();
    filter.setId(serviceObject.getId());
    return filter;
  }

  @Override
  public void updateEditableFields(
      CountryDynamicFormEntity entity, CountryDynamicFormInfo serviceObject) {
    if (HelperUtilsLocal.toLocalDateTime(entity.getEffectiveFrom())
        .isAfter(HelperUtilsLocal.todayDate())) {
      entity.setEffectiveFrom(serviceObject.getEffectiveFrom());
      entity.setEffectiveTo(serviceObject.getEffectiveTo());
      entity.setFieldInfoList(Jsons.toJsonObj(serviceObject.getFieldInfoList()));
    } else if (!HelperUtilsLocal.parseDate(serviceObject.getEffectiveTo())
        .isBefore(HelperUtilsLocal.parseDate(entity.getEffectiveTo()))) {
      entity.setEffectiveTo(serviceObject.getEffectiveTo());
    }
    entity.setLastModifiedBy(rabbitRequestContext.getUserName());
    entity.setLastModifiedOn(HelperUtils.now());
  }

  @Override
  public void updateResponse(CountryDynamicFormInfo info, boolean autoVerifyEnable) {
    info.setAutoApproveEnabled(autoVerifyEnable);
  }
}
