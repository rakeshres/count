package global.citytech.remitpulse.countries.repositories.impl.helpers.impl;

import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.StringIdentifier;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.commons.domains.sortable.SortableFilterCriteria;
import global.citytech.remitpulse.countries.repositories.constants.CountryDynamicFormDatabaseMapper;
import global.citytech.remitpulse.countries.repositories.converters.CountryDynamicFormConverter;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.impl.helpers.CountryDynamicFormSearchAllRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryDynamicFormFilter;
import global.citytech.remitpulse.countries.repositories.serviceobjects.CountryDynamicFormSearchRequest;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** @author bipin on 2020-03-12 14:17 */
@Named
@Dependent
public class CountryDynamicFormSearchAllRepositoryHelperImpl
    implements CountryDynamicFormSearchAllRepositoryHelper {

  private CountryDynamicFormConverter countryDynamicFormConverter;
  private CountryDynamicFormDao countryDynamicFormDao;

  @Inject
  public CountryDynamicFormSearchAllRepositoryHelperImpl(
      CountryDynamicFormConverter countryDynamicFormConverter,
      CountryDynamicFormDao countryDynamicFormDao) {
    this.countryDynamicFormConverter = countryDynamicFormConverter;
    this.countryDynamicFormDao = countryDynamicFormDao;
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
  public SortableFilterCriteria prepareFilterCriteriaWithSearchParamsOnly(
      CountryDynamicFormSearchRequest searchRequest) {
    CountryDynamicFormFilter filter= new CountryDynamicFormFilter();
    filter.setIdList(searchRequest.getIdList());
    filter.setCountryIso3(searchRequest.getCountryIso3());
    filter.setType(searchRequest.getType());
    filter.setActiveDate(searchRequest.getActiveDate());
    filter.setName(searchRequest.getName());
    return filter;
  }

  @Override
  public void setSortByCriteria(
      SortableFilterCriteria filterCriteria, CountryDynamicFormSearchRequest searchRequest) {
    try{
      filterCriteria.setSortBy(
          CountryDynamicFormDatabaseMapper.getByCode(searchRequest.getSortBy()).getFieldNameInDatabase()
      );
    }  catch (IllegalArgumentException e){
      filterCriteria.setSortBy(CountryDynamicFormDatabaseMapper.NAME.getFieldNameInDatabase());
    }
  }
}
