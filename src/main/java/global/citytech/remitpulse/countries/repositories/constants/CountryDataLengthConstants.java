package global.citytech.remitpulse.countries.repositories.constants;

/** @author bipin on 6/19/19 3:07 PM. */
public enum CountryDataLengthConstants {
  COUNTRY_NAME_MIN_LENGTH(4),
  COUNTRY_NAME_MAX_LENGTH(50),
    ISO_2_VALID_LENGTH(2),
    ISO_3_VALID_LENGTH(3),
  NUMERIC_CODE_LENGTH(3),
  SANCTION_FILTER_DECIMAL_VALUE_MAX_LENGTH(2),
  ;

  private final int value;

  CountryDataLengthConstants(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
