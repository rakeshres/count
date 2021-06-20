package global.citytech.remitpulse.countries.repositories.validators.impl.dbandotherservicesvalidator;

import global.citytech.remitpulse.countries.repositories.converters.CountryConverter;
import global.citytech.remitpulse.countries.repositories.domains.services.customfields.CustomFieldModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormDao;
import global.citytech.remitpulse.countries.repositories.internal.dto.CountryDynamicFormLetter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** @author bipin on 2020-03-05 18:26 */
@Named
@Dependent
public class CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImpl
    implements BaseCountryDynamicFormMasterTypeDbAndOtherServicesValidator {

  private MasterModuleService masterModuleService;
  private CountryDynamicFormDao countryDynamicFormDao;
  private AuditDao auditDao;
  private CountryDao countryDao;
  private CountryDynamicFormLetter countryDynamicFormLetter;
  private CountryConverter countryConverter;
  private CustomFieldModuleService customFieldModuleService;

  @Inject
  public CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImpl(
      MasterModuleService masterModuleService,
      CountryDynamicFormDao countryDynamicFormDao,
      AuditDao auditDao,
      CountryDao countryDao,
      CountryDynamicFormLetter countryDynamicFormLetter,
      CountryConverter countryConverter, CustomFieldModuleService customFieldModuleService) {
    this.masterModuleService = masterModuleService;
    this.countryDynamicFormDao = countryDynamicFormDao;
    this.auditDao = auditDao;
    this.countryDao = countryDao;
    this.countryDynamicFormLetter = countryDynamicFormLetter;
    this.countryConverter = countryConverter;
    this.customFieldModuleService = customFieldModuleService;
  }

  @Override
  public MasterModuleService getMasterModuleService() {
    return this.masterModuleService;
  }

  @Override
  public CountryDynamicFormDao getCountryDynamicFormDao() {
    return this.countryDynamicFormDao;
  }

  @Override
  public CountryDao getCountryDao() {
    return this.countryDao;
  }

  @Override
  public AuditDao getAuditDao() {
    return this.auditDao;
  }

  @Override
  public CountryDynamicFormLetter getCountryDynamicFormLetter() {
    return this.countryDynamicFormLetter;
  }

  @Override
  public CountryConverter getCountryConverter() {
    return this.countryConverter;
  }

  @Override
  public CustomFieldModuleService getCustomFieldModuleService() {
    return this.customFieldModuleService;
  }
}
