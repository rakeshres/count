package global.citytech.remitpulse.countries.repositories.internal.auditlog;

import global.citytech.rabbit.core.commons.ServiceObject;

import java.util.List;

/**
 * @author bipin on 6/24/19 11:08 AM.
 */
public class CountryPendingInfoSearchRequest implements ServiceObject {
    private String searchParameter;
    private String sortBy;
    private String sortParameter;
    private int pageNumber;
    private int pageSize;
    private List<String> statusList;
  private String entityKey;

  public String getEntityKey() {
    return entityKey;
  }

  public void setEntityKey(String entityKey) {
    this.entityKey = entityKey;
  }

  public List<String> getStatusList() {
    return statusList;
  }

  public void setStatusList(List<String> statusList) {
    this.statusList = statusList;
  }

  public String getSearchParameter() {
        return searchParameter;
    }

    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortParameter() {
        return sortParameter;
    }

    public void setSortParameter(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
