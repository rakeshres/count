package global.citytech.remitpulse.countries.repositories.domains.services.config;

/** @author roslina */
public class Occupation {
  private String code;
  private boolean isActive;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public boolean getActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
