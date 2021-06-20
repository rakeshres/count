package global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains;

import java.util.ArrayList;
import java.util.List;

/** @author sunil */
public class ModuleConfigurationSearchRequest {

  private String code;
  private String moduleIdentifier;
  private Boolean autoApproveEnabled;
  private List<String> moduleIdentifierList;

  public ModuleConfigurationSearchRequest() {
    moduleIdentifierList = new ArrayList<>();
  }

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

  public List<String> getModuleIdentifierList() {
    return moduleIdentifierList;
  }

  public void setModuleIdentifierList(List<String> moduleIdentifierList) {
    this.moduleIdentifierList = moduleIdentifierList;
  }
}
