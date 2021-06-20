package global.citytech.remitpulse.countries.repositories.domains.countrydynamicform;

import global.citytech.rabbit.core.dynamic.DynamicType;

import java.util.ArrayList;
import java.util.List;

/** @author raju.dhital@citytech.global on 8/19/19 4:48 PM project custom-fields */
public class FieldInfo {

  private String id;  //----
  private String fieldId; //--
  private DynamicType type;//---
  private boolean required;//---
  private boolean active; //--
  private boolean readOnly;
  private Object defaultValue;//---
  private boolean searchable;
  private List<ValidationRule> validationRules;//--
  private List<SelectableValue> selectableValues;//--
  private String dataSourceId;
  private String renderType;//---
  private String dateType;//--
  private boolean modifiable;//---
  private String version;//---
  private String moduleIdentifier;//---

  public FieldInfo() {
    this.validationRules = new ArrayList<>();
    this.selectableValues = new ArrayList<>();
  }

  public String getFieldId() {
    return fieldId;
  }

  public void setFieldId(String fieldId) {
    this.fieldId = fieldId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DynamicType getType() {
    return type;
  }

  public void setType(DynamicType type) {
    this.type = type;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public Object getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }

  public boolean isSearchable() {
    return searchable;
  }

  public void setSearchable(boolean searchable) {
    this.searchable = searchable;
  }

  public List<ValidationRule> getValidationRules() {
    return validationRules;
  }

  public void setValidationRules(List<ValidationRule> validationRules) {
    this.validationRules = validationRules;
  }

  public List<SelectableValue> getSelectableValues() {
    return selectableValues;
  }

  public void setSelectableValues(List<SelectableValue> selectableValues) {
    this.selectableValues = selectableValues;
  }

  public String getDataSourceId() {
    return dataSourceId;
  }

  public void setDataSourceId(String dataSourceId) {
    this.dataSourceId = dataSourceId;
  }

  public String getRenderType() {
    return renderType;
  }

  public void setRenderType(String renderType) {
    this.renderType = renderType;
  }

  public String getDateType() {
    return dateType;
  }

  public void setDateType(String dateType) {
    this.dateType = dateType;
  }

  public boolean isModifiable() {
    return modifiable;
  }

  public void setModifiable(boolean modifiable) {
    this.modifiable = modifiable;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getModuleIdentifier() {
    return moduleIdentifier;
  }

  public void setModuleIdentifier(String moduleIdentifier) {
    this.moduleIdentifier = moduleIdentifier;
  }

}
