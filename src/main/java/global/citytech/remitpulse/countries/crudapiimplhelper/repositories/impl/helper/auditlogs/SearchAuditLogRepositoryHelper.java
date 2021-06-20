package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.impl.helper.auditlogs;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.audits.AuditLogConverter;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.commons.Entity;
import global.citytech.rabbit.core.commons.EntityConverter;
import global.citytech.rabbit.core.commons.PageableData;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.remitpulse.countries.crudapi.impl.exceptions.ExceptionManager;
import global.citytech.remitpulse.countries.crudapi.impl.helper.RepositoryHelper;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogSearchRequest;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.domains.SortParameter;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogFilter;
import global.citytech.remitpulse.countries.rest.AuditLogDatabaseMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/** @author sunil */
public interface SearchAuditLogRepositoryHelper
    extends RepositoryHelper<AuditLogSearchRequest, PageableData<AuditLogInfo>> {

  Logger LOGGER = Logger.getLogger(SearchAuditLogRepositoryHelper.class.getName());

  AuditDao getAuditLogDao();

  Class getClassByEntityType(String entity);

  EntityConverter getConverterByEntityType(String entity);

  @Override
  default PageableData<AuditLogInfo>  execute(AuditLogSearchRequest auditLogSearchRequest) {

    AuditLogFilter filterCriteria =
        this.prepareAuditLogFilterCriteria(auditLogSearchRequest);

    List<AuditLogEntity> entities = getAuditLogDao().findAll(filterCriteria);
    LOGGER.info("Got " + entities.size() + " AuditLogs");

    if (entities.isEmpty()) {
      return preparePageableData(Collections.emptyList(), filterCriteria);
    }

    List<AuditLogInfo> auditLogInfos = new ArrayList<>();
    entities.forEach(limitEntity -> auditLogInfos.add(toServiceObject(limitEntity)));

    return preparePageableData(auditLogInfos, filterCriteria);
  }

  private AuditLogFilter prepareAuditLogFilterCriteria(
      AuditLogSearchRequest auditLogSearchRequest) {

    AuditLogFilter filterCriteria = new AuditLogFilter();

    filterCriteria.setEntity(auditLogSearchRequest.getEntity());
    filterCriteria.setEntityKey(auditLogSearchRequest.getEntityKey());
    filterCriteria.setStatusList(auditLogSearchRequest.getStatusList());

    prepareSortCriteria(auditLogSearchRequest, filterCriteria);
    HelperUtilsLocal.preparePaginationCriteria(auditLogSearchRequest, filterCriteria);

    return filterCriteria;
  }

  private void prepareSortCriteria(
      AuditLogSearchRequest searchRequest, AuditLogFilter filterCriteria) {

    String sortParameter = searchRequest.getSortParameter();
    if (!Strings.isNullOrEmpty(sortParameter)) {
      try {
        SortParameter sortParamEnum = SortParameter.getByCode(sortParameter);
        filterCriteria.setSortParameter(sortParamEnum.getCode());
      } catch (IllegalArgumentException e) {
        filterCriteria.setSortParameter(SortParameter.ASC.getCode());
      }
    } else {
      filterCriteria.setSortParameter(SortParameter.ASC.getCode());
    }

    String sortBy = searchRequest.getSortBy();
    if (!Strings.isNullOrEmpty(sortBy)) {
      try {
        AuditLogDatabaseMapper sortByEnum = AuditLogDatabaseMapper.getByCode(sortBy);
        filterCriteria.setSortBy(sortByEnum.getDatabaseName());
      } catch (IllegalArgumentException e) {
        filterCriteria.setSortBy(AuditLogDatabaseMapper.RESPONDED_ON.getDatabaseName());
      }
    } else {
      filterCriteria.setSortBy(AuditLogDatabaseMapper.RESPONDED_ON.getDatabaseName());
    }
  }

  private AuditLogInfo toServiceObject(AuditLogEntity entity) {
    AuditLogInfo auditLogInfo = new AuditLogInfo();
    auditLogInfo.setId(entity.getId());
    auditLogInfo.setAction(entity.getAction().getDescription());
    auditLogInfo.setEntity(entity.getEntity());
    auditLogInfo.setEntityKey(entity.getEntityKey());
    auditLogInfo.setRequestRemarks(entity.getResponseRemarks());
    auditLogInfo.setRequestedBy(entity.getRequestedBy());
    auditLogInfo.setRequestedOn(entity.getRequestedOn());
    auditLogInfo.setRespondedBy(entity.getRespondedBy());
    auditLogInfo.setRespondedOn(entity.getRespondedOn());
    auditLogInfo.setResponseRemarks(entity.getResponseRemarks());
    auditLogInfo.setStatus(entity.getStatus().getDescription());

    setJsonTypeField(entity,auditLogInfo);
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
        ExceptionManager.throwInvalidAuditLogEntity();
      }
    }
    return null;
  }
  private PageableData<AuditLogInfo> preparePageableData(
      List<AuditLogInfo> auditLogInfos, AuditLogFilter filter) {

    return new PageableData<>(
        filter.getPageNumber(),
        this.getAuditLogDao().getTotalRecord(filter),
        filter.getPageSize(),
        auditLogInfos);
  }
}
