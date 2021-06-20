package global.citytech.remitpulse.countries.repositories.internal.filters;


import global.citytech.remitpulse.countries.commons.domains.auditlog.AuditReportModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author raju.dhital@citytech.global on 8/6/19 10:50 AM project finpulse-departments
 */
public class AuditReportFilterCriteria {
  private String agency;
  private List<String> entities;
  private LocalDateTime from;
  private List<String> lists;
  private int pageNumber;
  private int pageSize;
  private List<AuditReportModel> reportModels;
  private String sortField;
  private String sortOrder;
  private long startingIndex;
  private LocalDateTime to;

  public AuditReportFilterCriteria() {
    this.reportModels = new ArrayList<>();
    this.lists = new ArrayList<>();
    this.entities = new ArrayList<>();
  }

  public String getAgency() {
    return agency;
  }

  public void setAgency(String agency) {
    this.agency = agency;
  }

  public List<String> getEntities() {
    return entities;
  }

  public void setEntities(List<String> entities) {
    this.entities = entities;
  }

  public LocalDateTime getFrom() {
    return from;
  }

  public void setFrom(LocalDateTime from) {
    this.from = from;
  }

  public List<String> getLists() {
    return lists;
  }

  public void setLists(List<String> lists) {
    this.lists = lists;
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

  public List<AuditReportModel> getReportModels() {
    return reportModels;
  }

  public void setReportModels(List<AuditReportModel> reportModels) {
    this.reportModels = reportModels;
  }

  public String getSortField() {
    return sortField;
  }

  public void setSortField(String sortField) {
    this.sortField = sortField;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public long getStartingIndex() {
    this.startingIndex = this.pageSize * (this.pageNumber - 1);
    return this.startingIndex;
  }

  public void setStartingIndex(long startingIndex) {
    this.startingIndex = startingIndex;
  }

  public LocalDateTime getTo() {
    return to;
  }

  public void setTo(LocalDateTime to) {
    this.to = to;
  }
}
