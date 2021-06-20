package global.citytech.remitpulse.countries.rest.adaptors.auditlog;

import global.citytech.rabbit.core.commons.RequestAdaptor;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogSearchRequest;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.rest.adaptors.AdaptorCommon;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.json.JsonObject;

/** @author sunil */
@Named
@Dependent
public class AuditLogSearchRequestAdaptor
    implements RequestAdaptor<AuditLogSearchRequest>, AdaptorCommon {
  @Override
  public AuditLogSearchRequest toServiceObject(JsonObject jsonObject) {
    AuditLogSearchRequest searchRequest = new AuditLogSearchRequest();

    searchRequest.setEntity(jsonObject.getString("entity", ""));
    searchRequest.setEntityKey(jsonObject.getString("entityKey", ""));

    searchRequest.setStatusList(HelperUtilsLocal.getStringListByKey(jsonObject, "statusList"));

    setPageableFields(searchRequest, jsonObject);

    return searchRequest;
  }
}
