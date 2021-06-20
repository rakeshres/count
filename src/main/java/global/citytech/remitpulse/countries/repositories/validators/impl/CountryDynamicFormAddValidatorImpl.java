package global.citytech.remitpulse.countries.repositories.validators.impl;

import global.citytech.rabbit.core.commons.Validator;
import global.citytech.remitpulse.countries.commons.validators.DataElementValidator;
import global.citytech.remitpulse.countries.commons.validators.DbAndOtherServicesValidator;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.validators.CountryDynamicFormAddValidator;
import global.citytech.remitpulse.countries.repositories.validators.impl.dataelementvalidators.CountryDynamicFormAddDataElementValidatorImpl;
import global.citytech.remitpulse.countries.repositories.validators.impl.dbandotherservicesvalidator.CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImpl;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/** @author bipin on 2020-03-05 10:19 */
@Named
@Dependent
public class CountryDynamicFormAddValidatorImpl implements CountryDynamicFormAddValidator {

  private CountryDynamicFormAddDataElementValidatorImpl countryDynamicFormAddDataElementValidator;
  private CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImpl
      countryDynamicFormMasterTypeDbAndOtherServicesValidator;

  @Inject
  public CountryDynamicFormAddValidatorImpl(
      CountryDynamicFormAddDataElementValidatorImpl countryDynamicFormAddDataElementValidator,
      CountryDynamicFormMasterTypeDbAndOtherServicesValidatorImpl
          countryDynamicFormMasterTypeDbAndOtherServicesValidator) {
    this.countryDynamicFormAddDataElementValidator = countryDynamicFormAddDataElementValidator;
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
    return List.of(countryDynamicFormAddDataElementValidator);
  }

  @Override
  public List<DbAndOtherServicesValidator<CountryDynamicFormInfo>>
      getDbAndOtherServicesValidators() {
    return List.of(countryDynamicFormMasterTypeDbAndOtherServicesValidator);
  }
}
