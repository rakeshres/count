package global.citytech.remitpulse.countries.repositories.validators.impl.dataelementvalidators;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.dynamic.DynamicType;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.core.utils.LangUtils;
import global.citytech.remitpulse.countries.repositories.constants.CountryDynamicFormLengthConstants;
import global.citytech.remitpulse.countries.repositories.constants.FieldConstant;
import global.citytech.remitpulse.countries.repositories.constants.ModuleInformation;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.FieldInfo;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.SelectableValue;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.ValidationRule;

import java.util.List;
import java.util.logging.Logger;

/** @author bipin on 2020-03-05 10:22 */
public interface BaseCountryDynamicFormDataElementValidator
    extends CountryDynamicFormDataElementValidator {

  Logger LOGGER = Logger.getLogger(BaseCountryDynamicFormDataElementValidator.class.getName());

  @Override
  default void validate(CountryDynamicFormInfo infoToValidate) {
    LOGGER.info("Running: DataElement validator");
    CountryDynamicFormDataElementValidator.super.validate(infoToValidate);
  }

  @Override
  default void fieldNullOrEmptyValidation(CountryDynamicFormInfo info) {
    validateBasicInfoNullOrEmpty(info);
    // validateFieldInfos(info);
  }

  default void validateBasicInfoNullOrEmpty(CountryDynamicFormInfo info) {
    if (Strings.isNullOrEmpty(info.getName())) {
      ExceptionManager.throwFormNameIsMissing();
    }
    if (Strings.isNullOrEmpty(info.getCountryIso3())) {
      ExceptionManager.throwCountryNameMissing();
    }

    if (Strings.isNullOrEmpty(info.getType())) {
      ExceptionManager.throwFormTypeIsMissing();
    }
    if (Strings.isNullOrEmpty(info.getEffectiveFrom())) {
      ExceptionManager.throwEffectiveFromDateIsMissing();
    }

    if (Strings.isNullOrEmpty(info.getEffectiveTo())) {
      ExceptionManager.throwEffectiveToDateIsMissing();
    }

    if (info.getFieldInfoList().isEmpty()) {
      ExceptionManager.throwFormFieldsCannotBeEmpty();
    }
  }

  @Override
  default void fieldLengthValidation(CountryDynamicFormInfo info) {
    if (!Strings.isNullOrEmpty(info.getName())
        && info.getName().length() > CountryDynamicFormLengthConstants.NAME_MAX_LENGTH.getLength())
      ExceptionManager.throwFormNameLengthExceeds();
  }

  @Override
  default void fieldFormatValidation(CountryDynamicFormInfo info) {
    if (!Strings.isNullOrEmpty(info.getEffectiveFrom())
        && !HelperUtilsLocal.isValidDate(info.getEffectiveFrom())) {
      ExceptionManager.throwInvalidEffectiveFromDate();
    }

    if (!Strings.isNullOrEmpty(info.getEffectiveTo())
        && !HelperUtilsLocal.isValidDate(info.getEffectiveTo())) {
      ExceptionManager.throwInvalidEffectiveToDate();
    }
  }

  @Override
  default void fieldValueValidation(CountryDynamicFormInfo info) {
    if (!Strings.isNullOrEmpty(info.getEffectiveFrom())
        && HelperUtilsLocal.parseDate(info.getEffectiveFrom())
            .isBefore(HelperUtilsLocal.todayDate())) {
      ExceptionManager.throwEffectiveFromEffectiveToCannotBePreviousThanToday();
    }
    if (!Strings.isNullOrEmpty(info.getEffectiveFrom())
        && !Strings.isNullOrEmpty(info.getEffectiveTo())
        && HelperUtilsLocal.parseDate(info.getEffectiveFrom())
            .isAfter(HelperUtilsLocal.parseDate(info.getEffectiveTo()))) {
      ExceptionManager.throwEffectiveToShouldBeAfterEffectiveFrom();
    }

    validateFieldInfos(info);
  }

  default void validateFieldInfos(CountryDynamicFormInfo info) {
    info.getFieldInfoList().forEach(this::validateSingleField);
  }

  private void validateSingleField(FieldInfo fieldInfo) {
    LOGGER.info("Running:: validate single field");
    if (HelperUtils.isBlankOrNull(fieldInfo.getFieldId())) {
      ExceptionManager.throwFieldIdIsMissing();
    }

    validateModuleIdentifier(fieldInfo);

    if (!fieldInfo.isModifiable()) {
      this.validateDefaultValue(fieldInfo);
    }

    FieldConstant.DataType dType= null;
    try {
      dType= FieldConstant.getDataType(fieldInfo.getRenderType());
      fieldInfo.setType(dType.getDynamicType());
    } catch (IllegalArgumentException e){
      ExceptionManager.throwFieldDataTypeMissing();
    }

    if (fieldInfo.getType() == DynamicType.String) {
      this.validateFieldForStringDataType(fieldInfo);
    } else if (fieldInfo.getType() == DynamicType.Numeric) {
      this.validateFieldInfoForNumericDataType(fieldInfo);
    } else if (fieldInfo.getType() == DynamicType.Selectable) {
      this.validateFieldInfoForSelectableDataType(fieldInfo);
    }
  }

  private void validateModuleIdentifier(FieldInfo fieldInfo) {
    ModuleInformation.getByCode(fieldInfo.getModuleIdentifier());
  }

  private void validateDefaultValue(FieldInfo fieldInfo) {
    if (fieldInfo.getDefaultValue().toString().length() > 50) {
      ExceptionManager.throwDefaultTooLong();
    }
    if (fieldInfo.getType() == DynamicType.String
        && FieldConstant.DataType.NUMERIC.getCode().equals(fieldInfo.getRenderType())) {
      this.validateIfDefaultValueIsNumeric(fieldInfo.getDefaultValue().toString());
    }
  }

  private void validateIfDefaultValueIsNumeric(String value) {
    if (!LangUtils.isNumeric(value)) ExceptionManager.throwInvalidDefaultValue();
  }

  private void validateFieldForStringDataType(FieldInfo fieldInfo) {
    int minimumValue = 0;
    int maximumValue = 0;

    for (ValidationRule validationRule : fieldInfo.getValidationRules()) {
      if (validationRule.getRule().equalsIgnoreCase("minLength")) {
        minimumValue = getMinimumLength(validationRule);
      }
      if (validationRule.getRule().equalsIgnoreCase("maxLength")) {
        maximumValue = getMaximumLength(validationRule);
      }
      if (validationRule.getRule().equalsIgnoreCase("regex")) {
        validateRegex(validationRule);
      }
    }
    if (minimumValue >= maximumValue) {
      ExceptionManager.throwMaximumLengthLessShouldBeGreaterThanMinimum();
    }
  }

  private int getMinimumLength(ValidationRule validationRule) {
    int minimumValue;
    if (HelperUtils.isBlankOrNull(validationRule.getValue())) {
      ExceptionManager.throwMinimumLengthMissing();
    }
    if (!HelperUtils.isBlankOrNull(validationRule.getValue())) {
      if (!HelperUtils.isNumeric(validationRule.getValue())) {
        ExceptionManager.throwMinimumLengthNotNumeric();
      }
      if (Integer.parseInt(validationRule.getValue())
          < FieldConstant.MINIMUM_LENGTH_FOR_RULE_MIN_MAX_LENGTH) {
        ExceptionManager.throwMinimumLengthTooLess();
      }
    }
    minimumValue = Integer.parseInt(validationRule.getValue());
    return minimumValue;
  }

  private int getMaximumLength(ValidationRule validationRule) {
    int maximumValue;
    if (HelperUtils.isBlankOrNull(validationRule.getValue())) {
      ExceptionManager.throwMaximumLengthMissing();
    }
    if (!HelperUtils.isBlankOrNull(validationRule.getValue())) {
      if (!HelperUtils.isNumeric(validationRule.getValue())) {
        ExceptionManager.throwMinimumLengthNotNumeric();
      }
      if (Integer.parseInt(validationRule.getValue())
          > FieldConstant.MAXIMUM_LENGTH_FOR_RULE_MIN_MAX_LENGTH)
        ExceptionManager.throwMaximumLengthTooLong();
    }
    maximumValue = Integer.parseInt(validationRule.getValue());
    return maximumValue;
  }

  private static void validateRegex(ValidationRule validationRule) {

    if (HelperUtils.isBlankOrNull(validationRule.getValue())) {
      return;
    }

    if (!HelperUtilsLocal.isValidRegex(validationRule.getValue())) {
      ExceptionManager.throwRegexValueInvalid();
    }
  }

  private void validateFieldInfoForNumericDataType(FieldInfo fieldInfo) {
    int minimumValue = 0;
    int maximumValue = 0;
    for (ValidationRule validationRule : fieldInfo.getValidationRules()) {
      if (validationRule.getRule().equalsIgnoreCase("minValue")) {
        minimumValue = getMinimumValue(validationRule);
      }
      if (validationRule.getRule().equalsIgnoreCase("maxValue")) {
        maximumValue = getMaximumValue(validationRule);
      }

      if (validationRule.getRule().equalsIgnoreCase("roundOffAfter")) {
        getRoundOffAfter(validationRule);
      }
    }
    if (minimumValue >= maximumValue) {
      ExceptionManager.throwMaximumValueLessShouldBeGreaterThanMinimumValue();
    }
  }

  private int getMinimumValue(ValidationRule validationRule) {
    int minimumValue;
    if (HelperUtils.isBlankOrNull(validationRule.getValue())) {
      ExceptionManager.throwMinimumValueMissing();
    }
    if (!HelperUtils.isBlankOrNull(validationRule.getValue())) {
      if (!HelperUtils.isNumeric(validationRule.getValue())) {
        ExceptionManager.throwMinimumValueNotNumeric();
      }
      if (Integer.parseInt(validationRule.getValue())
          < FieldConstant.MINIMUM_LENGTH_FOR_RULE_MAX_MAX_VALUE) {
        ExceptionManager.throwMinimumValueTooLess();
      }
    }
    minimumValue = Integer.parseInt(validationRule.getValue());
    return minimumValue;
  }

  private int getMaximumValue(ValidationRule validationRule) {
    int maximumValue;
    if (HelperUtils.isBlankOrNull(validationRule.getValue())) {
      ExceptionManager.throwMaximumValueMissing();
    }
    if (!HelperUtils.isBlankOrNull(validationRule.getValue())) {
      if (!HelperUtils.isNumeric(validationRule.getValue())) {
        ExceptionManager.throwMaximumValueNotNumeric();
      }
      if (Integer.parseInt(validationRule.getValue())
          > FieldConstant.MAXIMUM_LENGTH_FOR_RULE_MAX_MAX_VALUE)
        ExceptionManager.throwMaximumValueTooLong();
    }
    maximumValue = Integer.parseInt(validationRule.getValue());
    return maximumValue;
  }

  private int getRoundOffAfter(ValidationRule validationRule) {
    int roundOffAfter;
    if (HelperUtils.isBlankOrNull(validationRule.getValue())) {
      ExceptionManager.throwRoundOffAfterMissing();
    }
    if (!HelperUtils.isBlankOrNull(validationRule.getValue())) {
      if (!HelperUtils.isNumeric(validationRule.getValue())) {
        ExceptionManager.throwRoundOffAfterNotNumeric();
      }

      if (Integer.parseInt(validationRule.getValue())
          < FieldConstant.MINIMUM_LENGTH_FOR_RULE_ROUND_OFF_AFTER) {
        ExceptionManager.throwRoundOffAfterTooLess();
      }

      if (Integer.parseInt(validationRule.getValue())
          > FieldConstant.MAXIMUM_LENGTH_FOR_RULE_ROUND_OFF_AFTER)
        ExceptionManager.throwRoundOffAfterTooLong();
    }
    roundOffAfter = Integer.parseInt(validationRule.getValue());
    return roundOffAfter;
  }

  private void validateFieldInfoForSelectableDataType(FieldInfo fieldInfo) {

    String dataSource = fieldInfo.getDataSourceId();
    List<SelectableValue> selectableValues = fieldInfo.getSelectableValues();

    if (HelperUtils.isBlankOrNull(dataSource)
        && (selectableValues == null || selectableValues.isEmpty())) {
      ExceptionManager.throwBothSelectableValuesAndDataSourceShouldNotBeEmpty();
    }

    if (!HelperUtils.isBlankOrNull(dataSource)
        && (selectableValues != null && !selectableValues.isEmpty())) {
      ExceptionManager.throwBothSelectableValuesAndDataSourceAreNotAllowed();
    }

    if ((selectableValues == null || selectableValues.isEmpty())
        && !HelperUtils.isBlankOrNull(dataSource)
    // TODO
    //    && !masterModuleService.isValidCustomFieldDataSource(dataSource)
    ) {

      ExceptionManager.throwDataSourceIsInvalid();
    }
  }
}
