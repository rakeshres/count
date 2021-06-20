package global.citytech.remitpulse.countries.rest.adaptors;

import global.citytech.remitpulse.countries.commons.domains.searchrequest.PageableSearchRequest;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.SortableSearchRequest;
import global.citytech.remitpulse.countries.rest.supports.commons.HelperUtils;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** @author sunil */
public interface AdaptorCommon {

  default void setPageableFields(
      PageableSearchRequest pageableSearchRequest, JsonObject jsonObject) {

    setSortableFields(pageableSearchRequest, jsonObject);
    pageableSearchRequest.setPageNumber(jsonObject.getInt("pageNumber", 0));
    pageableSearchRequest.setPageSize(jsonObject.getInt("pageSize", 0));
  }

  default void setSortableFields(
      SortableSearchRequest sortableSearchRequest, JsonObject jsonObject) {

    sortableSearchRequest.setSortBy(jsonObject.getString("sortBy", ""));
    sortableSearchRequest.setSortParameter(jsonObject.getString("sortParameter", ""));
  }

  default Double getDoubleValueByKey(JsonObject jsonObject, String key) {
    Double doubleValue = null;
    if (jsonObject.containsKey(key)) {
      HelperUtils.throwExceptionIfInvalidJsonNumber(jsonObject, key);
      doubleValue = jsonObject.getJsonNumber(key).doubleValue();
    }
    return doubleValue;
  }

  default Long getLongValueByKey(JsonObject jsonObject, String key) {
    Long longValue = null;
    if (jsonObject.containsKey(key) && !jsonObject.isNull(key)) {
      HelperUtils.throwExceptionIfInvalidJsonNumber(jsonObject, key);
      longValue = jsonObject.getJsonNumber(key).longValue();
    }
    return longValue;
  }

  default Integer getIntegerValueByKey(JsonObject jsonObject, String key) {
    Integer integerValue = null;
    if (jsonObject.containsKey(key) && !jsonObject.isNull(key)) {
      HelperUtils.throwExceptionIfInvalidJsonNumber(jsonObject, key);
      integerValue = jsonObject.getJsonNumber(key).intValue();
    }
    return integerValue;
  }

  default boolean getBooleanValueByKey(JsonObject jsonObject, String key, boolean defaultValue) {
    HelperUtils.throwExceptionIfInvalidJsonBoolean(jsonObject, key);
    return jsonObject.getBoolean(key, defaultValue);
  }

  default Boolean getBooleanValueByKey(JsonObject jsonObject, String key) {
    Boolean booleanValue = null;
    if (jsonObject.containsKey(key)) {
      HelperUtils.throwExceptionIfInvalidJsonBoolean(jsonObject, key);
      booleanValue = jsonObject.getBoolean(key);
    }
    return booleanValue;
  }

  default List<String> getStringListByKey(JsonObject jsonObject, String key) {
    if (jsonObject.containsKey(key) && !jsonObject.isNull(key)) {

      HelperUtils.throwExceptionIfInvalidJsonArray(jsonObject, key);
      return convertJsonArrayToStringList(jsonObject.getJsonArray(key));
    }
    return null;
  }

  default List<String> convertJsonArrayToStringList(JsonArray jsonArray) {
    return IntStream.range(0, jsonArray.size())
        .filter(i -> !jsonArray.isNull(i))
        .mapToObj(jsonArray::getString)
        .collect(Collectors.toList());
  }
}
