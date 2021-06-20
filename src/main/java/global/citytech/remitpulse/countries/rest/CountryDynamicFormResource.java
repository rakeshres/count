package global.citytech.remitpulse.countries.rest;

import global.citytech.rabbit.core.commons.RequestAdaptor;
import global.citytech.remitpulse.countries.crudapi.CRUDRepository;
import global.citytech.remitpulse.countries.repositories.CountryDynamicFormRepository;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.serviceobjects.CountryDynamicFormSearchRequest;
import global.citytech.remitpulse.countries.rest.adaptors.countrydynamicform.CountryDynamicFormAddRequestAdaptor;
import global.citytech.remitpulse.countries.rest.adaptors.countrydynamicform.CountryDynamicFormSearchRequestAdaptor;
import global.citytech.remitpulse.countries.rest.adaptors.countrydynamicform.CountryDynamicFormUpdateRequestAdaptor;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Path;

/** @author bipin on 2020-03-04 16:28 */
@Named
@Dependent
@Path("dynamic-form")
public class CountryDynamicFormResource
    extends CommonResource<
        CountryDynamicFormInfo, CountryDynamicFormSearchRequest, CountryDynamicFormSearchRequest> {
  private CountryDynamicFormAddRequestAdaptor countryDynamicFormAddRequestAdaptor;
  private CountryDynamicFormRepository countryDynamicFormRepository;
  private CountryDynamicFormSearchRequestAdaptor countryDynamicFormSearchRequestAdaptor;
  private CountryDynamicFormUpdateRequestAdaptor countryDynamicFormUpdateRequestAdaptor;

  @Inject
  public CountryDynamicFormResource(
      CountryDynamicFormAddRequestAdaptor countryDynamicFormAddRequestAdaptor,
      CountryDynamicFormRepository countryDynamicFormRepository,
      CountryDynamicFormSearchRequestAdaptor countryDynamicFormSearchRequestAdaptor,
      CountryDynamicFormUpdateRequestAdaptor countryDynamicFormUpdateRequestAdaptor) {
    this.countryDynamicFormAddRequestAdaptor = countryDynamicFormAddRequestAdaptor;
    this.countryDynamicFormRepository = countryDynamicFormRepository;
    this.countryDynamicFormSearchRequestAdaptor = countryDynamicFormSearchRequestAdaptor;
    this.countryDynamicFormUpdateRequestAdaptor = countryDynamicFormUpdateRequestAdaptor;
  }

  @Override
  protected CRUDRepository<
          CountryDynamicFormInfo, CountryDynamicFormSearchRequest, CountryDynamicFormSearchRequest>
      getCRUDRepository() {
    return countryDynamicFormRepository;
  }

  @Override
  protected RequestAdaptor<CountryDynamicFormInfo> getAddRequestAdaptor() {
    return this.countryDynamicFormAddRequestAdaptor;
  }

  @Override
  protected RequestAdaptor<CountryDynamicFormInfo> getUpdateRequestAdaptor() {
    return this.countryDynamicFormUpdateRequestAdaptor;
  }

  @Override
  protected void setPrimaryKey(CountryDynamicFormInfo serviceObject, String code) {
    serviceObject.setId(code);
  }

  @Override
  protected CountryDynamicFormInfo getServiceObjectWithPrimaryKey(String code) {
    CountryDynamicFormInfo countryDynamicFormInfo = new CountryDynamicFormInfo();
    countryDynamicFormInfo.setId(code);
    return countryDynamicFormInfo;
  }

  @Override
  protected RequestAdaptor<CountryDynamicFormSearchRequest> getPageableSearchRequestAdaptor() {
    return countryDynamicFormSearchRequestAdaptor;
  }

  @Override
  protected RequestAdaptor<CountryDynamicFormSearchRequest> getSearchRequestAdaptor() {
    return countryDynamicFormSearchRequestAdaptor;
  }
}
