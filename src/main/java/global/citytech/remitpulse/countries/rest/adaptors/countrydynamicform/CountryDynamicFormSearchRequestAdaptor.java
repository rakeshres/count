package global.citytech.remitpulse.countries.rest.adaptors.countrydynamicform;

import global.citytech.rabbit.core.commons.RequestAdaptor;
import global.citytech.remitpulse.countries.repositories.serviceobjects.CountryDynamicFormSearchRequest;
import global.citytech.remitpulse.countries.rest.adaptors.AdaptorCommon;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.json.JsonObject;

/** @author bipin on 2020-03-05 15:33 */
@Named
@Dependent
public class CountryDynamicFormSearchRequestAdaptor
    implements RequestAdaptor<CountryDynamicFormSearchRequest>, AdaptorCommon {
  @Override
  public CountryDynamicFormSearchRequest toServiceObject(JsonObject jsonObject) {
    CountryDynamicFormSearchRequest request = new CountryDynamicFormSearchRequest();
    request.setCountryIso3(jsonObject.getString("countryIso3", ""));
    request.setType(jsonObject.getString("type", ""));
    if (jsonObject.containsKey("idList")) {
      request.setIdList(getStringListByKey(jsonObject, "idList"));
    }
    request.setName(jsonObject.getString("name", ""));
    request.setActiveDate(jsonObject.getString("activeDate", ""));

    setPageableFields(request, jsonObject);
    return request;
  }
}
