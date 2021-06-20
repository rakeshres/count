package global.citytech.remitpulse.countries.repositories.converters;

import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.Currency;
import global.citytech.remitpulse.countries.repositories.domains.services.master.OperationType;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryEntity;

import java.util.List;

/** @author bipin on 2020-03-06 13:41 */
public class CountryConverter implements AuditableEntityConverter<CountryEntity, CountryInfo> {
  @Override
  public CountryEntity toEntity(CountryInfo countryInfo) {
    return null;
  }

  @Override
  public CountryInfo toServiceObject(CountryEntity countryEntity) {
    List<Currency> currencies = Jsons.fromJsonToList(countryEntity.getCurrencies(), Currency[].class);
    CountryInfo countryInfo = new CountryInfo();
    countryInfo.setId(countryEntity.getId());
    countryInfo.setName(countryEntity.getName());
    countryInfo.setNumericCode(countryEntity.getNumericCode());
    countryInfo.setIso3(countryEntity.getIso3());
    countryInfo.setIso2(countryEntity.getIso2());
    countryInfo.setActive(countryEntity.isActive());

    List<OperationType> operationTypes =
        Jsons.fromJsonToList(countryEntity.getOperationTypes(), OperationType[].class);
    countryInfo.setOperationTypeList(operationTypes);
    countryInfo.setCurrencyList(currencies);
    return countryInfo;
  }
}
