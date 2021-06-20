package global.citytech.remitpulse.countries.repositories.impl;

import global.citytech.rabbit.core.commons.Validator;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.PageableSearchRequest;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.SearchRequest;
import global.citytech.remitpulse.countries.crudapi.impl.helper.*;
import global.citytech.remitpulse.countries.repositories.CountryDynamicFormRepositoryResourceProvider;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.impl.helpers.*;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;
import global.citytech.remitpulse.countries.repositories.serviceobjects.CountryDynamicFormSearchRequest;
import global.citytech.remitpulse.countries.repositories.validators.CountryDynamicFormAddValidator;
import global.citytech.remitpulse.countries.repositories.validators.CountryDynamicFormUpdateValidator;

import javax.inject.Inject;

/** @author bipin on 2020-03-04 14:58 */
public class CountryDynamicFormRepositoryResourceProviderImpl
    implements CountryDynamicFormRepositoryResourceProvider {

  private CountryDynamicFormAddRepositoryHelper countryDynamicFormAddRepositoryHelper;
  private CountryDynamicFormPageableSearchRepositoryHelper
      countryDynamicFormPageableSearchRepositoryHelper;
  private CountryDynamicFormAddValidator countryDynamicFormAddValidator;
  private CountryDynamicFormSearchAllRepositoryHelper countryDynamicFormSearchAllRepositoryHelper;
  private CountryDynamicFormUpdateValidator countryDynamicFormUpdateValidator;
  private CountryDynamicFormViewDetailsRepositoryHelper
      countryDynamicFormViewDetailsRepositoryHelper;
  private CountryDynamicFormUpdateRepositoryHelper countryDynamicFormUpdateRepositoryHelper;

  @Inject
  public CountryDynamicFormRepositoryResourceProviderImpl(
      CountryDynamicFormAddRepositoryHelper countryDynamicFormAddRepositoryHelper,
      CountryDynamicFormPageableSearchRepositoryHelper
          countryDynamicFormPageableSearchRepositoryHelper,
      CountryDynamicFormAddValidator countryDynamicFormAddValidator,
      CountryDynamicFormSearchAllRepositoryHelper countryDynamicFormSearchAllRepositoryHelper,
      CountryDynamicFormUpdateValidator countryDynamicFormUpdateValidator,
      CountryDynamicFormViewDetailsRepositoryHelper countryDynamicFormViewDetailsRepositoryHelper,
      CountryDynamicFormUpdateRepositoryHelper countryDynamicFormUpdateRepositoryHelper) {
    this.countryDynamicFormAddRepositoryHelper = countryDynamicFormAddRepositoryHelper;
    this.countryDynamicFormPageableSearchRepositoryHelper =
        countryDynamicFormPageableSearchRepositoryHelper;
    this.countryDynamicFormAddValidator = countryDynamicFormAddValidator;
    this.countryDynamicFormSearchAllRepositoryHelper = countryDynamicFormSearchAllRepositoryHelper;
    this.countryDynamicFormUpdateValidator = countryDynamicFormUpdateValidator;
    this.countryDynamicFormViewDetailsRepositoryHelper =
        countryDynamicFormViewDetailsRepositoryHelper;
    this.countryDynamicFormUpdateRepositoryHelper = countryDynamicFormUpdateRepositoryHelper;
  }

  @Override
  public Validator<CountryDynamicFormInfo> getAddValidator() {
    return this.countryDynamicFormAddValidator;
  }

  @Override
  public BaseAddRepositoryHelper<CountryDynamicFormEntity, CountryDynamicFormInfo>
      getAddRepositoryHelper() {
    return countryDynamicFormAddRepositoryHelper;
  }

  @Override
  public Validator<CountryDynamicFormInfo> getViewDetailsValidator() {
    return countryDynamicFormInfo -> {};
  }

  @Override
  public BaseViewDetailsRepositoryHelper<CountryDynamicFormEntity, CountryDynamicFormInfo>
      getViewDetailsRepositoryHelper() {
    return countryDynamicFormViewDetailsRepositoryHelper;
  }

  @Override
  public Validator<CountryDynamicFormInfo> getUpdateValidator() {
    return countryDynamicFormUpdateValidator;
  }

  @Override
  public BaseUpdateRepositoryHelper<CountryDynamicFormEntity, CountryDynamicFormInfo>
      getUpdateRepositoryHelper() {
    return countryDynamicFormUpdateRepositoryHelper;
  }

  @Override
  public Validator<PageableSearchRequest> getSearchValidator() {
    return countryDynamicFormInfo -> {};
  }

  @Override
  public BasePageableSearchRepositoryHelper<
          CountryDynamicFormEntity, CountryDynamicFormInfo, CountryDynamicFormSearchRequest>
      getSearchRepositoryHelper() {
    return countryDynamicFormPageableSearchRepositoryHelper;
  }

  @Override
  public Validator<SearchRequest> getSearchAllValidator() {
    return countryDynamicFormInfo -> {};
  }

  @Override
  public BaseSearchAllRepositoryHelper<
          CountryDynamicFormEntity, CountryDynamicFormInfo, CountryDynamicFormSearchRequest>
      getSearchAllRepositoryHelper() {
    return countryDynamicFormSearchAllRepositoryHelper;
  }

  @Override
  public Validator<SearchRequest> getSearchAllKeyNameValidator() {
    return null;
  }

  @Override
  public BaseSearchAllKeyNameRepositoryHelper<
          CountryDynamicFormEntity, CountryDynamicFormInfo, CountryDynamicFormSearchRequest>
      getSearchAllKeyNameRepositoryHelper() {
    return null;
  }
}
