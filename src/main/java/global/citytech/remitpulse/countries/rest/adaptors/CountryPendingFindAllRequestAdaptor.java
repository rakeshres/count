package global.citytech.remitpulse.countries.rest.adaptors;

import global.citytech.remitpulse.countries.repositories.internal.auditlog.CountryPendingInfoSearchRequest;
import global.citytech.rabbit.core.commons.RequestAdaptor;

import javax.json.JsonObject;

/**
 * @author bipin on 6/24/19 11:46 AM.
 */
public class CountryPendingFindAllRequestAdaptor implements RequestAdaptor<CountryPendingInfoSearchRequest> {

    @Override
    public CountryPendingInfoSearchRequest toServiceObject(JsonObject jsonObject) {
        CountryPendingInfoSearchRequest request= new CountryPendingInfoSearchRequest();
        request.setEntityKey(jsonObject.getString("entityKey",""));
        request.setSearchParameter(jsonObject.getString("searchParameter",""));
        request.setSortBy(jsonObject.getString("sortBy",""));
        request.setSortParameter(jsonObject.getString("sortParameter",""));
        request.setPageNumber(jsonObject.getInt("pageNumber",0));
        request.setPageSize(jsonObject.getInt("pageSize",0));
        return request;
    }
}
