package global.citytech.remitpulse.countries.repositories.internal.filters;

import global.citytech.remitpulse.countries.commons.domains.pageable.PageableFilterCriteria;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryEntity;
import global.citytech.rabbit.core.commons.FilterCriteria;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author bipin on 6/5/19 2:28 PM.
 */
public class AuditLogFilter implements PageableFilterCriteria {
    private String id;
    private CountryEntity countryEntity;
    private CountryDynamicFormEntity countryDynamicFormEntity;
    private String entity;
    private String entityKey;
    private String status;
    private String newData;
    private List<String> statusList;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer startingIndex;
    private String sortParameter;
    private String sortBy;
    private String searchParameter;
    private String filterType;


    public AuditLogFilter() {
        this.statusList= new ArrayList<String>();
        countryEntity = new CountryEntity();
        countryDynamicFormEntity= new CountryDynamicFormEntity();
    }

  public String getFilterType() {
    return filterType;
  }

  public void setFilterType(String filterType) {
    this.filterType = filterType;
  }

  public CountryDynamicFormEntity getCountryDynamicFormEntity() {
    return countryDynamicFormEntity;
  }

  public void setCountryDynamicFormEntity(CountryDynamicFormEntity countryDynamicFormEntity) {
    this.countryDynamicFormEntity = countryDynamicFormEntity;
  }

  public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public void setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }

    public String getEntityKey() {
        return entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getStatusList() {
        return statusList;
    }

    public void setStatusList(List statusList) {
        this.statusList = statusList;
    }

    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


  @Override
  public Integer getPageNumber() {
    return pageNumber;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {
    this.pageNumber= pageNumber;
  }

  @Override
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }


  @Override
  public void setPageSize(Integer pageSize) {
    this.pageSize= pageSize;
  }

  public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartingIndex() {
        return startingIndex;
    }

  @Override
  public void setStartingIndex(Integer startingIndex) {
this.startingIndex= startingIndex;
  }

  public void setStartingIndex(int startingIndex) {
        this.startingIndex = startingIndex;
    }

    public String getSortParameter() {
        return sortParameter;
    }

    public void setSortParameter(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSearchParameter() {
        return searchParameter;
    }

    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }

    @Override
    public String toString() {
        return "AuditLogFilter{" +
                ", entity='" + entity + '\'' +
                ", status='" + status + '\'' +
                ", newData='" + newData + '\'' +
                ", statusList=" + statusList +
                '}';
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuditLogFilter that = (AuditLogFilter) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(countryEntity, that.countryEntity) &&
        Objects.equals(countryDynamicFormEntity, that.countryDynamicFormEntity) &&
        Objects.equals(entity, that.entity) &&
        Objects.equals(entityKey, that.entityKey) &&
        Objects.equals(status, that.status) &&
        Objects.equals(newData, that.newData) &&
        Objects.equals(statusList, that.statusList) &&
        Objects.equals(pageNumber, that.pageNumber) &&
        Objects.equals(pageSize, that.pageSize) &&
        Objects.equals(startingIndex, that.startingIndex) &&
        Objects.equals(sortParameter, that.sortParameter) &&
        Objects.equals(sortBy, that.sortBy) &&
        Objects.equals(searchParameter, that.searchParameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, countryEntity,countryDynamicFormEntity, entity, entityKey, status, newData, statusList, pageNumber, pageSize, startingIndex, sortParameter, sortBy, searchParameter);
  }
}
