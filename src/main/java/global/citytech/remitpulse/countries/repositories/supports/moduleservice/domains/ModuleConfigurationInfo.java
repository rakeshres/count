package global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains;

import global.citytech.rabbit.core.commons.ServiceObject;

/** @author sunil */
public class ModuleConfigurationInfo implements ServiceObject {

  private String code;
  private String moduleIdentifier;
  private Boolean autoApproveEnabled;
  private Boolean active;
  private String createdBy;
  private String createdOn;
  private String lastModifiedBy;
  private String lastModifiedOn;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getModuleIdentifier() {
    return moduleIdentifier;
  }

  public void setModuleIdentifier(String moduleIdentifier) {
    this.moduleIdentifier = moduleIdentifier;
  }

  public Boolean getAutoApproveEnabled() {
    return autoApproveEnabled;
  }

  public void setAutoApproveEnabled(Boolean autoApproveEnabled) {
    this.autoApproveEnabled = autoApproveEnabled;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public String getLastModifiedOn() {
    return lastModifiedOn;
  }

  public void setLastModifiedOn(String lastModifiedOn) {
    this.lastModifiedOn = lastModifiedOn;
  }
}
