package global.citytech.remitpulse.countries.commons.domains;

/** @author Sanish Maharjan on 2019-08-08 */
public enum FilterOperator {
  AND("AND", "AND"),
  NONE("NONE", "");

  private final String code;
  private final String displayName;

  private FilterOperator(String code, String displayName) {
    this.code = code;
    this.displayName = displayName;
  }

  public static FilterOperator getByName(String name) {
    FilterOperator[] var1 = values();
    int var2 = var1.length;

    for (int var3 = 0; var3 < var2; ++var3) {
      FilterOperator operator = var1[var3];
      if (operator.name().equals(name)) {
        return operator;
      }
    }

    return AND;
  }

  public static FilterOperator getByDisplayName(String code) {
    FilterOperator[] var1 = values();
    int var2 = var1.length;

    for (int var3 = 0; var3 < var2; ++var3) {
      FilterOperator operator = var1[var3];
      if (operator.name().equals(code)) {
        return operator;
      }
    }

    return AND;
  }

  public static FilterOperator getByCode(String code) {
    FilterOperator[] var1 = values();
    int var2 = var1.length;

    for (int var3 = 0; var3 < var2; ++var3) {
      FilterOperator operator = var1[var3];
      if (operator.code.equals(code)) {
        return operator;
      }
    }

    return AND;
  }

  public String getCode() {
    return this.code;
  }

  public String getDisplayName() {
    return this.displayName;
  }
}
