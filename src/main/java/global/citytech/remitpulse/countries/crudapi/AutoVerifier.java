package global.citytech.remitpulse.countries.crudapi;

import global.citytech.rabbit.core.audits.AuditAction;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.Entity;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.crudapi.impl.exceptions.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;

import java.util.Optional;
import java.util.logging.Logger;

/** @author sunil */
public interface AutoVerifier {

  Logger LOGGER = Logger.getLogger(AutoVerifier.class.getName());

  AuditDao getAuditLogDao();

  RabbitRequestContext getRabbitRequestContext();

  AbstractRepository getDaoBasedOnEntityType(String entityName);

  boolean isValidEntity(String entityName);

  Class getEntityClass(String entityName);

  boolean isAutoVerifyEnable(String entityName);

  default boolean riseNewEntityAddRequestEventForAutoVerify(AuditLogEntity savedLogEntity) {
    return checkAndAutoApprove(savedLogEntity);
  }

  default boolean riseEntityUpdateRequestEventForAutoVerify(AuditLogEntity savedLogEntity) {
    return checkAndAutoApprove(savedLogEntity);
  }

  default boolean checkAndAutoApprove(AuditLogEntity savedLogEntity) {
    boolean autoVerifyEnable = isAutoVerifyEnable(savedLogEntity.getEntity());
    if (autoVerifyEnable) {
      new Thread(() -> this.approve(savedLogEntity, true)).start();
    }
    return autoVerifyEnable;
  }

  default void approve(AuditLogEntity logEntity, boolean isAutoVerify) {

    LOGGER.info("Approving audit log :: " + Jsons.toJsonObj(logEntity));

    Entity entity = prepareAuditableEntityBasedOnEntityType(logEntity);

    this.validateAuditLogBeforeApproval(logEntity, isAutoVerify);

    this.processAuditableEntityAfterApproval(entity, logEntity);

    this.updateAuditLog(logEntity, AuditStatus.APPROVED, isAutoVerify);
  }

  default void validateAuditLogBeforeApproval(AuditLogEntity logEntity, boolean isAutoVerify) {
    if (!isAutoVerify) {
      this.validateIfMakerCheckerSame(logEntity);
    }
  }

  default void reject(AuditLogEntity logEntity, boolean isAutoVerify) {

    LOGGER.info("Rejecting audit log :: " + Jsons.toJsonObj(logEntity));

    this.validateAuditLogBeforeApproval(logEntity, isAutoVerify);

    this.updateAuditLog(logEntity, AuditStatus.REJECTED, isAutoVerify);
  }

  default void processAuditableEntityAfterApproval(Entity entity, AuditLogEntity auditLogEntity) {

    AbstractRepository abstractRepository = getDaoBasedOnEntityType(auditLogEntity.getEntity());

    LOGGER.info("PROCESS AFTER APPROVAL :: " + Jsons.toJsonObj(entity));

    if (auditLogEntity.getAction() == AuditAction.ADD) {
      LOGGER.info("INSERT");
      this.insertEntity(entity, abstractRepository);
    } else if (auditLogEntity.getAction() == AuditAction.UPDATE) {
      LOGGER.info("UPDATE");
      this.updateEntity(entity, abstractRepository);
    } else {
      ExceptionManager.throwInvalidAuditLogAction();
    }
  }

  default Entity prepareAuditableEntityBasedOnEntityType(AuditLogEntity logEntity) {

    if (isValidEntity(logEntity.getEntity()) && logEntity.getNewData() != null) {
      return (Entity)
              Jsons.fromJsonToObj(logEntity.getNewData(), getEntityClass(logEntity.getEntity()));
    } else {
      ExceptionManager.throwInvalidAuditLogEntity();
      return null;
    }
  }

  private void validateIfMakerCheckerSame(AuditLogEntity entity) {

    if (entity.getRequestedBy().equalsIgnoreCase(getRabbitRequestContext().getUserName()))
      ExceptionManager.throwMakerAndCheckerCannotBeSame();
  }

  private void insertEntity(Entity entity, AbstractRepository abstractRepository) {
    if (abstractRepository.insert(entity).isEmpty()) {
      ExceptionManager.throwErrorOnInsertingIntoEntityTable();
    }
  }

  private void updateEntity(Entity entity, AbstractRepository abstractRepository) {
    if (abstractRepository.update(entity).isEmpty()) {
      ExceptionManager.throwErrorOnUpdatingIntoEntityTable();
    }
  }

  private void updateAuditLog(AuditLogEntity logEntity, AuditStatus status, boolean isAutoVerify) {

    Optional<AuditLogEntity> optionalAuditLogEntity =
            getAuditLogDao().update(this.prepareAuditLogForUpdate(logEntity, status, isAutoVerify));
    if (optionalAuditLogEntity.isEmpty()) {
      ExceptionManager.throwUpdatingAuditLogFailed();
    }
  }

  private AuditLogEntity prepareAuditLogForUpdate(
      AuditLogEntity entity, AuditStatus status, boolean isAutoVerify) {

    entity.setStatus(status);
    if (isAutoVerify) {
      entity.setRespondedBy("SYSTEM");
    } else {
      entity.setRespondedBy(getRabbitRequestContext().getUserName());
    }
    entity.setRespondedOn(HelperUtils.now());
    return entity;
  }
}
