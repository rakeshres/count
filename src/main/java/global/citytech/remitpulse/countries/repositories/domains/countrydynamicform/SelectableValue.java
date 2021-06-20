package global.citytech.remitpulse.countries.repositories.domains.countrydynamicform;

/** @author raju.dhital@citytech.global on 8/21/19 11:23 AM project custom-fields */
public class SelectableValue {

  private String option;
  private String value;

  public SelectableValue() {
  }

  public SelectableValue(String option, String value) {
    this.option = option;
    this.value = value;
  }

  public String getOption() {
    return option;
  }

  public void setOption(String option) {
    this.option = option;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
