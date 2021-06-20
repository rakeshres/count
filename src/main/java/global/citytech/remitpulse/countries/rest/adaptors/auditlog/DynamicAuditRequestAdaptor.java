package global.citytech.remitpulse.countries.rest.adaptors.auditlog;

import com.google.common.flogger.FluentLogger;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.commons.domains.auditlog.AuditReportModel;
import global.citytech.remitpulse.countries.commons.domains.auditlog.DynamicAuditSearchCriteria;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

/** @author raju.dhital@citytech.global on 8/6/19 10:00 AM project finpulse-departments */
@Named
@Dependent
public class DynamicAuditRequestAdaptor {

  public DynamicAuditSearchCriteria prepareDynamicReportRequestModel(JsonObject object) {
    JsonArray jsonArray = object.getJsonArray("reportModel");
    DynamicAuditSearchCriteria searchCriteria = new DynamicAuditSearchCriteria();
    if (jsonArray != null) {
      FluentLogger.forEnclosingClass().atInfo().log("EMPTY JSON ARRAY ::: ");
      searchCriteria.setReportModels(this.prepareReportModelList(jsonArray));
    }
    searchCriteria.setPageNumber(object.getInt("pageNumber", 1));
    searchCriteria.setPageSize(object.getInt("pageSize", 10));
    searchCriteria.setSortField(object.getString("sortField", ""));
    searchCriteria.setSortOrder(object.getString("sortOrder", ""));
    FluentLogger.forEnclosingClass()
        .atInfo()
        .log("REPORT SEARCH :: " + Jsons.toJsonObj(searchCriteria));
    return searchCriteria;
  }

  private List<AuditReportModel> prepareReportModelList(JsonArray jsonArray) {
    List<AuditReportModel> auditReportModels = new ArrayList<>();
    jsonArray.stream()
        .forEach(
            jsonValue -> {
              JsonObject jsonObject = Jsons.toJsonObject(jsonValue.toString());
              AuditReportModel auditReportModel = new AuditReportModel();
              auditReportModel.setField(jsonObject.getString("field", ""));
              auditReportModel.setValue(jsonObject.getString("value", ""));
              auditReportModel.setOperator(jsonObject.getString("operator", ""));
              auditReportModel.setCustomizable(jsonObject.getBoolean("customizable", false));
              auditReportModel.setCondition(jsonObject.getString("condition", ""));
              if (!jsonObject.containsKey("dateValue") || jsonObject.isNull("dateValue")) {
                auditReportModel.setDateValue("");
              } else {
                auditReportModel.setDateValue(jsonObject.getString("dateValue"));
              }
              auditReportModel.setOrder(jsonObject.getInt("order", 1));
              auditReportModels.add(auditReportModel);
            });
    return auditReportModels;
  }
}
