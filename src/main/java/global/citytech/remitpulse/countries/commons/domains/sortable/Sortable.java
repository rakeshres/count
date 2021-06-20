package global.citytech.remitpulse.countries.commons.domains.sortable;

/** @author sunil */
public interface Sortable {
  void setSortBy(String sortBy);

  String getSortBy();

  void setSortParameter(String sortParameter);

  String getSortParameter();
}
