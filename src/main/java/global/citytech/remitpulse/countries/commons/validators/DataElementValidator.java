package global.citytech.remitpulse.countries.commons.validators;

import global.citytech.rabbit.core.commons.Validator;

import java.util.logging.Logger;

/** @author sunil */
public interface DataElementValidator<T> extends Validator<T> {

  Logger LOGGER = Logger.getLogger(DataElementValidator.class.getName());

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

    LOGGER.info("fieldValueValidation");
    this.fieldExistenceValidation(infoToValidate);
  }

  default void fieldNullOrEmptyValidation(T infoToValidate) {}

  default void fieldLengthValidation(T infoToValidate) {}

  default void fieldFormatValidation(T infoToValidate) {}

  default void fieldValueValidation(T infoToValidate) {}

  default void fieldExistenceValidation(T infoToValidate) {}
}
