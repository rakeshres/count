package global.citytech.remitpulse.countries.repositories.internal;

import global.citytech.remitpulse.countries.repositories.AutoVerifyService;
import global.citytech.remitpulse.countries.repositories.auditlog.validators.auditlogs.EntityAlreadyExistInPendingStateValidator;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfoSearchRequest;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.impl.CountryRepositoryImpl;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.LocationDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.config.ConfigDao;
import global.citytech.remitpulse.countries.repositories.internal.service.CountryValidatorService;
import global.citytech.rabbit.core.commons.PageableData;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.mto.MtoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/** @author bipin on 6/25/19 2:37 PM. */
class CountryRepositoryImplFindAllTest {
  private static CountryRepositoryImpl countryRepository;

  @BeforeAll
  static void setup() {
    CountryDao countryDao = Mockito.mock(CountryDao.class);
    ConfigDao configDao = Mockito.mock(ConfigDao.class);
    CountryValidatorService countryValidatorService = Mockito.mock(CountryValidatorService.class);
    AuditDao auditDao = Mockito.mock(AuditDao.class);
    MasterModuleService masterModuleService = Mockito.mock(MasterModuleService.class);
    CurrencyModuleService currencyModuleService = Mockito.mock(CurrencyModuleService.class);
    RabbitRequestContext rabbitRequestContext = Mockito.mock(RabbitRequestContext.class);
    AutoVerifyService autoVerifyService = Mockito.mock(AutoVerifyService.class);
    LocationDao locationDao = Mockito.mock(LocationDao.class);
    EntityAlreadyExistInPendingStateValidator<CountryInfo>
        entityAlreadyExistInPendingStateValidator =
            Mockito.mock(EntityAlreadyExistInPendingStateValidator.class);
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
  @DisplayName("Should throw exception if invalid sort parameter")
  void shouldThrowExceptionIfInvalidSortParameter() {
    CountryInfoSearchRequest request = new CountryInfoSearchRequest();
    request.setSortParameter("descs");
    AppException exception =
        assertThrows(AppException.class, () -> countryRepository.findAll(request));
    Assertions.assertEquals(
        ExceptionManager.CountryError.INVALID_SORT_PARAMETER.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid sort by parameter")
  void shouldThrowExceptionIfINvalidsortByParameter() {
    CountryInfoSearchRequest request = new CountryInfoSearchRequest();
    request.setSortParameter("desc");
    request.setSortBy("asdfasdf");
    AppException exception =
        assertThrows(AppException.class, () -> countryRepository.findAll(request));
    assertEquals(
        ExceptionManager.CountryError.INVALID_SORT_BY_PARAMETER.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should work well")
  void shouldWorkWell() {
    CountryInfoSearchRequest request = new CountryInfoSearchRequest();
    request.setSortParameter("desc");
    request.setSortBy("iso3");
    PageableData response = countryRepository.findAll(request);
    assertNotNull(response);
  }
}
