package global.citytech.remitpulse.countries.repositories.auditlog.validators.auditlogs;

import global.citytech.remitpulse.countries.repositories.auditlog.dto.WithKey;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogFilter;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.core.commons.Validator;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/** @author sunil */
@Named
@Dependent
public class EntityAlreadyExistInPendingStateValidator<T extends WithKey> implements Validator<T> {

  private static final Logger LOGGER =
      Logger.getLogger(EntityAlreadyExistInPendingStateValidator.class.getName());

  private AuditDao auditLogDao;

  @Inject
  public EntityAlreadyExistInPendingStateValidator(AuditDao auditLogDao) {
    this.auditLogDao = auditLogDao;
  }

  @Override
  public void validate(T infoWithId) {

    LOGGER.info("Running: EntityAlreadyExistInPendingStateDbAndOtherServicesValidator");

    AuditLogFilter auditLogFilterCriteria = prepareAuditLogFilterById(infoWithId.getKey());
    Optional<AuditLogEntity> auditLogEntity = auditLogDao.find(auditLogFilterCriteria);
    if (auditLogEntity.isPresent()) {
      ExceptionManager.throwEntityAlreadyInPendingState();
    }
  }

  private AuditLogFilter prepareAuditLogFilterById(String id) {

    AuditLogFilter auditLogFilterCriteria = new AuditLogFilter();
    auditLogFilterCriteria.setEntityKey(id);
    auditLogFilterCriteria.setStatusList(List.of(AuditStatus.PENDING.getDescription()));
    return auditLogFilterCriteria;
  }
}
