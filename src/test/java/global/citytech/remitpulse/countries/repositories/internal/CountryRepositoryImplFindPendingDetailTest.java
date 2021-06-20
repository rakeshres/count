//package global.citytech.remitpulse.repositories.internal;
//
//import global.citytech.rabbit.core.audits.AuditLogInfo;
//import global.citytech.rabbit.microprofile.exceptions.AppException;
//import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
//import global.citytech.remitpulse.repositories.domains.ExceptionManager;
//import global.citytech.remitpulse.repositories.domains.country.CountryInfo;
//import global.citytech.remitpulse.repositories.domains.services.currency.CurrencyModuleService;
//import global.citytech.remitpulse.repositories.domains.services.master.MasterModuleService;
//import global.citytech.remitpulse.repositories.internal.dao.audit.AuditDao;
//import global.citytech.remitpulse.repositories.internal.dao.country.CountryDao;
//import global.citytech.remitpulse.repositories.internal.filters.AuditLogFilter;
//import global.citytech.remitpulse.repositories.internal.service.CountryValidatorService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
///** @author bipin on 6/24/19 12:13 PM. */
//class CountryRepositoryImplFindPendingDetailTest {
//  private static CountryRepositoryImpl countryRepository;
//
//  @BeforeAll
//  static void setup() {
//    CountryDao countryDao = Mockito.mock(CountryDao.class);
//    CountryValidatorService countryValidatorService = Mockito.mock(CountryValidatorService.class);
//    AuditDao auditDao = Mockito.mock(AuditDao.class);
//    MasterModuleService masterModuleService = Mockito.mock(MasterModuleService.class);
//    RabbitRequestContext rabbitRequestContext = Mockito.mock(RabbitRequestContext.class);
//    CurrencyModuleService currencyModuleService = Mockito.mock(CurrencyModuleService.class);
//
//    AuditLogFilter auditLogFilter1 = new AuditLogFilter();
//    auditLogFilter1.setId("idnotfound");
//    when(auditDao.find(auditLogFilter1)).thenReturn(Optional.empty());
//
//    countryRepository =
//        new CountryRepositoryImpl(
//            countryDao,
//            countryValidatorService,
//            auditDao,
//            masterModuleService,
//            currencyModuleService,
//            rabbitRequestContext);
//  }
//
//  @Test
//  @DisplayName("Should throw exception if id is missing")
//  void shouldThrowExceptionIfIdIsMissing() {
//    AuditLogInfo countryInfo = new AuditLogInfo();
//    AppException exception =
//        assertThrows(AppException.class, () -> countryRepository.findPendingDetail(countryInfo));
//    assertEquals(ExceptionManager.CountryError.ID_MISSING.getDescription(), exception.getMessage());
//  }
//
//  @Test
//  @DisplayName("Should throw exception if id does not exists")
//  void shouldThrowExceptionIfIdDoesNotExists() {
//    AuditLogInfo auditLogInfo = new AuditLogInfo();
//    auditLogInfo.setId("idnotfound");
//    AppException exception =
//        assertThrows(AppException.class, () -> countryRepository.findPendingDetail(auditLogInfo));
//    assertEquals(
//        ExceptionManager.CountryError.ID_DOES_NOT_EXISTS.getDescription(), exception.getMessage());
//  }
//}
