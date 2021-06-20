package global.citytech.remitpulse.countries.repositories.impl.helpers;

import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.EntityConverter;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs.BaseAuditLogRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.converters.CountryDynamicFormConverter;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.modulesconfig.ModulesConfigService;

import javax.inject.Inject;
import java.util.Optional;

/** @author bipin on 2020-03-04 16:39 */
public class BaseAuditLogRepositoryHelperImpl implements BaseAuditLogRepositoryHelper {

  private CountryDynamicFormDao countryDynamicFormDao;
  private ModulesConfigService modulesConfigService;
  private CountryDynamicFormConverter countryDynamicFormConverter;

  @Inject
  public BaseAuditLogRepositoryHelperImpl(
      CountryDynamicFormDao countryDynamicFormDao,
      ModulesConfigService modulesConfigService,
      CountryDynamicFormConverter countryDynamicFormConverter) {
    this.countryDynamicFormDao = countryDynamicFormDao;
    this.modulesConfigService = modulesConfigService;
    this.countryDynamicFormConverter = countryDynamicFormConverter;
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

  @Override
  public AbstractRepository getDaoBasedOnEntityType(String entity) {
    if (EntityName.COUNTRY_DYNAMIC_FORM.name().equalsIgnoreCase(entity)) {
      return countryDynamicFormDao;
    } else {
      ExceptionManager.throwInvalidAuditLogEntity();
      return null;
    }
  }

  @Override
  public boolean isAutoVerifyEnable(String entity) {
    Optional<EntityName> entityName = EntityName.getByName(entity);
    if (entityName.isEmpty()) {
      ExceptionManager.throwInvalidAuditLogEntity();
    }

    return modulesConfigService.isAutoVerifyEnabled(entityName.get().getModuleIdentifier());
  }
}
