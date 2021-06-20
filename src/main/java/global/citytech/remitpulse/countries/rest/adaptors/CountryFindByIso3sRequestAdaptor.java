package global.citytech.remitpulse.countries.rest.adaptors;

import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.rabbit.core.commons.RequestAdaptor;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

/** @author bipin on 6/27/19 5:04 PM. */
public class CountryFindByIso3sRequestAdaptor implements RequestAdaptor<CountryInfo> {

  @Override
  public CountryInfo toServiceObject(JsonObject jsonObject) {
    CountryInfo countryInfo = new CountryInfo();
    List<String> iso3List = new ArrayList<>();

    JsonArray jsonValues = jsonObject.getJsonArray("iso3List");
    for (int i = 0; i < jsonValues.size(); i++) {
      iso3List.add(jsonValues.getString(i));
    }
    countryInfo.setIso3List(iso3List);

    return countryInfo;
  }
}
