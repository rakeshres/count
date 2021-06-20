package global.citytech.remitpulse.countries.repositories.domains.country;

/**
 * @author sankalpa on 4/12/21
 */
public class GetLocationsRequest {
    private String parentCode;
    private String categoryCode;
    private String countryCode;

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getCategory() {
        return categoryCode;
    }

    public void setCategory(String category) {
        this.categoryCode = category;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
