package global.citytech.remitpulse.countries.repositories.internal.filters;

import global.citytech.rabbit.core.commons.FilterCriteria;

/**
 * @author sankalpa on 4/12/21
 */
public class LocationFilter implements FilterCriteria {
    private String category;
    private String parentCode;
    private String countryCode;
    private boolean active;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
