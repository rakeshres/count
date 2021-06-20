package global.citytech.remitpulse.countries.repositories.domains.services.customfields;

/**
 * @author bipin on 2020-03-10 13:27
 */
public class SingleFieldServiceInfo {
  private String identifier;
  private String label;

  public SingleFieldServiceInfo() {
  }

  public SingleFieldServiceInfo(String identifier) {
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
