package global.citytech.remitpulse.countries.commons.constants;

/**
 * @author sankalpa on 5/6/20
 */
public class DropDownData {
  private final String code;
  private final String title;

  public DropDownData(String code, String title) {
    this.code = code;
    this.title = title;
  }

  public String getCode() {
    return code;
  }

  public String getTitle() {
    return title;
  }
}
