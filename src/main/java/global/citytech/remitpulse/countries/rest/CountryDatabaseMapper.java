package global.citytech.remitpulse.countries.rest;

/**
 * @author bipin on 6/25/19 2:51 PM.
 */
public enum  CountryDatabaseMapper {
    ISO3("iso3","iso3"),
    ISO2("iso2","iso2"),
    NUMERIC_CODE("numericCode","numeric_code"),
    NAME("name","name");

    private String requestVariable;
    private String systemVariable;

    CountryDatabaseMapper(String requestVariable, String systemVariable) {
        this.requestVariable = requestVariable;
        this.systemVariable = systemVariable;
    }

    public String getRequestVariable(){
        return this.requestVariable;
    }

    public String getSystemVariable(){
        return this.systemVariable;

    }
}
