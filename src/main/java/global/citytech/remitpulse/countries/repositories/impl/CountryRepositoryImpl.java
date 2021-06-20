package global.citytech.remitpulse.countries.repositories.impl;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.audits.*;
import global.citytech.rabbit.core.commons.PageableData;
import global.citytech.rabbit.core.search.dynamic.MultiValueData;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.commons.domains.auditlog.DynamicAuditSearchCriteria;
import global.citytech.remitpulse.countries.repositories.AutoVerifyService;
import global.citytech.remitpulse.countries.repositories.CountryRepository;
import global.citytech.remitpulse.countries.repositories.auditlog.validators.auditlogs.EntityAlreadyExistInPendingStateValidator;
import global.citytech.remitpulse.countries.repositories.constants.LocationConstant;
import global.citytech.remitpulse.countries.repositories.converters.AuditReportFilterConverter;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.SortParameter;
import global.citytech.remitpulse.countries.repositories.domains.country.*;
import global.citytech.remitpulse.countries.repositories.domains.services.config.*;
import global.citytech.remitpulse.countries.repositories.domains.services.corridor.Corridor;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.Currency;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.commons.constants.MasterType;
import global.citytech.remitpulse.countries.repositories.domains.services.master.OperationType;
import global.citytech.remitpulse.countries.repositories.domains.services.paymentmethod.PaymentMethod;
import global.citytech.remitpulse.countries.repositories.internal.auditlog.CountryPendingInfoSearchRequest;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.LocationDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.LocationEntity;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.config.ConfigDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.config.ConfigEntity;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryEntity;
import global.citytech.remitpulse.countries.repositories.internal.dto.audit.AuditLogDto;
import global.citytech.remitpulse.countries.repositories.internal.dto.country.CountryInfoDto;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogFilter;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditReportFilterCriteria;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryFilter;
import global.citytech.remitpulse.countries.repositories.internal.filters.LocationFilter;
import global.citytech.remitpulse.countries.repositories.internal.service.CountryValidatorService;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.mto.MtoUpdateRequest;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.mto.MtoService;
import global.citytech.remitpulse.countries.rest.AuditLogDatabaseMapper;
import global.citytech.remitpulse.countries.rest.CountryDatabaseMapper;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** @author bipin on 6/20/19 12:45 PM. */
public class CountryRepositoryImpl implements CountryRepository {
  private static Logger logger = Logger.getLogger(CountryRepositoryImpl.class.getName());
  private CountryDao countryDao;
  private RabbitRequestContext rabbitRequestContext;
  private CountryValidatorService countryValidatorService;
  private AuditDao auditDao;
  private MasterModuleService masterModuleService;
  private CurrencyModuleService currencyModuleService;
  private AutoVerifyService autoVerifyService;
  private EntityAlreadyExistInPendingStateValidator<CountryInfo>
      entityAlreadyExistInPendingStateValidator;
  private ConfigDao configDao;
  private MtoService mtoService;
  private LocationDao locationDao;

  @Inject
  public CountryRepositoryImpl(
      CountryDao countryDao,
      CountryValidatorService countryValidatorService,
      AuditDao auditDao,
      MasterModuleService masterModuleService,
      CurrencyModuleService currencyModuleService,
      RabbitRequestContext rabbitRequestContext,
      AutoVerifyService autoVerifyService,
      EntityAlreadyExistInPendingStateValidator<CountryInfo>
          entityAlreadyExistInPendingStateValidator,
      ConfigDao configDao,
      MtoService mtoService,
      LocationDao locationDao) {
    this.countryDao = countryDao;
    this.countryValidatorService = countryValidatorService;
    this.auditDao = auditDao;
    this.masterModuleService = masterModuleService;
    this.currencyModuleService = currencyModuleService;
    this.rabbitRequestContext = rabbitRequestContext;
    this.autoVerifyService = autoVerifyService;
    this.entityAlreadyExistInPendingStateValidator = entityAlreadyExistInPendingStateValidator;
    this.configDao = configDao;
    this.mtoService = mtoService;
    this.locationDao = locationDao;
  }

  @Override
  public CountryInfo create(CountryInfo countryInfo) {
    this.countryValidatorService.validateBasicInformation(countryInfo);
    this.countryValidatorService.validateCurrencyInformation(countryInfo);
    this.countryValidatorService.validateCorridorInformation(countryInfo);
    this.countryValidatorService.validatePaymentMethods(countryInfo);
    this.countryValidatorService.validateConfigs(countryInfo);
    this.checkDuplicateDataWhileAdding(countryInfo);

    CountryEntity entity = this.prepareCountryEntityFromInfo(countryInfo);
    ConfigEntity configEntity = this.prepareConfigEntityFromInfo(countryInfo);
    entity.setId(HelperUtils.generateUUID());
    entity.setCreatedBy(rabbitRequestContext.getUserName());
    entity.setCreatedOn(HelperUtils.now());
    AuditLogEntity auditLogEntity = this.prepareAuditLogEntityFromCountryEntity(entity);
    auditLogEntity.setAction(AuditAction.ADD);
    auditDao.insert(auditLogEntity);

    Boolean autoApproveEnabled =
        this.autoVerifyService.verifyAndApprove(
            auditLogEntity, auditDao, entity, countryDao, rabbitRequestContext);
    if (autoApproveEnabled) {
      configDao.insert(configEntity);
    }
    return convertCountryEntityToInfoAfterSuccessAddUpdate(entity, autoApproveEnabled);
  }

  private void updateMtoMasterData(CountryInfo countryInfo) {
    MtoUpdateRequest request = new MtoUpdateRequest();
    request.setCountry(countryInfo.getIso3());
    request.setMasterDataConfiguration(prepareMasterDataConfiguration(countryInfo));
    this.mtoService.updateMtoMasterDetail(request);
  }

  private MasterDataConfiguration prepareMasterDataConfiguration(CountryInfo countryInfo) {
    MasterDataConfiguration configuration = new MasterDataConfiguration();
    List<MasterDataInfo> idTypes = new ArrayList<>();
    List<MasterDataInfo> purposeOfRemittances = new ArrayList<>();
    List<MasterDataInfo> sourceOfFunds = new ArrayList<>();
    List<MasterDataInfo> relationships = new ArrayList<>();
    List<MasterDataInfo> occupations = new ArrayList<>();
    Config config = countryInfo.getConfigs();
    if(config.getIdTypeList()!=null && !config.getIdTypeList().isEmpty())
    config.getIdTypeList().parallelStream()
            .forEach(item->idTypes.add(new MasterDataInfo(item.getCode(),item.getCode())));
    if(config.getPurposeOfRemittanceList()!=null && !config.getPurposeOfRemittanceList().isEmpty())
    config.getPurposeOfRemittanceList().parallelStream()
            .forEach(item->purposeOfRemittances.add(new MasterDataInfo(item.getCode(),item.getCode())));
    if(config.getSourceOfFundList()!=null && !config.getSourceOfFundList().isEmpty())
    config.getSourceOfFundList().parallelStream()
            .forEach(item->sourceOfFunds.add(new MasterDataInfo(item.getCode(),item.getCode())));
    if(config.getRelationshipList()!=null && !config.getRelationshipList().isEmpty())
    config.getRelationshipList().parallelStream()
            .forEach(item->relationships.add(new MasterDataInfo(item.getCode(),item.getCode())));
    if(config.getOccupationList()!=null && !config.getOccupationList().isEmpty())
    config.getOccupationList().parallelStream()
            .forEach(item->occupations.add(new MasterDataInfo(item.getCode(),item.getCode())));
    configuration.setIdType(idTypes);
    configuration.setPurpose(purposeOfRemittances);
    configuration.setSourceOfFund(sourceOfFunds);
    configuration.setRelationship(relationships);
    configuration.setOccupation(occupations);
    return configuration;
  }

  private ConfigEntity prepareConfigEntityFromInfo(CountryInfo countryInfo) {
    ConfigEntity configEntity = new ConfigEntity();
    configEntity.setCountryIso3Code(countryInfo.getIso3());
    configEntity.setIdTypes(Jsons.toJsonObj(countryInfo.getConfigs().getIdTypeList()));
    configEntity.setPurposeOfRemittances(
        Jsons.toJsonObj(countryInfo.getConfigs().getPurposeOfRemittanceList()));
    configEntity.setSourceOfFunds(Jsons.toJsonObj(countryInfo.getConfigs().getSourceOfFundList()));
    configEntity.setRelationships(Jsons.toJsonObj(countryInfo.getConfigs().getRelationshipList()));
    configEntity.setOccupations(Jsons.toJsonObj(countryInfo.getConfigs().getOccupationList()));
    return configEntity;
  }

  private static CountryInfo convertCountryEntityToInfoAfterSuccessAddUpdate(
      CountryEntity ent, Boolean autoApproveEnabled) {
    CountryInfo info = new CountryInfo();
    info.setId(ent.getId());
    info.setIso3(ent.getIso3());
    info.setAutoApproveEnabled(autoApproveEnabled);
    return info;
  }

  private AuditLogEntity prepareAuditLogEntityFromCountryEntity(CountryEntity ce) {
    AuditLogEntity auditLogEntity = new AuditLogEntity();
    auditLogEntity.setId(HelperUtils.generateUUID());
    auditLogEntity.setEntityKey(ce.getId());
    auditLogEntity.setNewData(Jsons.toJsonObj(ce));
    auditLogEntity.setEntity(EntityName.COUNTRY.getEntityName());
    auditLogEntity.setStatus(AuditStatus.PENDING);
    auditLogEntity.setRequestedBy(this.rabbitRequestContext.getUserName());
    auditLogEntity.setRequestedOn(HelperUtils.now());

    return auditLogEntity;
  }

  private AuditLogInfo convertAuditEntityToAuditLogInfo(AuditLogEntity auditLogEntity) {
    AuditLogInfo auditLogInfo = new AuditLogInfo();
    auditLogInfo.setId(auditLogEntity.getId());
    auditLogInfo.setStatus(auditLogEntity.getStatus().getDescription());
    auditLogInfo.setAction(auditLogEntity.getAction().getDescription());
    return auditLogInfo;
  }

  private CountryEntity prepareCountryEntityFromInfo(CountryInfo countryInfo) {
    CountryEntity entity = new CountryEntity();
    entity.setActive(countryInfo.getActive());
    entity.setName(countryInfo.getName().trim().toUpperCase());
    entity.setNumericCode(countryInfo.getNumericCode());
    entity.setIso2(countryInfo.getIso2().trim().toUpperCase());
    entity.setIso3(countryInfo.getIso3().trim().toUpperCase());
    entity.setOperationTypes(Jsons.toJsonObj(countryInfo.getOperationTypeList()));
    entity.setCurrencies(Jsons.toJsonObj(countryInfo.getCurrencyList()));
    entity.setCorridors(Jsons.toJsonObj(countryInfo.getCorridorList()));
    entity.setPaymentMethods(Jsons.toJsonObj(countryInfo.getPaymentMethodList()));
    entity.setConfigs(Jsons.toJsonObj(countryInfo.getConfigs()));
    return entity;
  }

  private void checkDuplicateDataWhileApproving(CountryInfo countryInfo) {
    if (this.getCountryWithNameFilter(countryInfo).isPresent()) {
      ExceptionManager.throwCountryNameAlreadyExists();
    }

    if (this.getCountryWithNumericCodeFilter(countryInfo).isPresent()) {
      ExceptionManager.throwNumericCodeAlreadyExists();
    }

    if (this.getCountryWithIso2Filter(countryInfo).isPresent()) {
      ExceptionManager.throwIso2AlreadyExists();
    }

    if (this.getCountryWithIso3Filter(countryInfo).isPresent()) {
      ExceptionManager.throwIso3AlreadyExists();
    }
  }

  private void checkDuplicateDataWhileAdding(CountryInfo countryInfo) {
    if (this.getCountryWithNameFilter(countryInfo).isPresent()) {
      ExceptionManager.throwCountryNameAlreadyExists();
    }

    if (this.getCountryWithNumericCodeFilter(countryInfo).isPresent()) {
      ExceptionManager.throwNumericCodeAlreadyExists();
    }

    if (this.getCountryWithIso2Filter(countryInfo).isPresent()) {
      ExceptionManager.throwIso2AlreadyExists();
    }

    if (this.getCountryWithIso3Filter(countryInfo).isPresent()) {
      ExceptionManager.throwIso3AlreadyExists();
    }

    if (this.auditDao
        .find(this.prepareAuditLogFilterForValidatingUniqueCountryNameCheck(countryInfo))
        .isPresent()) {
      ExceptionManager.throwCountryNameAlreadyExistsInPending();
    }

    if (this.auditDao
        .find(this.prepareAuditLogFilterForValidatingUniqueNumericCodeCheck(countryInfo))
        .isPresent()) {
      ExceptionManager.throwNumericCodeAlreadyExistsInPending();
    }

    if (this.auditDao
        .find(this.prepareAuditLogFilterForValidatingUniqueIso2Check(countryInfo))
        .isPresent()) {
      ExceptionManager.throwIso2AlreadyExistsInPending();
    }

    if (this.auditDao
        .find(this.prepareAuditLogFilterForValidatingUniqueIso3Check(countryInfo))
        .isPresent()) {
      ExceptionManager.throwIso3AlreadyExistsInPending();
    }

    if (countryInfo.getPaymentMethodList() != null
        && countryInfo.getPaymentMethodList().size() >= 2) {
      if (isDuplicatePaymentTitleFound(countryInfo.getPaymentMethodList())) {
        ExceptionManager.throwDuplicatePaymentTitleNotAllowedInPaymentMethodDetail();
      }

      if (isDuplicatePaymentCodeFound(countryInfo.getPaymentMethodList())) {
        ExceptionManager.throwDuplicatePaymentCodeNotAllowedInPaymentMethodDetail();
      }
    }
  }

  private Optional<CountryEntity> getCountryWithNameFilter(CountryInfo info) {
    CountryFilter fc = new CountryFilter();
    fc.setName(info.getName().trim());
    logger.info("FILTER FOR NAME SEARCH " + Jsons.toJsonObj(fc));
    return this.countryDao.find(fc);
  }

  private Optional<CountryEntity> getCountryWithNumericCodeFilter(CountryInfo info) {
    CountryFilter fc = new CountryFilter();
    fc.setNumericCode(info.getNumericCode());
    logger.info("FILTER FOR NUMERIC CODE SEARCH :: " + Jsons.toJsonObj(fc));
    return this.countryDao.find(fc);
  }

  private Optional<CountryEntity> getCountryWithIso2Filter(CountryInfo info) {
    CountryFilter fc = new CountryFilter();
    fc.setIso2(info.getIso2().trim());
    logger.info("FILTER FOR Iso2 SEARCH :: " + Jsons.toJsonObj(fc));
    return this.countryDao.find(fc);
  }

  private Optional<CountryEntity> getCountryWithIso3Filter(CountryInfo info) {
    CountryFilter fc = new CountryFilter();
    fc.setIso3(info.getIso3().trim());
    logger.info("FILTER FOR Iso3 SEARCH :: " + Jsons.toJsonObj(fc));
    return this.countryDao.find(fc);
  }

  private AuditLogFilter prepareAuditLogFilterForValidatingUniqueCountryNameCheck(
      CountryInfo countryInfo) {
    AuditLogFilter fc1 = new AuditLogFilter();
    fc1.getCountryEntity().setName(countryInfo.getName().trim());
    fc1.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    logger.info("FILTER FOR UNIQUE NAME IN PENDING CHECK:: " + Jsons.toJsonObj(fc1));
    return fc1;
  }

  private AuditLogFilter prepareAuditLogFilterForValidatingUniqueNumericCodeCheck(
      CountryInfo countryInfo) {
    AuditLogFilter fc = new AuditLogFilter();
    fc.getCountryEntity().setNumericCode(countryInfo.getNumericCode());
    fc.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    logger.info("FILTER FOR UNIQUE NUMERIC CODE IN PENDING CHECK:: " + Jsons.toJsonObj(fc));
    return fc;
  }

  private AuditLogFilter prepareAuditLogFilterForValidatingUniqueIso2Check(
      CountryInfo countryInfo) {
    AuditLogFilter fc1 = new AuditLogFilter();
    fc1.getCountryEntity().setIso2(countryInfo.getIso2().trim().toUpperCase());
    fc1.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    return fc1;
  }

  private AuditLogFilter prepareAuditLogFilterForValidatingUniqueIso3Check(
      CountryInfo countryInfo) {
    AuditLogFilter fc1 = new AuditLogFilter();
    fc1.getCountryEntity().setIso3(countryInfo.getIso3().trim().toUpperCase());
    fc1.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    return fc1;
  }

  public boolean isDuplicatePaymentTitleFound(List<PaymentMethod> paymentMethodList) {
    Map<String, Integer> keyCountMap = new HashMap<>();
    paymentMethodList.forEach(
        paymentMethod -> {
          String key = Strings.nullToEmpty(paymentMethod.getTitle()).trim().toLowerCase();
          if (keyCountMap.containsKey(key)) {
            keyCountMap.put(key, keyCountMap.get(key) + 1);
          } else {
            keyCountMap.put(key, 1);
          }
        });
    return keyCountMap.values().stream().anyMatch(integer -> integer > 1);
  }

  public boolean isDuplicatePaymentCodeFound(List<PaymentMethod> paymentMethodList) {
    Map<String, Integer> keyCountMap = new HashMap<>();
    paymentMethodList.forEach(
        paymentMethod -> {
          String key = Strings.nullToEmpty(paymentMethod.getCode()).trim().toLowerCase();
          if (keyCountMap.containsKey(key)) {
            keyCountMap.put(key, keyCountMap.get(key) + 1);
          } else {
            keyCountMap.put(key, 1);
          }
        });
    return keyCountMap.values().stream().anyMatch(integer -> integer > 1);
  }

  @Override
  public PageableData findAuditList(CountryPendingInfoSearchRequest request) {
    logger.info("INSIDE FIND PENDING LIST ::" + Jsons.toJsonObj(request));
    AuditLogFilter filter = this.prepareFilterForFindingPendingList(request);
    List<AuditLogEntity> entities = this.auditDao.findAll(filter);
    if (entities.isEmpty()) {
      return new PageableData<>(
          filter.getPageNumber(),
          this.auditDao.getTotalRecord(filter),
          filter.getPageSize(),
          Collections.emptyList());
    }

    List<AuditLogInfo> auditLogInfos = preparePendingListForResponse(entities);
    return new PageableData<>(
        filter.getPageNumber(),
        this.auditDao.getTotalRecord(filter),
        filter.getPageSize(),
        auditLogInfos);
  }

  private AuditLogFilter prepareFilterForFindingPendingList(
      CountryPendingInfoSearchRequest request) {
    AuditLogFilter filter = new AuditLogFilter();
    if (request.getPageNumber() <= 0) request.setPageNumber(1);
    if (request.getPageSize() <= 0) request.setPageSize(10);
    filter.setPageNumber(request.getPageNumber());
    filter.setPageSize(request.getPageSize());
    filter.setStartingIndex(request.getPageSize() * (request.getPageNumber() - 1));
    filter.setSortBy(this.getSortBy(request));
    filter.setSortParameter(
        !HelperUtils.isBlankOrNull(request.getSortParameter())
            ? this.getSortParameter(request)
            : SortParameter.DESC.getCode());
    filter.setStatusList(request.getStatusList());
    filter.setEntityKey(request.getEntityKey());
    if (!Strings.isNullOrEmpty(request.getSearchParameter())) {
      filter.setSearchParameter(request.getSearchParameter().trim());
    }
    return filter;
  }

  private String getSortBy(CountryPendingInfoSearchRequest searchRequest) {
    AuditLogDatabaseMapper auditLogDatabaseMapper =
        AuditLogDatabaseMapper.getByCode(searchRequest.getSortBy());
    return auditLogDatabaseMapper.getSystemVariable();
  }

  private String getSortParameter(CountryPendingInfoSearchRequest searchRequest) {
    return searchRequest.getSortParameter().equalsIgnoreCase(SortParameter.ASC.getCode())
        ? SortParameter.ASC.getCode()
        : SortParameter.DESC.getCode();
  }

  private List<AuditLogInfo> preparePendingListForResponse(List<AuditLogEntity> entities) {
    List<AuditLogInfo> logList = new ArrayList<>();
    for (AuditLogEntity auditEntity : entities) {
      AuditLogInfo view = new AuditLogInfo();

      view.setRequestedBy(auditEntity.getRequestedBy());
      view.setRequestedOn(auditEntity.getRequestedOn());
      view.setAction(auditEntity.getAction().getDescription());
      view.setId(auditEntity.getId());
      view.setEntityKey(auditEntity.getEntityKey());
      view.setRespondedBy(auditEntity.getRespondedBy());
      view.setRespondedOn(auditEntity.getRespondedOn());
      view.setResponseRemarks(auditEntity.getResponseRemarks());
      view.setStatus(auditEntity.getStatus().getDescription());
      setOldDataAndNewDataToLog(auditEntity, view);
      logList.add(view);
    }
    return logList;
  }

  private void setOldDataAndNewDataToLog(
      AuditLogEntity auditLogEntity, AuditLogInfo<CountryEntity> info) {
    CountryEntity newEntity = Jsons.fromJsonToObj(auditLogEntity.getNewData(), CountryEntity.class);
    info.setNewData(newEntity);
    if (!HelperUtils.isBlankOrNull(auditLogEntity.getOldData())) {
      CountryEntity oldEntity =
          Jsons.fromJsonToObj(auditLogEntity.getOldData(), CountryEntity.class);
      info.setOldData(oldEntity);
    }
  }

  @Override
  public AuditLogDto findAuditDetail(AuditLogInfo auditLogInfo) {
    validateIfIdMissing(auditLogInfo.getId());
    AuditLogEntity entity = getAuditEntityFromId(auditLogInfo.getId());
    return prepareCountryInfoDto(entity);
  }

  private void validateIfIdMissing(String id) {
    if (Strings.isNullOrEmpty(id)) {
      ExceptionManager.throwIdMissing();
    }
  }

  private AuditLogEntity getAuditEntityFromId(String id) {
    AuditLogFilter fc = new AuditLogFilter();
    fc.setId(id);
    Optional<AuditLogEntity> entity = this.auditDao.find(fc);
    if (entity.isEmpty()) {
      ExceptionManager.throwIdDoesNotExists();
    }
    logger.info("AUDIT LOG ENTITY FOUND :: ");

    return entity.get();
  }

  private AuditLogDto prepareCountryInfoDto(AuditLogEntity entity) {
    AuditLogDto view = new AuditLogDto();
    view.setNewData(
        prepareCountryInfoDto(Jsons.fromJsonToObj(entity.getNewData(), CountryEntity.class)));

    if (!Strings.isNullOrEmpty(entity.getOldData())) {
      view.setOldData(
          prepareCountryInfoDto(Jsons.fromJsonToObj(entity.getOldData(), CountryEntity.class)));
    }
    view.setRequestedBy(entity.getRequestedBy());
    view.setRequestedOn(entity.getRequestedOn());
    view.setAction(entity.getAction().getDescription());
    view.setId(entity.getId());
    view.setStatus(entity.getStatus().getDescription());
    view.setRespondedBy(entity.getRespondedBy());
    view.setRespondedOn(entity.getRespondedOn());
    view.setRequestRemarks(entity.getRequestRemarks());
    view.setResponseRemarks(entity.getResponseRemarks());
    view.setEntity(entity.getEntity());
    view.setEntityKey(entity.getEntityKey());
    return view;
  }

  private CountryInfoDto prepareCountryInfoDto(CountryEntity cE) {
    CountryInfoDto view = new CountryInfoDto();
    view.setId(cE.getId());
    view.setName(cE.getName());
    view.setNumericCode(cE.getNumericCode());
    view.setIso2(cE.getIso2());
    view.setIso3(cE.getIso3());
    view.setActive(cE.isActive());
    view.setCurrencyList(Jsons.fromJsonToList(cE.getCurrencies(), Currency[].class));
    view.setOperationTypeList(Jsons.fromJsonToList(cE.getOperationTypes(), OperationType[].class));
    view.setCorridorList(Jsons.fromJsonToList(cE.getCorridors(), Corridor[].class));
    view.setPaymentMethodList(Jsons.fromJsonToList(cE.getPaymentMethods(), PaymentMethod[].class));
    view.setConfigs(Jsons.fromJsonToObj(cE.getConfigs(), Config.class));
    return view;
  }

  private String convertOperationTypeCodeIntoName(String ot, List<MasterInfo> infoList) {
    for (MasterInfo masterInfo : infoList) {
      if (masterInfo.getCode().equals(ot)) {
        return masterInfo.getDescription();
      }
    }
    return ot;
  }

  private String convertCurrencyIsoAlphaToCurrencyName(
      String isoAlpha, List<CurrencyInfo> currencyInfoList) {
    for (CurrencyInfo currencyInfo : currencyInfoList) {
      if (currencyInfo.getIsoAlpha().equals(isoAlpha)) {
        return currencyInfo.getCurrencyName();
      }
    }
    return isoAlpha;
  }

  @Override
  public AuditLogInfo approve(AuditLogInfo auditLogInfo) {
    AuditLogEntity auditEntity = this.getPendingAuditEntityFromId(auditLogInfo.getId());
    //    CountryEntity countryEntity = this.prepareCountryEntityFromAuditLog(auditEntity);
    checkIfMakerCheckerSame(auditEntity);
    logger.info("CHECK MAKER CHECKER SAME COMPLETE");
    if (auditEntity.getAction() == AuditAction.ADD) {
      updateLogAndAddToCountryTable(auditEntity);
    }
    if (auditEntity.getAction() == AuditAction.UPDATE) {
      updateLogAndUpdateToCountryTable(auditEntity);
    }
    return auditLogInfo;
  }

  private void updateLogAndUpdateToCountryTable(AuditLogEntity auditLogEntity) {
    CountryEntity entity = Jsons.fromJsonToObj(auditLogEntity.getNewData(), CountryEntity.class);
    ConfigEntity configEntity = this.getConfigEntity(entity);
    if (countryDao.update(entity).isPresent()) {
      updateLogStatusToApproved(auditLogEntity);
      configDao.update(configEntity);
      this.updateMtoMasterData(convertCountryEntityToCountryInfo(entity));
    }
  }

  private void updateLogAndAddToCountryTable(AuditLogEntity auditLogEntity) {
    CountryEntity entity = Jsons.fromJsonToObj(auditLogEntity.getNewData(), CountryEntity.class);
    CountryInfo info = this.getCountryInfoFromEntity(entity);
    ConfigEntity configEntity = this.getConfigEntity(entity);
    checkDuplicateDataWhileApproving(info);
    logger.info("DUPLICATE CHECK IS COMPLETE");
    if (countryDao.insert(entity).isPresent()) {
      updateLogStatusToApproved(auditLogEntity);
      configDao.insert(configEntity);
    }
  }

  private ConfigEntity getConfigEntity(CountryEntity entity) {
    Config configInfo = Jsons.fromJsonToObj(entity.getConfigs(), Config.class);
    ConfigEntity configEntity = new ConfigEntity();
    configEntity.setCountryIso3Code(entity.getIso3());
    configEntity.setIdTypes(Jsons.toJsonObj(configInfo.getIdTypeList()));
    configEntity.setPurposeOfRemittances(Jsons.toJsonObj(configInfo.getPurposeOfRemittanceList()));
    configEntity.setSourceOfFunds(Jsons.toJsonObj(configInfo.getSourceOfFundList()));
    configEntity.setRelationships(Jsons.toJsonObj(configInfo.getRelationshipList()));
    configEntity.setOccupations(Jsons.toJsonObj(configInfo.getOccupationList()));
    return configEntity;
  }

  private void updateLogStatusToApproved(AuditLogEntity auditLogEntity) {
    auditLogEntity.setRespondedBy(rabbitRequestContext.getUserName());
    auditLogEntity.setRespondedOn(HelperUtils.now());
    auditLogEntity.setStatus(AuditStatus.APPROVED);
    auditDao.update(auditLogEntity);
  }

  private CountryInfo getCountryInfoFromEntity(CountryEntity entity) {
    CountryInfo info = new CountryInfo();
    info.setName(entity.getName());
    info.setNumericCode(entity.getNumericCode());
    info.setIso2(entity.getIso2());
    info.setIso3(entity.getIso3());
    return info;
  }

  private AuditLogEntity getPendingAuditEntityFromId(String id) {
    AuditLogFilter fc = new AuditLogFilter();
    fc.setId(id);
    fc.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    Optional<AuditLogEntity> entity = this.auditDao.find(fc);
    if (entity.isEmpty()) {
      ExceptionManager.throwIdDoesNotExists();
    }
    logger.info("AUDIT LOG ENTITY FOUND :: ");

    return entity.get();
  }

  private void checkIfMakerCheckerSame(AuditLogEntity auditLogEntity) {
    logger.info("CHECKING MAKER AND CHECKER SAME");
    if (auditLogEntity.getRequestedBy().equalsIgnoreCase(rabbitRequestContext.getUserName()))
      ExceptionManager.throwMakerCheckerIsSameUser();
  }

  private CountryEntity prepareCountryEntityFromAuditLog(AuditLogEntity entity) {
    return Jsons.fromJsonToObj(entity.getNewData(), CountryEntity.class);
  }


  @Override
  public PageableData<CountryInfo> findAll(CountryInfoSearchRequest request) {
    this.validateFindAllSearchRequestData(request);

    CountryFilter filter = this.prepareFilterForFindAll(request);
    List<CountryEntity> searchedEntity = this.countryDao.findAll(filter);

    List<CountryInfo> countryInfos = new ArrayList<>();
    MasterInfo mi = new MasterInfo();
    mi.setMasterTypeList(Collections.singletonList(MasterType.OPERATION_TYPE.getCode()));
    List<MasterInfo> masterInfoList = this.masterModuleService.fetchMasterInfo(mi);
    for (CountryEntity e : searchedEntity) {
      List<String> operationTypesDescription = new ArrayList<>();
      List<OperationType> operationTypes =
          Jsons.fromJsonToList(e.getOperationTypes(), OperationType[].class);
      for (OperationType ot : operationTypes) {
        operationTypesDescription.add(
            this.convertOperationTypeCodeIntoName(ot.getCode(), masterInfoList));
      }
      CountryInfo countryInfo = new CountryInfo();
      countryInfo.setId(e.getId());
      countryInfo.setName(e.getName());
      countryInfo.setIso3(e.getIso3());
      countryInfo.setIso2(e.getIso2());
      countryInfo.setActive(e.isActive());
      countryInfo.setNumericCode(e.getNumericCode());
      countryInfo.setOperationTypeList(operationTypes);
      countryInfo.setOperationTypesDescription(operationTypesDescription);
      countryInfos.add(countryInfo);
    }
    return new PageableData<>(
        filter.getPageNumber(),
        this.countryDao.getCount(filter),
        filter.getPageSize(),
        countryInfos);
  }

  private void validateFindAllSearchRequestData(CountryInfoSearchRequest request) {

    if (!Strings.isNullOrEmpty(request.getSortParameter())
        && !(request.getSortParameter().equalsIgnoreCase(SortParameter.ASC.getCode())
            || request.getSortParameter().equalsIgnoreCase(SortParameter.DESC.getCode()))) {

      ExceptionManager.throwInvalidSortParameter();
    }

    if (!Strings.isNullOrEmpty(request.getSortBy())
        && !(request.getSortBy().equalsIgnoreCase("name")
            || request.getSortBy().equalsIgnoreCase("numericCode")
            || request.getSortBy().equalsIgnoreCase("iso2")
            || request.getSortBy().equalsIgnoreCase("iso3"))) {
      ExceptionManager.throwInvalidSortByParameter();
    }
  }

  private CountryFilter prepareFilterForFindAll(CountryInfoSearchRequest request) {
    CountryFilter fc = new CountryFilter();
    if (request.getPageNumber() <= 0) request.setPageNumber(1);
    if (request.getPageSize() <= 0) request.setPageSize(10);
    fc.setPageNumber(request.getPageNumber());
    fc.setPageSize(request.getPageSize());
    fc.setActive(request.getActive());
    fc.setStartingIndex(request.getPageSize() * (request.getPageNumber() - 1));

    if (!Strings.isNullOrEmpty(request.getIso3Parameter())) {
      fc.setIso3(request.getIso3Parameter().trim());
    } else if (!Strings.isNullOrEmpty(request.getNameParameter())) {
      fc.setName(request.getNameParameter());
    } else {
      fc.setNumericCode(request.getNumericCodeParameter());
    }

    if (Strings.isNullOrEmpty(request.getSortBy())
        || request
            .getSortBy()
            .trim()
            .equalsIgnoreCase(CountryDatabaseMapper.NAME.getRequestVariable())) {
      fc.setSortBy(CountryDatabaseMapper.NAME.getSystemVariable());
      fc.setSortParameter(SortParameter.ASC.getCode());
    } else if (request
        .getSortBy()
        .trim()
        .equalsIgnoreCase(CountryDatabaseMapper.ISO3.getRequestVariable())) {
      fc.setSortBy(CountryDatabaseMapper.ISO3.getSystemVariable());
    }else if (request
      .getSortBy()
      .trim()
        .equalsIgnoreCase(CountryDatabaseMapper.ISO2.getRequestVariable())) {
    fc.setSortBy(CountryDatabaseMapper.ISO2.getSystemVariable());
  }
    else {
      fc.setSortBy(CountryDatabaseMapper.NUMERIC_CODE.getSystemVariable());
    }

    if (Strings.isNullOrEmpty(request.getSortParameter())
        || request.getSortParameter().trim().equalsIgnoreCase(SortParameter.ASC.getCode())) {
      fc.setSortParameter(SortParameter.ASC.getCode());
    } else {
      fc.setSortParameter(SortParameter.DESC.getCode());
    }
    return fc;
  }

  @Override
  public CountryInfo findOne(CountryInfo countryInfo) {
    CountryEntity countryEntity = this.findValidation(countryInfo);
    return this.convertCountryEntityToCountryInfo(countryEntity);
  }

  @Override
  public CountryInfo getCountryWithMasterDataConf(CountryInfo countryInfo) {
    CountryEntity countryEntity = this.findValidation(countryInfo);
    CountryInfo info = this.convertCountryEntityToCountryInfo(countryEntity);
    Config countryConfig = this.getCountryConfig(info);
    Map masterInfoMap = getMasterInfos();
    MasterDataConfiguration masterDataConfiguration =
        this.convertConfigToMasterInfo(countryConfig,masterInfoMap);
    this.setMasterDataConfigToCountry(masterDataConfiguration,info);
    return info;
  }

  private void setMasterDataConfigToCountry(MasterDataConfiguration masterDataConfiguration,CountryInfo info) {
    info.setMasterDataConfiguration(masterDataConfiguration);
  }

  private MasterDataConfiguration convertConfigToMasterInfo(Config countryConfig, Map masterInfos) {
    MasterDataConfiguration info = new MasterDataConfiguration();
    setRelationship(info,countryConfig,masterInfos);
    setOccupation(info,countryConfig,masterInfos);
    setSourceOfFund(info,countryConfig,masterInfos);
    setPurpose(info,countryConfig,masterInfos);
    setIdType(info,countryConfig,masterInfos);
    return info;
  }

  private void setIdType(MasterDataConfiguration info, Config countryConfig, Map masterInfos) {
    List<IdType> idTypes = countryConfig.getIdTypeList();
    List<MasterDataInfo> masterDataInfos = new ArrayList<>();
    idTypes.stream().forEach(idType -> {
      MasterDataInfo masterDataInfo = new MasterDataInfo();
      masterDataInfo.setCode(idType.getCode());
      setTitle(masterInfos, masterDataInfo);
      masterDataInfos.add(masterDataInfo);
    });
    info.setIdType(masterDataInfos);
  }

  private void setTitle(Map masterInfos, MasterDataInfo masterDataInfo) {
    if(masterInfos.containsKey(masterDataInfo.getCode()))
      masterDataInfo.setTitle(String.valueOf(masterInfos.get(masterDataInfo.getCode())));
  }

  private void setRelationship(MasterDataConfiguration info, Config countryConfig, Map masterInfos) {
    List<Relationship> relationships = countryConfig.getRelationshipList();
    List<MasterDataInfo> masterDataInfos = new ArrayList<>();
    relationships.stream().forEach(relationship -> {
      MasterDataInfo masterDataInfo = new MasterDataInfo();
      masterDataInfo.setCode(relationship.getCode());
      setTitle(masterInfos, masterDataInfo);
      masterDataInfos.add(masterDataInfo);
    });
    info.setRelationship(masterDataInfos);
  }

  private void setOccupation(MasterDataConfiguration info, Config countryConfig, Map masterInfos) {
    List<Occupation> occupations = countryConfig.getOccupationList();
    List<MasterDataInfo> masterDataInfos = new ArrayList<>();
    occupations.stream().forEach(occupation -> {
      MasterDataInfo masterDataInfo = new MasterDataInfo();
      masterDataInfo.setCode(occupation.getCode());
      setTitle(masterInfos, masterDataInfo);
      masterDataInfos.add(masterDataInfo);
    });
    info.setOccupation(masterDataInfos);
  }
  private void setSourceOfFund(MasterDataConfiguration info, Config countryConfig, Map masterInfos) {
    List<SourceOfFund> sourceOfFunds = countryConfig.getSourceOfFundList();
    List<MasterDataInfo> masterDataInfos = new ArrayList<>();
    sourceOfFunds.stream().forEach(sourceOfFund -> {
      MasterDataInfo masterDataInfo = new MasterDataInfo();
      masterDataInfo.setCode(sourceOfFund.getCode());
      setTitle(masterInfos, masterDataInfo);
      masterDataInfos.add(masterDataInfo);
    });
    info.setSourceOfFund(masterDataInfos);
  }
  private void setPurpose(MasterDataConfiguration info, Config countryConfig, Map masterInfos) {
    List<PurposeOfRemittance> purposeOfRemittances = countryConfig.getPurposeOfRemittanceList();
    List<MasterDataInfo> masterDataInfos = new ArrayList<>();
    purposeOfRemittances.stream().forEach(purposeOfRemittance -> {
      MasterDataInfo masterDataInfo = new MasterDataInfo();
      masterDataInfo.setCode(purposeOfRemittance.getCode());
      setTitle(masterInfos, masterDataInfo);
      masterDataInfos.add(masterDataInfo);
    });
    info.setPurpose(masterDataInfos);
  }

  private Config getCountryConfig(CountryInfo country) {
    Config config = country.getConfigs();
    if(config==null){
      throw new AppException(ExceptionManager.CountryError.COUNTRY_CONFIGURATION_NOT_FOUND);
    }
    return config;
  }

  private Map getMasterInfos() {
    MasterInfo masterInfo = new MasterInfo();
    masterInfo.setMasterTypeList(List.of(
        MasterType.OCCUPATION.getCode(),
        MasterType.RELATIONSHIP.getCode(),
        MasterType.ID.getCode(),
        MasterType.REMITTANCE_REASON.getCode(),
        MasterType.SOURCE_OF_FUND.getCode()
    ));
    List<MasterInfo> masterInfos = this.masterModuleService.fetchMasterInfo(masterInfo);
    if(masterInfos.isEmpty()){
      throw new AppException(ExceptionManager.CountryError.MASTER_DATA_NOT_FOUND);
    }
    Map<String,String> masterMap = new HashMap<>();
    masterInfos.stream().forEach(info->{
      masterMap.put(info.getCode(),info.getDescription());
    });
    return masterMap;
  }

  private void validateCountryInfoIso3(CountryInfo countryInfo) {
    if (Strings.isNullOrEmpty(countryInfo.getIso3())) {
      ExceptionManager.throwIdMissing();
    }
  }

  private CountryEntity getCountryWithIdFilter(CountryInfo countryInfo) {
    CountryFilter filter = new CountryFilter();
    filter.setId(countryInfo.getId());
    Optional<CountryEntity> ent = this.countryDao.find(filter);
    return ent.orElse(null);
  }

  private void validateCountryInfo(CountryInfo countryInfo) {
    if (Strings.isNullOrEmpty(countryInfo.getId())) {
      ExceptionManager.throwIdMissing();
    }
  }

  @Override
  public CountryInfo update(CountryInfo countryInfo) {
    this.countryValidatorService.validateBasicInformation(countryInfo);
    this.countryValidatorService.validateCurrencyInformation(countryInfo);
    this.countryValidatorService.validateCorridorInformation(countryInfo);
    this.countryValidatorService.validatePaymentMethods(countryInfo);
    this.countryValidatorService.validateConfigs(countryInfo);

    this.validateCountryInfo(countryInfo);
    CountryEntity countryEntity = this.getCountryWithIdFilter(countryInfo);
    ConfigEntity configEnty = Jsons.fromJsonToObj(countryEntity.getConfigs(), ConfigEntity.class);
    Config config = this.prepareConfigInfoFromEntity(configEnty);
    countryEntity.setConfigs(Jsons.toJsonObj(config));

    if (countryEntity == null) {
      ExceptionManager.throwIdDoesNotExists();
    }
    this.entityAlreadyExistInPendingStateValidator.validate(countryInfo);
    this.checkIfDataIsInPendingState(countryInfo);
    this.checkDuplicateDataWhileUpdating(countryInfo);
    logger.info("UNIQUE VALIDATION DONE ");
    CountryEntity entity = this.prepareCountryEntityFromInfo(countryInfo);
    entity.setName(countryEntity.getName());
    entity.setIso2(countryEntity.getIso2());
    entity.setIso3(countryEntity.getIso3());
    entity.setNumericCode(countryEntity.getNumericCode());
    entity.setId(countryInfo.getId());
    entity.setCreatedOn(countryEntity.getCreatedOn());
    entity.setCreatedBy(countryEntity.getCreatedBy());
    entity.setLastModifiedBy(rabbitRequestContext.getUserName());
    entity.setLastModifiedOn(HelperUtils.now());

    AuditLogEntity auditLogEntity = this.prepareAuditLogEntityFromCountryEntity(entity);
    auditLogEntity.setOldData(Jsons.toJsonObj(countryEntity));
    auditLogEntity.setAction(AuditAction.UPDATE);
    auditDao.insert(auditLogEntity);
    Boolean autoApproveEnabled =
        this.autoVerifyService.verifyAndApprove(
            auditLogEntity, auditDao, entity, countryDao, rabbitRequestContext);
    ConfigEntity configEntity = this.prepareConfigEntityFromInfo(countryInfo);
    if (autoApproveEnabled) {
      configDao.update(configEntity);
      this.updateMtoMasterData(countryInfo);
    }
    return convertCountryEntityToInfoAfterSuccessAddUpdate(entity, autoApproveEnabled);
  }

  private void checkIfDataIsInPendingState(CountryInfo countryInfo) {
    AuditLogFilter filter = new AuditLogFilter();
    filter.setEntityKey(countryInfo.getId());
    filter.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    if (this.auditDao.find(filter).isPresent()) {
      ExceptionManager.throwDataInPendingState();
    }
  }

  private void checkDuplicateDataWhileUpdating(CountryInfo countryInfo) {

    // unique name
    Optional<CountryEntity> countryEntity1 = this.getCountryWithNameFilter(countryInfo);
    if (countryEntity1.isPresent() && !countryEntity1.get().getId().equals(countryInfo.getId())) {
      ExceptionManager.throwCountryNameAlreadyExists();
    }

    // unique numeric code
    Optional<CountryEntity> countryEntity2 = this.getCountryWithNumericCodeFilter(countryInfo);
    if (countryEntity2.isPresent() && !countryEntity2.get().getId().equals(countryInfo.getId())) {
      ExceptionManager.throwNumericCodeAlreadyExists();
    }
    // unique iso2
    Optional<CountryEntity> countryEntity3 = this.getCountryWithIso2Filter(countryInfo);
    if (countryEntity3.isPresent() && !countryEntity3.get().getId().equals(countryInfo.getId())) {
      ExceptionManager.throwIso2AlreadyExists();
    }
    // unique iso3
    Optional<CountryEntity> countryEntity4 = this.getCountryWithIso3Filter(countryInfo);
    if (countryEntity4.isPresent() && !countryEntity4.get().getId().equals(countryInfo.getId())) {
      ExceptionManager.throwIso3AlreadyExists();
    }

    Optional<AuditLogEntity> auditLogEntity1 =
        this.auditDao.find(
            this.prepareAuditLogFilterForValidatingUniqueCountryNameCheck(countryInfo));
    if (auditLogEntity1.isPresent()
        && !auditLogEntity1.get().getEntityKey().equals(countryInfo.getId())) {
      ExceptionManager.throwCountryNameAlreadyExistsInPending();
    }

    Optional<AuditLogEntity> auditLogEntity2 =
        this.auditDao.find(
            this.prepareAuditLogFilterForValidatingUniqueNumericCodeCheck(countryInfo));
    if (auditLogEntity2.isPresent()
        && !auditLogEntity2.get().getEntityKey().equals(countryInfo.getId())) {
      ExceptionManager.throwNumericCodeAlreadyExistsInPending();
    }
    Optional<AuditLogEntity> auditLogEntity3 =
        this.auditDao.find(this.prepareAuditLogFilterForValidatingUniqueIso2Check(countryInfo));

    if (auditLogEntity3.isPresent()
        && !auditLogEntity3.get().getEntityKey().equals(countryInfo.getId())) {
      ExceptionManager.throwIso2AlreadyExistsInPending();
    }
    Optional<AuditLogEntity> auditLogEntity4 =
        this.auditDao.find(this.prepareAuditLogFilterForValidatingUniqueIso3Check(countryInfo));

    if (auditLogEntity4.isPresent()
        && !auditLogEntity4.get().getEntityKey().equals(countryInfo.getId())) {
      ExceptionManager.throwIso3AlreadyExistsInPending();
    }

    if (countryInfo.getPaymentMethodList() != null
        && countryInfo.getPaymentMethodList().size() >= 2) {
      if (isDuplicatePaymentTitleFound(countryInfo.getPaymentMethodList())) {
        ExceptionManager.throwDuplicatePaymentTitleNotAllowedInPaymentMethodDetail();
      }

      if (isDuplicatePaymentCodeFound(countryInfo.getPaymentMethodList())) {
        ExceptionManager.throwDuplicatePaymentCodeNotAllowedInPaymentMethodDetail();
      }
    }
  }

  @Override
  public List<CountryInfo> findByValues(CountryInfo countryInfo) {
    List<String> iso3List = countryInfo.getIso3List();
    CountryFilter countryFilter = new CountryFilter();
    countryFilter.setActive(true);
    countryFilter.setIso3List(iso3List);
    List<CountryEntity> countryEntityList = this.countryDao.findAll(countryFilter);
    List<CountryInfo> finalInfoList = new ArrayList<>();

    for (CountryEntity ent : countryEntityList) {
      finalInfoList.add(this.convertCountryEntityToCountryInfoForList(ent));
    }
    return finalInfoList;
  }

  @Override
  public AuditLogInfo reject(AuditLogInfo auditLogInfo) {
    this.validateRejectRemarks(auditLogInfo);
    AuditLogEntity auditEntity = this.getPendingAuditEntityFromId(auditLogInfo.getId());
    checkIfMakerCheckerSame(auditEntity);
    AuditLogEntity auditLogEntityToReject =
        prepareAuditLogEntityToReject(auditEntity, auditLogInfo);
    if (auditDao.update(auditLogEntityToReject).isEmpty())
      throw new AppException(ExceptionManager.CountryError.UPDATING_AUDIT_LOG_FAILED);
    return auditLogInfo;
  }

  @Override
  public PageableData<AuditLogInfo> getAuditList(DynamicAuditSearchCriteria auditSearchCriteria) {
    AuditReportFilterCriteria auditFilter = AuditReportFilterConverter.prepareCountryAuditReportFilterCriteria(auditSearchCriteria);
    long totalRows = this.auditDao.countByDynamicFilter(auditFilter);
    List<AuditLogEntity> auditLogEntities = this.auditDao.findWithDynamicFilter(auditFilter);
    return new PageableData<>(
        auditFilter.getPageNumber(),
        totalRows,
        auditFilter.getPageSize(),
        preparePendingListForResponse(auditLogEntities));
  }

  @Override
  public List<MultiValueData> findLocationInfoByCountryCode(GetLocationsRequest countryInfo) {
    LocationFilter filter = prepareFilterToGetCountryInformation(countryInfo);
    List<LocationEntity> locationEntities = this.locationDao.getLocations(filter);
    List<MultiValueData> multiValueData = new ArrayList<>();
    locationEntities.stream().map(entity ->
            multiValueData.add(new MultiValueData(entity.getCode(), entity.getTitle()))).collect(Collectors.toList());
    return multiValueData;
  }

  private LocationFilter prepareFilterToGetCountryInformation(GetLocationsRequest countryInfo) {
    LocationFilter filter = new LocationFilter();
    filter.setActive(true);
    filter.setCategory(LocationConstant.getByCode(countryInfo.getCategory()).name());
    filter.setCountryCode(countryInfo.getCountryCode());
    filter.setParentCode(countryInfo.getParentCode());
    return filter;
  }

  private AuditLogEntity prepareAuditLogEntityToReject(
      AuditLogEntity auditLogEntity, AuditLogInfo auditLogInfo) {
    auditLogEntity.setResponseRemarks(auditLogInfo.getResponseRemarks().trim().toUpperCase());
    auditLogEntity.setRespondedBy(rabbitRequestContext.getUserName());
    auditLogEntity.setRespondedOn(HelperUtils.now());
    auditLogEntity.setStatus(AuditStatus.REJECTED);
    return auditLogEntity;
  }

  private void validateRejectRemarks(AuditLogInfo auditLogInfo) {
    if (HelperUtils.isBlankOrNull(auditLogInfo.getResponseRemarks())) {
      throw new AppException(ExceptionManager.CountryError.REJECT_REMARKS_MISSING);
    }
  }

  private CountryInfo convertCountryEntityToCountryInfoForList(CountryEntity ent) {
    CountryInfo countryInfo = new CountryInfo();
    countryInfo.setId(ent.getId());
    countryInfo.setName(ent.getName());
    countryInfo.setIso3(ent.getIso3());
    countryInfo.setIso2(ent.getIso2());
    countryInfo.setActive(ent.isActive());
    List<OperationType> operationTypes =
        Jsons.fromJsonToList(ent.getOperationTypes(), OperationType[].class);
    List<Currency> currencyList = Jsons.fromJsonToList(ent.getCurrencies(), Currency[].class);
    countryInfo.setCurrencyList(currencyList);
    countryInfo.setOperationTypeList(operationTypes);
    countryInfo.setOperationTypesDescription(
        operationTypes.stream().map(OperationType::getCode).collect(Collectors.toList()));
    countryInfo.setNumericCode(ent.getNumericCode());
    countryInfo.setCorridorList(Jsons.fromJsonToList(ent.getCorridors(), Corridor[].class));
    countryInfo.setPaymentMethodList(
        Jsons.fromJsonToList(ent.getPaymentMethods(), PaymentMethod[].class));
    return countryInfo;
  }

  private CountryInfo convertCountryEntityToCountryInfo(CountryEntity ce) {
    List<Currency> currencies = Jsons.fromJsonToList(ce.getCurrencies(), Currency[].class);
    List<Corridor> corridors = Jsons.fromJsonToList(ce.getCorridors(), Corridor[].class);
    List<PaymentMethod> paymentMethods =
        Jsons.fromJsonToList(ce.getPaymentMethods(), PaymentMethod[].class);
    CountryInfo countryInfo = new CountryInfo();
    countryInfo.setId(ce.getId());
    countryInfo.setName(ce.getName());
    countryInfo.setNumericCode(ce.getNumericCode());
    countryInfo.setIso3(ce.getIso3());
    countryInfo.setIso2(ce.getIso2());
    countryInfo.setActive(ce.isActive());

    List<OperationType> operationTypes =
        Jsons.fromJsonToList(ce.getOperationTypes(), OperationType[].class);
    countryInfo.setOperationTypeList(operationTypes);
    countryInfo.setCurrencyList(currencies);
    countryInfo.setCorridorList(corridors);
    countryInfo.setPaymentMethodList(paymentMethods);
    countryInfo.setConfigs(
        this.prepareConfigInfoFromEntity(Jsons.fromJsonToObj(ce.getConfigs(), ConfigEntity.class)));
    return countryInfo;
  }

  private Config prepareConfigInfoFromEntity(ConfigEntity configEntity) {
    Config config = new Config();
    config.setIdTypeList(Jsons.fromJsonToList(configEntity.getIdTypes(), IdType[].class));
    config.setPurposeOfRemittanceList(
        Jsons.fromJsonToList(configEntity.getPurposeOfRemittances(), PurposeOfRemittance[].class));
    config.setSourceOfFundList(
        Jsons.fromJsonToList(configEntity.getSourceOfFunds(), SourceOfFund[].class));
    config.setRelationshipList(
        Jsons.fromJsonToList(configEntity.getRelationships(), Relationship[].class));
    config.setOccupationList(
        Jsons.fromJsonToList(configEntity.getOccupations(), Occupation[].class));
    return config;
  }

  private CountryEntity findValidation(CountryInfo countryInfo) {
    this.validateCountryInfoIso3(countryInfo);
    Optional<CountryEntity> countryEntity = this.getCountryWithIso3Filter(countryInfo);
    if (countryEntity.isEmpty()) {
      ExceptionManager.throwIdDoesNotExists();
    }
    return countryEntity.get();
  }
}
