package global.citytech.remitpulse.countries.repositories.domains.country;

/**
 * @author sankalpa on 9/3/20
 */
public class MasterDataInfo {
  private String code;
  private String thirdPartyCode;
  private String title;

  public MasterDataInfo() {
  }

  public MasterDataInfo(String code, String thirdPartyCode) {
    this.code = code;
    this.thirdPartyCode = thirdPartyCode;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getThirdPartyCode() {
    return thirdPartyCode;
  }

  public void setThirdPartyCode(String thirdPartyCode) {
    this.thirdPartyCode = thirdPartyCode;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
