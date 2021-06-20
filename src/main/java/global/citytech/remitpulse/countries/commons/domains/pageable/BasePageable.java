package global.citytech.remitpulse.countries.commons.domains.pageable;


import global.citytech.remitpulse.countries.commons.domains.sortable.BaseSortable;

/** @author sunil */
public class BasePageable extends BaseSortable implements Pageable {
  private Integer pageNumber;
  private Integer pageSize;

  @Override
  public Integer getPageNumber() {
    return pageNumber;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  @Override
  public Integer getPageSize() {
    return pageSize;
  }

  @Override
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
