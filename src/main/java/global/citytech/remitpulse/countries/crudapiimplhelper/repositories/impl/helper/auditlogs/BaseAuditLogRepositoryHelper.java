package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs;

import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.Entity;
import global.citytech.rabbit.core.commons.EntityConverter;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.remitpulse.countries.crudapi.impl.exceptions.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;

/** @author sunil */
public interface BaseAuditLogRepositoryHelper {

  Class getClassByEntityType(String entity);

  EntityConverter getConverterByEntityType(String entity);

  AbstractRepository getDaoBasedOnEntityType(String entity);

  boolean isAutoVerifyEnable(String entity);

  default boolean isValidEntity(String entityName) {
    return EntityName.getByName(entityName).isPresent();
  }

  default AuditLogInfo toServiceObject(AuditLogEntity auditLogEntity) {
    AuditLogInfo auditLogInfo = new AuditLogInfo();
    auditLogInfo.setId(auditLogEntity.getId());
    auditLogInfo.setAction(auditLogEntity.getAction().getDescription());
    auditLogInfo.setEntity(auditLogEntity.getEntity());
    auditLogInfo.setEntityKey(auditLogEntity.getEntityKey());
    auditLogInfo.setRequestRemarks(auditLogEntity.getResponseRemarks());
    auditLogInfo.setRequestedBy(auditLogEntity.getRequestedBy());
    auditLogInfo.setRequestedOn(auditLogEntity.getRequestedOn());
    auditLogInfo.setRespondedBy(auditLogEntity.getRespondedBy());
    auditLogInfo.setRespondedOn(auditLogEntity.getRespondedOn());
    auditLogInfo.setResponseRemarks(auditLogEntity.getResponseRemarks());
    auditLogInfo.setStatus(auditLogEntity.getStatus().getDescription());

    setJsonTypeField(auditLogEntity, auditLogInfo);

    return auditLogInfo;
  }

  default void setJsonTypeField(AuditLogEntity auditLogEntity, AuditLogInfo auditLogInfo) {

    Class entityClass = getClassByEntityType(auditLogEntity.getEntity());
    EntityConverter converter = getConverterByEntityType(auditLogEntity.getEntity());

    try {
      auditLogInfo.setNewData(toEntity(entityClass, converter, auditLogEntity.getNewData()));
    } catch (AppException e) {
      ExceptionManager.throwInvalidAuditLogNewDataEntity();
    }

    try {
      auditLogInfo.setOldData(toEntity(entityClass, converter, auditLogEntity.getOldData()));
    } catch (AppException e) {
      ExceptionManager.throwInvalidAuditLogOldDataEntity();
    }
  }

  default ServiceObject toEntity(Class entityClass, EntityConverter converter, String jsonString) {
    Entity entity = toServiceObject(jsonString, entityClass);
    if (entity != null) {
      return converter.toServiceObject(entity);
    }
    return null;
  }

  default Entity toServiceObject(String jsonString, Class entityClass) {
    if (!HelperUtils.isBlankOrNull(jsonString)) {
      try {
        return (Entity) Jsons.fromJsonToObj(jsonString, entityClass);
      } catch (Exception e) {
        e.printStackTrace();
        ExceptionManager.throwInvalidAuditLogEntity();
      }
    }
    return null;
  }
}
