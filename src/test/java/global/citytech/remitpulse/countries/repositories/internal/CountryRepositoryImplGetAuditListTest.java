package global.citytech.remitpulse.countries.repositories.internal;

import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.commons.domains.auditlog.DynamicAuditSearchCriteria;
import global.citytech.remitpulse.countries.repositories.AutoVerifyService;
import global.citytech.remitpulse.countries.repositories.auditlog.validators.auditlogs.EntityAlreadyExistInPendingStateValidator;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.impl.CountryRepositoryImpl;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.LocationDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.config.ConfigDao;
import global.citytech.remitpulse.countries.repositories.internal.service.CountryValidatorService;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.mto.MtoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

/**
 * @author sankalpa on 5/6/20
 */
public class CountryRepositoryImplGetAuditListTest {
  private static CountryRepositoryImpl countryRepository;

  @BeforeAll
  static void setup() {
    CountryDao countryDao = Mockito.mock(CountryDao.class);
    ConfigDao configDao = Mockito.mock(ConfigDao.class);
    CountryValidatorService countryValidatorService = Mockito.mock(CountryValidatorService.class);
    AuditDao auditDao = Mockito.mock(AuditDao.class);
    MasterModuleService masterModuleService = Mockito.mock(MasterModuleService.class);
    RabbitRequestContext rabbitRequestContext = Mockito.mock(RabbitRequestContext.class);
    LocationDao locationDao = Mockito.mock(LocationDao.class);

    CurrencyModuleService currencyModuleService = Mockito.mock(CurrencyModuleService.class);
    doNothing().when(countryValidatorService).validateBasicInformation(any());
    doNothing().when(countryValidatorService).validateCurrencyInformation(any());
    AutoVerifyService autoVerifyService = Mockito.mock(AutoVerifyService.class);
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
  @DisplayName("SHOULD WORK WELL")
  void shouldWorkWell() {
    assertNotNull(countryRepository.getAuditList(new DynamicAuditSearchCriteria()));
  }
}
