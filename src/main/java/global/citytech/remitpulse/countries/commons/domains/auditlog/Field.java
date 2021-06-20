package global.citytech.remitpulse.countries.commons.domains.auditlog;

import global.citytech.rabbit.core.search.SearchCondition;
import global.citytech.remitpulse.countries.commons.constants.DropDownData;

import java.util.List;

/**
 * @author sankalpa on 5/6/20
 */
public interface Field {
  String getCode();

  String getDisplayName();

  boolean isDefaultActive();

  List<SearchCondition> getConditions();

  List<DropDownData> getDropDowndatas();

  boolean isCustomizable();

  String getDataType();

  List<Field> getFields();
}
