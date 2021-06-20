package global.citytech.remitpulse.countries.repositories.constants;

/**
 * @author bipin on 2020-03-09 12:00
 */
public enum CountryDynamicFormLengthConstants {
  NAME_MAX_LENGTH(50),

  ;

  private int length;

  public int getLength() {
    return length;
  }

  CountryDynamicFormLengthConstants(int length) {
    this.length = length;
  }
}
