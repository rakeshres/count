package global.citytech.remitpulse.countries.commons.constants;

import global.citytech.rabbit.core.search.SearchCondition;
import global.citytech.remitpulse.countries.commons.domains.auditlog.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sankalpa on 5/6/20
 */
public enum CountryAuditField implements Field {
  NAME("name", "Country Name", "name", "String", SearchFilterCondition.searchConditionsForString()),
  REQUEST_ACTION(
            "action",
                "Request",
                "action",
                "dropdown",
                true,
            SearchFilterCondition.searchConditionsForDropDown(),
  forRequestAction()),
  STATUS(
            "status",
                "Status",
                "status",
                "dropdown",
                true,
            SearchFilterCondition.searchConditionsForDropDown(),
  forStatus()),
  REQUESTED_BY(
            "requestedBy",
                "Requested By",
                "requested_by",
                "String",
            SearchFilterCondition.searchConditionsForString()),
  REQUESTED_ON(
            "requestedOn",
                "Requested On",
                "requested_on",
                "Date",
            SearchFilterCondition.searchConditionsForDate()),
  RESPONDED_BY(
            "respondedBy",
                "Verified By",
                "responded_by",
                "String",
            SearchFilterCondition.searchConditionsForString()),
  RESPONDED_ON(
            "respondedOn",
                "Verified On",
                "responded_on",
                "Date",
            SearchFilterCondition.searchConditionsForDate());

  private final String code;
  private final String displayName;
  private final String columnName;
  private final boolean defaultActive;
  private final String dataType;
  private final List<DropDownData> dropDowndatas;
  private List<SearchCondition> conditions;
  private boolean isCustomizable;

  CountryAuditField(
      String code,
      String displayName,
      String columnName,
      String dataType,
      List<SearchCondition> conditions) {
    this.code = code;
    this.displayName = displayName;
    this.columnName = columnName;
    this.defaultActive = false;
    this.dataType = dataType;
    this.conditions = conditions;
    this.dropDowndatas = new ArrayList<>();
  }

  CountryAuditField(
      String code,
      String displayName,
      String columnName,
      String dataType,
      boolean defaultActive,
      List<SearchCondition> conditions,
      List<DropDownData> dropDowndatas) {
    this.code = code;
    this.displayName = displayName;
    this.columnName = columnName;
    this.dataType = dataType;
    this.defaultActive = defaultActive;
    this.conditions = conditions;
    this.dropDowndatas = dropDowndatas;
  }

  public static CountryAuditField getByCode(String code) {
    for (CountryAuditField field : CountryAuditField.values()) {
      if (field.getCode().equalsIgnoreCase(code)) {
        return field;
      }
    }
    return CountryAuditField.REQUESTED_ON;
  }

  public static List<DropDownData> forRequestAction() {
    return getRequestActionForDropDown();
  }

  static List<DropDownData> getRequestActionForDropDown() {
    List<DropDownData> dropDownDatas = new ArrayList<>();
    dropDownDatas.add(new DropDownData("ADD", "Add"));
    dropDownDatas.add(new DropDownData("UPDATE", "Update"));
    dropDownDatas.sort(Comparator.comparing(DropDownData::getTitle, String::compareToIgnoreCase));
    return dropDownDatas;
  }

  public static List<DropDownData> forStatus() {
    return getStatusForDropDown();
  }

  static List<DropDownData> getStatusForDropDown() {
    List<DropDownData> recordStatus = new ArrayList<>();
    recordStatus.add(new DropDownData("APPROVED", "Approved"));
    recordStatus.add(new DropDownData("PENDING", "Pending"));
    recordStatus.add(new DropDownData("REJECTED", "Rejected"));
    recordStatus.sort(Comparator.comparing(DropDownData::getTitle, String::compareToIgnoreCase));
    return recordStatus;
  }

  public String getCode() {
    return code;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getColumnName() {
    return columnName;
  }

  public boolean isDefaultActive() {
    return defaultActive;
  }

  public String getDataType() {
    return dataType;
  }

  @Override
  public List<Field> getFields() {
    return Arrays.stream(CountryAuditField.values()).collect(Collectors.toList());
  }

  public List<DropDownData> getDropDowndatas() {
    return dropDowndatas;
  }

  public List<SearchCondition> getConditions() {
    return conditions;
  }

  public void setConditions(List<SearchCondition> conditions) {
    this.conditions = conditions;
  }

  public boolean isCustomizable() {
    return isCustomizable;
  }

  public void setCustomizable(boolean customizable) {
    isCustomizable = customizable;
  }


}
