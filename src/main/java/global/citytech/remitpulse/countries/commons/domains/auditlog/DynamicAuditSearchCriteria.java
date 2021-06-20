package global.citytech.remitpulse.countries.commons.domains.auditlog;

import java.util.List;

/** @author Sanish Maharjan on 2019-08-08 */
public class DynamicAuditSearchCriteria {
  private String id;
  private String name;
  private int pageSize;
  private int pageNumber;
  private String fromDate;
  private String toDate;
  private List<AuditReportModel> reportModels;
  private String sortField;
  private String sortOrder;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public String getFromDate() {
    return fromDate;
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getToDate() {
    return toDate;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
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
}
