package global.citytech.remitpulse.countries.commons.validators;

import global.citytech.rabbit.core.commons.Validator;

import java.util.ArrayList;
import java.util.List;

/** @author sunil */
public interface ValidatorProcessor<T> extends Validator<T> {

  default List<Validator<T>> getValidators() {

    List<DataElementValidator<T>> dataElementValidators = getDataElementValidators();
    List<DbAndOtherServicesValidator<T>> dbAndOtherServicesValidators =
        getDbAndOtherServicesValidators();

    List<Validator<T>> validators = new ArrayList<>();
    validators.addAll(dataElementValidators);
    validators.addAll(dbAndOtherServicesValidators);
    return validators;
  }

  List<DataElementValidator<T>> getDataElementValidators();

  List<DbAndOtherServicesValidator<T>> getDbAndOtherServicesValidators();

  default void validate(T request) {
    this.getValidators().forEach(validator -> validator.validate(request));
  }
}
