package global.citytech.remitpulse.countries.repositories.internal.dao.country.config;

import global.citytech.rabbit.core.commons.AuditableEntity;
import global.citytech.rabbit.core.commons.Entity;

import java.util.Objects;

/** @author roslina */
public class ConfigEntity extends AuditableEntity implements Entity {
  private String countryIso3Code;
  private String idTypes;
  private String purposeOfRemittances;
  private String sourceOfFunds;
  private String relationships;
  private String occupations;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ConfigEntity that = (ConfigEntity) o;
    return countryIso3Code.equals(that.countryIso3Code)
        && idTypes.equals(that.idTypes)
        && purposeOfRemittances.equals(that.purposeOfRemittances)
        && sourceOfFunds.equals(that.sourceOfFunds)
        && relationships.equals(that.relationships)
        && occupations.equals(that.occupations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        countryIso3Code, idTypes, purposeOfRemittances, sourceOfFunds, relationships, occupations);
  }
}
