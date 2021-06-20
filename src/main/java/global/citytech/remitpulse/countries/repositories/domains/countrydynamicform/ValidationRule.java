package global.citytech.remitpulse.countries.repositories.domains.countrydynamicform;

/** @author raju.dhital@citytech.global on 8/20/19 6:21 PM project custom-fields */
public class ValidationRule {

  private String rule;
  private String value;
  private String message;

  public ValidationRule() {}

  public ValidationRule(String rule, String value) {
    this.rule = rule;
    this.value = value;
  }

  public ValidationRule(String rule) {
    this.rule = rule;
  }

  public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
