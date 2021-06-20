package global.citytech.remitpulse.countries.repositories.internal;

import global.citytech.rabbit.core.audits.AuditAction;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.core.commons.PageableData;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.repositories.AutoVerifyService;
import global.citytech.remitpulse.countries.repositories.auditlog.validators.auditlogs.EntityAlreadyExistInPendingStateValidator;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.domains.SortParameter;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.impl.CountryRepositoryImpl;
import global.citytech.remitpulse.countries.repositories.internal.auditlog.CountryPendingInfoSearchRequest;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryEntity;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.LocationDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.config.ConfigDao;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogFilter;
import global.citytech.remitpulse.countries.repositories.internal.service.CountryValidatorService;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.mto.MtoService;
import global.citytech.remitpulse.countries.rest.AuditLogDatabaseMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/** @author bipin on 2019-10-10 16:20. */
class CountryRepositoryImplPendingListTest {
  private static CountryRepositoryImpl countryRepository;

  @BeforeAll
  static void setup() {
    CountryDao countryDao = Mockito.mock(CountryDao.class);
    ConfigDao configDao = Mockito.mock(ConfigDao.class);
    CountryValidatorService countryValidatorService = Mockito.mock(CountryValidatorService.class);
    AuditDao auditDao = Mockito.mock(AuditDao.class);
    MasterModuleService masterModuleService = Mockito.mock(MasterModuleService.class);
    RabbitRequestContext rabbitRequestContext = Mockito.mock(RabbitRequestContext.class);
    CurrencyModuleService currencyModuleService = Mockito.mock(CurrencyModuleService.class);
    AutoVerifyService autoVerifyService = Mockito.mock(AutoVerifyService.class);
    LocationDao locationDao = Mockito.mock(LocationDao.class);
    EntityAlreadyExistInPendingStateValidator<CountryInfo>
        entityAlreadyExistInPendingStateValidator =
            Mockito.mock(EntityAlreadyExistInPendingStateValidator.class);
    AuditLogFilter auditLogFilter1 = new AuditLogFilter();
    auditLogFilter1.setStatusList(Arrays.asList(AuditStatus.PENDING.getDescription()));
    auditLogFilter1.setPageNumber(1);
    auditLogFilter1.setPageSize(10);
    auditLogFilter1.setStartingIndex(0);
    auditLogFilter1.setSortParameter(SortParameter.DESC.getCode());
    auditLogFilter1.setSortBy(AuditLogDatabaseMapper.REQUESTED_ON.getSystemVariable());
    AuditLogEntity auditLogEntity1 = new AuditLogEntity();
    auditLogEntity1.setEntityKey("returnWell");
    auditLogEntity1.setAction(AuditAction.UPDATE);
    auditLogEntity1.setStatus(AuditStatus.REJECTED);
    auditLogEntity1.setEntity(EntityName.COUNTRY.getEntityName());
    CountryEntity countryEntity = new CountryEntity();
    countryEntity.setId("asdfsdf");
    countryEntity.setIso2("II");
    countryEntity.setIso3("III");
    auditLogEntity1.setNewData(Jsons.toJsonObj(countryEntity));
    auditLogEntity1.setOldData(Jsons.toJsonObj(countryEntity));
    when(auditDao.findAll(any())).thenReturn(Collections.singletonList(auditLogEntity1));

    MtoService mtoService = Mockito.mock(MtoService.class);
    countryRepository =
            new CountryRepositoryImpl(
                    countryDao,
                    countryValidatorService,
                    auditDao,
                    masterModuleService,
                    currencyModuleService,
                    rabbitRequestContext,
                    autoVerifyService,
                    entityAlreadyExistInPendingStateValidator,
                    configDao, mtoService,
                    locationDao);
  }

  @Test
  @DisplayName("Should return data")
  void shouldReturnData() {
    CountryPendingInfoSearchRequest request = new CountryPendingInfoSearchRequest();
    request.setStatusList(Collections.singletonList(AuditStatus.PENDING.getDescription()));
    PageableData pageableData = countryRepository.findAuditList(request);
    System.out.println("pageableData :: " + Jsons.toJsonObj(pageableData));
    assertEquals("returnWell", ((AuditLogInfo) pageableData.getData().get(0)).getEntityKey());
  }
}
