package global.citytech.remitpulse.countries.repositories.impl.helpers.impl;

import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.StringIdentifier;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.commons.domains.pageable.PageableFilterCriteria;
import global.citytech.remitpulse.countries.repositories.constants.CountryDynamicFormDatabaseMapper;
import global.citytech.remitpulse.countries.repositories.converters.CountryDynamicFormConverter;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.impl.helpers.CountryDynamicFormPageableSearchRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryDynamicFormFilter;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryFilter;
import global.citytech.remitpulse.countries.repositories.serviceobjects.CountryDynamicFormSearchRequest;
import global.citytech.remitpulse.countries.rest.CountryDatabaseMapper;

import javax.inject.Inject;

/** @author bipin on 2020-03-05 15:10 */
public class CountryDynamicFormPageableSearchRepositoryHelperImpl
    implements CountryDynamicFormPageableSearchRepositoryHelper {

  private CountryDynamicFormDao countryDynamicFormDao;
  private CountryDynamicFormConverter countryDynamicFormConverter;

  @Inject
  public CountryDynamicFormPageableSearchRepositoryHelperImpl(
      CountryDynamicFormDao countryDynamicFormDao,
      CountryDynamicFormConverter countryDynamicFormConverter) {
    this.countryDynamicFormDao = countryDynamicFormDao;
    this.countryDynamicFormConverter = countryDynamicFormConverter;
  }

  @Override
  public AuditableEntityConverter<CountryDynamicFormEntity, CountryDynamicFormInfo> getConverter() {
    return countryDynamicFormConverter;
  }

  @Override
  public AbstractRepository<CountryDynamicFormEntity, StringIdentifier> getEntityDao() {
    return countryDynamicFormDao;
  }

  @Override
  public PageableFilterCriteria prepareFilterCriteriaWithSearchParamsOnly(
      CountryDynamicFormSearchRequest pageableSearchRequest) {
    CountryDynamicFormFilter filter= new CountryDynamicFormFilter();
    filter.setCountryIso3(pageableSearchRequest.getCountryIso3());
    filter.setType(pageableSearchRequest.getType());
    filter.setName(pageableSearchRequest.getName());

    return filter;
  }

  @Override
  public void setSortByCriteria(
      PageableFilterCriteria filterCriteria, CountryDynamicFormSearchRequest searchRequest) {
    try {
      filterCriteria.setSortBy(
          CountryDynamicFormDatabaseMapper.getByCode(searchRequest.getSortBy()).getFieldNameInDatabase());
    } catch (IllegalArgumentException e) {
      filterCriteria.setSortBy(CountryDynamicFormDatabaseMapper.COUNTRY_ISO3.getFieldNameInDatabase());
    }
  }
}
