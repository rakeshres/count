package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.validators.auditlogs;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.remitpulse.countries.crudapi.impl.exceptions.ExceptionManager;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogSearchRequest;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.validators.BasePageableSearchRequestValidator;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.rest.AuditLogDatabaseMapper;

import java.util.List;
import java.util.Optional;

/** @author sunil */
public interface SearchAuditLogValidator
        extends BasePageableSearchRequestValidator<AuditLogSearchRequest> {

  @Override
  default boolean isInvalidSortBy(String sortBy) {
    try {
      AuditLogDatabaseMapper.getByCode(sortBy);
      return false;
    } catch (IllegalArgumentException e) {
      return true;
    }
  }

  @Override
  default void validate(AuditLogSearchRequest auditLogSearchRequest) {

    BasePageableSearchRequestValidator.super.validate(auditLogSearchRequest);

    String entityType = auditLogSearchRequest.getEntity();
    if (!Strings.isNullOrEmpty(entityType)) {

      Optional<EntityName> entityTypeByName = EntityName.getByName(entityType);
      if (entityTypeByName.isEmpty()) {
        ExceptionManager.throwInvalidAuditLogEntity();
      }
    }

    if (auditLogSearchRequest.getStatusList() != null
        && isContainsAnyInvalidStatus(auditLogSearchRequest.getStatusList())) {

      ExceptionManager.throwInvalidAuditLogStatus();
    }
  }

  default boolean isContainsAnyInvalidStatus(List<String> statusList) {

    return statusList.stream()
        .anyMatch(
            status -> {
              if (!Strings.isNullOrEmpty(status)) {
                try {
                  AuditStatus.getByCode(status);
                } catch (IllegalArgumentException e) {
                  return true;
                }
              }
              return false;
            });
  }
}
