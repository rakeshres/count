package global.citytech.remitpulse.countries.repositories.internal.dto;

import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author bipin on 2020-03-06 13:32
 */
@Named
@RequestScoped
public class CountryDynamicFormLetter {
  private CountryInfo countryInfo;

  public CountryInfo getCountryInfo() {
    return countryInfo;
  }

  public void setCountryInfo(CountryInfo countryInfo) {
    this.countryInfo = countryInfo;
  }

  public CountryDynamicFormLetter() {
  }

  public CountryDynamicFormLetter(CountryInfo countryInfo) {
    this.countryInfo = countryInfo;
  }
}
