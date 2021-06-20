package global.citytech.remitpulse.countries.commons.domains.pageable;

/** @author sunil */
public interface Pageable {
  Integer getPageNumber();

  void setPageNumber(Integer pageNumber);

  Integer getPageSize();

  void setPageSize(Integer pageSize);
}
