package global.citytech.remitpulse.countries.repositories.internal.dao.country;

import global.citytech.rabbit.core.commons.AuditableEntity;
import global.citytech.rabbit.core.commons.Entity;

import java.util.Objects;

/** @author bipin on 6/19/19 2:12 PM. */
public class CountryEntity extends AuditableEntity implements Entity {
  private String id;
  private String name;
  private String numericCode;
  private String iso2;
  private String iso3;
  private String operationTypes;
  private String currencies;
  private String corridors;
  private String paymentMethods;
  private String configs;

  public String getConfigs() {
    return configs;
  }

  public void setConfigs(String configs) {
    this.configs = configs;
  }

  public String getPaymentMethods() {
    return paymentMethods;
  }

  public void setPaymentMethods(String paymentMethods) {
    this.paymentMethods = paymentMethods;
  }

  public String getCorridors() {
    return corridors;
  }

  public void setCorridors(String corridors) {
    this.corridors = corridors;
  }

  public String getCurrencies() {
    return currencies;
  }

  public void setCurrencies(String currencies) {
    this.currencies = currencies;
  }

  public String getOperationTypes() {
    return operationTypes;
  }

  public void setOperationTypes(String operationTypes) {
    this.operationTypes = operationTypes;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CountryEntity that = (CountryEntity) o;
    return Objects.equals(id, that.id)
        && Objects.equals(name, that.name)
        && Objects.equals(numericCode, that.numericCode)
        && Objects.equals(iso2, that.iso2)
        && Objects.equals(iso3, that.iso3)
        && Objects.equals(operationTypes, that.operationTypes)
        && Objects.equals(corridors, that.corridors)
        && Objects.equals(paymentMethods, that.paymentMethods)
        && Objects.equals(configs, that.configs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, name, numericCode, iso2, iso3, operationTypes, corridors, paymentMethods, configs);
  }
}
