package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto;

import global.citytech.rabbit.core.audits.AuditLogEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/** @author sunil */
@Named
@RequestScoped
public class AuditLogLetter {

  private AuditLogEntity auditLogEntity;

  public AuditLogEntity getAuditLogEntity() {
    return auditLogEntity;
  }

  public void setAuditLogEntity(AuditLogEntity auditLogEntity) {
    this.auditLogEntity = auditLogEntity;
  }
}
