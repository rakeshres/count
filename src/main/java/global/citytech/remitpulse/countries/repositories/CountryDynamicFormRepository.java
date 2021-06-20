package global.citytech.remitpulse.countries.repositories;

import global.citytech.remitpulse.countries.crudapi.impl.BaseCRUDRepository;
import global.citytech.remitpulse.countries.crudapi.impl.CRUDRepositoryResourceProvider;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;
import global.citytech.remitpulse.countries.repositories.serviceobjects.CountryDynamicFormSearchRequest;

/** @author bipin on 2020-03-04 14:51 */
public interface CountryDynamicFormRepository
    extends BaseCRUDRepository<
    CountryDynamicFormEntity,
    CountryDynamicFormInfo,
    CountryDynamicFormSearchRequest,
    CountryDynamicFormSearchRequest> {

  CountryDynamicFormRepositoryResourceProvider getCountryDynamicFormRepositoryResourceProvider();

  @Override
  default CRUDRepositoryResourceProvider<
      CountryDynamicFormEntity,
      CountryDynamicFormInfo,
      CountryDynamicFormSearchRequest,
      CountryDynamicFormSearchRequest>
      getResourceProvider() {
    return getCountryDynamicFormRepositoryResourceProvider();
  }

}
