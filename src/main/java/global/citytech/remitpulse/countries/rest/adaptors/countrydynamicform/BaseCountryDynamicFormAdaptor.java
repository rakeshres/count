package global.citytech.remitpulse.countries.rest.adaptors.countrydynamicform;

import global.citytech.rabbit.core.commons.RequestAdaptor;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.repositories.constants.FieldConstant;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.FieldInfo;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.SelectableValue;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.ValidationRule;
import global.citytech.remitpulse.countries.rest.adaptors.AdaptorCommon;
import global.citytech.remitpulse.countries.rest.supports.commons.HelperUtils;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

/** @author bipin on 2020-03-04 15:58 */
public class BaseCountryDynamicFormAdaptor
    implements RequestAdaptor<CountryDynamicFormInfo>, AdaptorCommon {
  @Override
  public CountryDynamicFormInfo toServiceObject(JsonObject jsonObject) {
    CountryDynamicFormInfo info = new CountryDynamicFormInfo();
    info.setId(jsonObject.getString("id", ""));
    info.setName(jsonObject.getString("name", ""));
    info.setType(jsonObject.getString("type", ""));
    info.setEffectiveFrom(jsonObject.getString("effectiveFrom", ""));
    info.setEffectiveTo(jsonObject.getString("effectiveTo", ""));
    info.setCountryIso3(jsonObject.getString("countryIso3", ""));

    info.setFieldInfoList(getFieldInfoListByKey(jsonObject, "fieldInfoList"));
    return info;
  }

  private List<FieldInfo> getFieldInfoListByKey(JsonObject jsonObject, String key) {
    HelperUtils.throwExceptionIfInvalidJsonArray(jsonObject, key);
    return prepareFieldInfoList(jsonObject.getJsonArray(key));
  }

  private List<FieldInfo> prepareFieldInfoList(JsonArray jsonArray) {
    List<FieldInfo> fieldInfos = new ArrayList<>();
    if (jsonArray != null && !jsonArray.isEmpty()) {
      jsonArray.forEach(
          jsonValue -> {
            JsonObject jsonObject = Jsons.toJsonObject(jsonValue.toString());
            FieldInfo fieldInfo = parseFieldInfo(jsonObject);
            fieldInfos.add(fieldInfo);
          });
    }
    return fieldInfos;
  }

  private FieldInfo parseFieldInfo(JsonObject jsonObject) {
    FieldInfo fieldInfo = new FieldInfo();

    fieldInfo.setFieldId(jsonObject.getString("fieldId", ""));

    if (FieldConstant.DataType.BOOLEAN.getCode().equalsIgnoreCase(fieldInfo.getRenderType())) {
      fieldInfo.setDefaultValue(String.valueOf(jsonObject.getBoolean("defaultValue", false)));
    } else {
      fieldInfo.setDefaultValue(jsonObject.getString("defaultValue", ""));
    }

    fieldInfo.setRequired(jsonObject.getBoolean("required", false));
    fieldInfo.setModifiable(jsonObject.getBoolean("modifiable", false));
    fieldInfo.setActive(jsonObject.getBoolean("active", false));
    fieldInfo.setSearchable(jsonObject.getBoolean("searchable", false));
    fieldInfo.setValidationRules(getValidationRuleListByKey(jsonObject, "validationRules"));
    fieldInfo.setSelectableValues(getSelectableValueListByKey(jsonObject, "selectableValues"));
    fieldInfo.setDataSourceId(jsonObject.getString("dataSourceId", ""));
    fieldInfo.setRenderType(jsonObject.getString("renderType", ""));
    fieldInfo.setModuleIdentifier(jsonObject.getString("moduleIdentifier", ""));
    return fieldInfo;
  }

  private List<ValidationRule> getValidationRuleListByKey(JsonObject jsonObject, String key) {
    HelperUtils.throwExceptionIfInvalidJsonArray(jsonObject, key);
    return prepareValidationRuleList(jsonObject.getJsonArray(key));
  }

  private List<ValidationRule> prepareValidationRuleList(JsonArray jsonArray) {
    List<ValidationRule> fieldInfos = new ArrayList<>();
    if (jsonArray != null && !jsonArray.isEmpty()) {
      jsonArray.forEach(
          jsonValue -> {
            JsonObject jsonObject = Jsons.toJsonObject(jsonValue.toString());
            ValidationRule validationRule = parseValidationRule(jsonObject);
            fieldInfos.add(validationRule);
          });
    }
    return fieldInfos;
  }

  private ValidationRule parseValidationRule(JsonObject jsonObject) {

    ValidationRule validationRule = new ValidationRule();
    validationRule.setRule(jsonObject.getString("rule"));
    if (shouldTakeBooleanValue(validationRule.getRule())) {
      validationRule.setValue(String.valueOf(jsonObject.getBoolean("value", false)));
    } else {
      validationRule.setValue(jsonObject.getString("value", ""));
    }
    validationRule.setMessage(jsonObject.getString("message", ""));
    return validationRule;
  }

  private static boolean shouldTakeBooleanValue(String value) {
    String[] booleanValue = {"futureAllowed", "pastAllowed"};
    for (String str : booleanValue) {
      if (str.equalsIgnoreCase(value)) {
        return true;
      }
    }
    return false;
  }

  private List<SelectableValue> getSelectableValueListByKey(JsonObject jsonObject, String key) {
    HelperUtils.throwExceptionIfInvalidJsonArray(jsonObject, key);
    return prepareSelectableValueList(jsonObject.getJsonArray(key));
  }

  private List<SelectableValue> prepareSelectableValueList(JsonArray jsonArray) {
    List<SelectableValue> selectableValues = new ArrayList<>();
    if (jsonArray != null && !jsonArray.isEmpty()) {
      jsonArray.forEach(
          jsonValue -> {
            JsonObject jsonObject = Jsons.toJsonObject(jsonValue.toString());
            SelectableValue selectableValue = parseSelectableValue(jsonObject);
            selectableValues.add(selectableValue);
          });
    }
    return selectableValues;
  }

  private SelectableValue parseSelectableValue(JsonObject jsonObject) {
    SelectableValue selectableValue = new SelectableValue();
    selectableValue.setOption(jsonObject.getString("option"));
    selectableValue.setValue(jsonObject.getString("value"));
    return selectableValue;
  }
}
