package global.citytech.remitpulse.countries.commons.constants;

/** @author roslina */
public enum PaymentMethodChannel {
  BANK("Bank Transfer", "BNK", true),
  MOBILE_WALLET("Mobile Wallet", "MWT", true),
  CASH("Cash", "CSH", false);

  private String title;
  private String code;
  private boolean variant;

  PaymentMethodChannel(String title, String code, boolean variant) {
    this.title = title;
    this.code = code;
    this.variant = variant;
  }

  public String getTitle() {
    return title;
  }

  public String getCode() {
    return code;
  }

  public boolean getVariant() {
    return variant;
  }
}
