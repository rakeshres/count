package global.citytech.remitpulse.countries.commons.constants;

/**
 * @author bipin on 7/4/19 11:37 AM.
 */
public enum MasterType {
    OPERATION_TYPE("OT"),
    PAYMENT_CHANNEL("PM"),
    DYNAMIC_FORM_TYPE("DFT"),
    OCCUPATION("OC"),
    RELATIONSHIP("RE"),
    ID("ID"),
    REMITTANCE_REASON("RR"),
    SOURCE_OF_FUND("IS")

;
    private String code;

    public String getCode() {
        return code;
    }

    MasterType(String code){
        this.code=code;
    }
}
