package global.citytech.remitpulse.countries.repositories.domains.services.master;

/**
 * @author bipin on 6/19/19 5:48 PM.
 */
public class OperationType {
    private String code;
    private String effectiveFrom;
    private String effectiveTo;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(String effectiveTo) {
        this.effectiveTo = effectiveTo;
    }
}
