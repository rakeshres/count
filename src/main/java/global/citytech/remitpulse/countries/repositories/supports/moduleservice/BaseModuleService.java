package global.citytech.remitpulse.countries.repositories.supports.moduleservice;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.exceptions.RabbitExceptionType;
import global.citytech.rabbit.core.rest.HttpClient;
import global.citytech.rabbit.core.rest.HttpClientRequest;
import global.citytech.rabbit.core.rest.HttpClientResponse;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.exceptions.AppException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/** @author sunil */
public abstract class BaseModuleService {

  private static final Logger LOGGER = Logger.getLogger(BaseModuleService.class.getName());
  private final String TOKEN = "X-XSRF-TOKEN";

  @Context HttpHeaders httpHeaders;

  protected abstract String getModuleUrl();

  public String getModuleName() {
    return "";
  }

  public Map<String, String> prepareHeaderMap() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-type", "application/json");
    headers.put(TOKEN, this.getTokenValue());
    return headers;
  }

  private String getTokenValue() {
    return Optional.of(this.httpHeaders.getHeaderString(TOKEN)).orElse("");
  }

  public HttpClientRequest prepareHttpClientRequest(String url, Map headers, String body) {
    return new HttpClientRequest.Builder()
        .with(
            $ -> {
              $.url = url;
              $.headers = headers;
              $.body = body;
            })
        .build();
  }

  public HttpClientRequest prepareHttpClientRequest(String serviceUrl, Object request) {
    try {
      if (request == null) {
        return this.prepareHttpClientRequest(
            getModuleUrl() + serviceUrl, this.prepareHeaderMap(), null);
      } else {
        return this.prepareHttpClientRequest(
            getModuleUrl() + serviceUrl, this.prepareHeaderMap(), Jsons.toJsonObj(request));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw getCouldNotPrepareRequestForModuleCallException(e.getMessage());
    }
  }

  public Optional<HttpClientResponse> getHttpClientResponse(HttpClientRequest httpClientRequest) {

    try {
      return new HttpClient().post(httpClientRequest);
    } catch (Exception e) {
      e.printStackTrace();
      throw getCouldNotGetResponseFromModuleCallException(e.getMessage());
    }
  }

  public Optional<HttpClientResponse> getHttpClientResponseForGet(
      HttpClientRequest httpClientRequest) {

    try {
      return new HttpClient().get(httpClientRequest);
    } catch (Exception e) {
      e.printStackTrace();
      throw getCouldNotGetResponseFromModuleCallException(e.getMessage());
    }
  }

  private AppException getCouldNotPrepareRequestForModuleCallException(String message) {
    return prepareAppException(
        ModuleServiceErrorCode.GENERIC_REQUEST_EXCEPTION_CODE.getCode(),
        "Could not prepare request for " + getModuleName() + " module, error: " + message);
  }

  private AppException getCouldNotGetResponseFromModuleCallException(String message) {
    return prepareAppException(
        ModuleServiceErrorCode.GENERIC_RESPONSE_EXCEPTION_CODE.getCode(),
        "Could not get response from " + getModuleName() + " module, error: " + message);
  }

  public void throwIfExceptionOnResponse(HttpClientResponse response) {
    if (!"0".equalsIgnoreCase(response.getCode())) {

      LOGGER.info(
          "Error response from " + getModuleName() + " module : " + Jsons.toJsonObj(response));

      if (isErrorCodeIsAppExceptionType(response.getCode())) {
        prepareAndThrowException(response);
      }

      throw getCouldNotGetResponseFromModuleCallException(
          "[" + response.getCode() + " " + response.getMessage() + "]");
    }
  }

  private boolean isErrorCodeIsAppExceptionType(String code) {
    return !Strings.isNullOrEmpty(code) && code.length() >= 5;
  }

  private void prepareAndThrowException(HttpClientResponse response) {
    throw prepareAppException(
        response.getCode(), "[" + getModuleName() + "] " + response.getMessage());
  }

  private AppException prepareAppException(String code, String message) {
    LOGGER.info("Error code : " + code + " message : " + message);
    return new AppException(
        new RabbitExceptionType() {
          @Override
          public String getCode() {
            return code;
          }

          @Override
          public String getDescription() {
            return message;
          }
        });
  }

  public enum ModuleServiceErrorCode {
    GENERIC_RESPONSE_EXCEPTION_CODE("IRS10001"),
    GENERIC_REQUEST_EXCEPTION_CODE("IRS10002"),
    ;

    String code;

    ModuleServiceErrorCode(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }

    public static ModuleServiceErrorCode getByCode(String code) {

      Optional<ModuleServiceErrorCode> errorCode =
          Arrays.stream(ModuleServiceErrorCode.values())
              .filter(e -> e.getCode().equalsIgnoreCase(code))
              .findFirst();

      if (errorCode.isPresent()) return errorCode.get();

      throw new IllegalArgumentException(code);
    }

    public static boolean isModuleServiceErrorCode(String code) {
      try {
        ModuleServiceErrorCode.getByCode(code);
        return true;
      } catch (IllegalArgumentException e) {
        return false;
      }
    }
  }
}
