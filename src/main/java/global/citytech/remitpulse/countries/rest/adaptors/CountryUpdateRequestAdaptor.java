package global.citytech.remitpulse.countries.rest.adaptors;

import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;

import javax.json.JsonObject;

/** @author bipin on 6/26/19 3:55 PM. */
public class CountryUpdateRequestAdaptor implements CountryDataAdaptor {
  @Override
  public CountryInfo toServiceObject(JsonObject jsonObject) {
    CountryInfo info = this.convertCommonData(jsonObject);
    info.setId(jsonObject.getString("id", ""));
    return info;
  }
}
