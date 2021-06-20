package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto;

import global.citytech.rabbit.core.commons.ServiceObject;

/** @author sunil */
public class AuditLogVerifyRequest implements ServiceObject {

  private String id;
  private String remarks;
  private ActionToPerform actionToPerform;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public ActionToPerform getActionToPerform() {
    return actionToPerform;
  }

  public void setActionToPerform(ActionToPerform actionToPerform) {
    this.actionToPerform = actionToPerform;
  }

  public enum ActionToPerform {
    APPROVE,
    REJECT
  }
}
