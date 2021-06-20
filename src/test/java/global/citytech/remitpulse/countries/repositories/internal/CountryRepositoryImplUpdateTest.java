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
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/** @author bipin on 6/26/19 4:18 PM. */
@Disabled
class CountryRepositoryImplUpdateTest {
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
    LocationDao locationDao = Mockito.mock(LocationDao.class);
    AutoVerifyService autoVerifyService = Mockito.mock(AutoVerifyService.class);
    EntityAlreadyExistInPendingStateValidator<CountryInfo>
        entityAlreadyExistInPendingStateValidator =
            Mockito.mock(EntityAlreadyExistInPendingStateValidator.class);
    CountryFilter countryFilter0 = new CountryFilter();
    countryFilter0.setId("noId");
    when(countryDao.find(countryFilter0)).thenReturn(Optional.empty());

    CountryFilter countryFilter1 = new CountryFilter();
    countryFilter1.setId("dataInPendingState");
    when(countryDao.find(countryFilter1)).thenReturn(Optional.of(new CountryEntity()));
    AuditLogFilter auditLogFilter1 = new AuditLogFilter();
    auditLogFilter1.setEntityKey("dataInPendingState");
    auditLogFilter1.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    when(auditDao.find(auditLogFilter1)).thenReturn(Optional.of(new AuditLogEntity()));

    // name already exists
    CountryFilter countryFilter17 = new CountryFilter();
    countryFilter17.setName("nameAlreadyExists");
    CountryFilter countryFilter177 = new CountryFilter();
    countryFilter177.setId("nameAlreadyExists");
    when(countryDao.find(countryFilter177)).thenReturn(Optional.of(new CountryEntity()));
    CountryEntity countryEntity17 = new CountryEntity();
    countryEntity17.setId("nameAlreadyExistsd");
    when(countryDao.find(countryFilter17)).thenReturn(Optional.of(countryEntity17));
    // end of name already exists

    // numeric code already exists
    CountryFilter countryFilter2 = new CountryFilter();
    countryFilter2.setNumericCode("666");
    CountryFilter countryFilter22 = new CountryFilter();
    countryFilter22.setId("666");
    when(countryDao.find(countryFilter22)).thenReturn(Optional.of(new CountryEntity()));
    CountryEntity countryEntity22 = new CountryEntity();
    countryEntity22.setId("6666");
    when(countryDao.find(countryFilter2)).thenReturn(Optional.of(countryEntity22));
    // end of numeric code already exists

    // iso2 already exists
    CountryFilter countryFilter3 = new CountryFilter();
    countryFilter3.setIso2("iso2AlreadyExists");
    CountryFilter countryFilter33 = new CountryFilter();
    countryFilter33.setId("iso2AlreadyExists");
    when(countryDao.find(countryFilter33)).thenReturn(Optional.of(new CountryEntity()));
    CountryEntity countryEntity33 = new CountryEntity();
    countryEntity33.setId("iso2AlreadyExistss");
    when(countryDao.find(countryFilter3)).thenReturn(Optional.of(countryEntity33));
    // end of iso2 already exists

    // iso3 already exists
    CountryFilter countryFilter4 = new CountryFilter();
    countryFilter4.setIso3("iso3AlreadyExists");
    CountryFilter countryFilter44 = new CountryFilter();
    countryFilter44.setId("iso3AlreadyExists");
    when(countryDao.find(countryFilter44)).thenReturn(Optional.of(new CountryEntity()));
    CountryEntity countryEntity44 = new CountryEntity();
    countryEntity44.setId("iso3AlreadyExistss");
    when(countryDao.find(countryFilter4)).thenReturn(Optional.of(countryEntity44));
    // end of iso3 already exists
    //
    // name already exists in pending
    AuditLogFilter auditLogFilter5 = new AuditLogFilter();
    auditLogFilter5.getCountryEntity().setName("nameAlreadyExistsInPending");
    CountryFilter countryFilter55 = new CountryFilter();
    countryFilter55.setId("nameAlreadyExistsInPending");
    when(countryDao.find(countryFilter55)).thenReturn(Optional.of(new CountryEntity()));
    AuditLogEntity auditLogEntity55 = new AuditLogEntity();
    auditLogEntity55.setEntityKey("nameAlreadyExistsInPendings");
    auditLogFilter5.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    when(auditDao.find(auditLogFilter5)).thenReturn(Optional.of(auditLogEntity55));
    // end of name already exists in pending
    //
    // numeric code already exists in pending
    AuditLogFilter auditLogFilter6 = new AuditLogFilter();
    auditLogFilter6.getCountryEntity().setNumericCode("999");
    CountryFilter countryFilter66 = new CountryFilter();
    countryFilter66.setId("999");
    when(countryDao.find(countryFilter66)).thenReturn(Optional.of(new CountryEntity()));
    auditLogFilter6.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    AuditLogEntity auditLogEntity66 = new AuditLogEntity();
    auditLogEntity66.setEntityKey("9996");
    when(auditDao.find(auditLogFilter6)).thenReturn(Optional.of(auditLogEntity66));
    // end of numeric code already exists in pending

    // iso2 already exists in pending
    AuditLogFilter auditLogFilter7 = new AuditLogFilter();
    auditLogFilter7.getCountryEntity().setIso2("iso2AlreadyExistsInPending".toUpperCase());
    CountryFilter countryFilter77 = new CountryFilter();
    countryFilter77.setId("iso2AlreadyExistsInPending");
    when(countryDao.find(countryFilter77)).thenReturn(Optional.of(new CountryEntity()));
    auditLogFilter7.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    AuditLogEntity auditLogEntity77 = new AuditLogEntity();
    auditLogEntity77.setEntityKey("iso2AlreadyExistsInPendings");
    when(auditDao.find(auditLogFilter7)).thenReturn(Optional.of(auditLogEntity77));
    // end of iso2 already exists in pending

    // iso3 already exists in pending
    AuditLogFilter auditLogFilter8 = new AuditLogFilter();
    auditLogFilter8.getCountryEntity().setIso3("iso3AlreadyExistsInPending".toUpperCase());
    CountryFilter countryFilter88 = new CountryFilter();
    countryFilter88.setId("iso3AlreadyExistsInPending");
    when(countryDao.find(countryFilter88)).thenReturn(Optional.of(new CountryEntity()));
    auditLogFilter8.setStatusList(Collections.singletonList(AuditStatus.PENDING));
    AuditLogEntity auditLogEntity88 = new AuditLogEntity();
    auditLogEntity88.setEntityKey("iso3AlreadyExistsInPendings");
    when(auditDao.find(auditLogFilter8)).thenReturn(Optional.of(auditLogEntity88));

    // end of iso3 already exists in pending

    // start of all works well
    CountryFilter countryFilter9 = new CountryFilter();
    countryFilter9.setId("shouldWorkWell");
    when(countryDao.find(countryFilter9)).thenReturn(Optional.of(new CountryEntity()));
    // end of all works well
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
  @DisplayName("Should throw exception if id missing")
  void shouldThrowExceptionIfIdMissing() {
    CountryInfo info = new CountryInfo();
    AppException exception = assertThrows(AppException.class, () -> countryRepository.update(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.ID_MISSING.getDescription(), exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if id does not exists")
  void shouldThrowExceptionIfIdDoesNotExists() {
    CountryInfo info = new CountryInfo();
    info.setId("noId");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.update(info));
    assertEquals(
        ExceptionManager.CountryError.ID_DOES_NOT_EXISTS.getDescription(), exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if that data is in pending state")
  void shouldThrowExceptionIfDataInPendingState() {
    CountryInfo masterInfo = new CountryInfo();
    masterInfo.setId("dataInPendingState");
    AppException exception =
        assertThrows(AppException.class, () -> countryRepository.update(masterInfo));
    assertEquals(
        ExceptionManager.CountryError.DATA_IN_PENDING_LIST.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if name already exists")
  void shouldThrowExceptionIfNameAlreadyExists() {
    CountryInfo info = new CountryInfo();
    info.setId("nameAlreadyExists");
    info.setName("nameAlreadyExists");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.update(info));
    assertEquals(
        ExceptionManager.CountryError.COUNTRY_NAME_ALREADY_EXISTS.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if numeric code already exists")
  void shouldThrowExceptionIfNumericCodeAlreadyExists() {
    CountryInfo info = new CountryInfo();
    info.setNumericCode("666");
    info.setId("666");
    info.setName("666");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.update(info));
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
    info.setId("iso2AlreadyExists");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.update(info));
    assertEquals(
        ExceptionManager.CountryError.ISO2_ALREADY_EXISTS.getDescription(), exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if iso3 already exists")
  void shouldThrowExceptionIfIso3AlreadyExists() {
    CountryInfo info = new CountryInfo();
    info.setId("iso3AlreadyExists");
    info.setIso3("iso3AlreadyExists");
    info.setIso2("iso3AlreadyExists");
    info.setName("iso3AlreadyExists");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.update(info));
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
    info.setId("nameAlreadyExistsInPending");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.update(info));
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
    info.setId("999");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.update(info));
    assertEquals(
        ExceptionManager.CountryError.NUMERIC_CODE_ALREADY_EXISTS_IN_PENDING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if iso2 already exists in pending")
  void shouldThrowExceptionIfIso2AlreadyExistsInPending() {
    CountryInfo info = new CountryInfo();
    info.setId("iso2AlreadyExistsInPending");
    info.setIso2("iso2AlreadyExistsInPending".toUpperCase());
    info.setIso3("iso2AlreadyExistsInPending".toUpperCase());
    info.setName("iso2AlreadyExistsInPending");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.update(info));
    assertEquals(
        ExceptionManager.CountryError.ISO2_ALREADY_EXISTS_IN_PENDING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if iso3 already exists in pending")
  void shouldThrowExceptionIfIso3AlreadyExistsInPending() {
    CountryInfo info = new CountryInfo();
    info.setId("iso3AlreadyExistsInPending");
    info.setIso3("iso3AlreadyExistsInPending".toUpperCase());
    info.setIso2("iso3AlreadyExistsInPending".toUpperCase());
    info.setName("iso3AlreadyExistsInPending");
    AppException exception = assertThrows(AppException.class, () -> countryRepository.update(info));
    assertEquals(
        ExceptionManager.CountryError.ISO3_ALREADY_EXISTS_IN_PENDING.getDescription(),
        exception.getMessage());
  }

  @Test
  @Disabled
  @DisplayName("Should work well")
  void shouldWorkWell() {
    CountryInfo info = new CountryInfo();
    info.setId("shouldWorkWell");
    info.setIso3("shouldWorkWell".toUpperCase());
    info.setIso2("shouldWorkWell".toUpperCase());
    info.setName("shouldWorkWell");
    info.setActive(true);
    assertNotNull(countryRepository.update(info));
  }
}
