package global.citytech.remitpulse.countries.repositories.validators.impl.dataelementvalidators;

import com.google.common.base.Strings;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;

/**
 * @author bipin on 2020-03-06 17:00
 */
public class CountryDynamicFormUpdateDataElementValidatorImpl implements BaseCountryDynamicFormDataElementValidator {

  @Override
  public void validate(CountryDynamicFormInfo infoToValidate) {
    BaseCountryDynamicFormDataElementValidator.super.validate(infoToValidate);
  }

  @Override
  public void fieldNullOrEmptyValidation(CountryDynamicFormInfo info) {
    if ( Strings.isNullOrEmpty(info.getId())){
      ExceptionManager.throwIdMissing();
    }

    BaseCountryDynamicFormDataElementValidator.super.fieldNullOrEmptyValidation(info);
  }

  @Override
  public void fieldValueValidation(CountryDynamicFormInfo info) {

    if (!Strings.isNullOrEmpty(info.getEffectiveFrom())
        && !Strings.isNullOrEmpty(info.getEffectiveTo())
        && HelperUtilsLocal.parseDate(info.getEffectiveFrom())
        .isAfter(HelperUtilsLocal.parseDate(info.getEffectiveTo()))) {
      ExceptionManager.throwEffectiveToShouldBeAfterEffectiveFrom();
    }

    validateFieldInfos(info);
  }
}
