package global.citytech.remitpulse.countries.commons.validators;

import global.citytech.rabbit.core.commons.Validator;

import java.util.logging.Logger;

/** @author sunil */
public interface BaseValidator<T> extends Validator<T> {
  Logger LOGGER = Logger.getLogger(BaseValidator.class.getName());

  @Override
  default void validate(T infoToValidate) {
    LOGGER.info("fieldNullOrEmptyValidation");
    this.fieldNullOrEmptyValidation(infoToValidate);

    LOGGER.info("fieldLengthValidation");
    this.fieldLengthValidation(infoToValidate);

    LOGGER.info("fieldFormatValidation");
    this.fieldFormatValidation(infoToValidate);

    LOGGER.info("fieldValueValidation");
    this.fieldValueValidation(infoToValidate);

    LOGGER.info("fieldExistenceValidation");
    this.fieldExistenceValidation(infoToValidate);

    LOGGER.info("fieldExistenceInLocalDbValidation");
    this.fieldExistenceInLocalDbValidation(infoToValidate);

    LOGGER.info("fieldValueValidationWithLocalDbRecords");
    this.fieldValueValidationWithLocalDbRecords(infoToValidate);

    LOGGER.info("duplicateValidation");
    this.duplicateValidation(infoToValidate);

    LOGGER.info("thirdPartyFieldExistenceValidation");
    this.thirdPartyFieldExistenceValidation(infoToValidate);

    LOGGER.info("fieldValueValidationWithThirdPartyData");
    this.fieldValueValidationWithThirdPartyData(infoToValidate);
  }

  default void fieldNullOrEmptyValidation(T infoToValidate) {}

  default void fieldLengthValidation(T infoToValidate) {}

  default void fieldFormatValidation(T infoToValidate) {}

  default void fieldValueValidation(T infoToValidate) {}

  default void fieldExistenceValidation(T infoToValidate) {}

  default void fieldExistenceInLocalDbValidation(T infoToValidate) {}

  default void fieldValueValidationWithLocalDbRecords(T infoToValidate) {}

  default void duplicateValidation(T infoToValidate) {}

  default void thirdPartyFieldExistenceValidation(T infoToValidate) {}

  default void fieldValueValidationWithThirdPartyData(T infoToValidate) {}
}
