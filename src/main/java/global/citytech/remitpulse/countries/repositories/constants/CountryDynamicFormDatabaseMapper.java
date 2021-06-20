package global.citytech.remitpulse.countries.repositories.constants;

/**
 * @author bipin on 2020-03-05 15:30
 */
public enum  CountryDynamicFormDatabaseMapper {
  COUNTRY_ISO3("country_iso3","countryIso3"),
  NAME("name","name"),
  TYPE("type","type")


    ;

  private String columnName;
  private String fieldName;

  CountryDynamicFormDatabaseMapper(String columnName, String fieldName) {
    this.columnName = columnName;
    this.fieldName = fieldName;
  }

  public String getFieldNameInDatabase() {
    return this.columnName;
  }

  public String getFieldName() {
    return this.fieldName;
  }

  public static CountryDynamicFormDatabaseMapper getByCode(String fieldName) {
    for (CountryDynamicFormDatabaseMapper v : values())
      if (v.getFieldName().equalsIgnoreCase(fieldName)) return v;
    throw new IllegalArgumentException();
  }
}
