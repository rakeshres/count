package global.citytech.remitpulse.countries.rest.supports.commons;

import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;

import javax.json.JsonObject;
import javax.json.JsonValue;

/** @author sunil */
public class HelperUtils {

  public static void throwExceptionIfInvalidJsonNumber(JsonObject jsonObject, String fieldName) {
    if (jsonObject.containsKey(fieldName) && isInvalidNumber(jsonObject, fieldName)) {
      ExceptionManager.throwJsonError();
    }
  }

  static boolean isInvalidNumber(JsonObject jsonObject, String fieldName) {
    return jsonObject.isNull(fieldName)
        || !jsonObject.get(fieldName).getValueType().equals(JsonValue.ValueType.NUMBER);
  }

  public static void throwExceptionIfInvalidJsonArray(JsonObject jsonObject, String fieldName) {
    if (jsonObject.containsKey(fieldName)
        && (jsonObject.isNull(fieldName)
            || !jsonObject.get(fieldName).getValueType().equals(JsonValue.ValueType.ARRAY))) {
      ExceptionManager.throwJsonError();
    }
  }

  public static void throwExceptionIfInvalidJsonBoolean(JsonObject jsonObject, String fieldName) {
    if (jsonObject.containsKey(fieldName) && isInvalidBoolean(jsonObject, fieldName)) {
      ExceptionManager.throwJsonError();
    }
  }

  public static boolean isInvalidBoolean(JsonObject jsonObject, String fieldName) {
    return jsonObject.isNull(fieldName)
            || !(jsonObject.get(fieldName).getValueType().equals(JsonValue.ValueType.TRUE)
            || jsonObject.get(fieldName).getValueType().equals(JsonValue.ValueType.FALSE));
  }
}
