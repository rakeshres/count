package global.citytech.remitpulse.countries.repositories.constants;

import global.citytech.rabbit.core.dynamic.DynamicType;

/** @author sanish on 6/20/19 */
public class FieldConstant {
  public static final int REJECT_REMARKS_LENGTH = 250;
  public static final String FIELD = "FIELD";
  public static final String MESSAGE = "message";
  public static final String FALSE = "false";

  public static final int MINIMUM_LENGTH_FOR_RULE_MIN_MAX_LENGTH = 0;
  public static final int MAXIMUM_LENGTH_FOR_RULE_MIN_MAX_LENGTH = 999;

  public static final int MINIMUM_LENGTH_FOR_RULE_MAX_MAX_VALUE = 0;
  public static final int MAXIMUM_LENGTH_FOR_RULE_MAX_MAX_VALUE = 99999999;

  public static final int MINIMUM_LENGTH_FOR_RULE_ROUND_OFF_AFTER = 0;
  public static final int MAXIMUM_LENGTH_FOR_RULE_ROUND_OFF_AFTER = 4;

  public static final int MAXIMUM_LENGTH_FIELD_LABEL = 50;

  private FieldConstant() {}

  public static DataType getDataType(String rule) {
    for (DataType dataType : DataType.values()) {
      if (dataType.getCode().equalsIgnoreCase(rule)) return dataType;
    }
    throw new IllegalArgumentException("NO DATA TYPE FOUND FOR :: " + rule);
  }

  public enum DataType {
    NUMERIC("Numeric", "number", DynamicType.String),
    TEXT("Text", "text", DynamicType.String),
    DECIMAL("Decimal", "decimal", DynamicType.Numeric),
    MULTI_VALUE("Multi Value", "multiValue", DynamicType.Selectable),
    DATE("Date", "date", DynamicType.Date),
    BOOLEAN("Boolean", "boolean", DynamicType.Boolean),
    ;

    private String displayName;
    private String code;
    private DynamicType dynamicType;

    DataType(String displayName, String code, DynamicType dynamicType) {
      this.displayName = displayName;
      this.code = code;
      this.dynamicType = dynamicType;
    }

    public String getDisplayName() {
      return displayName;
    }

    public String getCode() {
      return code;
    }

    public DynamicType getDynamicType() {
      return dynamicType;
    }
  }

  public enum ValidationRule {
    MIN_LENGTH("minLength", "min"),
    MAX_LENGTH("maxLength", "max"),
    MIN_VALUE("minValue", "min"),
    MAX_VALUE("maxValue", "max"),
    REGEX("regex", "pattern"),
    FUTURE_ALLOWED("futureAllowed", "futureAllowed"),
    PAST_ALLOWED("pastAllowed", "pastAllowed"),
    ROUND_OFF_AFTER("roundOffAfter", "roundOffAfter"),
    DATE_FORMAT("dateFormat", "dateFormat"),
    DATE_TYPE("dateType", "dateType"),
    ;

    private String rule;
    private String code;

    ValidationRule(String rule, String code) {
      this.rule = rule;
      this.code = code;
    }

    public static String getCodeByRule(String rule) {
      for (ValidationRule validationRule : ValidationRule.values()) {
        if (validationRule.getRule().equalsIgnoreCase(rule)) return validationRule.getCode();
      }
      throw new IllegalArgumentException("NO CODE FOUND FOR RULE:: " + rule);
    }

    public String getRule() {
      return rule;
    }

    public String getCode() {
      return code;
    }
  }

  public enum Action {
    ADD,
    UPDATE;
  }
}
