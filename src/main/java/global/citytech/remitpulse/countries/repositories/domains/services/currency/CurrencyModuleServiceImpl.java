package global.citytech.remitpulse.countries.repositories.domains.services.currency;

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

/** @author bipin on 6/21/19 2:39 PM. */
public class CurrencyModuleServiceImpl implements CurrencyModuleService {

  private final String TOKEN = "X-XSRF-TOKEN";


  private static Logger logger = Logger.getLogger(CurrencyModuleServiceImpl.class.getName());

  @Context HttpHeaders httpHeaders;

  @Override
  public List<CurrencyInfo> fetchCurrencies(CurrencyInfo currencyInfo) {
    Service service = ServiceDiscoveryImpl.getInstance().getService("currencies");
    logger.info("CALLING CURRENCY MODULE FETCH CURRENCIES "+Jsons.toJsonObj(currencyInfo));
    HttpClient httpClient = new HttpClient();
    logger.info("URL :: "+service.getUrl()+"/v1/currencies/ms/search/values");
    HttpClientRequest httpClientRequest =
        this.prepareHttpClientRequest(
            service.getUrl()+"/v1/currencies/ms/search/values", this.prepareHeaderMap(), Jsons.toJsonObj(currencyInfo));

    Optional<HttpClientResponse> httpClientResponse = httpClient.post(httpClientRequest);
    if (httpClientResponse.isEmpty()) {
      logger.info("HTTP CLIENT RESPONSE NULL");
      return Collections.emptyList();
    }

    logger.info("CURRENCIES DATA RESPONSE " + httpClientResponse.get().getData());
    return Jsons.fromJsonToList(
        Jsons.toJsonObj(httpClientResponse.get().getData()), CurrencyInfo[].class);

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
