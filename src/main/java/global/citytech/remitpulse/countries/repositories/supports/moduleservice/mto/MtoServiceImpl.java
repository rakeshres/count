package global.citytech.remitpulse.countries.repositories.supports.moduleservice.mto;

import global.citytech.rabbit.core.rest.HttpClient;
import global.citytech.rabbit.core.rest.HttpClientRequest;
import global.citytech.rabbit.core.rest.HttpClientResponse;
import global.citytech.rabbit.core.rest.servicediscovery.Service;
import global.citytech.rabbit.core.rest.servicediscovery.ServiceDiscoveryImpl;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.BaseModuleService;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.mto.MtoBulkUpdateResponse;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.mto.MtoUpdateRequest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author sankalpa on 11/10/20
 */
public class MtoServiceImpl extends BaseModuleService implements MtoService{

    private final String TOKEN = "X-XSRF-TOKEN";

    @Context
    HttpHeaders httpHeaders;

    private static Logger logger = Logger.getLogger(MtoServiceImpl.class.getName());

    @Override
    public Optional<MtoBulkUpdateResponse> updateMtoMasterDetail(MtoUpdateRequest request) {
        logger.info("CALLING MTO MODULE "+ Jsons.toJsonObj(request));
        HttpClient httpClient = new HttpClient();
        logger.info("URL :: "+getServiceUrl()+"/v1/mtos/ms/master-detail/");
        HttpClientRequest httpClientRequest =
                this.prepareHttpClientRequest(
                        getServiceUrl()+
                                "/v1/mtos/ms/master-detail/", this.prepareHeaderMap(), Jsons.toJsonObj(request));
        Optional<HttpClientResponse> httpClientResponse = httpClient.post(httpClientRequest);

        if (httpClientResponse.isEmpty()) {
            logger.info("HTTP CLIENT RESPONSE NULL");
            return Optional.empty();
        }
        Optional<MtoBulkUpdateResponse> mtoBulkUpdateResponse = Optional.ofNullable(Jsons.fromJsonToObj(
                Jsons.toJsonObj(httpClientResponse.get().getData()), MtoBulkUpdateResponse.class));
        return mtoBulkUpdateResponse;
    }

    @Override
    protected String getModuleUrl() {
        Service service = ServiceDiscoveryImpl.getInstance().getService("mtos");
        return service.getUrl();
    }

    private Map<String, String> preparePublicHeaderMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        return headers;
    }

    private static String getServiceUrl(){
        Service service = ServiceDiscoveryImpl.getInstance().getService("mtos");
        return service.getUrl();
    }

    private String getTokenValue() {
        return Optional.of(this.httpHeaders.getHeaderString(TOKEN)).orElse("");
    }
}
