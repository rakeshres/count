package global.citytech.remitpulse.countries.repositories.validators.impl.dbandotherservicesvalidator;

import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.commons.constants.MasterConstants;
import global.citytech.remitpulse.countries.commons.constants.MasterType;
import global.citytech.remitpulse.countries.repositories.converters.CountryConverter;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.customfields.CustomFieldModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.customfields.CustomFieldServiceInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.customfields.SingleFieldServiceInfo;
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

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** @author bipin on 2020-03-05 18:25 */
public interface BaseCountryDynamicFormMasterTypeDbAndOtherServicesValidator
    extends CountryDynamicFormMasterTypeDbAndOtherServicesValidator {

  static final Logger LOGGER =
      Logger.getLogger(BaseCountryDynamicFormMasterTypeDbAndOtherServicesValidator.class.getName());

  MasterModuleService getMasterModuleService();

  CountryDynamicFormDao getCountryDynamicFormDao();

  CountryDao getCountryDao();

  AuditDao getAuditDao();

  CountryDynamicFormLetter getCountryDynamicFormLetter();

  CountryConverter getCountryConverter();

  CustomFieldModuleService getCustomFieldModuleService();

  @Override
  default void validate(CountryDynamicFormInfo infoToValidate) {
    LOGGER.info("Running: Master type db and other services validator");
    CountryDynamicFormMasterTypeDbAndOtherServicesValidator.super.validate(infoToValidate);
  }

  @Override
  default void duplicateValidation(CountryDynamicFormInfo info) {
    LOGGER.info("STARTING DUPLICATE VALIDATION");
    checkIfFormNameAlreadyExists(info);
    checkIfFormNameAlreadyExistsInPendingState(info);
    checkIfActiveFormExistsForThatCountry(info);
    checkIfActiveFormExistsForThatCountryInPending(info);
  }

  private void checkIfFormNameAlreadyExists(CountryDynamicFormInfo info) {
    LOGGER.info("CHECKING FORM NAME IN TABLE");

    CountryDynamicFormFilter filter = new CountryDynamicFormFilter();
    filter.setName(info.getName().trim());

    Optional<CountryDynamicFormEntity> entityOpt = getCountryDynamicFormDao().find(filter);
    if (entityOpt.isPresent() && !entityOpt.get().getId().equals(info.getId())) {
      ExceptionManager.throwFormNameAlreadyExists();
    }
  }

  private void checkIfFormNameAlreadyExistsInPendingState(CountryDynamicFormInfo info) {
    LOGGER.info("CHECKING FORM NAME IN PENDING");
    AuditLogFilter filter = new AuditLogFilter();
    filter.setStatusList(List.of(AuditStatus.PENDING));
    CountryDynamicFormEntity countryDynamicFormEntity = new CountryDynamicFormEntity();
    countryDynamicFormEntity.setName(info.getName());
    filter.setCountryDynamicFormEntity(countryDynamicFormEntity);

    Optional<AuditLogEntity> entityOpt = getAuditDao().find(filter);
    if (entityOpt.isPresent() && !entityOpt.get().getEntityKey().equals(info.getId())) {
      ExceptionManager.throwFormNameAlreadyExistsInPending();
    }
  }

  private void checkIfActiveFormExistsForThatCountry(CountryDynamicFormInfo info) {
    LOGGER.info("CHECKING ACTIVE FORM IN THE TABLE");
    CountryDynamicFormFilter filter = new CountryDynamicFormFilter();
    filter.setCountryIso3(info.getCountryIso3());
    filter.setType(info.getType());
    filter.setEffectiveFrom(info.getEffectiveFrom());
    filter.setEffectiveTo(info.getEffectiveTo());
    filter.setFilterType(CountryDynamicFormFilter.FilterType.DATE_OVERLAPPED.name());

    List<CountryDynamicFormEntity> entityOpt = getCountryDynamicFormDao().findAll(filter);

    if ( (entityOpt.size()>1)
        || (entityOpt.size()==1 && !entityOpt.get(0).getId().equals(info.getId()))) {
      ExceptionManager.throwEffectiveFromAndToOverlappedWithCountryDynamicForm();
    }
  }

  private void checkIfActiveFormExistsForThatCountryInPending(CountryDynamicFormInfo info) {
    LOGGER.info("CHECKING ACTIVE FORM IN PENDING");
    AuditLogFilter filter = new AuditLogFilter();
    filter.setEntity(EntityName.COUNTRY_DYNAMIC_FORM.name());
    filter.setStatusList(List.of(AuditStatus.PENDING.getCode()));
    filter.getCountryDynamicFormEntity().setCountryIso3(info.getCountryIso3());
    filter.getCountryDynamicFormEntity().setType(info.getType());
    filter.getCountryDynamicFormEntity().setEffectiveTo(info.getEffectiveTo());
    filter.getCountryDynamicFormEntity().setEffectiveFrom(info.getEffectiveFrom());
    filter.setFilterType(CountryDynamicFormFilter.FilterType.DATE_OVERLAPPED.name());

    List<AuditLogEntity> entityOpt = getAuditDao().findAll(filter);

    if ((entityOpt.size()>1) || ( entityOpt.size()==1 && !entityOpt.get(0).getEntityKey().equals(info.getId()) )) {
      ExceptionManager.throwEffectiveFromAndToOverlappedWithCountryFormInPendingState();
    }
  }

  @Override
  default void thirdPartyFieldExistenceValidation(CountryDynamicFormInfo info) {
    checkIfCountryIso3Valid(info);
    checkIfFormTypeIsValid(info);
  }

  private void checkIfCountryIso3Valid(CountryDynamicFormInfo info) {
    CountryFilter countryFilter = new CountryFilter();
    countryFilter.setIso3(info.getCountryIso3());
    Optional<CountryEntity> countryEntityOpt = getCountryDao().find(countryFilter);
    if (countryEntityOpt.isEmpty()) {
      ExceptionManager.throwCountryIso3IsInvalid();
    }
    getCountryDynamicFormLetter()
        .setCountryInfo(getCountryConverter().toServiceObject(countryEntityOpt.get()));
  }

  private void checkIfFormTypeIsValid(CountryDynamicFormInfo info) {
    MasterInfo masterInfo = new MasterInfo();
    masterInfo.setMasterTypeList(List.of(MasterType.DYNAMIC_FORM_TYPE.getCode()));

    List<MasterInfo> masterInfoList = getMasterModuleService().fetchMasterInfo(masterInfo);
    LOGGER.info("MASTER INFO LIST FETCHED " + Jsons.toJsonObj(masterInfoList));
    if (masterInfoList.stream()
        .filter($ -> $.getCode().equals(info.getType()))
        .findFirst()
        .isEmpty()) {
      ExceptionManager.throwFormTypeIsInvalid();
    }
  }

  @Override
  default void fieldValueValidationWithThirdPartyData(CountryDynamicFormInfo info) {
    checkIfFormTypeIsValidWithinCountryInfo(info, getCountryDynamicFormLetter().getCountryInfo());
    checkIfFormFieldIdsAreValid(info);
  }

  private void checkIfFormTypeIsValidWithinCountryInfo(
      CountryDynamicFormInfo info, CountryInfo countryInfo) {
    LOGGER.info("INFO:: " + Jsons.toJsonObj(info));
    if (MasterConstants.DynamicFormType.SENDER.getCode().equals(info.getType())) {
      checkIfOperationTypeExistsInCountry(
          MasterConstants.OperationType.SEND.getCode(), countryInfo);
    }

    if (MasterConstants.DynamicFormType.BENEFICIARY.getCode().equals(info.getType())) {
      checkIfOperationTypeExistsInCountry(
          MasterConstants.OperationType.PAYOUT.getCode(), countryInfo);
    }
  }

  private void checkIfOperationTypeExistsInCountry(String code, CountryInfo countryInfo) {
    LOGGER.info(
        "COUNTRY INFO, Operation type list:: "
            + Jsons.toJsonObj(countryInfo.getOperationTypeList()));
    LOGGER.info("CODE " + code);
    Optional<OperationType> ot =
        countryInfo.getOperationTypeList().stream()
            .filter($ -> code.equals($.getCode()))
            .findFirst();

    if (ot.isEmpty()
        || !HelperUtilsLocal.isCurrentDateBetweenFromAndTo(
            ot.get().getEffectiveFrom(), ot.get().getEffectiveTo())) {
      ExceptionManager.throwProvidedFormTypeNotAllowedInThisCountry();
    }
  }

  private void checkIfFormFieldIdsAreValid(CountryDynamicFormInfo info) {
    LOGGER.info("FIELD IDS Validation");
    List<SingleFieldServiceInfo> inputFieldList =
        info.getFieldInfoList().stream()
            .map($ -> new SingleFieldServiceInfo($.getFieldId()))
            .collect(Collectors.toList());

    CustomFieldServiceInfo customFieldServiceInfo= new CustomFieldServiceInfo();
    customFieldServiceInfo.setFields(inputFieldList);

    List<CustomFieldServiceInfo> customFieldServiceInfoList =
        getCustomFieldModuleService().getCustomFieldList(customFieldServiceInfo);

    if (customFieldServiceInfoList.size() != info.getFieldInfoList().size()) {
      ExceptionManager.throwFieldIdsAreInvalid();
    }
  }
}
