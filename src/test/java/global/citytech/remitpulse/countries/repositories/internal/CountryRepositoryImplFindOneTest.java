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
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryFilter;
import global.citytech.remitpulse.countries.repositories.internal.service.CountryValidatorService;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.mto.MtoService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/** @author bipin on 6/26/19 10:52 AM. */
class CountryRepositoryImplFindOneTest {
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
    CountryFilter countryFilter1 = new CountryFilter();
    countryFilter1.setIso3("workedWell");
    when(countryDao.find(countryFilter1)).thenReturn(Optional.of(new CountryEntity()));

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
  @DisplayName("Should throw exception if country id missing")
  void shouldThrowExceptionIfCountryIdMissing() {
    CountryInfo countryInfo = new CountryInfo();
    AppException exception =
        assertThrows(AppException.class, () -> countryRepository.findOne(countryInfo));
    Assertions.assertEquals(
        ExceptionManager.CountryError.ID_MISSING.getDescription(), exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if country does not exists")
  void shouldThrowExceptionIfCountryDoesNotExists() {
    CountryInfo countryInfo = new CountryInfo();
    countryInfo.setIso3("noCountry");
    AppException exception =
        assertThrows(AppException.class, () -> countryRepository.findOne(countryInfo));
    assertEquals(
        ExceptionManager.CountryError.ID_DOES_NOT_EXISTS.getDescription(), exception.getMessage());
  }

  @Test
  @Disabled
  @DisplayName("Should work well")
  void shouldWorkWell() {
    CountryInfo countryInfo = new CountryInfo();
    countryInfo.setIso3("workedWell");
    CountryInfo info = countryRepository.findOne(countryInfo);
    assertNotNull(info);
  }
}
