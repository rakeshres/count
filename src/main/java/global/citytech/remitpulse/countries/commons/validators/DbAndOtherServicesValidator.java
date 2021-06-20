package global.citytech.remitpulse.countries.commons.validators;

import global.citytech.rabbit.core.commons.Validator;

import java.util.logging.Logger;

/** @author sunil */
public interface DbAndOtherServicesValidator<T> extends Validator<T> {
  Logger LOGGER = Logger.getLogger(DbAndOtherServicesValidator.class.getName());

  @Override
  default void validate(T infoToValidate) {

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

  default void fieldExistenceInLocalDbValidation(T infoToValidate) {}

  default void fieldValueValidationWithLocalDbRecords(T infoToValidate) {}

  default void duplicateValidation(T infoToValidate) {}

  default void thirdPartyFieldExistenceValidation(T infoToValidate) {}

  default void fieldValueValidationWithThirdPartyData(T infoToValidate) {}
}
