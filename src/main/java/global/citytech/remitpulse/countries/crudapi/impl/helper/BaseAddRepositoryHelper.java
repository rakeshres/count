package global.citytech.remitpulse.countries.crudapi.impl.helper;

import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.commons.AuditableEntity;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.crudapi.AutoVerifier;
import global.citytech.remitpulse.countries.crudapi.impl.exceptions.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;

import java.util.Optional;

/** @author sunil */
public interface BaseAddRepositoryHelper<E extends AuditableEntity, T extends ServiceObject>
    extends RepositoryHelper<T, T> {

  AuditDao getAuditLogDao();

  AutoVerifier getAutoVerifier();

  AuditableEntityConverter<E, T> getConverter();

  String getEntityName();

  RabbitRequestContext getRabbitRequestContext();

  void setPrimaryKey(E auditableEntity);

  String getPrimaryKey(E auditableEntity);

  void updateResponse(T info, boolean autoVerifyEnable);

  @Override
  default T execute(T serviceObject) {

    E auditableEntity = this.prepareAuditableEntityForAdd(serviceObject);
    AuditLogEntity auditLogEntity = this.prepareAuditLogEntity(auditableEntity);

    Optional<AuditLogEntity> savedLogEntity = getAuditLogDao().insert(auditLogEntity);
    if (savedLogEntity.isEmpty()) {
      ExceptionManager.throwInsertingIntoAuditLogFailed();
    }

    boolean autoVerifyEnable =
        this.getAutoVerifier().riseNewEntityAddRequestEventForAutoVerify(savedLogEntity.get());

    return prepareResponse(auditableEntity, autoVerifyEnable);
  }

  default T prepareResponse(E auditableEntity, boolean autoVerifyEnable) {

    T info = this.getConverter().toServiceObject(auditableEntity);
    updateResponse(info, autoVerifyEnable);
    return info;
  }

  default E prepareAuditableEntityForAdd(T serviceObject) {

    E auditableEntity = this.getConverter().toEntity(serviceObject);
    HelperUtilsLocal.updateAuditableEntityForAdd(auditableEntity, this.getCurrentUserName());
    this.setPrimaryKey(auditableEntity);
    return auditableEntity;
  }

  default AuditLogEntity prepareAuditLogEntity(E auditableEntity) {
    return HelperUtilsLocal.prepareAuditLogEntityForAdd(
        getPrimaryKey(auditableEntity), getEntityName(), getCurrentUserName(), auditableEntity);
  }

  private String getCurrentUserName() {
    return this.getRabbitRequestContext().getUserName();
  }
}
