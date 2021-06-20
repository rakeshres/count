package global.citytech.remitpulse.countries.repositories.domains;

import global.citytech.rabbit.core.exceptions.RabbitExceptionType;
import global.citytech.rabbit.microprofile.exceptions.AppException;

/** @author bipin on 5/20/19 3:45 PM. */
public class ExceptionManager extends AppException {

  public ExceptionManager(RabbitExceptionType rabbitExceptionType) {
    super(rabbitExceptionType);
  }

  public static void throwCustomFieldException(String message) {
    CountryDynamicFormError.CUSTOM_FIELD_EXCEPTION.update(message);
    throw new AppException(CountryDynamicFormError.CUSTOM_FIELD_EXCEPTION);
  }

  public static void throwGeneralException(String code) {
    RabbitExceptionType type = getExceptionByCode(code);
    throw new AppException(type);
  }

  public static RabbitExceptionType getExceptionByCode(String code) {
    for (CountryError roleError : CountryError.values()) {
      if (roleError.getCode().equalsIgnoreCase(code)) {
        return roleError;
      }
    }
    return CountryError.COUNTRY_NAME_MISSING;
  }

  public static void throwCountryNameMissing() {
    throw new AppException(CountryError.COUNTRY_NAME_MISSING);
  }

  public static void throwCountryNameLengthLess() {
    throw new AppException(CountryError.COUNTRY_NAME_LENGTH_LESS);
  }

  public static void throwCountryNameLengthExceeded() {
    throw new AppException(CountryError.COUNTRY_NAME_LENGTH_EXCEEDED);
  }

  public static void throwIso2Missing() {
    throw new AppException(CountryError.ISO_2_MISSING);
  }

  public static void throwIso2Invalid() {
    throw new AppException(CountryError.ISO2_INVALID);
  }

  public static void throwIso3Invalid() {
    throw new AppException(CountryError.ISO3_INVALID);
  }

  public static void throwIso3Missing() {
    throw new AppException(CountryError.ISO_3_MISSING);
  }

  public static void throwIso2LengthInvalid() {
    throw new AppException(CountryError.ISO_2_LENGTH_INVALID);
  }

  public static void throwIso3LengthInvalid() {
    throw new AppException(CountryError.ISO_3_LENGTH_INVALID);
  }

  public static void throwContinentMissing() {
    throw new AppException(CountryError.CONTINENT_MISSING);
  }

  public static void throwContinentInvalid() {
    throw new AppException(CountryError.CONTINENT_INVALID);
  }

  public static void throwSanctionFilterMoreThan100() {
    throw new AppException(CountryError.SANCTION_FILTER_MORE_THAN_100);
  }

  public static void throwCountryNameAlreadyExists() {
    throw new AppException(CountryError.COUNTRY_NAME_ALREADY_EXISTS);
  }

  public static void throwIso2AlreadyExists() {
    throw new AppException(CountryError.ISO2_ALREADY_EXISTS);
  }

  public static void throwIso3AlreadyExists() {
    throw new AppException(CountryError.ISO3_ALREADY_EXISTS);
  }

  public static void throwNumericCodeAlreadyExists() {
    throw new AppException(CountryError.NUMERIC_CODE_ALREADY_EXISTS);
  }

  public static void throwCountryNameAlreadyExistsInPending() {
    throw new AppException(CountryError.COUNTRY_NAME_ALREADY_EXISTS_IN_PENDING);
  }

  public static void throwIso2AlreadyExistsInPending() {
    throw new AppException(CountryError.ISO2_ALREADY_EXISTS_IN_PENDING);
  }

  public static void throwIso3AlreadyExistsInPending() {
    throw new AppException(CountryError.ISO3_ALREADY_EXISTS_IN_PENDING);
  }

  public static void throwNumericCodeAlreadyExistsInPending() {
    throw new AppException(CountryError.NUMERIC_CODE_ALREADY_EXISTS_IN_PENDING);
  }

  public static void throwInvalidOperationType() {
    throw new AppException(CountryError.INVALID_OPERATION_TYPE);
  }

  public static void throwInvalidEffectiveFromDate() {
    throw new AppException(CountryError.INVALID_EFFECTIVE_FROM_DATE);
  }

  public static void throwCurrencyLimitDateNotWithinRangeMentionedInOperationTypes() {
    throw new AppException(CountryError.DATE_NOT_WITHIN_RANGE_MENTIONED_IN_OPERATION_TYPES);
  }

  public static void throwInvalidEffectiveToDate() {
    throw new AppException(CountryError.INVALID_EFFECTIVE_TO_DATE);
  }

  public static void throwInvalidCurrencyCode() {
    throw new AppException(CountryError.INVALID_CURRENCY_CODE);
  }

  public static void throwEffectiveToShouldBeAfterEffectiveFrom() {
    throw new AppException(CountryError.EFFECTIVE_TO_SHOULD_BE_AFTER_EFFECTIVE_FROM);
  }

  public static void throwEffectiveFromEffectiveToCannotBePreviousThanToday() {
    throw new AppException(CountryError.EFFECTIVE_FROM_EFFECTIVE_TO_CANNOT_BE_PREVIOUS_THAN_TODAY);
  }

  public static void throwCurrencyShouldNotBeEmpty() {
    throw new AppException(CountryError.CURRENCY_SHOULD_NOT_BE_EMPTY);
  }

  public static void throwDefaultCurrencyNotProvided() {
    throw new AppException(CountryError.DEFAULT_CURRENCY_SHOULD_BE_PROVIDED);
  }

  public static void throwDefaultCurrencyShouldBeOne() {
    throw new AppException(CountryError.ONE_DEFAULT_CURRENCY_SHOULD_BE_PROVIDED);
  }

  public static void throwOperationTypeForCurrencyMissing() {
    throw new AppException(CountryError.OPERATION_TYPE_FOR_CURRENCY_MISSING);
  }

  public static void throwInvalidPaymentChannel() {
    throw new AppException(CountryError.INVALID_PAYMENT_CHANNEL);
  }

  public static void throwLimitNotProvidedForOperationType() {
    throw new AppException(CountryError.LIMIT_NOT_PROVIDED);
  }

  public static void throwAmountLessThanZero() {
    throw new AppException(CountryError.AMOUNT_LESS_THAN_ZERO);
  }

  public static void throwMaxAmountShouldBeGreaterThanMinAmount() {
    throw new AppException(CountryError.MAX_AMOUNT_SHOULD_BE_GREATER_THAN_MIN);
  }

  public static void throwEffectiveFromAndToShouldNotBeOverlapped() {
    throw new AppException(CountryError.EFFECTIVE_FROM_EFFECTIVE_TO_SHOULD_NOT_BE_OVERLAPPED);
  }

  public static void throwIdMissing() {
    throw new AppException(CountryError.ID_MISSING);
  }

  public static void throwIdDoesNotExists() {
    throw new AppException(CountryError.ID_DOES_NOT_EXISTS);
  }

  public static void throwInvalidSortParameter() {
    throw new AppException(CountryError.INVALID_SORT_PARAMETER);
  }

  public static void throwInvalidSortByParameter() {
    throw new AppException(CountryError.INVALID_SORT_BY_PARAMETER);
  }

  public static void throwDataInPendingState() {
    throw new AppException(CountryError.DATA_IN_PENDING_LIST);
  }

  public static void throwJsonError() {
    throw new AppException(CountryError.JSON_ERROR);
  }

  public static void throwRepeatedCurrencyCode() {
    throw new AppException(CountryError.REPEATED_CURRENCY_CODE);
  }

  public static void throwOperationTypeInCurrencyDoesNotMatchInOperationTyepInBasicInfo() {
    throw new AppException(
        CountryError.OPERATION_TYPE_IN_CURRENCY_DO_NOT_MATCH_OPERATION_TYPE_IN_BASIC_INFO);
  }

  public static void throwNumericCodeMissing() {
    throw new AppException(CountryError.NUMERIC_CODE_MISSING);
  }

  public static void throwNumericCodeInvalid() {
    throw new AppException(CountryError.NUMERIC_CODE_INVALID);
  }

  public static void throwSanctionFilterShouldBeUpTo2Decimal() {
    throw new AppException(CountryError.SANCTION_FILTER_DECIMAL_MORE_THAN_TWO);
  }

  public static void throwCountryNameIsInvalid() {
    throw new AppException(CountryError.COUNTRY_NAME_INVALID);
  }

  public static void throwMakerCheckerIsSameUser() {
    throw new AppException(CountryError.MAKER_CHECKER_SAME);
  }

  public static void throwModuleIdentifierIsInvalid() {
    throw new AppException(AuditLogError.MODULE_IDENTIFIER_IS_INVALID);
  }

  public static void throwCouldNotGetModuleConfiguration() {
    throw new AppException(AuditLogError.COULD_NOT_GET_MODULE_CONFIGURATION);
  }

  public static void throwAutoVerifyConfigurationForTheModuleIsInvalid() {
    throw new AppException(AuditLogError.AUTO_VERIFY_CONFIGURATION_FOR_THE_MODULE_IS_INVALID);
  }

  public static void throwInvalidAuditLogEntity() {
    throw new AppException(AuditLogError.INVALID_AUDIT_LOG_ENTITY);
  }

  public static void throwEntityAlreadyInPendingState() {
    throw new AppException(AuditLogError.ENTITY_ALREADY_IN_PENDING_STATE);
  }

  public static void throwInvalidDecimalValueCountInMinAmount() {
    throw new AppException(CountryError.INVALID_DECIMAL_VALUE_COUNT_IN_MINIMUM_AMOUNT);
  }

  public static void throwInvalidDecimalValueCountInMaxAmount() {
    throw new AppException(CountryError.INVALID_DECIMAL_VALUE_COUNT_IN_MAXIMUM_AMOUNT);
  }

  public static void throwFormNameIsMissing() {
    throw new AppException(CountryDynamicFormError.FORM_NAME_MISSING);
  }

  public static void throwFormTypeIsMissing() {
    throw new AppException(CountryDynamicFormError.FORM_TYPE_MISSING);
  }

  public static void throwEffectiveFromDateIsMissing() {
    throw new AppException(CountryDynamicFormError.EFFECTIVE_FROM_DATE_MISSING);
  }

  public static void throwEffectiveToDateIsMissing() {
    throw new AppException(CountryDynamicFormError.EFFECTIVE_TO_DATE_MISSING);
  }

  public static void throwFormFieldsCannotBeEmpty() {
    throw new AppException(CountryDynamicFormError.FIELDS_CANNOT_BE_EMPTY);
  }

  public static void throwFieldIdIsMissing() {
    throw new AppException(CountryDynamicFormError.FIELD_ID_MISSING);
  }

  public static void throwLabelTooLong() {
    throw new AppException(CountryDynamicFormError.LABEL_TOO_LONG);
  }

  public static void throwDefaultTooLong() {
    throw new AppException(CountryDynamicFormError.DEFAULT_VALUE_TOO_LONG);
  }

  public static void throwInvalidDefaultValue() {
    throw new AppException(CountryDynamicFormError.INVALID_DEFAULT_VALUE);
  }

  public static void throwFieldDataTypeMissing() {
    throw new AppException(CountryDynamicFormError.FIELD_DATA_TYPE_MISSING);
  }

  public static void throwMaximumLengthLessShouldBeGreaterThanMinimum() {
    throw new AppException(CountryDynamicFormError.MAXIMUM_LENGTH_SHOULD_BE_GREATER_THAN_MINIMUM);
  }

  public static void throwMinimumLengthMissing() {
    throw new AppException(CountryDynamicFormError.MINIMUM_LENGTH_MISSING);
  }

  public static void throwMinimumLengthNotNumeric() {
    throw new AppException(CountryDynamicFormError.MINIMUM_LENGTH_SHOULD_BE_NUMERIC);
  }

  public static void throwMinimumLengthTooLess() {
    throw new AppException(CountryDynamicFormError.MINIMUM_LENGTH_SHOULD_BE_GREATER_THAN_MIN_LIMIT);
  }

  public static void throwMaximumLengthMissing() {
    throw new AppException(CountryDynamicFormError.MAXIMUM_LENGTH_MISSING);
  }

  public static void throwMaximumLengthTooLong() {
    throw new AppException(CountryDynamicFormError.MAXIMUM_LENGTH_SHOULD_BE_LESS_THAN_MAX_LIMIT);
  }

  public static void throwRegexValueInvalid() {
    throw new AppException(CountryDynamicFormError.REGEX_VALUE_INVALID);
  }

  public static void throwMinimumValueMissing() {
    throw new AppException(CountryDynamicFormError.MINIMUM_VALUE_MISSING);
  }

  public static void throwMinimumValueNotNumeric() {
    throw new AppException(CountryDynamicFormError.MINIMUM_VALUE_SHOULD_BE_NUMERIC);
  }

  public static void throwMinimumValueTooLess() {
    throw new AppException(CountryDynamicFormError.MINIMUM_VALUE_SHOULD_BE_GREATER_THAN_MIN_LENGTH);
  }

  public static void throwMaximumValueMissing() {
    throw new AppException(CountryDynamicFormError.MAXIMUM_VALUE_MISSING);
  }

  public static void throwMaximumValueNotNumeric() {
    throw new AppException(CountryDynamicFormError.MAXIMUM_VALUE_SHOULD_BE_NUMERIC);
  }

  public static void throwMaximumValueTooLong() {
    throw new AppException(CountryDynamicFormError.MAXIMUM_VALUE_SHOULD_BE_LESS_THAN_MAX_LIMIT);
  }

  public static void throwRoundOffAfterMissing() {
    throw new AppException(CountryDynamicFormError.ROUND_OFF_AFTER_MISSING);
  }

  public static void throwRoundOffAfterNotNumeric() {
    throw new AppException(CountryDynamicFormError.ROUND_OFF_AFTER_SHOULD_BE_NUMERIC);
  }

  public static void throwRoundOffAfterTooLess() {
    throw new AppException(CountryDynamicFormError.ROUND_OFF_AFTER_SHOULD_BE_GREATER_THAN_MINIMUM);
  }

  public static void throwRoundOffAfterTooLong() {
    throw new AppException(CountryDynamicFormError.ROUND_OFF_AFTER_SHOULD_BE_LESS_THAN_MAX_LENGTH);
  }

  public static void throwMaximumValueLessShouldBeGreaterThanMinimumValue() {
    throw new AppException(CountryDynamicFormError.MAXIMUM_VALUE_SHOULD_BE_GREATER_THAN_MINIMUM);
  }

  public static void throwBothSelectableValuesAndDataSourceShouldNotBeEmpty() {
    throw new AppException(
        CountryDynamicFormError.BOTH_SELECTABLE_VALUES_AND_DATA_SOURCE_SHOULD_NOT_BE_EMPTY);
  }

  public static void throwBothSelectableValuesAndDataSourceAreNotAllowed() {
    throw new AppException(
        CountryDynamicFormError.BOTH_SELECTABLE_VALUES_AND_DATA_SOURCE_ARE_NOT_ALLOWED);
  }

  public static void throwDataSourceIsInvalid() {
    throw new AppException(CountryDynamicFormError.DATA_SOURCE_IS_INVALID);
  }

  public static void throwFormNameAlreadyExists() {
    throw new AppException(CountryDynamicFormError.FORM_NAME_ALREADY_EXISTS);
  }

  public static void throwFormNameAlreadyExistsInPending() {
    throw new AppException(CountryDynamicFormError.FORM_NAME_ALREADY_EXISTS_IN_PENDING);
  }

  public static void throwCountryIso3IsInvalid() {
    throw new AppException(CountryDynamicFormError.COUNTRY_ISO3_INVALID);
  }

  public static void throwFormTypeIsInvalid() {
    throw new AppException(CountryDynamicFormError.INVALID_FORM_TYPE);
  }

  public static void throwProvidedFormTypeNotAllowedInThisCountry() {
    throw new AppException(CountryDynamicFormError.PROVIDED_FORM_TYPE_NOT_ALLOWED_IN_THIS_COUNTRY);
  }

  public static void throwFieldIdsAreInvalid() {
    throw new AppException(CountryDynamicFormError.FIELD_IDS_INVALID);
  }

  public static void throwEffectiveFromAndToOverlappedWithCountryDynamicForm() {
    throw new AppException(
        CountryDynamicFormError.EFFECTIVE_FROM_EFFECTIVE_TO_SHOULD_NOT_BE_OVERLAPPED);
  }

  public static void throwEffectiveFromAndToOverlappedWithCountryFormInPendingState() {
    throw new AppException(
        CountryDynamicFormError.EFFECTIVE_FROM_EFFECTIVE_TO_SHOULD_NOT_BE_OVERLAPPED_IN_PENDING);
  }

  public static void throwFormNameLengthExceeds() {
    throw new AppException(CountryDynamicFormError.FORM_NAME_LENGTH_EXCEEDED);
  }

  public static void throwCorridorShouldNotBeEmpty() {
    throw new AppException(CountryError.CORRIDOR_SHOULD_NOT_BE_EMPTY);
  }

  public static void throwInvalidSendCurrency() {
    throw new AppException(CountryError.INVALID_SEND_CURRENCY);
  }

  public static void throwInvalidPayoutCurrency() {
    throw new AppException(CountryError.INVALID_PAYOUT_CURRENCY);
  }

  public static void throwCountryCannotBeSameAsSetupCountry() {
    throw new AppException(CountryError.COUNTRY_CANNOT_SAME_AS_SETUP_COUNTRY);
  }

  public static void throwInvalidDestinationCountry() {
    throw new AppException(CountryError.INVALID_DESTINATION_COUNTRY);
  }

  public static void throwInvalidDestionationCurrency() {
    throw new AppException(CountryError.INVALID_DESTINATION_CURRENCY);
  }

  public static void throwInvalidOriginCountry() {
    throw new AppException(CountryError.INVALID_ORIGIN_COUNTRY);
  }

  public static void throwInvalidOriginCurrency() {
    throw new AppException(CountryError.INVALID_ORIGIN_CURRENCY);
  }

  public static void throwCorridorSendCurrencyMissing() {
    throw new AppException(CountryError.CORRIDOR_SEND_CURRENCY_ISO_ALPHA_MISSING);
  }

  public static void throwCorridorCountryMissing() {
    throw new AppException(CountryError.CORRIDOR_COUNTRY_ISO3_MISSING);
  }

  public static void throwCorridorDestinationCurrencyMissing() {
    throw new AppException(CountryError.CORRIDOR_DESTINATION_CURRENCY_ISO_ALPHA_MISSING);
  }

  public static void throwCorridorPayoutCurrencyMissing() {
    throw new AppException(CountryError.CORRIDOR_PAYOUT_CURRENCY_ISO_ALPHA_MISSING);
  }

  public static void throwCorridorOriginCurrencyMissing() {
    throw new AppException(CountryError.CORRIDOR_ORIGIN_CURRENCY_ISO_ALPHA_MISSING);
  }

  public static void throwPaymentMethodShouldNotBeEmpty() {
    throw new AppException(CountryError.PAYMENT_METHOD_SHOULD_NOT_BE_EMPTY);
  }

  public static void throwPaymentChannelMissing() {
    throw new AppException(CountryError.PAYMENT_CHANNEL_MISSING);
  }

  public static void throwPaymentTitleMissing() {
    throw new AppException(CountryError.PAYMENT_TITLE_MISSING);
  }

  public static void throwPaymentTitleLengthExceed() {
    throw new AppException(CountryError.PAYMENT_TITLE_LENGTH_EXCEED);
  }

  public static void throwPaymentCodeMissing() {
    throw new AppException(CountryError.PAYMENT_CODE_MISSING);
  }

  public static void throwPaymentCodeLengthExceed() {
    throw new AppException(CountryError.PAYMENT_CODE_LENGTH_EXCEED);
  }

  public static void throwInvalidPaymentCode() {
    throw new AppException(CountryError.INVALID_PAYMENT_CODE);
  }

  public static void throwIso3NotFound() {
    throw new AppException(CountryError.ISO3_NOT_FOUND);
  }

  public static void throwDuplicatePaymentCodeNotAllowedInPaymentMethodDetail() {
    throw new AppException(CountryError.DUPLICATE_PAYMENT_CODE_NOT_ALLOWED_IN_PAYMENT_METHOD);
  }

  public static void throwDuplicatePaymentTitleNotAllowedInPaymentMethodDetail() {
    throw new AppException(CountryError.DUPLICATE_PAYMENT_TITLE_NOT_ALLOWED_IN_PAYMENT_METHOD);
  }

  public static void throwDuplicateCorridorNotAllowed() {
    throw new AppException(CountryError.DUPLICATE_CORRIDOR_NOT_ALLOWED);
  }

  public static void throwIdTypeNotFound() {
    throw new AppException(CountryError.ID_TYPE_NOT_FOUND);
  }

  public static void throwIdTypeCodeMissing() {
    throw new AppException(CountryError.ID_TYPE_CODE_MISSING);
  }

  public static void throwInvalidIdTypeCode() {
    throw new AppException(CountryError.INVALID_ID_TYPE_CODE);
  }

  public static void throwDuplicateIdTypeNotAllowed() {
    throw new AppException(CountryError.DUPLICATE_ID_TYPE);
  }

  public static void throwPurposeOfRemittanceNotFound() {
    throw new AppException(CountryError.PURPOSE_OF_REMITTANCE_NOT_FOUND);
  }

  public static void throwPurposeOfRemittanceCodeMissing() {
    throw new AppException(CountryError.PURPOSE_OF_REMITTANCE_CODE_MISSING);
  }

  public static void throwInvalidPurposeOfRemittanceCode() {
    throw new AppException(CountryError.INVALID_PURPOSE_OF_REMITTANCE_CODE);
  }

  public static void throwDuplicatePurposeOfRemittanceNotAllowed() {
    throw new AppException(CountryError.DUPLICATE_PURPOSE_OF_REMITTANCE);
  }

  public static void throwSourceOfFundNotFound() {
    throw new AppException(CountryError.SOURCE_OF_FUND_NOT_FOUND);
  }

  public static void throwSourceOfFundCodeMissing() {
    throw new AppException(CountryError.SOURCE_OF_FUND_CODE_MISSING);
  }

  public static void throwInvalidSourceOfFundCode() {
    throw new AppException(CountryError.INVALID_SOURCE_OF_FUND_CODE);
  }

  public static void throwDuplicateSourceOfFundNotAllowed() {
    throw new AppException(CountryError.DUPLICATE_SOURCE_OF_FUND);
  }

  public static void throwRelationshipNotFound() {
    throw new AppException(CountryError.RELATIONSHIP_NOT_FOUND);
  }

  public static void throwRelationshipCodeMissing() {
    throw new AppException(CountryError.RELATIONSHIP_CODE_MISSING);
  }

  public static void throwInvalidRelationshipCode() {
    throw new AppException(CountryError.INVALID_RELATIONSHIP_CODE);
  }

  public static void throwDuplicateRelationshipNotAllowed() {
    throw new AppException(CountryError.DUPLICATE_RELATIONSHIP);
  }

  public static void throwOccupationNotFound() {
    throw new AppException(CountryError.OCCUPATION_NOT_FOUND);
  }

  public static void throwOccupationCodeMissing() {
    throw new AppException(CountryError.OCCUPATION_CODE_MISSING);
  }

  public static void throwInvalidOccupationCode() {
    throw new AppException(CountryError.INVALID_OCCUPATION_CODE);
  }

  public static void throwDuplicateOccupationNotAllowed() {
    throw new AppException(CountryError.DUPLICATE_OCCUPATION);
  }

  public static void throwInvalidIdTypeFormatLength() {
    throw new AppException(CountryError.INVALID_ID_TYPE_FORMAT_LENGTH);
  }

  public static void throwIdTypeFormatMissing() {
    throw new AppException(CountryError.ID_TYPE_FORMAT_MISSING);
  }

  public enum CountryDynamicFormError implements RabbitExceptionType {
    FORM_NAME_MISSING("CDF00000", "Form name is missing"),
    FORM_TYPE_MISSING("CDF00001", "Form type is missing"),
    EFFECTIVE_FROM_DATE_MISSING("CDF00002", "Effective from date is missing"),
    EFFECTIVE_TO_DATE_MISSING("CDF00003", "Effective to date is missing"),
    FIELDS_CANNOT_BE_EMPTY("CDF00004", "Form fields cannot be empty"),

    FORM_NAME_LENGTH_EXCEEDED("CDF00005", "Form name length exceeds"),

    CUSTOM_FIELD_EXCEPTION("CDF00006", "Custom field exception"),

    FIELD_ID_MISSING("CDF00007", "Field id is missing"),
    LABEL_TOO_LONG("CDF00008", "Label cannot be more than 50 characters"),
    DEFAULT_VALUE_TOO_LONG("CDF00009", "Default value cannot be more than 50 characters"),
    INVALID_DEFAULT_VALUE("CDF00010", "Default value is invalid"),
    FIELD_DATA_TYPE_MISSING("CDF00011", "Field data type missing"),
    MAXIMUM_LENGTH_SHOULD_BE_GREATER_THAN_MINIMUM(
        "CDF00012", "Maximum value should be greater than minimum length"),
    MINIMUM_LENGTH_MISSING("CDF00013", "Minimum length missing"),
    MAXIMUM_LENGTH_MISSING("CDF00014", "Maximum length missing"),
    MINIMUM_LENGTH_SHOULD_BE_NUMERIC("CDF00015", "Minimum length should be numeric"),
    MINIMUM_LENGTH_SHOULD_BE_GREATER_THAN_MIN_LIMIT(
        "CDF00016", "Minimum length should be greater than min limit"),
    MAXIMUM_LENGTH_SHOULD_BE_LESS_THAN_MAX_LIMIT(
        "CDF00017", "Maximum length should be less than max limit"),

    REGEX_VALUE_INVALID("CDF00018", "Regular expressing value is invalid"),
    MINIMUM_VALUE_MISSING("CDF00019", "Minimum value missing"),
    MINIMUM_VALUE_SHOULD_BE_NUMERIC("CDF00020", "Minimum value should be numeric"),
    MINIMUM_VALUE_SHOULD_BE_GREATER_THAN_MIN_LENGTH(
        "CDF00021", "Minimum value should be greater than min length"),
    MAXIMUM_VALUE_MISSING("CDF00022", "Maximum Value missing"),
    MAXIMUM_VALUE_SHOULD_BE_NUMERIC("CDF00023", "Maximum value should be numeric"),
    MAXIMUM_VALUE_SHOULD_BE_LESS_THAN_MAX_LIMIT(
        "CDF00024", "Maximum value should be less than max limit"),
    ROUND_OFF_AFTER_MISSING("CDF00025", "Round off after missing"),
    ROUND_OFF_AFTER_SHOULD_BE_NUMERIC("CDF00026", "Round off after should be numeric"),
    ROUND_OFF_AFTER_SHOULD_BE_GREATER_THAN_MINIMUM(
        "CDF00027", "Round off after should be greater than minimum length"),
    ROUND_OFF_AFTER_SHOULD_BE_LESS_THAN_MAX_LENGTH(
        "CDF00028", "Round off after should be less than max limit"),
    MAXIMUM_VALUE_SHOULD_BE_GREATER_THAN_MINIMUM(
        "CDF00029", "Maximum value should be greater than minimum length"),
    BOTH_SELECTABLE_VALUES_AND_DATA_SOURCE_SHOULD_NOT_BE_EMPTY(
        "CDF00030", "Both selectable values and data source should not be empty"),
    BOTH_SELECTABLE_VALUES_AND_DATA_SOURCE_ARE_NOT_ALLOWED(
        "CDF00031", "Both selectable values and data source are not allowed"),
    DATA_SOURCE_IS_INVALID("CDF00032", "Data source is invalid"),

    FORM_NAME_ALREADY_EXISTS("CDF00033", "Form name already exists"),
    FORM_NAME_ALREADY_EXISTS_IN_PENDING("CDF00034", "Form name already exists in pending state"),
    EFFECTIVE_FROM_EFFECTIVE_TO_SHOULD_NOT_BE_OVERLAPPED(
        "CDF00035", "Effective from and to date overlapped with already existing data"),
    EFFECTIVE_FROM_EFFECTIVE_TO_SHOULD_NOT_BE_OVERLAPPED_IN_PENDING(
        "CDF00036",
        "Effective from and to date overlapped with already existing data in pending state"),

    COUNTRY_ISO3_INVALID("CDF00037", "Country iso3 is invalid"),
    INVALID_FORM_TYPE("CDF00038", "Form type is invalid"),
    PROVIDED_FORM_TYPE_NOT_ALLOWED_IN_THIS_COUNTRY(
        "CDF00039", "Provided form type is not allowed in this country"),

    FIELD_IDS_INVALID("CDF00040", "Some of the field ids are invalid");

    private void update(String message) {
      this.description = message;
    }

    private String code;
    private String description;

    CountryDynamicFormError(String code, String description) {
      this.code = code;
      this.description = description;
    }

    @Override
    public String getCode() {
      return code;
    }

    @Override
    public String getDescription() {
      return description;
    }
  }

  public enum CountryError implements RabbitExceptionType {
    JSON_ERROR("CM0000", "Json error"),
    COUNTRY_NAME_MISSING("CM10000", "Country name missing"),
    COUNTRY_NAME_LENGTH_LESS("CM10001", "Country name length less then required"),
    COUNTRY_NAME_LENGTH_EXCEEDED("CM10002", "Country name length exceeded"),
    ISO_2_MISSING("CM10003", "Iso 2 missing"),
    ISO_3_MISSING("CM10004", "Iso 3 missing"),
    ISO_2_LENGTH_INVALID("CM10005", "Iso 2 length invalid"),
    ISO_3_LENGTH_INVALID("CM10006", "Iso 3 length invalid"),
    CONTINENT_MISSING("CM10007", "Continent is missing"),
    CONTINENT_INVALID("CM10008", "Continent is invalid"),
    SANCTION_FILTER_MORE_THAN_100("CM10009", "Sanction filter more than 100"),
    INVALID_OPERATION_TYPE("CM10010", "Invalid operation type"),
    INVALID_EFFECTIVE_FROM_DATE("CM10011", "Invalid effective from date"),
    INVALID_EFFECTIVE_TO_DATE("CM10012", "Invalid effective to date"),
    EFFECTIVE_TO_SHOULD_BE_AFTER_EFFECTIVE_FROM(
        "CM10013", "Effective to date should be after effective from date"),
    ISO2_INVALID("CM10014", "Iso2 is invalid"),
    ISO3_INVALID("CM10015", "Iso3 is invalid"),
    COUNTRY_NAME_INVALID("CM10016", "Country name is invalid"),
    ID_MISSING("CM10017", "Id is missing"),
    ID_DOES_NOT_EXISTS("CM10018", "ID does not exists"),
    NUMERIC_CODE_MISSING("CM10019", "Numeric code is missing"),
    NUMERIC_CODE_INVALID("CM10020", "Numeric code is invalid"),
    SANCTION_FILTER_DECIMAL_MORE_THAN_TWO(
        "CM10021", "Sanction filter should not contain more than 2 decimal"),
    EFFECTIVE_FROM_EFFECTIVE_TO_CANNOT_BE_PREVIOUS_THAN_TODAY(
        "CM10022", "Effective from and effective to cannot be previous than today"),
    COUNTRY_NAME_ALREADY_EXISTS("CM20000", "Country name already exists"),
    ISO2_ALREADY_EXISTS("CM20001", "Iso2 already exists"),
    ISO3_ALREADY_EXISTS("CM20002", "Iso3 already exists"),
    NUMERIC_CODE_ALREADY_EXISTS("CM20003", "Numeric code already exists"),

    COUNTRY_NAME_ALREADY_EXISTS_IN_PENDING("CM20004", "Country name already exists in pending"),
    ISO2_ALREADY_EXISTS_IN_PENDING("CM20005", "Iso2 already exists in pending"),
    ISO3_ALREADY_EXISTS_IN_PENDING("CM20006", "Iso3 already exists in pending"),
    NUMERIC_CODE_ALREADY_EXISTS_IN_PENDING("CM20007", "Numeric code already exists in pending"),

    CURRENCY_SHOULD_NOT_BE_EMPTY("CM30000", "Currency should not be empty"),
    INVALID_CURRENCY_CODE("CM30001", "Invalid currency code"),
    REPEATED_CURRENCY_CODE("CM30010", "Currency code repeated"),
    DEFAULT_CURRENCY_SHOULD_BE_PROVIDED("CM30002", "No default currency provided"),
    ONE_DEFAULT_CURRENCY_SHOULD_BE_PROVIDED(
        "CM30003", "Only one default currency should be provided"),
    OPERATION_TYPE_FOR_CURRENCY_MISSING("CM30004", "Operation type for currency is missing"),
    INVALID_PAYMENT_CHANNEL("CM30005", "Invalid payment channel"),
    LIMIT_NOT_PROVIDED("CM30006", "Limit not provided "),
    AMOUNT_LESS_THAN_ZERO("CM30007", "Amount is less than zero"),
    MAX_AMOUNT_SHOULD_BE_GREATER_THAN_MIN(
        "CM30008", "Max amount should be greater or equal to minimum amount"),
    EFFECTIVE_FROM_EFFECTIVE_TO_SHOULD_NOT_BE_OVERLAPPED(
        "CM30009", "Effective from and to should not be overlapped"),
    DATE_NOT_WITHIN_RANGE_MENTIONED_IN_OPERATION_TYPES(
        "CM30010", "Date is not within range mentioned in operation types"),
    OPERATION_TYPE_IN_CURRENCY_DO_NOT_MATCH_OPERATION_TYPE_IN_BASIC_INFO(
        "CM30011", "Operation type in currency do not match operation type in basic information"),

    INVALID_SORT_PARAMETER("CM40001", "Invalid sort parameter"),
    INVALID_SORT_BY_PARAMETER("CM40002", "Invalid sort by parameter"),

    NOT_FOUND_IN_DATABASE("CM40003", "Not found in database"),

    DATA_IN_PENDING_LIST(
        "CM40004", "This data is in pending list. Please approve or reject that first."),
    MAKER_CHECKER_SAME("CM40005", "Maker Checker Same"),
    REJECT_REMARKS_MISSING("CM40006", "Reject Remarks Missing"),
    UPDATING_AUDIT_LOG_FAILED("CM40007", "Updating Audit log Failed"),
    INVALID_DECIMAL_VALUE_COUNT_IN_MINIMUM_AMOUNT(
        "CM40008", "Minimum amount decimal is greater than specified in currency"),
    INVALID_DECIMAL_VALUE_COUNT_IN_MAXIMUM_AMOUNT(
        "CM40008", "Maximum amount decimal is greater than specified in currency"),
    CORRIDOR_SHOULD_NOT_BE_EMPTY("CM40009", "Corridor should not be empty"),
    INVALID_SEND_CURRENCY("CM40010", "Invalid send Currency"),
    INVALID_PAYOUT_CURRENCY("CM40011", "Invalid payout Currency"),
    COUNTRY_CANNOT_SAME_AS_SETUP_COUNTRY("CM40012", "Country cannot be same as setup country"),
    INVALID_DESTINATION_COUNTRY("CM40013", "Invalid destination country"),
    INVALID_DESTINATION_CURRENCY("CM40014", "Invalid destination currency"),
    INVALID_ORIGIN_COUNTRY("CM40015", "Invalid origin country"),
    INVALID_ORIGIN_CURRENCY("CM40016", "Invalid origin currency"),
    CORRIDOR_SEND_CURRENCY_ISO_ALPHA_MISSING("CM40017", "Send currency iso alpha missing"),
    CORRIDOR_COUNTRY_ISO3_MISSING("CM40018", "Country Iso3 missing"),
    CORRIDOR_DESTINATION_CURRENCY_ISO_ALPHA_MISSING(
        "CM40019", "Destination currency iso alpha missing"),
    CORRIDOR_PAYOUT_CURRENCY_ISO_ALPHA_MISSING("CM40020", "Payout currency iso alpha missing"),
    CORRIDOR_ORIGIN_CURRENCY_ISO_ALPHA_MISSING("CM40021", "Origin currency iso alpha missing"),
    PAYMENT_METHOD_SHOULD_NOT_BE_EMPTY("CM40022", "Payment method should not be empty"),
    PAYMENT_CHANNEL_MISSING("CM40023", "Channel missing"),
    PAYMENT_TITLE_MISSING("CM40024", "Title missing"),
    PAYMENT_TITLE_LENGTH_EXCEED("CM40025", "Title length exceeded"),
    PAYMENT_CODE_MISSING("CM40026", "Code missing"),
    PAYMENT_CODE_LENGTH_EXCEED("CM40027", "Code length exceeded"),
    INVALID_PAYMENT_CODE("CM40028", "Invalid payment code"),
    ISO3_NOT_FOUND("CM40029", "Iso3 not found"),
    DUPLICATE_PAYMENT_CODE_NOT_ALLOWED_IN_PAYMENT_METHOD(
        "CM40030", "Duplicate payment code not allowed in payment method"),
    DUPLICATE_PAYMENT_TITLE_NOT_ALLOWED_IN_PAYMENT_METHOD(
        "CM40031", "Duplicate payment title not allowed in payment method"),
    ID_TYPE_NOT_FOUND("CM40032", "Id type not found"),
    ID_TYPE_CODE_MISSING("CM40033", "Id type code missing"),
    INVALID_ID_TYPE_CODE("CM40034", "Invalid id type code"),
    DUPLICATE_ID_TYPE("CM40035", "Duplicate id type not allowed"),
    PURPOSE_OF_REMITTANCE_NOT_FOUND("CM40036", "Purpose of remittance not found"),
    PURPOSE_OF_REMITTANCE_CODE_MISSING("CM40037", "Purpose of remittance code missing"),
    INVALID_PURPOSE_OF_REMITTANCE_CODE("CM40038", "Invalid purpose of remittance code"),
    DUPLICATE_PURPOSE_OF_REMITTANCE("CM40039", "Duplicate purpose of remittance not allowed"),
    SOURCE_OF_FUND_NOT_FOUND("CM40040", "Source of fund not found"),
    SOURCE_OF_FUND_CODE_MISSING("CM40041", "Source of fund code missing"),
    INVALID_SOURCE_OF_FUND_CODE("CM40042", "Invalid source of fund code"),
    DUPLICATE_SOURCE_OF_FUND("CM40043", "Duplicate source of fund not allowed"),
    RELATIONSHIP_NOT_FOUND("CM40044", "Relationship not found"),
    RELATIONSHIP_CODE_MISSING("CM40045", "Relationship code missing"),
    INVALID_RELATIONSHIP_CODE("CM40046", "Invalid relationship code"),
    DUPLICATE_RELATIONSHIP("CM40047", "Duplicate relationship not allowed"),
    OCCUPATION_NOT_FOUND("CM40048", "Occupation not found"),
    OCCUPATION_CODE_MISSING("CM40049", "Occupation code missing"),
    INVALID_OCCUPATION_CODE("CM40050", "Invalid occupation code"),
    DUPLICATE_OCCUPATION("CM40051", "Duplicate occupation not allowed"),
    INVALID_ID_TYPE_FORMAT_LENGTH("CM40052", "Id type format length exceeded"),
    DUPLICATE_CORRIDOR_NOT_ALLOWED("CM40053", "Duplicate corridor not allowed"),
    ID_TYPE_FORMAT_MISSING("CM40054","Id type format is missing"),
    MASTER_DATA_NOT_FOUND("CM40055","Master data not found."),
    COUNTRY_CONFIGURATION_NOT_FOUND("CM40056","Country configuration not found.");

    private String code;
    private String description;

    CountryError(String code, String description) {
      this.code = code;
      this.description = description;
    }

    @Override
    public String getCode() {
      return this.code;
    }

    @Override
    public String getDescription() {
      return this.description;
    }
  }

  public enum AuditLogError implements RabbitExceptionType {
    MODULE_IDENTIFIER_IS_INVALID("ALE20015", "Module identifier is invalid"),
    COULD_NOT_GET_MODULE_CONFIGURATION("ALE20016", "Could not get module configuration"),
    AUTO_VERIFY_CONFIGURATION_FOR_THE_MODULE_IS_INVALID(
        "ALE20017", "Auto verify configuration for the module is invalid"),
    INVALID_AUDIT_LOG_ENTITY("ALE20008", "Invalid audit log entity"),
    ENTITY_ALREADY_IN_PENDING_STATE("ALE20009", "Entity already in pending state"),
    ;

    private String code;
    private String description;

    AuditLogError(String code, String description) {
      this.code = code;
      this.description = description;
    }

    @Override
    public String getCode() {
      return this.code;
    }

    @Override
    public String getDescription() {
      return this.description;
    }
  }
}
