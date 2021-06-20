package global.citytech.remitpulse.countries.commons.constants;

import global.citytech.rabbit.core.search.SearchCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sankalpa on 5/6/20
 */
public enum SearchFilterCondition {
  BETWEEN("between", "Between", "BETWEEN"),
  CONTAINS("contains", "Contains", "LIKE", "%", "%"),
  END_WITH("ew", "Ends With", "LIKE", "%", ""),
  DATE_EQUAL("dateEqual", "Equal", "date"),
  EQUAL("eq", "Equals", "="),
  GREATER_THAN("gt", "Greater Than", ">"),
  LESS_THAN("lt", "Less Than", "<"),
  NOT_EQUAL("nt", "Not Equals", "<>"),
  START_WITH("sw", "Starts With", "LIKE", "", "%");
  ;

  private final String code;
  private final String beginsWith;
  private final String symbol;
  private final String endsWith;
  private final String displayName;

  private SearchFilterCondition(String code, String displayName, String symbol) {
    this.code = code;
    this.displayName = displayName;
    this.symbol = symbol;
    this.beginsWith = "";
    this.endsWith = "";
  }

  private SearchFilterCondition(
      String code, String displayName, String symbol, String beginsWith, String endsWith) {
    this.code = code;
    this.displayName = displayName;
    this.beginsWith = beginsWith;
    this.symbol = symbol;
    this.endsWith = endsWith;
  }

  public static SearchFilterCondition getByName(String name) {
    SearchFilterCondition[] var1 = values();
    int var2 = var1.length;

    for (int var3 = 0; var3 < var2; ++var3) {
      SearchFilterCondition c = var1[var3];
      if (c.name().equals(name)) {
        return c;
      }
    }

    return EQUAL;
  }

  public static SearchFilterCondition getByCode(String code) {
    SearchFilterCondition[] var1 = values();
    int var2 = var1.length;

    for (int var3 = 0; var3 < var2; ++var3) {
      SearchFilterCondition c = var1[var3];
      if (c.getCode().equals(code)) {
        return c;
      }
    }

    return EQUAL;
  }

  public static List<SearchCondition> searchConditionsForString() {
    SearchFilterCondition[] filterConditions =
        new SearchFilterCondition[] {CONTAINS, END_WITH, EQUAL, NOT_EQUAL, START_WITH};
    List<SearchCondition> conditions = new ArrayList();
    SearchFilterCondition[] var2 = filterConditions;
    int var3 = filterConditions.length;

    for (int var4 = 0; var4 < var3; ++var4) {
      SearchFilterCondition filterCondition = var2[var4];
      conditions.add(
          new SearchCondition(filterCondition.getCode(), filterCondition.getDisplayName()));
    }
    return conditions;
  }

  public static List<SearchCondition> searchConditionsForDate() {
    SearchFilterCondition[] filterConditions = new SearchFilterCondition[] {BETWEEN, DATE_EQUAL};
    List<SearchCondition> conditions = new ArrayList();
    SearchFilterCondition[] var2 = filterConditions;
    int var3 = filterConditions.length;

    for (int var4 = 0; var4 < var3; ++var4) {
      SearchFilterCondition filterCondition = var2[var4];
      conditions.add(
          new SearchCondition(filterCondition.getCode(), filterCondition.getDisplayName()));
    }

    return conditions;
  }

  public static List<SearchCondition> searchConditionsForDropDown() {
    SearchFilterCondition[] filterConditions = new SearchFilterCondition[] {EQUAL, NOT_EQUAL};
    List<SearchCondition> conditions = new ArrayList();
    SearchFilterCondition[] var2 = filterConditions;
    int var3 = filterConditions.length;

    for (int var4 = 0; var4 < var3; ++var4) {
      SearchFilterCondition filterCondition = var2[var4];
      conditions.add(
          new SearchCondition(filterCondition.getCode(), filterCondition.getDisplayName()));
    }

    return conditions;
  }

  public String getCode() {
    return this.code;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public String getBeginsWith() {
    return this.beginsWith;
  }

  public String getEndsWith() {
    return this.endsWith;
  }

  public String getDisplayName() {
    return this.displayName;
  }
}

