package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto;

import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.PageableSearchRequest;

import java.util.ArrayList;
import java.util.List;

/** @author sunil */
public class AuditLogSearchRequest implements ServiceObject, PageableSearchRequest {
  private String id;
  private String entity;
  private String entityKey;
  private List<String> statusList;

  private String sortBy;
  private String sortParameter;
  private Integer pageNumber;
  private Integer pageSize;

  public AuditLogSearchRequest() {
    statusList = new ArrayList<>();
    pageNumber = 0;
    pageSize = 0;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public List<String> getStatusList() {
    return statusList;
  }

  public void setStatusList(List<String> statusList) {
    this.statusList = statusList;
  }

  public String getEntityKey() {
    return entityKey;
  }

  public void setEntityKey(String entityKey) {
    this.entityKey = entityKey;
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

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
