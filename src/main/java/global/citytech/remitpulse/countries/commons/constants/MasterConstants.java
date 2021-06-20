package global.citytech.remitpulse.countries.commons.constants;

/** @author bipin on 2020-03-06 11:18 */
public class MasterConstants {

  public enum DynamicFormType {
    SENDER("SENDER"),
    BENEFICIARY("BENEFICIARY"),
    TRANSACTION("TRANSACTION"),
    ;

    private String code;

    DynamicFormType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum OperationType {
    PAYOUT("PYT"),
    SEND("SND"),
    ;

    private String code;

    OperationType(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }

  public enum ConfigType {
    ID("ID"),
    SOURCE_OF_FUND("IS"),
    RELATIONSHIP("RE"),
    PURPOSE_OF_REMITTANCE("RR"),
    OCCUPATION("OC");
    private String masterType;

    ConfigType(String masterType) {
      this.masterType = masterType;
    }

    public String getMasterType() {
      return masterType;
    }
  }
}
