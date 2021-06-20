package global.citytech.remitpulse.countries.repositories.internal.dto.country;

import global.citytech.remitpulse.countries.repositories.domains.services.config.Config;
import global.citytech.remitpulse.countries.repositories.domains.services.corridor.Corridor;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.Currency;
import global.citytech.remitpulse.countries.repositories.domains.services.master.OperationType;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.remitpulse.countries.repositories.domains.services.paymentmethod.PaymentMethod;

import java.util.List;

/** @author bipin on 2019-10-16 09:48. */
public class CountryInfoDto implements ServiceObject {
  private String id;
  private String name;
  private String numericCode;
  private String iso2;
  private String iso3;
  private Boolean active;
  private List<Currency> currencyList;
  private List<OperationType> operationTypeList;
  private List<Corridor> corridorList;
  private List<PaymentMethod> paymentMethodList;
  private Config configs;

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNumericCode() {
    return numericCode;
  }

  public void setNumericCode(String numericCode) {
    this.numericCode = numericCode;
  }

  public String getIso2() {
    return iso2;
  }

  public void setIso2(String iso2) {
    this.iso2 = iso2;
  }

  public String getIso3() {
    return iso3;
  }

  public void setIso3(String iso3) {
    this.iso3 = iso3;
  }

  public List<Currency> getCurrencyList() {
    return currencyList;
  }

  public void setCurrencyList(List<Currency> currencyList) {
    this.currencyList = currencyList;
  }

  public List<OperationType> getOperationTypeList() {
    return operationTypeList;
  }

  public void setOperationTypeList(List<OperationType> operationTypeList) {
    this.operationTypeList = operationTypeList;
  }

  public List<Corridor> getCorridorList() {
    return corridorList;
  }

  public void setCorridorList(List<Corridor> corridorList) {
    this.corridorList = corridorList;
  }

  public List<PaymentMethod> getPaymentMethodList() {
    return paymentMethodList;
  }

  public void setPaymentMethodList(List<PaymentMethod> paymentMethodList) {
    this.paymentMethodList = paymentMethodList;
  }

  public Config getConfigs() {
    return configs;
  }

  public void setConfigs(Config configs) {
    this.configs = configs;
  }
}
