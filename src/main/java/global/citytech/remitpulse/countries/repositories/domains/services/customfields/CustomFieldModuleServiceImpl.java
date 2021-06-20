package global.citytech.remitpulse.countries.repositories.domains.services.customfields;

import global.citytech.rabbit.core.rest.HttpClient;
import global.citytech.rabbit.core.rest.HttpClientRequest;
import global.citytech.rabbit.core.rest.HttpClientResponse;
import global.citytech.rabbit.core.rest.servicediscovery.Service;
import global.citytech.rabbit.core.rest.servicediscovery.ServiceDiscoveryImpl;
import global.citytech.rabbit.core.utils.Jsons;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author bipin on 2020-03-06 16:20
 */
public class CustomFieldModuleServiceImpl implements CustomFieldModuleService {
  private final String TOKEN = "X-XSRF-TOKEN";


  private static Logger logger = Logger.getLogger(CustomFieldModuleServiceImpl.class.getName());

  @Context
  HttpHeaders httpHeaders;


  @Override
  public List<CustomFieldServiceInfo> getCustomFieldList(CustomFieldServiceInfo inputList) {
    Service service = ServiceDiscoveryImpl.getInstance().getService("custom-fields");
    logger.info("CALLING CURRENCY MODULE FETCH CURRENCIES "+ Jsons.toJsonObj(inputList));
    HttpClient httpClient = new HttpClient();
    logger.info("URL :: "+service.getUrl()+"/v1/custom-fields/search/ids");
    HttpClientRequest httpClientRequest =
        this.prepareHttpClientRequest(
            service.getUrl()+"/v1/custom-fields/search/ids", this.prepareHeaderMap(), Jsons.toJsonObj(inputList));

    Optional<HttpClientResponse> httpClientResponse = httpClient.post(httpClientRequest);
    if (httpClientResponse.isEmpty()) {
      logger.info("HTTP CLIENT RESPONSE NULL");
      return Collections.emptyList();
    }

    logger.info("CUSTOM FIELD DATA RESPONSE " + httpClientResponse.get().getData());
    return Jsons.fromJsonToList(
        Jsons.toJsonObj(httpClientResponse.get().getData()), CustomFieldServiceInfo[].class);

  }

  private Map<String, String> prepareHeaderMap() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-type", "application/json");
    headers.put(TOKEN, this.getTokenValue());
    return headers;
  }

  private String getTokenValue() {
    return Optional.of(this.httpHeaders.getHeaderString(TOKEN)).orElse("");
  }

  private HttpClientRequest prepareHttpClientRequest(String url, Map headers, String body) {
    return new HttpClientRequest.Builder()
        .with(
            $ -> {
              $.url = url;
              $.headers = headers;
              $.body = body;
            })
        .build();
  }
}
