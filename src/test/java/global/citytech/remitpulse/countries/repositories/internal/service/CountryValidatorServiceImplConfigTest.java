package global.citytech.remitpulse.countries.repositories.internal.service;

import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.remitpulse.countries.commons.constants.CityConstants;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.config.*;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.paymentmethod.PaymentMethod;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryEntity;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/** @author roslina */
class CountryValidatorServiceImplConfigTest {
  private static CountryValidatorServiceImpl validatorService;
  private static MasterModuleService masterModuleService;
  private static CurrencyModuleService currencyModuleService;
  private static CountryDao countryDao;

  @BeforeAll
  static void setup() {
    masterModuleService = Mockito.mock(MasterModuleService.class);
    currencyModuleService = Mockito.mock(CurrencyModuleService.class);
    countryDao = Mockito.mock(CountryDao.class);

    validatorService =
        new CountryValidatorServiceImpl(masterModuleService, currencyModuleService, countryDao);
  }

  @Test
  @DisplayName("Should throw exception if id type empty")
  void shouldThrowExceptionIfIdTypeEmpty() {
    CountryInfo info = getCountryInfo();
    info.getConfigs().setIdTypeList(Collections.EMPTY_LIST);
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateConfigs(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.ID_TYPE_NOT_FOUND.getDescription(), exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if id type code missing")
  void shouldThrowExceptionIfIdTypeCodeMissing() {
    CountryInfo info = getCountryInfo();
    IdType idType = new IdType();
    idType.setCode("");
    info.getConfigs().setIdTypeList(Collections.singletonList(idType));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateConfigs(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.ID_TYPE_CODE_MISSING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if id type format length exceed")
  @Disabled
  // disabled as validation disabled in code according to enhancement REM-1205
  void shouldThrowExceptionIfIdTypeFormatLengthExceed() {
    CountryInfo info = getCountryInfo();
    IdType idType = new IdType();
    idType.setCode("PAS");
    idType.setFormat(HelperUtils.randomAlphabets(CityConstants.ID_TYPE_FORMAT_LENGTH + 1));
    info.getConfigs().setIdTypeList(Collections.singletonList(idType));
    AppException exception =
            assertThrows(AppException.class, () -> validatorService.validateConfigs(info));
    Assertions.assertEquals(
            ExceptionManager.CountryError.INVALID_ID_TYPE_FORMAT_LENGTH.getDescription(),
            exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if id type code invalid")
  void shouldThrowExceptionIfIdTypeCodeInvalid() {
    CountryInfo info = getCountryInfo();
    IdType idType = new IdType();
    idType.setCode("ASDF");
    idType.setFormat("yyyy-mm-dd");
    info.getConfigs().setIdTypeList(Collections.singletonList(idType));
    MasterInfo masterInfo = new MasterInfo();
    masterInfo.setCode("PAS");
    when(masterModuleService.fetchMasterInfo(any()))
        .thenReturn(Collections.singletonList(masterInfo));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateConfigs(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.INVALID_ID_TYPE_CODE.getDescription(),
        exception.getMessage());
  }

  @Test
  @Disabled
  @DisplayName("Should throw exception if id type code duplicate")
  void shouldThrowExceptionIfIdTypeCodeDuplicate() {
    CountryInfo info = getCountryInfo();
    IdType idType = new IdType();
    idType.setCode("PAS");
    idType.setFormat("yyyy-mm-dd");
    IdType idType1 = new IdType();
    idType1.setCode("PAS");
    idType1.setFormat("yyyy-mm-dd");
    info.getConfigs().setIdTypeList(List.of(idType, idType1));
    MasterInfo masterInfo = new MasterInfo();
    masterInfo.setCode("PAS");
    when(masterModuleService.fetchMasterInfo(masterInfo))
        .thenReturn(Collections.singletonList(masterInfo));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateConfigs(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.DUPLICATE_ID_TYPE.getDescription(), exception.getMessage());
  }

  private CountryInfo getCountryInfo() {
    CountryInfo info = new CountryInfo();
    Config config = new Config();
    IdType idType = new IdType();
    idType.setCode("PAS");
    PurposeOfRemittance purposeOfRemittance = new PurposeOfRemittance();
    purposeOfRemittance.setCode("BUS");
    SourceOfFund sourceOfFund = new SourceOfFund();
    sourceOfFund.setCode("SAL");
    Relationship relationship = new Relationship();
    relationship.setCode("SON");
    Occupation occupation = new Occupation();
    occupation.setCode("ENG");
    config.setIdTypeList(List.of(idType));
    config.setPurposeOfRemittanceList(List.of(purposeOfRemittance));
    config.setSourceOfFundList(List.of(sourceOfFund));
    config.setRelationshipList(List.of(relationship));
    config.setOccupationList(List.of(occupation));
    info.setIso3("USA");
    info.setConfigs(config);
    return info;
  }
}
