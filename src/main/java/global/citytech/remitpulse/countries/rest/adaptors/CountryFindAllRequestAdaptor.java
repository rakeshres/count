package global.citytech.remitpulse.countries.rest.adaptors;

import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfoSearchRequest;
import global.citytech.rabbit.core.commons.RequestAdaptor;

import javax.json.JsonObject;

/** @author bipin on 6/25/19 2:16 PM. */
public class CountryFindAllRequestAdaptor implements RequestAdaptor<CountryInfoSearchRequest> {
  @Override
  public CountryInfoSearchRequest toServiceObject(JsonObject jsonObject) {
    CountryInfoSearchRequest request = new CountryInfoSearchRequest();
    request.setSortBy(jsonObject.getString("sortBy", ""));
    request.setSortParameter(jsonObject.getString("sortParameter", ""));
    request.setIso3Parameter(jsonObject.getString("iso3Parameter", ""));
    request.setNameParameter(jsonObject.getString("nameParameter", ""));
    if (jsonObject.containsKey("active") && !jsonObject.isNull("active")) {
      request.setActive(jsonObject.getBoolean("active"));
    }
    request.setNumericCodeParameter(jsonObject.getString("numericCodeParameter", ""));
    request.setPageNumber(jsonObject.getInt("pageNumber", 0));
    request.setPageSize(jsonObject.getInt("pageSize", 0));
    return request;
  }
}
