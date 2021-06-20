package global.citytech.remitpulse.countries.repositories.internal.service;

import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;

/** @author bipin on 6/19/19 2:28 PM. */
public interface CountryValidatorService {
  void validateBasicInformation(CountryInfo request);

  void validateCurrencyInformation(CountryInfo request);

  void validateCorridorInformation(CountryInfo request);

  void validatePaymentMethods(CountryInfo request);

  void validateConfigs(CountryInfo request);
}
