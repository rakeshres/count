package global.citytech.remitpulse.countries.commons.domains.auditlog;

import global.citytech.remitpulse.countries.commons.constants.CountryAuditField;
import global.citytech.remitpulse.countries.commons.constants.SearchColumn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sankalpa on 5/6/20
 */
public class FieldAggregator {

  private FieldAggregator() {
  }

  public static List<SearchColumn> getSearchColumnForCountryAudit() {
    return prepareColumns(Arrays.stream(CountryAuditField.values()).collect(Collectors.toList()));
  }

  private static List<SearchColumn> prepareColumns(List<Field> fields) {
    List<SearchColumn> searchColumns = new ArrayList<>();
    fields.forEach(field -> searchColumns.add(
        new SearchColumn(
            field.getCode(),
            field.getDisplayName(),
            field.getDataType(),
            field.isDefaultActive(),
            field.getConditions(),
            field.getDropDowndatas(),
            false)));
    searchColumns.sort(Comparator.comparing(SearchColumn::getTitle, String::compareToIgnoreCase));
    return searchColumns;
  }
}
