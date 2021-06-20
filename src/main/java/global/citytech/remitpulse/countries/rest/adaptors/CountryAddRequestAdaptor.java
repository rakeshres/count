package global.citytech.remitpulse.countries.rest.adaptors;


import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;

import javax.json.JsonObject;


/** @author bipin on 6/21/19 6:16 PM. */
public class CountryAddRequestAdaptor implements CountryDataAdaptor {
    @Override
    public CountryInfo toServiceObject(JsonObject jsonObject) {
        return this.convertCommonData(jsonObject);
    }
}
