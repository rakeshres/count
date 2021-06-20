package global.citytech.remitpulse.countries.repositories.domains.country;

import global.citytech.remitpulse.countries.repositories.auditlog.dto.WithKey;
import global.citytech.remitpulse.countries.repositories.domains.services.config.Config;
import global.citytech.remitpulse.countries.repositories.domains.services.corridor.Corridor;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.Currency;
import global.citytech.remitpulse.countries.repositories.domains.services.master.OperationType;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.remitpulse.countries.repositories.domains.services.paymentmethod.PaymentMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** @author bipin on 6/19/19 1:59 PM. */
public class CountryInfo implements ServiceObject, WithKey {
  private String id;
  private String name;
  private String numericCode;
  private String iso2;
  private String iso3;
  private Boolean autoApproveEnabled;
  private List<Currency> currencyList;
  private List<OperationType> operationTypeList;
  private Boolean active;
  private List<String> operationTypesDescription;
  private List<String> iso3List;
  private List<Corridor> corridorList;
  private List<PaymentMethod> paymentMethodList;
  private Config configs;
  private MasterDataConfiguration masterDataConfiguration;

  public Config getConfigs() {
    return configs;
  }

  public void setConfigs(Config configs) {
    this.configs = configs;
  }

  public List<PaymentMethod> getPaymentMethodList() {
    return paymentMethodList;
  }

  public void setPaymentMethodList(List<PaymentMethod> paymentMethodList) {
    this.paymentMethodList = paymentMethodList;
  }

  public List<Corridor> getCorridorList() {
    return corridorList;
  }

  public void setCorridorList(List<Corridor> corridorList) {
    this.corridorList = corridorList;
  }

  public Boolean getAutoApproveEnabled() {
    return autoApproveEnabled;
  }

  public void setAutoApproveEnabled(Boolean autoApproveEnabled) {
    this.autoApproveEnabled = autoApproveEnabled;
  }

  public List<String> getIso3List() {
    return iso3List;
  }

  public void setIso3List(List<String> iso3List) {
    this.iso3List = iso3List;
  }

  public List<String> getOperationTypesDescription() {
    return operationTypesDescription;
  }

  public void setOperationTypesDescription(List<String> operationTypesDescription) {
    this.operationTypesDescription = operationTypesDescription;
  }

  public CountryInfo() {
    this.currencyList = new ArrayList<>();
    this.operationTypeList = new ArrayList<>();
    this.operationTypesDescription = new ArrayList<>();
    this.corridorList = new ArrayList<>();
    this.paymentMethodList = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
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

  public MasterDataConfiguration getMasterDataConfiguration() {
    return masterDataConfiguration;
  }

  public void setMasterDataConfiguration(MasterDataConfiguration masterDataConfiguration) {
    this.masterDataConfiguration = masterDataConfiguration;
  }

  @Override
  public String toString() {
    return "CountryInfo{"
        + "name='"
        + name
        + '\''
        + ", numericCode="
        + numericCode
        + ", iso2='"
        + iso2
        + '\''
        + ", iso3='"
        + iso3
        + '\''
        + ", operationTypeList="
        + operationTypeList
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CountryInfo that = (CountryInfo) o;
    return Objects.equals(id, that.id)
        && Objects.equals(name, that.name)
        && Objects.equals(numericCode, that.numericCode)
        && Objects.equals(iso2, that.iso2)
        && Objects.equals(iso3, that.iso3)
        && Objects.equals(currencyList, that.currencyList)
        && Objects.equals(operationTypeList, that.operationTypeList)
        && Objects.equals(active, that.active)
        && Objects.equals(operationTypesDescription, that.operationTypesDescription)
        && Objects.equals(corridorList, that.corridorList)
        && Objects.equals(paymentMethodList, that.paymentMethodList)
        && Objects.equals(configs, that.corridorList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        name,
        numericCode,
        iso2,
        iso3,
        currencyList,
        operationTypeList,
        active,
        operationTypesDescription,
        corridorList,
        paymentMethodList,
            configs);
  }

  @Override
  public String getKey() {
    return id;
  }
}
