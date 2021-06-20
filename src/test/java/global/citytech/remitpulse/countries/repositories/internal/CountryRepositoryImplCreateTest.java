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
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryEntity;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.LocationDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.config.ConfigDao;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogFilter;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryFilter;
import global.citytech.remitpulse.countries.repositories.internal.service.CountryValidatorService;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.mto.MtoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/** @author bipin on 6/20/19 12:57 PM. */
class CountryRepositoryImplCreateTest {
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
    EntityAlreadyExistInPendingStateValidator<CountryInfo>
        entityAlreadyExistInPendingStateValidator =
            Mockito.mock(EntityAlreadyExistInPendingStateValidator.class);
    // name already exists
    CountryFilter countryFilter1 = new CountryFilter();
    countryFilter1.setName("nameAlreadyExists");
    when(countryDao.find(countryFilter1)).thenReturn(Optional.of(new CountryEntity()));
    // end of name already exists

    // numeric code already exists
    CountryFilter countryFilter2 = new CountryFilter();
    countryFilter2.setNumericCode("666");
    when(countryDao.find(countryFilter2)).thenReturn(Optional.of(new CountryEntity()));
    // end of numeric code already exists

    // iso2 already exists
    CountryFilter countryFilter3 = new CountryFilter();
    countryFilter3.setIso2("iso2AlreadyExists");
    when(countryDao.find(countryFilter3)).thenReturn(Optional.of(new CountryEntity()));
    // end of iso2 already exists

    // iso3 already exists
    CountryFilter countryFilter4 = new CountryFilter();
    countryFilter4.setIso3("iso3AlreadyExists");
    when(countryDao.find(countryFilter4)).thenReturn(Optional.of(new CountryEntity()));
    // end of iso3 already exists

    // name already exists in pending
    AuditLogFilter auditLogFilter5 = new AuditLogFilter();
    auditLogFilter5.getCountryEntity().setName("nameAlreadyExistsInPending");
    auditLogFilter5.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    when(auditDao.find(auditLogFilter5)).thenReturn(Optional.of(new AuditLogEntity()));
    // end of name already exists in pending

    // numeric code already exists in pending
    AuditLogFilter auditLogFilter6 = new AuditLogFilter();
    auditLogFilter6.getCountryEntity().setNumericCode("999");
    auditLogFilter6.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    when(auditDao.find(auditLogFilter6)).thenReturn(Optional.of(new AuditLogEntity()));
    // end of numeric code already exists in pending

    // iso2 already exists in pending
    AuditLogFilter auditLogFilter7 = new AuditLogFilter();
    auditLogFilter7.getCountryEntity().setIso2("iso2AlreadyExistsInPending".toUpperCase());
    auditLogFilter7.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    when(auditDao.find(auditLogFilter7)).thenReturn(Optional.of(new AuditLogEntity()));
    // end of iso2 already exists in pending

    // iso3 already exists in pending
    AuditLogFilter auditLogFilter8 = new AuditLogFilter();
    auditLogFilter8.getCountryEntity().setIso3("iso3AlreadyExistsInPending".toUpperCase());
    auditLogFilter8.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    when(auditDao.find(auditLogFilter8)).thenReturn(Optional.of(new AuditLogEntity()));

    // end of iso3 already exists in pending

    // start of should work well

    // end of should work well

    MtoService mtoService = Mockito.mock(MtoService.class);
    LocationDao locationDao = Mockito.mock(LocationDao.class);
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
  @DisplayName("Should throw exception if name already exists")
  void shouldThrowExceptionIfNameAlreadyExists() {
    CountryInfo info = new CountryInfo();
    info.setName("nameAlreadyExists");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.create(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.COUNTRY_NAME_ALREADY_EXISTS.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if numeric code already exists")
  void shouldThrowExceptionIfNumericCodeAlreadyExists() {
    CountryInfo info = new CountryInfo();
    info.setNumericCode("666");
    info.setName("6666");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.create(info));
    assertEquals(
        ExceptionManager.CountryError.NUMERIC_CODE_ALREADY_EXISTS.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if iso2 already exists")
  void shouldThrowExceptionIfIso2AlreadyExists() {
    CountryInfo info = new CountryInfo();
    info.setIso2("iso2AlreadyExists");
    info.setName("iso2AlreadyExists");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.create(info));
    assertEquals(
        ExceptionManager.CountryError.ISO2_ALREADY_EXISTS.getDescription(), exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if iso3 already exists")
  void shouldThrowExceptionIfIso3AlreadyExists() {
    CountryInfo info = new CountryInfo();
    info.setIso3("iso3AlreadyExists");
    info.setIso2("iso3AlreadyExists");
    info.setName("iso3AlreadyExists");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.create(info));
    assertEquals(
        ExceptionManager.CountryError.ISO3_ALREADY_EXISTS.getDescription(), exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if name already exists in pending state")
  void shouldThrowExceptionIfNameAlreadyExistsInPendingState() {
    CountryInfo info = new CountryInfo();
    info.setName("nameAlreadyExistsInPending");
    info.setIso2("nameAlreadyExistsInPending");
    info.setIso3("nameAlreadyExistsInPending");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.create(info));
    assertEquals(
        ExceptionManager.CountryError.COUNTRY_NAME_ALREADY_EXISTS_IN_PENDING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if numeric code already exists in pending")
  void shouldThrowExceptionIfNumericCodeAlreadyExistsInPending() {
    CountryInfo info = new CountryInfo();
    info.setNumericCode("999");
    info.setName("999");
    info.setIso3("999");
    info.setIso2("999");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.create(info));
    assertEquals(
        ExceptionManager.CountryError.NUMERIC_CODE_ALREADY_EXISTS_IN_PENDING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if iso2 already exists in pending")
  void shouldThrowExceptionIfIso2AlreadyExistsInPending() {
    CountryInfo info = new CountryInfo();
    info.setIso2("iso2AlreadyExistsInPending".toUpperCase());
    info.setIso3("iso2AlreadyExistsInPending".toUpperCase());
    info.setName("iso2AlreadyExistsInPending");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.create(info));
    assertEquals(
        ExceptionManager.CountryError.ISO2_ALREADY_EXISTS_IN_PENDING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if iso3 already exists in pending")
  void shouldThrowExceptionIfIso3AlreadyExistsInPending() {
    CountryInfo info = new CountryInfo();
    info.setIso3("iso3AlreadyExistsInPending".toUpperCase());
    info.setIso2("iso3AlreadyExistsInPending".toUpperCase());
    info.setName("iso3AlreadyExistsInPending");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.create(info));
    assertEquals(
        ExceptionManager.CountryError.ISO3_ALREADY_EXISTS_IN_PENDING.getDescription(),
        exception.getMessage());
  }

  //  @Test
  //  @DisplayName("Should work well")
  //  void shouldWorkWell(){
  //    CountryInfo info = new CountryInfo();
  //    info.setIso3("shouldWorkWell".toUpperCase());
  //    info.setIso2("shouldWorkWell".toUpperCase());
  //    info.setName("shouldWorkWell");
  //    info.setActive(true);
  //    info.setContinent("AFRICA");
  //    AuditLogInfo response= countryRepository.create(info);
  //    assertNotNull(response);
  //  }
}
