package global.citytech.remitpulse.countries.repositories.domains.country;

import global.citytech.rabbit.core.commons.ServiceObject;

import java.util.Objects;

/** @author bipin on 6/25/19 2:20 PM. */
public class CountryInfoSearchRequest implements ServiceObject {
  private String iso3Parameter;
  private String nameParameter;
  private String numericCodeParameter;
  private String sortBy;
  private String sortParameter;
  private int pageNumber;
  private int pageSize;
  private Boolean active;

  public String getIso3Parameter() {
    return iso3Parameter;
  }

  public void setIso3Parameter(String iso3Parameter) {
    this.iso3Parameter = iso3Parameter;
  }

  public String getNameParameter() {
    return nameParameter;
  }

  public void setNameParameter(String nameParameter) {
    this.nameParameter = nameParameter;
  }

  public String getNumericCodeParameter() {
    return numericCodeParameter;
  }

  public void setNumericCodeParameter(String numericCodeParameter) {
    this.numericCodeParameter = numericCodeParameter;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public String getSortBy() {
    return sortBy;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public String getSortParameter() {
    return sortParameter;
  }

  public void setSortParameter(String sortParameter) {
    this.sortParameter = sortParameter;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CountryInfoSearchRequest that = (CountryInfoSearchRequest) o;
    return pageNumber == that.pageNumber
        && pageSize == that.pageSize
        && Objects.equals(iso3Parameter, that.iso3Parameter)
        && Objects.equals(nameParameter, that.nameParameter)
        && Objects.equals(numericCodeParameter, that.numericCodeParameter)
        && Objects.equals(sortBy, that.sortBy)
        && Objects.equals(sortParameter, that.sortParameter)
        && Objects.equals(active, that.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        iso3Parameter,
        nameParameter,
        numericCodeParameter,
        sortBy,
        sortParameter,
        pageNumber,
        pageSize,
        active);
  }
}
