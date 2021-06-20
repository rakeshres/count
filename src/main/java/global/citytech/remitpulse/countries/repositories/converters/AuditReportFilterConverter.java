package global.citytech.remitpulse.countries.repositories.converters;

import com.google.common.flogger.FluentLogger;
import global.citytech.rabbit.core.search.SortingOrder;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.commons.constants.CountryAuditField;
import global.citytech.remitpulse.countries.commons.constants.SearchFilterCondition;
import global.citytech.remitpulse.countries.commons.domains.FilterOperator;
import global.citytech.remitpulse.countries.commons.domains.auditlog.AuditReportModel;
import global.citytech.remitpulse.countries.commons.domains.auditlog.DynamicAuditSearchCriteria;
import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditReportFilterCriteria;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author raju.dhital@citytech.global on 8/6/19 10:12 AM project finpulse-departments
 */
public class AuditReportFilterConverter {

    private AuditReportFilterConverter() {
    }

    public static AuditReportFilterCriteria prepareCountryAuditReportFilterCriteria(
            DynamicAuditSearchCriteria dynamicAuditSearchCriteria) {
        AuditReportFilterCriteria auditReportFilterCriteria = new AuditReportFilterCriteria();
        auditReportFilterCriteria.setPageSize(dynamicAuditSearchCriteria.getPageSize());
        auditReportFilterCriteria.setPageNumber(dynamicAuditSearchCriteria.getPageNumber());
        auditReportFilterCriteria.setEntities(Collections.singletonList(EntityName.COUNTRY.getEntityName()));
        if (dynamicAuditSearchCriteria.getReportModels() != null)
            auditReportFilterCriteria.setReportModels(
                    toReportModelListCounty(dynamicAuditSearchCriteria.getReportModels()));
        auditReportFilterCriteria.setSortField(
                CountryAuditField.getByCode(dynamicAuditSearchCriteria.getSortField()).getColumnName());
        auditReportFilterCriteria.setSortOrder(
                SortingOrder.getByCode(dynamicAuditSearchCriteria.getSortOrder()).getCode());
        FluentLogger.forEnclosingClass()
                .atInfo()
                .log("FILTER::" + Jsons.toJsonObj(auditReportFilterCriteria));
        return auditReportFilterCriteria;
    }

    public static List<AuditReportModel> toReportModelListCounty(List<AuditReportModel> models) {
        List<AuditReportModel> reportModels = new ArrayList<>();
        models.forEach(reportModel -> reportModels.add(toReportModelCountry(reportModel)));
        return reportModels;
    }

    public static AuditReportModel toReportModelCountry(AuditReportModel model) {
        AuditReportModel reportModel = new AuditReportModel();
        SearchFilterCondition filterCondition = SearchFilterCondition.getByCode(model.getCondition());
        if (!HelperUtils.isBlankOrNull(model.getField())) {
            reportModel.setField(model.getField());
            CountryAuditField field = CountryAuditField.getByCode(model.getField());
            reportModel.setField(field.getColumnName());
            getFromCommonReportModel(model, reportModel, filterCondition);
        }
        return reportModel;
    }

    private static void getFromCommonReportModel(AuditReportModel model, AuditReportModel reportModel, SearchFilterCondition filterCondition) {
        reportModel.setCondition(filterCondition.getSymbol());
        reportModel.setOperator(FilterOperator.getByCode(model.getOperator()).getDisplayName());
        if ((StringUtils.equalsIgnoreCase(filterCondition.getSymbol(), "between"))
                || (StringUtils.equalsIgnoreCase(filterCondition.getSymbol(), "date"))) {
            reportModel.setCondition("BETWEEN");
            reportModel.setValue(
                    (HelperUtils.toLocalDateTime(model.getValue() + " 00:00:00")).toString());
            if (StringUtils.isBlank(model.getDateValue())) {
                reportModel.setDateValue(
                        (HelperUtils.toLocalDateTime(model.getValue() + " 23:59:59")).toString());
            } else {
                reportModel.setDateValue(
                        (HelperUtils.toLocalDateTime(model.getDateValue() + " 23:59:59")).toString());
            }
        } else {
            reportModel.setValue(model.getValue());
            reportModel.setDateValue("");
        }
        reportModel.setOrder(model.getOrder());
        reportModel.setBeginsWith(filterCondition.getBeginsWith());
        reportModel.setEndsWith(filterCondition.getEndsWith());
        reportModel.setCustomizable(model.isCustomizable());
        FluentLogger.forEnclosingClass()
                .atInfo()
                .log("REPORT MODEL IS ::: " + reportModel.toString());
    }
}
