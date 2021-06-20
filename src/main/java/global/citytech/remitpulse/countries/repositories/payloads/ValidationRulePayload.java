package global.citytech.remitpulse.countries.repositories.payloads;

/** @author raju.dhital@citytech.global on 8/19/19 4:44 PM project custom-fields */
public class ValidationRulePayload {
  private String rule;
  private String message;
  private String value;

  public ValidationRulePayload() {}

  public ValidationRulePayload(String rule, String message, String value) {
    this.rule = rule;
    this.message = message;
    this.value = value;
  }

  public ValidationRulePayload(String rule) {
    this.rule = rule;
  }

  public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
