package global.citytech.remitpulse.countries.repositories.internal;

import global.citytech.remitpulse.countries.repositories.AutoVerifyService;
import global.citytech.remitpulse.countries.repositories.auditlog.validators.auditlogs.EntityAlreadyExistInPendingStateValidator;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.impl.CountryRepositoryImpl;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.LocationDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.config.ConfigDao;
import global.citytech.remitpulse.countries.repositories.internal.dto.audit.AuditLogDto;
import global.citytech.remitpulse.countries.repositories.internal.dto.country.CountryInfoDto;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogFilter;
import global.citytech.remitpulse.countries.repositories.internal.service.CountryValidatorService;
import global.citytech.rabbit.core.audits.AuditAction;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.mto.MtoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/** @author bipin on 2019-10-16 10:06. */
class CountryRepositoryImplPendingDetailTest {
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
    doNothing().when(countryValidatorService).validateBasicInformation(any());
    doNothing().when(countryValidatorService).validateCurrencyInformation(any());
    AutoVerifyService autoVerifyService = Mockito.mock(AutoVerifyService.class);
    LocationDao locationDao = Mockito.mock(LocationDao.class);
    EntityAlreadyExistInPendingStateValidator<CountryInfo>
        entityAlreadyExistInPendingStateValidator =
            Mockito.mock(EntityAlreadyExistInPendingStateValidator.class);

    AuditLogFilter auditLogFilter1 = new AuditLogFilter();
    auditLogFilter1.setId("returnWell");
    AuditLogEntity auditLogEntity1 = new AuditLogEntity();
    CountryInfoDto countryInfoDto1 = new CountryInfoDto();
    auditLogEntity1.setNewData(Jsons.toJsonObj(countryInfoDto1));
    auditLogEntity1.setAction(AuditAction.ADD);
    auditLogEntity1.setStatus(AuditStatus.REJECTED);
    auditLogEntity1.setId("returnWell");
    when(auditDao.find(auditLogFilter1)).thenReturn(Optional.of(auditLogEntity1));

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
  @DisplayName("Should throw exception if id is missing")
  void shouldThrowExceptionIfIdIsMissing() {
    AuditLogInfo info = new AuditLogInfo();
    AppException exception =
        assertThrows(AppException.class, () -> countryRepository.findAuditDetail(info));
    assertEquals(ExceptionManager.CountryError.ID_MISSING.getDescription(), exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if id does not exists")
  void shouldThrowExceptionIfIdDoesNotExists() {
    AuditLogInfo info = new AuditLogInfo();
    info.setId("noId");
    AppException exception =
        assertThrows(AppException.class, () -> countryRepository.findAuditDetail(info));
    assertEquals(
        ExceptionManager.CountryError.ID_DOES_NOT_EXISTS.getDescription(), exception.getMessage());
  }

  @Test
  @Disabled
  @DisplayName("Should return audit log dto")
  void shouldReturnDto() {
    AuditLogInfo info = new AuditLogInfo();
    info.setId("returnWell");
    AuditLogDto dto = countryRepository.findAuditDetail(info);
    assertEquals(info.getId(), dto.getId());
  }
}
