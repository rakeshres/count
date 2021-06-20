package global.citytech.remitpulse.countries.repositories.internal.service;

import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.paymentmethod.PaymentMethod;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/** @author roslina */
class CountryValidatorServiceImplPaymentMethodTest {
  private static CountryValidatorServiceImpl validatorService;

  @BeforeAll
  static void setup() {
    MasterModuleService masterModuleService = Mockito.mock(MasterModuleService.class);
    CurrencyModuleService currencyModuleService = Mockito.mock(CurrencyModuleService.class);
    CountryDao countryDao = Mockito.mock(CountryDao.class);

    validatorService = new CountryValidatorServiceImpl(masterModuleService, currencyModuleService, countryDao);
  }

  @Test
  @Disabled
  @DisplayName("Should throw exception if payment method not provided")
  void shouldThrowExceptionIfPaymentMethodNotProvided() {
    CountryInfo info = new CountryInfo();
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validatePaymentMethods(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.PAYMENT_METHOD_SHOULD_NOT_BE_EMPTY.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if channel not provided")
  void shouldThrowExceptionIfChannelNotProvided() {
    CountryInfo info = new CountryInfo();
    List<PaymentMethod> paymentMethodList = new ArrayList<>();
    paymentMethodList.add(new PaymentMethod());
    info.setPaymentMethodList(paymentMethodList);
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validatePaymentMethods(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.PAYMENT_CHANNEL_MISSING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid channel provided")
  void shouldThrowExceptionIfInvalidChannelProvided() {
    CountryInfo info = new CountryInfo();
    List<PaymentMethod> paymentMethodList = new ArrayList<>();
    PaymentMethod paymentMethod = new PaymentMethod();
    paymentMethod.setChannel("ASDF");
    paymentMethodList.add(paymentMethod);
    info.setPaymentMethodList(paymentMethodList);
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validatePaymentMethods(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.INVALID_PAYMENT_CHANNEL.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if title not provided")
  void shouldThrowExceptionIfTitleNotProvided() {
    CountryInfo info = new CountryInfo();
    List<PaymentMethod> paymentMethodList = new ArrayList<>();
    PaymentMethod paymentMethod = new PaymentMethod();
    paymentMethod.setChannel("BNK");
    paymentMethodList.add(paymentMethod);
    info.setPaymentMethodList(paymentMethodList);
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validatePaymentMethods(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.PAYMENT_TITLE_MISSING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid title length")
  void shouldThrowExceptionIfInvalidTitleLength() {
    CountryInfo info = new CountryInfo();
    List<PaymentMethod> paymentMethodList = new ArrayList<>();
    PaymentMethod paymentMethod = new PaymentMethod();
    paymentMethod.setChannel("BNK");
    paymentMethod.setTitle("Nepal Investment Bank Limited");
    paymentMethodList.add(paymentMethod);
    info.setPaymentMethodList(paymentMethodList);
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validatePaymentMethods(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.PAYMENT_TITLE_LENGTH_EXCEED.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if code not provided")
  void shouldThrowExceptionIfCodeNotProvided() {
    CountryInfo info = new CountryInfo();
    List<PaymentMethod> paymentMethodList = new ArrayList<>();
    PaymentMethod paymentMethod = new PaymentMethod();
    paymentMethod.setChannel("BNK");
    paymentMethod.setTitle("NIBL");
    paymentMethodList.add(paymentMethod);
    info.setPaymentMethodList(paymentMethodList);
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validatePaymentMethods(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.PAYMENT_CODE_MISSING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid code length")
  void shouldThrowExceptionIfInvalidCodeLength() {
    CountryInfo info = new CountryInfo();
    List<PaymentMethod> paymentMethodList = new ArrayList<>();
    PaymentMethod paymentMethod = new PaymentMethod();
    paymentMethod.setChannel("BNK");
    paymentMethod.setTitle("NIBL");
    paymentMethod.setCode("NIBL1234567");
    paymentMethodList.add(paymentMethod);
    info.setPaymentMethodList(paymentMethodList);
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validatePaymentMethods(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.PAYMENT_CODE_LENGTH_EXCEED.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid code")
  void shouldThrowExceptionIfInvalidCode() {
    CountryInfo info = new CountryInfo();
    List<PaymentMethod> paymentMethodList = new ArrayList<>();
    PaymentMethod paymentMethod = new PaymentMethod();
    paymentMethod.setChannel("BNK");
    paymentMethod.setTitle("NIBL");
    paymentMethod.setCode("NIBL@123");
    paymentMethodList.add(paymentMethod);
    info.setPaymentMethodList(paymentMethodList);
    AppException exception =
        assertThrows(AppException.class, () -> validatorService.validatePaymentMethods(info));
    Assertions.assertEquals(
        ExceptionManager.CountryError.INVALID_PAYMENT_CODE.getDescription(),
        exception.getMessage());
  }
}
