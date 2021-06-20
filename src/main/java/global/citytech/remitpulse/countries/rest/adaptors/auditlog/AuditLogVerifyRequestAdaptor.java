package global.citytech.remitpulse.countries.rest.adaptors.auditlog;

import global.citytech.rabbit.core.commons.RequestAdaptor;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogVerifyRequest;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.json.JsonObject;

/** @author sunil */
@Named
@Dependent
public class AuditLogVerifyRequestAdaptor implements RequestAdaptor<AuditLogVerifyRequest> {
  @Override
  public AuditLogVerifyRequest toServiceObject(JsonObject jsonObject) {
    AuditLogVerifyRequest auditLogVerifyRequest = new AuditLogVerifyRequest();

    auditLogVerifyRequest.setRemarks(jsonObject.getString("remarks", ""));
    return auditLogVerifyRequest;
  }
}
