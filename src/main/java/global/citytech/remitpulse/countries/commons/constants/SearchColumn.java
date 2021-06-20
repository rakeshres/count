package global.citytech.remitpulse.countries.commons.constants;

import global.citytech.rabbit.core.search.SearchCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sankalpa on 5/6/20
 */
public class SearchColumn {
  private String code;
  private String title;
  private boolean selected;
  private String dataType;
  private List<DropDownData> dropDownDatas;
  private List<SearchCondition> conditions;
  private boolean customizable;

  public SearchColumn(
      String code,
      String title,
      String dataType,
      boolean selected,
      List<SearchCondition> conditions) {
    this.code = code;
    this.title = title;
    this.selected = selected;
    this.dataType = dataType;
    this.dropDownDatas = new ArrayList<>();
    this.conditions = conditions;
  }

  public SearchColumn(String code, String title, List<SearchCondition> conditions) {
    this.code = code;
    this.title = title;
    this.selected = false;
    this.conditions = conditions;
    this.dropDownDatas = new ArrayList<>();
  }

  public SearchColumn(
      String code,
      String title,
      String dataType,
      boolean selected,
      List<SearchCondition> conditions,
      List<DropDownData> lists,
      boolean customizable) {
    this.code = code;
    this.title = title;
    this.selected = selected;
    this.dataType = dataType;
    this.conditions = conditions;
    this.dropDownDatas = lists;
    this.customizable = customizable;
  }

  public SearchColumn(String code, String title, boolean selected) {
    this.code = code;
    this.title = title;
    this.selected = selected;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public List<DropDownData> getDropDownDatas() {
    return dropDownDatas;
  }

  public void setDropDownDatas(List<DropDownData> dropDownDatas) {
    this.dropDownDatas = dropDownDatas;
  }

  public List<SearchCondition> getConditions() {
    return conditions;
  }

  public void setConditions(List<SearchCondition> conditions) {
    this.conditions = conditions;
  }

  public boolean isCustomizable() {
    return customizable;
  }

  public void setCustomizable(boolean customizable) {
    this.customizable = customizable;
  }
}
