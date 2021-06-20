package global.citytech.remitpulse.countries.repositories.domains.services.corridor;

/**
 * @author roslina
 */
public class CorridorDetail {
    private String currencyIsoAlpha;
    private String countryIso3;
    private String destinationCurrencyIsoAlpha;
    private String originCurrencyIsoAlpha;
    private Boolean isActive;

    public String getCurrencyIsoAlpha() {
        return currencyIsoAlpha;
    }

    public void setCurrencyIsoAlpha(String currencyIsoAlpha) {
        this.currencyIsoAlpha = currencyIsoAlpha;
    }

    public String getCountryIso3() {
        return countryIso3;
    }

    public void setCountryIso3(String countryIso3) {
        this.countryIso3 = countryIso3;
    }

    public String getDestinationCurrencyIsoAlpha() {
        return destinationCurrencyIsoAlpha;
    }

    public void setDestinationCurrencyIsoAlpha(String destinationCurrencyIsoAlpha) {
        this.destinationCurrencyIsoAlpha = destinationCurrencyIsoAlpha;
    }

    public String getOriginCurrencyIsoAlpha() {
        return originCurrencyIsoAlpha;
    }

    public void setOriginCurrencyIsoAlpha(String originCurrencyIsoAlpha) {
        this.originCurrencyIsoAlpha = originCurrencyIsoAlpha;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
