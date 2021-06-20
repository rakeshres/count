package global.citytech.remitpulse.countries.repositories.domains;


import com.google.common.base.Strings;
import global.citytech.rabbit.core.audits.AuditAction;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.core.commons.AuditableEntity;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.commons.constants.CityConstants;
import global.citytech.remitpulse.countries.commons.domains.pageable.PageableFilterCriteria;
import global.citytech.remitpulse.countries.commons.domains.pageable.PageableRequest;
import global.citytech.remitpulse.countries.commons.domains.sortable.Sortable;
import global.citytech.remitpulse.countries.repositories.constants.FieldConstant;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author bipin on 5/21/19 12:03 PM.
 */
public class HelperUtilsLocal {

  private static Logger LOGGER= Logger.getLogger(HelperUtilsLocal.class.getName());

  public static AuditLogEntity prepareAuditLogEntityForUpdate(
      String entityKey,
      String entityName,
      String userId,
      AuditableEntity auditableEntity,
      String oldData) {

    AuditLogEntity auditLogEntity =
        prepareAuditLogEntity(entityKey, entityName, userId, AuditStatus.PENDING, auditableEntity);
    auditLogEntity.setAction(AuditAction.UPDATE);
    auditLogEntity.setOldData(oldData);
    return auditLogEntity;
  }

  public static void updateAuditableEntityForUpdate(
      AuditableEntity auditableEntity, String userId) {

    auditableEntity.setLastModifiedBy(userId);
    auditableEntity.setLastModifiedOn(HelperUtils.now());
  }

  public static void updateAuditableEntityForAdd(AuditableEntity auditableEntity, String userId) {

    auditableEntity.setActive(true);
    auditableEntity.setCreatedBy(userId);
    auditableEntity.setCreatedOn(HelperUtils.now());
  }

  public static AuditLogEntity prepareAuditLogEntityForAdd(
      String entityKey, String entityName, String userId, AuditableEntity auditableEntity) {

    AuditLogEntity auditLogEntity =
        prepareAuditLogEntity(entityKey, entityName, userId, AuditStatus.PENDING, auditableEntity);
    auditLogEntity.setAction(AuditAction.ADD);
    return auditLogEntity;
  }

  public static List<String> getStringListByKey(JsonObject jsonObject, String key) {

    if (jsonObject.containsKey(key)) {
      JsonArray jsonArray = jsonObject.getJsonArray(key);

      List<String> valueList = new ArrayList<>();
      for (int i = 0; i < jsonArray.size(); i++) {
        valueList.add(jsonArray.getString(i));
      }
      return valueList;
    }
    return Collections.emptyList();
  }

  public static AuditLogEntity prepareAuditLogEntity(
      String entityKey,
      String entityName,
      String userId,
      AuditStatus auditStatus,
      AuditableEntity auditableEntity) {

    AuditLogEntity auditLogEntity = new AuditLogEntity();
    auditLogEntity.setId(HelperUtils.generateUUID());
    auditLogEntity.setEntityKey(entityKey);
    auditLogEntity.setNewData(Jsons.toJsonObj(auditableEntity));
    auditLogEntity.setEntity(entityName);
    auditLogEntity.setStatus(auditStatus);
    auditLogEntity.setRequestedBy(userId);
    auditLogEntity.setRequestedOn(HelperUtils.now());
    return auditLogEntity;
  }


  public static String getSortParameter(Sortable sortable) {
    if (!Strings.isNullOrEmpty(sortable.getSortParameter())
        && sortable.getSortParameter().equalsIgnoreCase(SortParameter.DESC.getCode())) {
      return SortParameter.DESC.getCode();
    } else {
      return SortParameter.ASC.getCode();
    }
  }

  public static void preparePaginationCriteria(
      PageableRequest searchRequest, PageableFilterCriteria filterCriteria) {

    filterCriteria.setPageNumber(
        getDefaultIfZero(
            searchRequest.getPageNumber() == null ? 0 : searchRequest.getPageNumber(),
            CityConstants.PAGINATION_DEFAULT_PAGE_NUMBER));
    filterCriteria.setPageSize(
        getDefaultIfZero(
            searchRequest.getPageSize() == null ? 0 : searchRequest.getPageSize(),
            CityConstants.PAGINATION_DEFAULT_PAGE_SIZE));
    filterCriteria.setStartingIndex(calculateStartingIndex(filterCriteria));
    LOGGER.info("Pagination request added : " + Jsons.toJsonObj(filterCriteria));
  }

  public static boolean isValidRegex(String regex) {
    try {
      Pattern.compile(regex);
    } catch (PatternSyntaxException e) {
      return false;
    }

    return true;
  }

  static int calculateStartingIndex(PageableFilterCriteria pageableFilter) {
    return pageableFilter.getPageSize() * (pageableFilter.getPageNumber() - 1);
  }

  static int getDefaultIfZero(int number, int defaultValue) {
    return number <= 0 ? defaultValue : number;
  }

    public static LocalDate toLocalDateTime(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(stringDate, formatter);
    }

    public static LocalDate todayDate(){
      return LocalDate.now();
    }

    public static boolean containsOnlyAlphabeticCharacters(String str) {
      return str != null && !str.equals("") && str.matches("^[a-zA-Z]*$");
    }

  public static boolean isValidDate(String date) {
    try {
      LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } catch (DateTimeParseException var2) {
      return false;
    }

    return true;
  }

  public static boolean checkEquals(String code, FieldConstant.DataType dataType) {
    return dataType.getCode().equalsIgnoreCase(code);
  }

  public static boolean isCurrentDateBetweenFromAndTo(String effectiveFrom,String effectiveTo){
    return LocalDate.now().equals(parseDate(effectiveFrom))
        || LocalDate.now().equals(parseDate(effectiveTo))
        || (LocalDate.now().isBefore(parseDate(effectiveTo)) && LocalDate.now().isAfter(parseDate(effectiveFrom)))
        ;

  }

  public static LocalDate parseDate(String date){
    try {
      return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } catch (DateTimeParseException var2) {
      throw var2;
    }
  }


}
