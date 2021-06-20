package global.citytech.remitpulse.countries.rest.adaptors.auditlog;

import global.citytech.remitpulse.countries.commons.domains.auditlog.DynamicAuditSearchCriteria;

import javax.json.JsonObject;

/**
 * @author sankalpa on 5/6/20
 */
public class CountryModelJsonAdapter {
  private CountryModelJsonAdapter() {
  }

  public static DynamicAuditSearchCriteria toDynamicAuditSearchRequest(JsonObject jsonObject) {
    DynamicAuditRequestAdaptor dynamicAuditRequestAdaptor = new DynamicAuditRequestAdaptor();
    return dynamicAuditRequestAdaptor.prepareDynamicReportRequestModel(jsonObject);
  }
}
