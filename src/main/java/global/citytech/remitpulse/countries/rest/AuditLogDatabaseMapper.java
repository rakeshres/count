package global.citytech.remitpulse.countries.rest;

/** @author bipin on 6/10/19 3:07 PM. */
public enum AuditLogDatabaseMapper {
  REQUESTED_BY("requestedBy", "requested_by"),
  REQUESTED_ON("requestedOn", "requested_on"),
  RESPONDED_BY("respondedBy", "responded_by"),
  RESPONDED_ON("respondedOn", "responded_on"),
  ACTION("action", "action"),
  NAME("name", "name"),
  CODE("code", "code"),
  RESPONSE_REMARKS("responseRemarks", "response_remarks"),
  ;

  private String requestVariable;
  private String systemVariable;

  AuditLogDatabaseMapper(String requestVariable, String systemVariable) {
    this.requestVariable = requestVariable;
    this.systemVariable = systemVariable;
  }

  public String getDatabaseName() {
    return this.requestVariable;
  }

  public String getSystemVariable() {
    return this.systemVariable;
  }

  public static AuditLogDatabaseMapper getByCode(String code) {
    for (AuditLogDatabaseMapper auditLogDatabaseMapper : AuditLogDatabaseMapper.values()) {
      if (auditLogDatabaseMapper.getDatabaseName().equals(code)) {
        return auditLogDatabaseMapper;
      }
    }
    return AuditLogDatabaseMapper.REQUESTED_ON;
  }
}
