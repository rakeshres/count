package global.citytech.remitpulse.countries.repositories.internal.dto.audit;

import global.citytech.remitpulse.countries.repositories.internal.dto.country.CountryInfoDto;
import global.citytech.rabbit.core.commons.ServiceObject;

/**
 * @author bipin on 2019-10-16 09:44.
 */
public class AuditLogDto implements ServiceObject {
  private String id;
  private String entity;
  private String entityKey;
  private String action;
  private String status;
  private CountryInfoDto newData;
  private String changes;
  private String requestedBy;
  private String requestedOn;
  private String respondedBy;
  private String respondedOn;
  private String requestRemarks;
  private String responseRemarks;
  private CountryInfoDto oldData;

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

  public String getEntityKey() {
    return entityKey;
  }

  public void setEntityKey(String entityKey) {
    this.entityKey = entityKey;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public CountryInfoDto getNewData() {
    return newData;
  }

  public void setNewData(CountryInfoDto newData) {
    this.newData = newData;
  }

  public String getChanges() {
    return changes;
  }

  public void setChanges(String changes) {
    this.changes = changes;
  }

  public String getRequestedBy() {
    return requestedBy;
  }

  public void setRequestedBy(String requestedBy) {
    this.requestedBy = requestedBy;
  }

  public String getRequestedOn() {
    return requestedOn;
  }

  public void setRequestedOn(String requestedOn) {
    this.requestedOn = requestedOn;
  }

  public String getRespondedBy() {
    return respondedBy;
  }

  public void setRespondedBy(String respondedBy) {
    this.respondedBy = respondedBy;
  }

  public String getRespondedOn() {
    return respondedOn;
  }

  public void setRespondedOn(String respondedOn) {
    this.respondedOn = respondedOn;
  }

  public String getRequestRemarks() {
    return requestRemarks;
  }

  public void setRequestRemarks(String requestRemarks) {
    this.requestRemarks = requestRemarks;
  }

  public String getResponseRemarks() {
    return responseRemarks;
  }

  public void setResponseRemarks(String responseRemarks) {
    this.responseRemarks = responseRemarks;
  }

  public CountryInfoDto getOldData() {
    return oldData;
  }

  public void setOldData(CountryInfoDto oldData) {
    this.oldData = oldData;
  }
}
