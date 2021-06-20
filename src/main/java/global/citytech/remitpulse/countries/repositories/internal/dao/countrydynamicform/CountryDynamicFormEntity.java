package global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform;

import global.citytech.rabbit.core.commons.AuditableEntity;
import global.citytech.rabbit.core.commons.Entity;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.FieldInfo;

import java.util.List;
import java.util.Objects;

/** @author bipin on 2020-03-04 14:46 */
public class CountryDynamicFormEntity extends AuditableEntity implements Entity {
  private String id;
  private String countryIso3;
  private String name;
  private String type;
  private String effectiveFrom;
  private String effectiveTo;
  private String fieldInfoList;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getFieldInfoList() {
    return fieldInfoList;
  }

  public void setFieldInfoList(String fieldInfoList) {
    this.fieldInfoList = fieldInfoList;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
    CountryDynamicFormEntity that = (CountryDynamicFormEntity) o;
    return Objects.equals(id, that.id)
        && Objects.equals(countryIso3, that.countryIso3)
        && Objects.equals(name, that.name)
        && Objects.equals(type, that.type)
        && Objects.equals(effectiveFrom, that.effectiveFrom)
        && Objects.equals(effectiveTo, that.effectiveTo)
        && Objects.equals(fieldInfoList, that.fieldInfoList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, countryIso3, name, type, effectiveFrom, effectiveTo, fieldInfoList);
  }
}
