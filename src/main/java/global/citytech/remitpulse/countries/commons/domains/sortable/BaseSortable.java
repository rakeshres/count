package global.citytech.remitpulse.countries.commons.domains.sortable;

/** @author sunil */
public class BaseSortable implements Sortable {
  private String sortBy;
  private String sortParameter;

  @Override
  public String getSortBy() {
    return sortBy;
  }

  @Override
  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  @Override
  public String getSortParameter() {
    return sortParameter;
  }

  @Override
  public void setSortParameter(String sortParameter) {
    this.sortParameter = sortParameter;
  }
}
