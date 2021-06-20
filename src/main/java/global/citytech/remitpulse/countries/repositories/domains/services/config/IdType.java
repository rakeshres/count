package global.citytech.remitpulse.countries.repositories.domains.services.config;

/** @author roslina */
public class IdType {
  private String code;
  private Boolean hasIssueDate;
  private Boolean hasExpiryDate;
  private String format;
  private Boolean isActive;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Boolean getHasIssueDate() {
    return hasIssueDate;
  }

  public void setHasIssueDate(Boolean hasIssueDate) {
    this.hasIssueDate = hasIssueDate;
  }

  public Boolean getHasExpiryDate() {
    return hasExpiryDate;
  }

  public void setHasExpiryDate(Boolean hasExpiryDate) {
    this.hasExpiryDate = hasExpiryDate;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }
}
