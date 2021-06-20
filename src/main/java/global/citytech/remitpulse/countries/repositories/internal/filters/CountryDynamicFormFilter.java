package global.citytech.remitpulse.countries.repositories.internal.filters;

import global.citytech.remitpulse.countries.commons.domains.pageable.BasePageableFilterCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author bipin on 2020-03-05 15:17
 */
public class CountryDynamicFormFilter  extends BasePageableFilterCriteria {
  private String id;
  private String name;
  private List<String> idList;
  private String countryIso3;
  private String type;
  private String activeDate;
  private String effectiveFrom;
  private String effectiveTo;
  private String filterType;
  public enum FilterType{
    DATE_OVERLAPPED
  }

  public CountryDynamicFormFilter() {
    this.idList= new ArrayList<>();
  }

  public String getActiveDate() {
    return activeDate;
  }

  public void setActiveDate(String activeDate) {
    this.activeDate = activeDate;
  }

  public List<String> getIdList() {
    return idList;
  }

  public void setIdList(List<String> idList) {
    this.idList = idList;
  }

  public String getFilterType() {
    return filterType;
  }

  public void setFilterType(String filterType) {
    this.filterType = filterType;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getEffectiveFrom() {
    return effectiveFrom;
  }

  public void setEffectiveFrom(String effectiveFrom) {
    this.effectiveFrom = effectiveFrom;
  }

  public String getEffectiveTo() {
    return effectiveTo;
  }

  public void setEffectiveTo(String effectiveTo) {
    this.effectiveTo = effectiveTo;
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


  public String getCountryIso3() {
    return countryIso3;
  }

  public void setCountryIso3(String countryIso3) {
    this.countryIso3 = countryIso3;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CountryDynamicFormFilter that = (CountryDynamicFormFilter) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(idList, that.idList) &&
        Objects.equals(countryIso3, that.countryIso3) &&
        Objects.equals(type, that.type) &&
        Objects.equals(effectiveFrom, that.effectiveFrom) &&
        Objects.equals(effectiveTo, that.effectiveTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, idList, countryIso3, type, effectiveFrom, effectiveTo);
  }
}
