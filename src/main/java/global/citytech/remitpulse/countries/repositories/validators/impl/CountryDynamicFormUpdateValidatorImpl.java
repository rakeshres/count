package global.citytech.remitpulse.countries.repositories.validators.impl;

import global.citytech.rabbit.core.commons.Validator;
import global.citytech.remitpulse.countries.commons.validators.DataElementValidator;
import global.citytech.remitpulse.countries.commons.validators.DbAndOtherServicesValidator;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.validators.CountryDynamicFormUpdateValidator;
import global.citytech.remitpulse.countries.repositories.validators.impl.dataelementvalidators.CountryDynamicFormUpdateDataElementValidatorImpl;
import global.citytech.remitpulse.countries.repositories.validators.impl.dbandotherservicesvalidator.CountryDynamicFormMasterTypeDbAndOtherServicesValidator;
import global.citytech.remitpulse.countries.repositories.validators.impl.dbandotherservicesvalidator.CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImpl;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/** @author bipin on 2020-03-06 16:59 */
@Named
@Dependent
public class CountryDynamicFormUpdateValidatorImpl implements CountryDynamicFormUpdateValidator {
  private CountryDynamicFormUpdateDataElementValidatorImpl
      countryDynamicFormUpdateDataElementValidator;
  private CountryDynamicFormMasterTypeDbAndOtherServicesValidator
      countryDynamicFormMasterTypeDbAndOtherServicesValidator;

  @Inject
  public CountryDynamicFormUpdateValidatorImpl(
      CountryDynamicFormUpdateDataElementValidatorImpl countryDynamicFormUpdateDataElementValidator,
      CountryDynamicFormMasterTypeDbAndOtherServicesValidator
          countryDynamicFormMasterTypeDbAndOtherServicesValidator) {
    this.countryDynamicFormUpdateDataElementValidator =
        countryDynamicFormUpdateDataElementValidator;
    this.countryDynamicFormMasterTypeDbAndOtherServicesValidator =
        countryDynamicFormMasterTypeDbAndOtherServicesValidator;
  }

  @Override
  public List<Validator<CountryDynamicFormInfo>> getValidators() {
    List<Validator<CountryDynamicFormInfo>> validators = new ArrayList<>();
    validators.addAll(getDataElementValidators());
    validators.addAll(getDbAndOtherServicesValidators());
    return validators;
  }

  @Override
  public List<DataElementValidator<CountryDynamicFormInfo>> getDataElementValidators() {
    return List.of(countryDynamicFormUpdateDataElementValidator);
  }

  @Override
  public List<DbAndOtherServicesValidator<CountryDynamicFormInfo>>
      getDbAndOtherServicesValidators() {
    return List.of(countryDynamicFormMasterTypeDbAndOtherServicesValidator);
  }
}
