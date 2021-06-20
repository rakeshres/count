package global.citytech.remitpulse.countries.repositories.internal.service;

import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.*;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.OperationType;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/** @author bipin on 6/21/19 10:57 AM. */
class CountryValidatorServiceImplCurrencyTest {
  private static CountryValidatorServiceImpl validatorService;

  @BeforeAll
  static void setup() {
    MasterModuleService masterModuleService = Mockito.mock(MasterModuleService.class);
    CurrencyModuleService currencyModuleService = Mockito.mock(CurrencyModuleService.class);
    CountryDao countryDao = Mockito.mock(CountryDao.class);

    // invalid currency code test
    CurrencyInfo currencyInfo0 = new CurrencyInfo();
    currencyInfo0.setIsoAlpha("TEST_TEST");
    currencyInfo0.setDecimalValue(2);
    CurrencyInfo currencyInfo1 = new CurrencyInfo();
    currencyInfo1.setIsoAlpha("INVALID_CURRENCY_CODES");
    currencyInfo1.setIsoAlphaList(Collections.singletonList("INVALID_CURRENCY_CODE"));
    when(currencyModuleService.fetchCurrencies(currencyInfo1))
        .thenReturn(Arrays.asList(currencyInfo0, currencyInfo1));
    // end of invalid currency code test

    // no operation type
    CurrencyInfo currencyInfo2 = new CurrencyInfo();
    currencyInfo2.setIsoAlpha("NO_OPERATION_TYPE");
    currencyInfo2.setDecimalValue(2);
    CurrencyInfo currencyInfo22 = new CurrencyInfo();

    currencyInfo22.setIsoAlphaList(Collections.singletonList("NO_OPERATION_TYPE"));
    when(currencyModuleService.fetchCurrencies(currencyInfo22))
        .thenReturn(Collections.singletonList(currencyInfo2));
    // end of no operation type
    //
    // no limit provided
    CurrencyInfo currencyInfo3 = new CurrencyInfo();
    currencyInfo3.setIsoAlpha("NO_LIMIT_PROVIDED");
    currencyInfo3.setDecimalValue(2);
    CurrencyInfo currencyInfo33 = new CurrencyInfo();
    currencyInfo33.setIsoAlphaList(Collections.singletonList("NO_LIMIT_PROVIDED"));
    when(currencyModuleService.fetchCurrencies(currencyInfo33))
        .thenReturn(Collections.singletonList(currencyInfo3));
    // end of no limit provided
    //
    //    //invalid payment channel
    CurrencyInfo currencyInfo4 = new CurrencyInfo();
    currencyInfo4.setIsoAlpha("INVALID_PAYMENT_CHANNEL");
    currencyInfo4.setDecimalValue(2);
    CurrencyInfo currencyInfo44 = new CurrencyInfo();
    currencyInfo44.setIsoAlphaList(Collections.singletonList("INVALID_PAYMENT_CHANNEL"));
    when(currencyModuleService.fetchCurrencies(currencyInfo44))
        .thenReturn(Collections.singletonList(currencyInfo4));
    MasterInfo masterInfo4 = new MasterInfo();
    masterInfo4.setMasterTypeList(Collections.singletonList("INVALID_PAYMENT_CHANNEL"));
    MasterInfo masterInfo44 = new MasterInfo();
    masterInfo4.setMasterTypeList(Collections.singletonList("INVALID_PAYMENT_CHANNELS"));
    when(masterModuleService.fetchMasterInfo(masterInfo4))
        .thenReturn(Collections.singletonList(masterInfo44));
    //    //end of invalid payment channel
    //
    //
    //    //less than 0
    CurrencyInfo currencyInfo5 = new CurrencyInfo();
    currencyInfo5.setIsoAlpha("LESS_THAN_0");
    currencyInfo5.setDecimalValue(2);
    CurrencyInfo currencyInfo55 = new CurrencyInfo();
    currencyInfo55.setIsoAlphaList(Collections.singletonList("LESS_THAN_0"));
    when(currencyModuleService.fetchCurrencies(currencyInfo55))
        .thenReturn(Collections.singletonList(currencyInfo5));
    MasterInfo masterInfo5 = new MasterInfo();
    masterInfo5.setMasterTypeList(Collections.singletonList("PM"));
    MasterInfo masterInfo55 = new MasterInfo();
    masterInfo55.setMasterTypeList(Collections.singletonList("PM"));
    masterInfo55.setCode("PM");
    when(masterModuleService.fetchMasterInfo(masterInfo5))
        .thenReturn(Collections.singletonList(masterInfo55));
    //    //end of less than 0
    //
    // max should be greater than min
    CurrencyInfo currencyInfo6 = new CurrencyInfo();
    currencyInfo6.setIsoAlpha("MAX_LESS_THAN_MIN");
    currencyInfo6.setDecimalValue(2);
    CurrencyInfo currencyInfo66 = new CurrencyInfo();
    currencyInfo66.setIsoAlphaList(Collections.singletonList("MAX_LESS_THAN_MIN"));
    when(currencyModuleService.fetchCurrencies(currencyInfo66))
        .thenReturn(Collections.singletonList(currencyInfo6));
    MasterInfo masterInfo6 = new MasterInfo();
    masterInfo6.setMasterTypeList(Collections.singletonList("PM"));
    MasterInfo masterInfo66 = new MasterInfo();
    masterInfo66.setMasterTypeList(Collections.singletonList("PM"));
    masterInfo66.setCode("PM");
    when(masterModuleService.fetchMasterInfo(masterInfo66))
        .thenReturn(Collections.singletonList(masterInfo66));
    // end of max should be greater than min
    //
    //
    // invalid effective from date in limit
    CurrencyInfo currencyInfo10 = new CurrencyInfo();
    currencyInfo10.setIsoAlpha("INVALID_FROM_DATE_IN_LIMIT");
    currencyInfo10.setDecimalValue(2);
    CurrencyInfo currencyInfo1010 = new CurrencyInfo();
    currencyInfo1010.setIsoAlphaList(Collections.singletonList("INVALID_FROM_DATE_IN_LIMIT"));
    when(currencyModuleService.fetchCurrencies(currencyInfo1010))
        .thenReturn(Collections.singletonList(currencyInfo10));
    MasterInfo masterInfo10 = new MasterInfo();
    masterInfo10.setMasterTypeList(Collections.singletonList("PM"));
    MasterInfo masterInfo1010 = new MasterInfo();
    masterInfo1010.setMasterTypeList(Collections.singletonList("PM"));
    masterInfo1010.setCode("PM");
    when(masterModuleService.fetchMasterInfo(masterInfo1010))
        .thenReturn(Collections.singletonList(masterInfo1010));
    // end of effective from date in limit
    //
    //    //invalid effective to date in limit
    CurrencyInfo currencyInfo11 = new CurrencyInfo();
    currencyInfo11.setIsoAlpha("INVALID_TO_DATE_IN_LIMIT");
    currencyInfo11.setDecimalValue(2);
    CurrencyInfo currencyInfo1111 = new CurrencyInfo();
    currencyInfo1111.setIsoAlphaList(Collections.singletonList("INVALID_TO_DATE_IN_LIMIT"));
    when(currencyModuleService.fetchCurrencies(currencyInfo1111))
        .thenReturn(Collections.singletonList(currencyInfo11));
    MasterInfo masterInfo11 = new MasterInfo();
    masterInfo11.setMasterTypeList(Collections.singletonList("PM"));
    MasterInfo masterInfo1111 = new MasterInfo();
    masterInfo1111.setMasterTypeList(Collections.singletonList("PM"));
    masterInfo1111.setCode("PM");
    when(masterModuleService.fetchMasterInfo(masterInfo1010))
        .thenReturn(Collections.singletonList(masterInfo1111));
    //    //end of effective to date in limit
    //
    //
    // effective from greater than effective to
    CurrencyInfo currencyInfo7 = new CurrencyInfo();
    currencyInfo7.setIsoAlpha("FROM_GREATER_THAN_TWO");
    currencyInfo7.setDecimalValue(2);
    CurrencyInfo currencyInfo77 = new CurrencyInfo();
    currencyInfo77.setIsoAlphaList(Collections.singletonList("FROM_GREATER_THAN_TWO"));
    when(currencyModuleService.fetchCurrencies(currencyInfo77))
        .thenReturn(Collections.singletonList(currencyInfo7));
    MasterInfo masterInfo7 = new MasterInfo();
    masterInfo7.setMasterTypeList(Collections.singletonList("PM"));
    MasterInfo masterInfo77 = new MasterInfo();
    masterInfo77.setMasterTypeList(Collections.singletonList("PM"));
    masterInfo77.setCode("PM");
    when(masterModuleService.fetchMasterInfo(masterInfo77))
        .thenReturn(Collections.singletonList(masterInfo77));
    // end of effective from greater than effective to
    //
    //
    // date in limit should not be overlapped
    CurrencyInfo currencyInfo8 = new CurrencyInfo();
    currencyInfo8.setIsoAlpha("OVERLAPPED");
    currencyInfo8.setDecimalValue(2);
    CurrencyInfo currencyInfo88 = new CurrencyInfo();
    currencyInfo88.setIsoAlphaList(Collections.singletonList("OVERLAPPED"));
    when(currencyModuleService.fetchCurrencies(currencyInfo88))
        .thenReturn(Collections.singletonList(currencyInfo8));
    MasterInfo masterInfo8 = new MasterInfo();
    masterInfo8.setMasterTypeList(Collections.singletonList("PM"));
    MasterInfo masterInfo88 = new MasterInfo();
    masterInfo88.setMasterTypeList(Collections.singletonList("PM"));
    masterInfo88.setCode("PM");
    when(masterModuleService.fetchMasterInfo(masterInfo88))
        .thenReturn(Collections.singletonList(masterInfo88));
    // end of date in limit should not be overlapped
    //
    // more operation type in currency than basic page
    CurrencyInfo currencyInfo9 = new CurrencyInfo();
    currencyInfo9.setIsoAlpha("MORE_IN_CURRENCY");
    currencyInfo9.setDecimalValue(2);
    CurrencyInfo currencyInfo99 = new CurrencyInfo();
    currencyInfo99.setIsoAlphaList(Collections.singletonList("MORE_IN_CURRENCY"));
    when(currencyModuleService.fetchCurrencies(currencyInfo99))
        .thenReturn(Collections.singletonList(currencyInfo9));
    MasterInfo masterInfo9 = new MasterInfo();
    masterInfo9.setMasterTypeList(Collections.singletonList("PM"));
    MasterInfo masterInfo99 = new MasterInfo();
    masterInfo99.setMasterTypeList(Collections.singletonList("PM"));
    masterInfo99.setCode("PM");
    when(masterModuleService.fetchMasterInfo(masterInfo99))
        .thenReturn(Collections.singletonList(masterInfo99));
    // end of more operation type in currency than basic info page

    // more operation type basic page than currency page
    CurrencyInfo currencyInfo100 = new CurrencyInfo();
    currencyInfo100.setIsoAlpha("MORE_IN_BASIC");
    currencyInfo100.setDecimalValue(2);
    CurrencyInfo currencyInfo100100 = new CurrencyInfo();
    currencyInfo100100.setIsoAlphaList(Collections.singletonList("MORE_IN_BASIC"));
    when(currencyModuleService.fetchCurrencies(currencyInfo100100))
        .thenReturn(Collections.singletonList(currencyInfo100));
    MasterInfo masterInfo100 = new MasterInfo();
    masterInfo100.setMasterTypeList(Collections.singletonList("PM"));
    MasterInfo masterInfo101 = new MasterInfo();
    masterInfo101.setMasterTypeList(Collections.singletonList("PM"));
    masterInfo101.setCode("PM");
    when(masterModuleService.fetchMasterInfo(masterInfo101))
        .thenReturn(Collections.singletonList(masterInfo101));
    //    end of more operation type basic page than currency page

    // decimal value count
    CurrencyInfo currencyInfoCount = new CurrencyInfo();
    currencyInfoCount.setIsoAlpha("AUS");
    currencyInfoCount.setDecimalValue(2);

    CurrencyInfo currencyInfoCount11 = new CurrencyInfo();
    currencyInfoCount11.setIsoAlpha("NPL");
    currencyInfoCount11.setDecimalValue(3);
    CurrencyInfo currencyInfoCount1 = new CurrencyInfo();
    currencyInfoCount1.setIsoAlphaList(Arrays.asList("AUS", "NPL"));
    when(currencyModuleService.fetchCurrencies(currencyInfoCount1))
        .thenReturn(Arrays.asList(currencyInfoCount, currencyInfoCount11));
    // end of decimal value count

    validatorService = new CountryValidatorServiceImpl(masterModuleService, currencyModuleService, countryDao);
  }

  @Test
  @DisplayName("Should throw exception if currency not provided")
  void shouldThrowExceptionIfCurrencyNotProvided() {
    CountryInfo info = new CountryInfo();
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.CURRENCY_SHOULD_NOT_BE_EMPTY.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if no default currency")
  void shouldThrowExceptionIfNoDefaultCurrencyProvided() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setDefault(false);
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.DEFAULT_CURRENCY_SHOULD_BE_PROVIDED.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if multiple default currency provided")
  void shouldThrowExceptionIfMultipleDefaultCurrencyProvided() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setDefault(true);
    Currency currency1 = new Currency();
    currency1.setDefault(true);
    info.setCurrencyList(Arrays.asList(currency, currency1));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.ONE_DEFAULT_CURRENCY_SHOULD_BE_PROVIDED.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if currency code repeated")
  void shouldThrowExceptionIfCurrencyCodeRepeated() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("REPEATED_CURRENCY_CODE");
    currency.setDefault(true);

    Currency currency2 = new Currency();
    currency2.setIsoAlpha("REPEATED_CURRENCY_CODE");
    currency2.setDefault(false);
    info.setCurrencyList(Arrays.asList(currency, currency2));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.REPEATED_CURRENCY_CODE.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if currency code invalid")
  void shouldThrowExceptionIfCurrencyCodeInvalid() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("INVALID_CURRENCY_CODE");
    currency.setDefault(true);

    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.INVALID_CURRENCY_CODE.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if operation type not provided for a currency")
  void shouldThrowExceptionIfOperationTypeNotProvidedForCurrency() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("NO_OPERATION_TYPE");
    currency.setDefault(true);
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.OPERATION_TYPE_FOR_CURRENCY_MISSING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if limits not provided for given operation type")
  void shouldThrowExceptionIfLimitNotProvidedForGivenOperationType() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("NO_LIMIT_PROVIDED");
    currency.setDefault(true);
    currency.setCurrencyOperationTypeList(Collections.singletonList(new CurrencyOperationType()));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.LIMIT_NOT_PROVIDED.getDescription(), exception.getMessage());
  }

  // @Test
  @DisplayName("Should throw exception if invalid payment channel")
  void shouldThrowExceptionIfInvalidPaymentChannelProvided() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("INVALID_PAYMENT_CHANNEL");
    currency.setDefault(true);
    CurrencyOperationType cot = new CurrencyOperationType();
    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("INVALID_PAYMENT_CHANNEL");
    cot.setCurrencyLimitList(Collections.singletonList(limit));
    currency.setCurrencyOperationTypeList(Collections.singletonList(cot));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.INVALID_PAYMENT_CHANNEL.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if amount less than 0")
  void shouldThrowExceptionIfAmountLessThan0() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("LESS_THAN_0");
    currency.setDefault(true);
    CurrencyOperationType cot = new CurrencyOperationType();
    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(-3.0);
    cot.setCurrencyLimitList(Collections.singletonList(limit));
    currency.setCurrencyOperationTypeList(Collections.singletonList(cot));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.AMOUNT_LESS_THAN_ZERO.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if max less than min")
  void shouldThrowExceptionIfMaxLessThanMin() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("MAX_LESS_THAN_MIN");
    currency.setDefault(true);
    CurrencyOperationType cot = new CurrencyOperationType();
    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(3.0);
    limit.setMinimumAmount(10.2);
    cot.setCurrencyLimitList(Collections.singletonList(limit));
    currency.setCurrencyOperationTypeList(Collections.singletonList(cot));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.MAX_AMOUNT_SHOULD_BE_GREATER_THAN_MIN.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid effective from date in limit")
  void shouldThrowExceptionIfInvalidEffectiveFromDateInLimit() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("INVALID_FROM_DATE_IN_LIMIT");
    currency.setDefault(true);
    CurrencyOperationType cot = new CurrencyOperationType();
    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(30.0);
    limit.setMinimumAmount(10.2);
    limit.setEffectiveFrom("adfa");
    limit.setEffectiveTo("12-11-2012");
    cot.setCurrencyLimitList(Collections.singletonList(limit));
    currency.setCurrencyOperationTypeList(Collections.singletonList(cot));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.INVALID_EFFECTIVE_FROM_DATE.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid effective to date in limit")
  void shouldThrowExceptionIfInvalidEffectiveToDateInLimit() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("INVALID_TO_DATE_IN_LIMIT");
    currency.setDefault(true);
    CurrencyOperationType cot = new CurrencyOperationType();
    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(30.0);
    limit.setMinimumAmount(10.2);
    limit.setEffectiveFrom("2012-12-12");
    limit.setEffectiveTo("asdfa");
    cot.setCurrencyLimitList(Collections.singletonList(limit));
    currency.setCurrencyOperationTypeList(Collections.singletonList(cot));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.INVALID_EFFECTIVE_TO_DATE.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if effective from greater than effective to")
  void shouldThrowExceptionIfEffectiveFromGreaterThanEffectiveTo() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("FROM_GREATER_THAN_TWO");
    currency.setDefault(true);
    CurrencyOperationType cot = new CurrencyOperationType();
    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(12.0);
    limit.setMinimumAmount(10.2);
    limit.setEffectiveFrom("2030-12-12");
    limit.setEffectiveTo("2030-12-11");
    cot.setCurrencyLimitList(Collections.singletonList(limit));
    currency.setCurrencyOperationTypeList(Collections.singletonList(cot));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.EFFECTIVE_TO_SHOULD_BE_AFTER_EFFECTIVE_FROM.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if date overlapped in limit")
  void shouldThrowExceptionIfDateOverlappedInLimit() {
    CountryInfo info = new CountryInfo();
    Currency currency = new Currency();
    currency.setIsoAlpha("OVERLAPPED");
    currency.setDefault(true);
    CurrencyOperationType cot = new CurrencyOperationType();
    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(12.0);
    limit.setMinimumAmount(10.2);
    limit.setEffectiveFrom("2030-12-20");
    limit.setEffectiveTo("2030-12-30");

    CurrencyLimit limit2 = new CurrencyLimit();
    limit2.setChannel("PM");
    limit2.setMaximumAmount(12.0);
    limit2.setMinimumAmount(10.2);
    limit2.setEffectiveFrom("2030-12-11");
    limit2.setEffectiveTo("2030-12-24");
    cot.setCurrencyLimitList(Arrays.asList(limit, limit2));
    currency.setCurrencyOperationTypeList(Collections.singletonList(cot));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.EFFECTIVE_FROM_EFFECTIVE_TO_SHOULD_NOT_BE_OVERLAPPED
            .getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName(
      "Should throw exception if Operation type given in currency is not mentioned in operation type in basic info page ")
  void
      shouldThrowExceptionIfOperationTypeGivenInCurrencyIsNotMentionedInOperationTypeInBasicInfo() {
    CountryInfo info = new CountryInfo();

    OperationType operationType = new OperationType();
    operationType.setCode("PYT");
    info.setOperationTypeList(Collections.singletonList(operationType));
    Currency currency = new Currency();
    currency.setIsoAlpha("MORE_IN_CURRENCY");
    currency.setDefault(true);
    CurrencyOperationType cot = new CurrencyOperationType();
    cot.setCode("SND");
    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(12.0);
    limit.setMinimumAmount(10.2);
    limit.setEffectiveFrom("2030-12-27");
    limit.setEffectiveTo("2030-12-30");

    CurrencyLimit limit2 = new CurrencyLimit();
    limit2.setChannel("PM");
    limit2.setMaximumAmount(12.0);
    limit2.setMinimumAmount(10.2);
    limit2.setEffectiveFrom("2030-12-11");
    limit2.setEffectiveTo("2030-12-24");
    cot.setCurrencyLimitList(Arrays.asList(limit, limit2));
    currency.setCurrencyOperationTypeList(Collections.singletonList(cot));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError
            .OPERATION_TYPE_IN_CURRENCY_DO_NOT_MATCH_OPERATION_TYPE_IN_BASIC_INFO
            .getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName(
      "Should throw exception if Operation type given in basic info page is more than currency page")
  void shouldThrowExceptionIfOperationTypeGivenInBasicInfoPageIsMoreThanCurrencyPage() {
    CountryInfo info = new CountryInfo();

    OperationType operationType = new OperationType();
    operationType.setCode("PYT");
    OperationType operationType1 = new OperationType();
    operationType1.setCode("SND");
    info.setOperationTypeList(Arrays.asList(operationType, operationType1));
    Currency currency = new Currency();
    currency.setIsoAlpha("MORE_IN_BASIC");
    currency.setDefault(true);
    CurrencyOperationType cot = new CurrencyOperationType();
    cot.setCode("SND");
    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(12.0);
    limit.setMinimumAmount(10.2);
    limit.setEffectiveFrom("2030-12-27");
    limit.setEffectiveTo("2030-12-30");

    CurrencyLimit limit2 = new CurrencyLimit();
    limit2.setChannel("PM");
    limit2.setMaximumAmount(12.0);
    limit2.setMinimumAmount(10.2);
    limit2.setEffectiveFrom("2030-12-11");
    limit2.setEffectiveTo("2030-12-24");
    cot.setCurrencyLimitList(Arrays.asList(limit, limit2));
    currency.setCurrencyOperationTypeList(Collections.singletonList(cot));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError
            .OPERATION_TYPE_IN_CURRENCY_DO_NOT_MATCH_OPERATION_TYPE_IN_BASIC_INFO
            .getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName(
      "Should throw exception if date given in currency limit list is not within date mentioned in operation types in basic information page")
  void shouldThrowExceptionIfDateGivenInCurrencyLimitListIsNotWithinDateInOT() {
    CountryInfo info = new CountryInfo();

    OperationType operationType1 = new OperationType();
    operationType1.setCode("SND");
    operationType1.setEffectiveFrom("2019-12-20");
    operationType1.setEffectiveTo("2019-12-27");
    info.setOperationTypeList(Collections.singletonList(operationType1));
    Currency currency = new Currency();
    currency.setIsoAlpha("MORE_IN_BASIC");
    currency.setDefault(true);
    CurrencyOperationType cot = new CurrencyOperationType();
    cot.setCode("SND");
    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(12.0);
    limit.setMinimumAmount(10.2);
    limit.setEffectiveFrom("2019-12-19");
    limit.setEffectiveTo("2019-12-22");

    CurrencyLimit limit2 = new CurrencyLimit();
    limit2.setChannel("PM");
    limit2.setMaximumAmount(12.0);
    limit2.setMinimumAmount(10.2);
    limit2.setEffectiveFrom("2019-12-23");
    limit2.setEffectiveTo("2019-12-24");
    cot.setCurrencyLimitList(Arrays.asList(limit, limit2));
    currency.setCurrencyOperationTypeList(Collections.singletonList(cot));
    info.setCurrencyList(Collections.singletonList(currency));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.DATE_NOT_WITHIN_RANGE_MENTIONED_IN_OPERATION_TYPES
            .getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid decimal count for min amount")
  void shouldThrowExceptionIfInvalidDecimalCountForMinAmount() {
    CountryInfo info = new CountryInfo();

    OperationType operationType = new OperationType();
    operationType.setCode("SND");
    operationType.setEffectiveFrom("2019-12-20");
    operationType.setEffectiveTo("2019-12-27");
    info.setOperationTypeList(Collections.singletonList(operationType));

    Currency currency = new Currency();
    currency.setIsoAlpha("AUS");
    currency.setDefault(true);

    Currency currency1 = new Currency();
    currency1.setIsoAlpha("NPL");
    currency1.setDefault(false);

    CurrencyOperationType currencyOperationType = new CurrencyOperationType();
    currencyOperationType.setCode("SND");

    CurrencyOperationType currencyOperationType1 = new CurrencyOperationType();
    currencyOperationType1.setCode("SND");

    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(900.12);
    limit.setMinimumAmount(100.123);
    limit.setEffectiveFrom("2019-12-21");
    limit.setEffectiveTo("2019-12-22");

    CurrencyLimit limit2 = new CurrencyLimit();
    limit2.setChannel("PM");
    limit2.setMaximumAmount(5050.12);
    limit2.setMinimumAmount(500.123);
    limit2.setEffectiveFrom("2019-12-23");
    limit2.setEffectiveTo("2019-12-24");

    CurrencyLimit limit9 = new CurrencyLimit();
    limit9.setChannel("PM");
    limit9.setMaximumAmount(12.123);
    limit9.setMinimumAmount(10.1234);
    limit9.setEffectiveFrom("2019-12-21");
    limit9.setEffectiveTo("2019-12-22");

    CurrencyLimit limit8 = new CurrencyLimit();
    limit8.setChannel("PM");
    limit8.setMaximumAmount(12.123);
    limit8.setMinimumAmount(10.1234);
    limit8.setEffectiveFrom("2019-12-23");
    limit8.setEffectiveTo("2019-12-24");

    currencyOperationType.setCurrencyLimitList(Arrays.asList(limit, limit2));
    currency.setCurrencyOperationTypeList(Collections.singletonList(currencyOperationType));

    currencyOperationType1.setCurrencyLimitList(Arrays.asList(limit8, limit9));
    currency1.setCurrencyOperationTypeList(Collections.singletonList(currencyOperationType1));
    info.setCurrencyList(Arrays.asList(currency, currency1));
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
        ExceptionManager.CountryError.INVALID_DECIMAL_VALUE_COUNT_IN_MINIMUM_AMOUNT
            .getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid decimal count for max amount")
  void shouldThrowExceptionIfInvalidDecimalCountForMaxAmount() {
    CountryInfo info = new CountryInfo();

    OperationType operationType = new OperationType();
    operationType.setCode("SND");
    operationType.setEffectiveFrom("2019-12-20");
    operationType.setEffectiveTo("2019-12-27");
    info.setOperationTypeList(Collections.singletonList(operationType));

    Currency currency = new Currency();
    currency.setIsoAlpha("AUS");
    currency.setDefault(true);

    Currency currency1 = new Currency();
    currency1.setIsoAlpha("NPL");
    currency1.setDefault(false);

    CurrencyOperationType currencyOperationType = new CurrencyOperationType();
    currencyOperationType.setCode("SND");

    CurrencyOperationType currencyOperationType1 = new CurrencyOperationType();
    currencyOperationType1.setCode("SND");

    CurrencyLimit limit = new CurrencyLimit();
    limit.setChannel("PM");
    limit.setMaximumAmount(900.123);
    limit.setMinimumAmount(100.12);
    limit.setEffectiveFrom("2019-12-21");
    limit.setEffectiveTo("2019-12-22");

    CurrencyLimit limit2 = new CurrencyLimit();
    limit2.setChannel("PM");
    limit2.setMaximumAmount(5050.123);
    limit2.setMinimumAmount(500.12);
    limit2.setEffectiveFrom("2019-12-23");
    limit2.setEffectiveTo("2019-12-24");

    CurrencyLimit limit9 = new CurrencyLimit();
    limit9.setChannel("PM");
    limit9.setMaximumAmount(12.1234);
    limit9.setMinimumAmount(10.123);
    limit9.setEffectiveFrom("2019-12-21");
    limit9.setEffectiveTo("2019-12-22");

    CurrencyLimit limit8 = new CurrencyLimit();
    limit8.setChannel("PM");
    limit8.setMaximumAmount(12.1234);
    limit8.setMinimumAmount(10.123);
    limit8.setEffectiveFrom("2019-12-23");
    limit8.setEffectiveTo("2019-12-24");

    currencyOperationType.setCurrencyLimitList(Arrays.asList(limit, limit2));
    currency.setCurrencyOperationTypeList(Collections.singletonList(currencyOperationType));

    currencyOperationType1.setCurrencyLimitList(Arrays.asList(limit8, limit9));
    currency1.setCurrencyOperationTypeList(Collections.singletonList(currencyOperationType1));
    info.setCurrencyList(Arrays.asList(currency, currency1));
    AppException exception =
            assertThrows(AppException.class, () -> validatorService.validateCurrencyInformation(info));
    assertEquals(
            ExceptionManager.CountryError.INVALID_DECIMAL_VALUE_COUNT_IN_MAXIMUM_AMOUNT
                    .getDescription(),
            exception.getMessage());
  }
}
