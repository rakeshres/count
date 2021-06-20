package global.citytech.remitpulse.countries.repositories.internal.dao.country;

import global.citytech.rabbit.core.commons.AuditableEntity;

/** @author roslina */
public class CountryView extends AuditableEntity {
  private String id;
  private String name;
  private String numericCode;
  private String iso2;
  private String iso3;
  private String operationTypes;
  private String currencies;
  private String corridors;
  private String paymentMethods;

  private String countryIso3Code;
  private String idTypes;
  private String purposeOfRemittances;
  private String sourceOfFunds;
  private String relationships;
  private String occupations;

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

  public String getOperationTypes() {
    return operationTypes;
  }

  public void setOperationTypes(String operationTypes) {
    this.operationTypes = operationTypes;
  }

  public String getCurrencies() {
    return currencies;
  }

  public void setCurrencies(String currencies) {
    this.currencies = currencies;
  }

  public String getCorridors() {
    return corridors;
  }

  public void setCorridors(String corridors) {
    this.corridors = corridors;
  }

  public String getPaymentMethods() {
    return paymentMethods;
  }

  public void setPaymentMethods(String paymentMethods) {
    this.paymentMethods = paymentMethods;
  }

  public String getCountryIso3Code() {
    return countryIso3Code;
  }

  public void setCountryIso3Code(String countryIso3Code) {
    this.countryIso3Code = countryIso3Code;
  }

  public String getIdTypes() {
    return idTypes;
  }

  public void setIdTypes(String idTypes) {
    this.idTypes = idTypes;
  }

  public String getPurposeOfRemittances() {
    return purposeOfRemittances;
  }

  public void setPurposeOfRemittances(String purposeOfRemittances) {
    this.purposeOfRemittances = purposeOfRemittances;
  }

  public String getSourceOfFunds() {
    return sourceOfFunds;
  }

  public void setSourceOfFunds(String sourceOfFunds) {
    this.sourceOfFunds = sourceOfFunds;
  }

  public String getRelationships() {
    return relationships;
  }

  public void setRelationships(String relationships) {
    this.relationships = relationships;
  }

  public String getOccupations() {
    return occupations;
  }

  public void setOccupations(String occupations) {
    this.occupations = occupations;
  }
}
