package global.citytech.remitpulse.countries.repositories.impl;

import global.citytech.remitpulse.countries.repositories.CountryDynamicFormRepository;
import global.citytech.remitpulse.countries.repositories.CountryDynamicFormRepositoryResourceProvider;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** @author bipin on 2020-03-04 14:56 */
@Named
@Dependent
public class CountryDynamicFormRepositoryImpl implements CountryDynamicFormRepository {

  private CountryDynamicFormRepositoryResourceProvider countryDynamicFormRepositoryResourceProvider;

  @Inject
  public CountryDynamicFormRepositoryImpl(
      CountryDynamicFormRepositoryResourceProvider countryDynamicFormRepositoryResourceProvider) {
    this.countryDynamicFormRepositoryResourceProvider = countryDynamicFormRepositoryResourceProvider;
  }

  @Override
  public CountryDynamicFormRepositoryResourceProvider getCountryDynamicFormRepositoryResourceProvider() {
    return countryDynamicFormRepositoryResourceProvider;
  }
}
