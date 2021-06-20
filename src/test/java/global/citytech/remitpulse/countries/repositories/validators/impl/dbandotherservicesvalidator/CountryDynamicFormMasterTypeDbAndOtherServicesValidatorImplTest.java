package global.citytech.remitpulse.countries.repositories.validators.impl.dbandotherservicesvalidator;

import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.remitpulse.countries.commons.constants.MasterConstants;
import global.citytech.remitpulse.countries.commons.constants.MasterType;
import global.citytech.remitpulse.countries.repositories.converters.CountryConverter;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.FieldInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.customfields.CustomFieldModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.OperationType;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryEntity;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;
import global.citytech.remitpulse.countries.repositories.internal.dto.CountryDynamicFormLetter;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogFilter;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryDynamicFormFilter;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/** @author bipin on 2020-03-06 10:13 */
class CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImplTest {
  private static CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImpl validator1;
  private static CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImpl validator2;

  @BeforeEach
  void setup() {
    MasterModuleService masterModuleService = Mockito.mock(MasterModuleService.class);
    CountryDynamicFormDao countryDynamicFormDao = Mockito.mock(CountryDynamicFormDao.class);
    AuditDao auditDao = Mockito.mock(AuditDao.class);
    CountryDao countryDao = Mockito.mock(CountryDao.class);

    CountryConverter countryConverter = Mockito.mock(CountryConverter.class);
    CustomFieldModuleService customFieldModuleService= Mockito.mock(CustomFieldModuleService.class);

    CountryDynamicFormFilter countryDynamicFormFilter1 = new CountryDynamicFormFilter();
    countryDynamicFormFilter1.setName("FORM_EXISTS");
    CountryDynamicFormEntity entity1= new CountryDynamicFormEntity();
    entity1.setId("FORM_EXISTS");
    when(countryDynamicFormDao.find(countryDynamicFormFilter1))
        .thenReturn(Optional.of(entity1));

    AuditLogFilter auditLogFilter2 = new AuditLogFilter();
    CountryDynamicFormEntity countryDynamicFormEntity2 = new CountryDynamicFormEntity();
    countryDynamicFormEntity2.setName("FORM_EXISTS_IN_PENDING");
    auditLogFilter2.setCountryDynamicFormEntity(countryDynamicFormEntity2);
    auditLogFilter2.setStatusList(List.of(AuditStatus.PENDING));
    AuditLogEntity auditLogEntity2= new AuditLogEntity();
    auditLogEntity2.setEntityKey("FORM_EXISTS_IN_PENDING");
    when(auditDao.find(auditLogFilter2)).thenReturn(Optional.of(auditLogEntity2));

    CountryDynamicFormFilter countryDynamicFormFilter3= new CountryDynamicFormFilter();
    countryDynamicFormFilter3.setType(MasterConstants.DynamicFormType.SENDER.getCode());
    countryDynamicFormFilter3.setCountryIso3("ACTIVE_FORM_EXISTS");
    countryDynamicFormFilter3.setEffectiveTo(HelperUtilsLocal.todayDate().plusDays(2).toString());
    countryDynamicFormFilter3.setEffectiveFrom(HelperUtilsLocal.todayDate().minusDays(5).toString());
    countryDynamicFormFilter3.setFilterType(CountryDynamicFormFilter.FilterType.DATE_OVERLAPPED.name());
    CountryDynamicFormEntity countryDynamicFormEntity3= new CountryDynamicFormEntity();
    countryDynamicFormEntity3.setId("ACTIVE_FORM_EXISTS");
    when(countryDynamicFormDao.findAll(countryDynamicFormFilter3)).thenReturn(List.of(countryDynamicFormEntity3));

    CountryFilter countryFilter5 = new CountryFilter();
    countryFilter5.setIso3("VALID_COUNTRY_ISO3");
    when(countryDao.find(countryFilter5)).thenReturn(Optional.of(getValidCountry()));



    MasterInfo masterInfo6 = new MasterInfo();
    masterInfo6.setMasterTypeList(List.of(MasterType.DYNAMIC_FORM_TYPE.getCode()));
    MasterInfo masterInfo06 = new MasterInfo();
    masterInfo06.setCode("SENDER");
    when(masterModuleService.fetchMasterInfo(masterInfo6)).thenReturn(List.of(masterInfo06));

    CountryFilter countryFilter7= new CountryFilter();
    countryFilter7.setIso3("SENDER_FORM_NOT_ALLOWED");

    CountryInfo countryInfo7= new CountryInfo();
    OperationType operationType17= new OperationType();
    operationType17.setCode(MasterConstants.OperationType.SEND.getCode());
    operationType17.setEffectiveFrom(LocalDate.now().minusDays(4).toString());
    operationType17.setEffectiveTo(LocalDate.now().minusDays(3).toString());
    countryInfo7.setOperationTypeList(List.of(operationType17));
    CountryDynamicFormLetter countryDynamicFormLetterInvalidLetter= Mockito.mock(CountryDynamicFormLetter.class);

    when(countryDynamicFormLetterInvalidLetter.getCountryInfo()).thenReturn(countryInfo7);

    CountryDynamicFormLetter countryDynamicFormLetter1 =
        Mockito.mock(CountryDynamicFormLetter.class);


    CountryInfo countryInfo8= new CountryInfo();
    OperationType operationType18= new OperationType();
    operationType18.setCode(MasterConstants.OperationType.SEND.getCode());
    operationType18.setEffectiveFrom(LocalDate.now().minusDays(4).toString());
    operationType18.setEffectiveTo(LocalDate.now().plusDays(3).toString());
    countryInfo8.setOperationTypeList(List.of(operationType18));
    when(countryDynamicFormLetter1.getCountryInfo()).thenReturn(countryInfo8);

    validator1 =
        new CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImpl(
            masterModuleService,
            countryDynamicFormDao,
            auditDao,
            countryDao,
            countryDynamicFormLetter1,
            countryConverter, customFieldModuleService);

    validator2=
        new CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImpl(
            masterModuleService,
            countryDynamicFormDao,
            auditDao,
            countryDao,
            countryDynamicFormLetterInvalidLetter,
            countryConverter, customFieldModuleService);
  }

  private CountryEntity getValidCountry() {
    CountryEntity ent = new CountryEntity();
    ent.setIso3("VALID_COUNTRY_ISO3");
    return ent;
  }

  @Test
  @DisplayName("Should throw exception if form name already exists in database")
  void shouldThrowExceptionIfFormNameAlreadyExistsInDatabase() {
    CountryDynamicFormInfo info = new CountryDynamicFormInfo();
    info.setName("FORM_EXISTS");
    AppException exception = assertThrows(AppException.class, () -> validator1.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.FORM_NAME_ALREADY_EXISTS.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if form name already exists in database in pending state")
  void shouldThrowExceptionIfFormNameAlreadyExistsInDatabaseInPendingState() {
    CountryDynamicFormInfo info = new CountryDynamicFormInfo();
    info.setName("FORM_EXISTS_IN_PENDING");
    AppException exception = assertThrows(AppException.class, () -> validator1.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.FORM_NAME_ALREADY_EXISTS_IN_PENDING
            .getDescription(),
        exception.getMessage());
  }

   @Test
  @DisplayName("Should throw exception if active form already exists for that country")
  void shouldThrowExceptionIfActiveFormAlreadyExistsForThatCountry() {
    CountryDynamicFormInfo info = new CountryDynamicFormInfo();
    info.setName("ACTIVE_FORM_EXISTS");
    info.setEffectiveFrom(HelperUtilsLocal.todayDate().minusDays(5).toString());
    info.setEffectiveTo(HelperUtilsLocal.todayDate().plusDays(2).toString());
    info.setCountryIso3("ACTIVE_FORM_EXISTS");
    info.setType((MasterConstants.DynamicFormType.SENDER.getCode()));
    AppException exception = assertThrows(AppException.class, () -> validator1.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.EFFECTIVE_FROM_EFFECTIVE_TO_SHOULD_NOT_BE_OVERLAPPED
            .getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if country is invalid")
  void shouldThrowExceptionIfCountryIsInvalid() {
    CountryDynamicFormInfo info = new CountryDynamicFormInfo();
    info.setName("INVALID_COUNTRY");
    info.setCountryIso3("INVALID_COUNTRY");
    AppException exception = assertThrows(AppException.class, () -> validator1.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.COUNTRY_ISO3_INVALID.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if form type is invalid")
  void shouldThrowExceptionIfFormTypeIsInvalid() {
    CountryDynamicFormInfo info = new CountryDynamicFormInfo();
    info.setName("FORM_TYPE_INVALID");
    info.setCountryIso3("VALID_COUNTRY_ISO3");
    info.setType("FORM_TYPE_INVALID");
    AppException exception = assertThrows(AppException.class, () -> validator1.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.INVALID_FORM_TYPE.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if Sender form is not allowed for this country")
  void shouldThrowExceptionIfSenderFormIsNotAllowedForThisCountry() {
    CountryDynamicFormInfo info = new CountryDynamicFormInfo();
    info.setName("VALID_COUNTRY_ISO3");
    info.setCountryIso3("VALID_COUNTRY_ISO3");
    info.setType(MasterConstants.DynamicFormType.SENDER.getCode());
    AppException exception = assertThrows(AppException.class, () -> validator2.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.PROVIDED_FORM_TYPE_NOT_ALLOWED_IN_THIS_COUNTRY.getDescription(),
        exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if field ids are invalid")
  void shouldThrowExceptionIfFieldIdsAreInvalid(){
    CountryDynamicFormInfo info = new CountryDynamicFormInfo();
    info.setName("VALID_COUNTRY_ISO3");
    info.setCountryIso3("VALID_COUNTRY_ISO3");
    info.setType(MasterConstants.DynamicFormType.SENDER.getCode());
    FieldInfo fieldInfo= new FieldInfo();
    fieldInfo.setFieldId("Field1");
    FieldInfo fieldInfo2= new FieldInfo();
    fieldInfo2.setFieldId("Field2");
    info.setFieldInfoList(List.of(fieldInfo,fieldInfo2));

    AppException exception = assertThrows(AppException.class, () -> validator1.validate(info));
    assertEquals(
        ExceptionManager.CountryDynamicFormError.FIELD_IDS_INVALID.getDescription(),
        exception.getMessage());

  }
}
