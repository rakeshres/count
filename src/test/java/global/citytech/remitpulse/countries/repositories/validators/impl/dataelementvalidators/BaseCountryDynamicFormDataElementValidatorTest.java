package global.citytech.remitpulse.countries.repositories.validators.impl.dataelementvalidators;

import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.remitpulse.countries.repositories.constants.CountryDynamicFormLengthConstants;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.FieldInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.enterprise.context.Dependent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** @author bipin on 2020-03-09 11:07 */
class BaseCountryDynamicFormDataElementValidatorTest {
  private BaseCountryDynamicFormDataElementValidator validator;

  @BeforeEach
  void setup() {
    validator = Mockito.spy(new BaseCountryDynamicFormDataElementValidator() {});
  }

  @Test
  @DisplayName("Should throw exception if form name is missing")
  void shouldThrowExceptionIfFormNameIsMissing() {
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setName("");
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.FORM_NAME_MISSING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if country iso3 is missing")
  void shouldThrowExceptionIfCountryIso3IsMissing() {
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setCountryIso3("");
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryError.COUNTRY_NAME_MISSING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if form type is missing")
  void shouldThrowExceptionIfFormTypeIsMissing() {
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setType("");
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.FORM_TYPE_MISSING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if effective from is missing")
  void shouldThrowExceptionIfEffectiveFromIsMissing() {
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setEffectiveFrom("");
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.EFFECTIVE_FROM_DATE_MISSING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if effective to is missing")
  void shouldThrowExceptionIfEffectiveToIsMissing() {
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setEffectiveTo("");
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.EFFECTIVE_TO_DATE_MISSING.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if field info is empty")
  void shouldThrowExceptionIfFieldInfoIsEmpty() {
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setFieldInfoList(List.of());
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.FIELDS_CANNOT_BE_EMPTY.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if form name length exceeds")
  void shouldThrowExceptionIfFormNameLengthExceeds(){
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setName(HelperUtils.randomAlphabets(CountryDynamicFormLengthConstants.NAME_MAX_LENGTH.getLength()+1));
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.FORM_NAME_LENGTH_EXCEEDED.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid effective from date format")
  void shouldThrowExceptionIfInvalidEffectiveFromDate(){
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setEffectiveFrom("asdfadf");
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryError.INVALID_EFFECTIVE_FROM_DATE.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if invalid effective to date format")
  void shouldThrowExceptionIfInvalidEffectiveToDate(){
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setEffectiveTo("asdfadf");
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryError.INVALID_EFFECTIVE_TO_DATE.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if effective from date is before today")
  void shouldThrowExceptionIfEffectiveFromDateIsBeforeToday(){
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setEffectiveFrom(HelperUtilsLocal.todayDate().minusDays(3).toString());
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryError.EFFECTIVE_FROM_EFFECTIVE_TO_CANNOT_BE_PREVIOUS_THAN_TODAY.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if effective from date is after effective to")
  void shouldThrowExceptionIfEffectiveFromDateIsAfterEffectiveTo(){
    CountryDynamicFormInfo info = getCountryDynamicFormInfo();
    info.setEffectiveFrom(HelperUtilsLocal.todayDate().plusDays(6).toString());
    AppException exception = assertThrows(AppException.class, () -> validator.validate(info));
    assertEquals(
        ExceptionManager.CountryError.EFFECTIVE_TO_SHOULD_BE_AFTER_EFFECTIVE_FROM.getDescription(),
        exception.getMessage());
  }

  private CountryDynamicFormInfo getCountryDynamicFormInfo() {
    CountryDynamicFormInfo info = new CountryDynamicFormInfo();
    info.setName("FORMNAME");
    info.setCountryIso3("NPL");
    info.setType("SENDER");
    info.setEffectiveFrom(HelperUtilsLocal.todayDate().toString());
    info.setEffectiveTo(HelperUtilsLocal.todayDate().plusDays(5).toString());
    info.setFieldInfoList(List.of(new FieldInfo()));
    return info;
  }
}
