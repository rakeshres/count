package global.citytech.remitpulse.countries.rest.adaptors.auditlog;

import global.citytech.rabbit.core.audits.AuditLogInfo;

import javax.json.JsonObject;

/**
 * @author sujan.koju@citytech.global 22/07/19
 */
public class JsonAdapter {

  private JsonAdapter() {
  }

  public static AuditLogInfo toAuditLogInfo(JsonObject object) {
    AuditLogInfo auditLogInfo = new AuditLogInfo();
    auditLogInfo.setId(object.getString("id", ""));
    auditLogInfo.setResponseRemarks(object.getString("remarks", "").toUpperCase());
    return auditLogInfo;
  }
}
