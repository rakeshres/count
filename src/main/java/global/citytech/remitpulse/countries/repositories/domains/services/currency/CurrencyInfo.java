package global.citytech.remitpulse.countries.repositories.domains.services.currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** @author bipin on 6/21/19 2:41 PM. */
public class CurrencyInfo {
  private String isoAlpha;
  private String currencyName;
  private List<String> isoAlphaList;
  private int decimalValue;

  public int getDecimalValue() {
    return decimalValue;
  }

  public void setDecimalValue(int decimalValue) {
    this.decimalValue = decimalValue;
  }

  public CurrencyInfo() {
    this.isoAlphaList = new ArrayList<>();
  }

  public List<String> getIsoAlphaList() {
    return isoAlphaList;
  }

  public void setIsoAlphaList(List<String> isoAlphaList) {
    this.isoAlphaList = isoAlphaList;
  }

  public String getCurrencyName() {
    return currencyName;
  }

  public void setCurrencyName(String name) {
    this.currencyName = name;
  }

  public String getIsoAlpha() {
    return isoAlpha;
  }

  public void setIsoAlpha(String isoAlpha) {
    this.isoAlpha = isoAlpha;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CurrencyInfo that = (CurrencyInfo) o;
    return Objects.equals(isoAlpha, that.isoAlpha)
        && Objects.equals(currencyName, that.currencyName)
        && Objects.equals(isoAlphaList, that.isoAlphaList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isoAlpha, currencyName, isoAlphaList);
  }
}
