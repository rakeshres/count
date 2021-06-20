package global.citytech.remitpulse.countries.repositories.domains.services.master;

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

/** @author bipin on 6/19/19 4:19 PM. */
public class MasterModuleServiceImpl implements MasterModuleService {

  private final String TOKEN = "X-XSRF-TOKEN";

  @Context HttpHeaders httpHeaders;

  private static Logger logger = Logger.getLogger(MasterModuleServiceImpl.class.getName());

  @Override
  public List<Continent> getContinents() {
    logger.info("CALLING CONTINENTS ");
    HttpClient httpClient = new HttpClient();
    logger.info("URL :: "+getServiceUrl()+"/v1/masters/ms/continents");
    HttpClientRequest httpClientRequest =
        this.prepareHttpClientRequest(getServiceUrl()+"/v1/masters/ms/continents", this.prepareHeaderMap(), null);

    Optional<HttpClientResponse> httpClientResponse = httpClient.get(httpClientRequest);
    if (httpClientResponse.isEmpty()) {
      logger.info("HTTP CLIENT RESPONSE NULL");
      return Collections.emptyList();
    }
    return Jsons.fromJsonToList(
        Jsons.toJsonObj(httpClientResponse.get().getData()), Continent[].class);
  }

  private Map<String, String> prepareHeaderMap() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-type", "application/json");
    headers.put(TOKEN, this.getTokenValue());
    return headers;
  }

  private Map<String, String> preparePublicHeaderMap() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-type", "application/json");
    return headers;
  }

  private static String getServiceUrl(){
    Service service = ServiceDiscoveryImpl.getInstance().getService("master");
    return service.getUrl();
  }

  private String getTokenValue() {
    return Optional.of(this.httpHeaders.getHeaderString(TOKEN)).orElse("");
  }

  @Override
  public List<MasterInfo> fetchMasterInfo(MasterInfo masterInfo) {
    logger.info("CALLING MASTER MODULE "+Jsons.toJsonObj(masterInfo));
    HttpClient httpClient = new HttpClient();
    logger.info("URL :: "+getServiceUrl()+"/v1/masters/ms/public/search/values");
    HttpClientRequest httpClientRequest =
        this.prepareHttpClientRequest(
            getServiceUrl()+
            "/v1/masters/ms/public/search/values", this.preparePublicHeaderMap(), Jsons.toJsonObj(masterInfo));
    Optional<HttpClientResponse> httpClientResponse = httpClient.post(httpClientRequest);

    if (httpClientResponse.isEmpty()) {
      logger.info("HTTP CLIENT RESPONSE NULL");
      return Collections.emptyList();
    }

    return Jsons.fromJsonToList(
        Jsons.toJsonObj(httpClientResponse.get().getData()), MasterInfo[].class);
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
