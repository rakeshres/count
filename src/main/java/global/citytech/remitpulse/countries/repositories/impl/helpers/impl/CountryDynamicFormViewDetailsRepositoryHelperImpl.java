package global.citytech.remitpulse.countries.repositories.impl.helpers.impl;

import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.FilterCriteria;
import global.citytech.rabbit.core.commons.StringIdentifier;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.repositories.converters.CountryDynamicFormConverter;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.impl.helpers.CountryDynamicFormViewDetailsRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryDynamicFormFilter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** @author bipin on 2020-03-05 16:08 */
@Named
@Dependent
public class CountryDynamicFormViewDetailsRepositoryHelperImpl
    implements CountryDynamicFormViewDetailsRepositoryHelper {

  private CountryDynamicFormConverter countryDynamicFormConverter;
  private CountryDynamicFormDao countryDynamicFormDao;

  @Inject
  public CountryDynamicFormViewDetailsRepositoryHelperImpl(
      CountryDynamicFormConverter countryDynamicFormConverter,
      CountryDynamicFormDao countryDynamicFormDao) {
    this.countryDynamicFormConverter = countryDynamicFormConverter;
    this.countryDynamicFormDao = countryDynamicFormDao;
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
  public String getPrimaryKey(CountryDynamicFormInfo serviceObject) {
    return serviceObject.getId();
  }

  @Override
  public FilterCriteria prepareFilterCriteriaWithPrimaryKey(CountryDynamicFormInfo serviceObject) {
    CountryDynamicFormFilter filter= new CountryDynamicFormFilter();
    filter.setId(serviceObject.getId());
    return filter;
  }
}
