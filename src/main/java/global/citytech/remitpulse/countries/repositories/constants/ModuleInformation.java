package global.citytech.remitpulse.countries.repositories.constants;


import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;

/** @author raju.dhital@citytech.global on 8/21/19 1:19 PM project custom-fields */
public enum ModuleInformation {
  KYC("kyc", "KYC"),
  TRANSACTION("transaction", "Transaction");
  private String code;
  private String name;

  ModuleInformation(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static ModuleInformation getByCode(String code) {
    ModuleInformation[] dataTypes = ModuleInformation.values();
    for (ModuleInformation dataType : dataTypes) {
      if (dataType.getCode().equalsIgnoreCase(code)) {
        return dataType;
      }
    }
    ExceptionManager.throwCustomFieldException("Invalid module name");
    return null;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
}
