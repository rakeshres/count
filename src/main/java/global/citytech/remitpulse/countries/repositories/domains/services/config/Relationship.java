package global.citytech.remitpulse.countries.repositories.domains.services.config;

/**
 * @author roslina
 */
public class Relationship {
    private String code;
    private Boolean isActive;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
