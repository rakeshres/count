package global.citytech.remitpulse.countries.crudapi.impl.helper;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.commons.*;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.crudapi.AutoVerifier;
import global.citytech.remitpulse.countries.crudapi.impl.exceptions.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;

import java.util.Optional;
import java.util.logging.Logger;

/** @author sunil */
public interface BaseUpdateRepositoryHelper<E extends AuditableEntity, T extends ServiceObject>
    extends RepositoryHelper<T, T> {
  Logger LOGGER = Logger.getLogger(BaseUpdateRepositoryHelper.class.getName());

  AbstractRepository<E, StringIdentifier> getEntityDao();

  AuditableEntityConverter<E, T> getConverter();

  AuditDao getAuditLogDao();

  AutoVerifier getAutoVerifier();

  String getPrimaryKey(T serviceObject);

  String getEntityName();

  RabbitRequestContext getRabbitRequestContext();

  FilterCriteria prepareFilterCriteriaWithPrimaryKey(T serviceObject);

  void updateEditableFields(E entity, T serviceObject);

  void updateResponse(T info, boolean autoVerifyEnable);

  @Override
  default T execute(T serviceObject) {

    E entity = this.getEntityFromDb(serviceObject);

    String oldEntityFromDb = Jsons.toJsonObj(entity);

    this.updateEditableFields(entity, serviceObject);
    HelperUtilsLocal.updateAuditableEntityForUpdate(entity, getCurrentUserName());

    AuditLogEntity auditLogEntity =
        this.prepareAuditLogEntity(entity, oldEntityFromDb, this.getPrimaryKey(serviceObject));

    LOGGER.info("AUDIT LOG TO SAVE ::" + Jsons.toJsonObj(auditLogEntity));
    Optional<AuditLogEntity> savedLogEntity = getAuditLogDao().insert(auditLogEntity);
    if (savedLogEntity.isEmpty()) {
      ExceptionManager.throwInsertingUpdatedEntityIntoAuditLogFailed();
    }

    boolean autoVerifyEnable =
        this.getAutoVerifier().riseEntityUpdateRequestEventForAutoVerify(savedLogEntity.get());

    return prepareResponse(entity, autoVerifyEnable);
  }

  default T prepareResponse(E auditableEntity, boolean autoVerifyEnable) {

    T info = this.getConverter().toServiceObject(auditableEntity);
    updateResponse(info, autoVerifyEnable);
    return info;
  }

  default E getEntityFromDb(T serviceObject) {

    String primaryKey = getPrimaryKey(serviceObject);

    if (Strings.isNullOrEmpty(primaryKey)) {
      ExceptionManager.throwPrimaryKeyCodeOrIdIsBlank();
    }

    FilterCriteria filterCriteria = this.prepareFilterCriteriaWithPrimaryKey(serviceObject);
    LOGGER.info("Getting entity with :" + Jsons.toJsonObj(filterCriteria));

    Optional<E> entityFromDb = getEntityDao().find(filterCriteria);

    if (entityFromDb.isEmpty()) {
      ExceptionManager.throwEntityNotFound();
    }

    return entityFromDb.get();
  }

  default AuditLogEntity prepareAuditLogEntity(
      E entity, String oldEntityFromDb, String primaryKey) {

    return HelperUtilsLocal.prepareAuditLogEntityForUpdate(
        primaryKey, this.getEntityName(), this.getCurrentUserName(), entity, oldEntityFromDb);
  }

  private String getCurrentUserName() {
    return this.getRabbitRequestContext().getUserName();
  }
}
