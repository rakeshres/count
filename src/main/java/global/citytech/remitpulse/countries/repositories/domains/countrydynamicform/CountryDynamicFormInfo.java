package global.citytech.remitpulse.countries.repositories.domains.countrydynamicform;

import global.citytech.rabbit.core.commons.ServiceObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bipin on 2020-03-04 14:47
 */
public class CountryDynamicFormInfo implements ServiceObject {
  private String id;
  private String name;
  private String countryIso3;
  private String type;
  private String effectiveFrom;
  private String effectiveTo;
  private List<FieldInfo> fieldInfoList;

  public CountryDynamicFormInfo() {
    fieldInfoList= new ArrayList<>();
  }

  private Boolean autoApproveEnabled;

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

  public Boolean getAutoApproveEnabled() {
    return autoApproveEnabled;
  }

  public void setAutoApproveEnabled(Boolean autoApproveEnabled) {
    this.autoApproveEnabled = autoApproveEnabled;
  }

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

  public List<FieldInfo> getFieldInfoList() {
    return fieldInfoList;
  }

  public void setFieldInfoList(List<FieldInfo> fieldInfoList) {
    this.fieldInfoList = fieldInfoList;
  }
}
