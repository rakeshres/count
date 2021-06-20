package global.citytech.remitpulse.countries.rest.adaptors;

import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.commons.RequestAdaptor;

import javax.json.JsonObject;

/**
 * @author sankalpa on 1/13/20
 */
public class CountryVerifyRequestAdaptor implements RequestAdaptor<AuditLogInfo> {
    @Override
    public AuditLogInfo toServiceObject(JsonObject jsonObject) {
        AuditLogInfo auditLogInfo = new AuditLogInfo();
        auditLogInfo.setResponseRemarks(jsonObject.getString("remarks",""));
        return auditLogInfo;
    }
}
