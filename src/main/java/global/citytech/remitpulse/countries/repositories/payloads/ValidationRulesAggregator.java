package global.citytech.remitpulse.countries.repositories.payloads;


import global.citytech.remitpulse.countries.repositories.constants.FieldConstant;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.ValidationRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author sanish on 7/2/19 */
public class ValidationRulesAggregator {
  public Map<String, List<ValidationRule>> prepareValidationRulesByCode(String code) {
    Map<String, List<ValidationRule>> response = new HashMap<>();

    List<ValidationRule> validationRuleInfo = new ArrayList<>();

    if (HelperUtilsLocal.checkEquals(code, FieldConstant.DataType.TEXT)) {

      validationRuleInfo.add(this.prepareValidationRule(FieldConstant.ValidationRule.MIN_LENGTH));
      validationRuleInfo.add(this.prepareValidationRule(FieldConstant.ValidationRule.MAX_LENGTH));
      validationRuleInfo.add(this.prepareValidationRule(FieldConstant.ValidationRule.REGEX));

    } else if (HelperUtilsLocal.checkEquals(code, FieldConstant.DataType.NUMERIC)) {

      validationRuleInfo.add(this.prepareValidationRule(FieldConstant.ValidationRule.MIN_LENGTH));
      validationRuleInfo.add(this.prepareValidationRule(FieldConstant.ValidationRule.MAX_LENGTH));

    } else if (HelperUtilsLocal.checkEquals(code, FieldConstant.DataType.DATE)) {

      validationRuleInfo.add(
          this.prepareValidationRule(FieldConstant.ValidationRule.FUTURE_ALLOWED));
      validationRuleInfo.add(this.prepareValidationRule(FieldConstant.ValidationRule.PAST_ALLOWED));

    } else if (HelperUtilsLocal.checkEquals(code, FieldConstant.DataType.DECIMAL)) {

      validationRuleInfo.add(this.prepareValidationRule(FieldConstant.ValidationRule.MIN_VALUE));
      validationRuleInfo.add(this.prepareValidationRule(FieldConstant.ValidationRule.MAX_VALUE));
      validationRuleInfo.add(
          this.prepareValidationRule(FieldConstant.ValidationRule.ROUND_OFF_AFTER));

    } else if (HelperUtilsLocal.checkEquals(code, FieldConstant.DataType.MULTI_VALUE)
        || HelperUtilsLocal.checkEquals(code, FieldConstant.DataType.BOOLEAN)) {

      return new HashMap<>();

    } else {
      ExceptionManager.throwCustomFieldException("INVALID DATA TYPE CODE:: " + code);
    }

    response.put("validationRules", validationRuleInfo);
    return response;
  }

  private ValidationRule prepareValidationRule(FieldConstant.ValidationRule validationRule) {
    return new ValidationRule(validationRule.getRule());
  }
}
