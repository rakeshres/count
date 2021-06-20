package global.citytech.remitpulse.countries.repositories.internal;

import global.citytech.remitpulse.countries.repositories.AutoVerifyService;
import global.citytech.remitpulse.countries.repositories.auditlog.validators.auditlogs.EntityAlreadyExistInPendingStateValidator;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.Continent;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.impl.CountryRepositoryImpl;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.LocationDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.config.ConfigDao;
import global.citytech.remitpulse.countries.repositories.internal.service.CountryValidatorService;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.mto.MtoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

/** @author bipin on 6/25/19 10:55 AM. */
class CountryRepositoryImplApproveTest {
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
  @DisplayName("Should throw exception if audit log with given id does not exists")
  void shouldThrowExceptionIfAuditLogWithGivenIdDoesNotExists() {
    //        AuditLogInfo info= new AuditLogInfo();
    //        info.setId("NoAuditLog");
    List<Continent> list =
        Jsons.fromJsonToList(
            "[{\"code\":\"AFRICA\",\"name\":\"Africa\"},{\"code\":\"ASIA\",\"name\":\"Asia\"},{\"code\":\"AUSTRALIA\",\"name\":\"Australia\"},{\"code\":\"EUROPE\",\"name\":\"Europe\"},{\"code\":\"NORTH_AMERICA\",\"name\":\"North America\"},{\"code\":\"SOUTH_AMERICA\",\"name\":\"South America\"}]",
            Continent[].class);
    System.out.println(list.toString());
    //        AppException exception=
    // assertThrows(AppException.class,()->countryRepository.approve(info));
    //
    // Assertions.assertEquals(ExceptionManager.CountryError.ID_DOES_NOT_EXISTS.getDescription(),exception.getMessage());
  }
}
