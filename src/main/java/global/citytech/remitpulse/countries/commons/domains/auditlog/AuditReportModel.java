package global.citytech.remitpulse.countries.commons.domains.auditlog;

import com.google.common.flogger.FluentLogger;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Sanish Maharjan on 2019-08-08
 */
public class AuditReportModel {
    private String field;
    private String condition;
    private String value;
    private String operator;
    private int order;
    private String beginsWith;
    private String endsWith;
    private String between;
    private String dateValue;
    private boolean customizable;

    public AuditReportModel(
            String field,
            String condition,
            String value,
            String operator,
            int order,
            String beginsWith,
            String endsWith,
            String between,
            String dateValue,
            boolean customizable) {
        this.field = field;
        this.condition = condition;
        this.value = value;
        this.operator = operator;
        this.order = order;
        this.beginsWith = beginsWith;
        this.endsWith = endsWith;
        this.between = between;
        this.dateValue = dateValue;
        this.customizable = customizable;
    }

    public AuditReportModel() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getBeginsWith() {
        return beginsWith;
    }

    public void setBeginsWith(String beginsWith) {
        this.beginsWith = beginsWith;
    }

    public String getEndsWith() {
        return endsWith;
    }

    public void setEndsWith(String endsWith) {
        this.endsWith = endsWith;
    }

    public String getBetween() {
        return between;
    }

    public void setBetween(String between) {
        this.between = between;
    }

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

    public boolean isCustomizable() {
        return customizable;
    }

    public void setCustomizable(boolean customizable) {
        this.customizable = customizable;
    }

    @Override
    public String toString() {
        FluentLogger.forEnclosingClass().atInfo().log("AUDIT DYNAMIC SEARCH  ::: " + field);
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(field)) {
            if (!("name".equalsIgnoreCase(field)
                    || "iso3".equalsIgnoreCase(field)
                    || "numericCode".equalsIgnoreCase(field))) {
                this.prepareDataForNonJsonFields(stringBuilder);
            } else {
                stringBuilder.append(operator);
                stringBuilder.append(" ");
                if (!this.byPassLower(value) && !condition.equalsIgnoreCase("between")) {
                    stringBuilder.append("lower(");
                }
                stringBuilder.append("new_data::jsonb");
                stringBuilder.append("->>");
                stringBuilder.append("'");
                stringBuilder.append(field);
                stringBuilder.append("'");
                stringBuilder.append(this.prepareReport());
            }
        }
        FluentLogger.forEnclosingClass().atInfo().log("QUERY -->" + stringBuilder.toString());
        return stringBuilder.toString();
    }

    private void prepareDataForNonJsonFields(StringBuilder stringBuilder) {
        stringBuilder.append(operator);
        stringBuilder.append(" ");
        if (!this.byPassLower(value) && !condition.equalsIgnoreCase("between")) {
            stringBuilder.append("lower(");
        }
        stringBuilder.append(field);
        stringBuilder.append(this.prepareReport());
    }

    private String prepareReport() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!this.byPassLower(value) && !condition.equalsIgnoreCase("between")) {
            stringBuilder.append(")");
        }
        stringBuilder.append(" ");
        stringBuilder.append(condition);
        stringBuilder.append(" ");
        if (!StringUtils.isBlank(dateValue)) {
            stringBuilder.append("'").append(value).append("'").append("");
            stringBuilder.append("AND");
            stringBuilder.append("'").append(dateValue).append("'").append("");
        } else {
            if (!this.byPassLower(value) && !condition.equalsIgnoreCase("between")) {
                stringBuilder.append("lower(");
            }
            stringBuilder.append("'");
            stringBuilder.append(beginsWith);
            stringBuilder.append(value);
            stringBuilder.append(endsWith);
            stringBuilder.append("'");
            if (!this.byPassLower(value) && !condition.equalsIgnoreCase("between")) {
                stringBuilder.append(")");
            }
        }
        stringBuilder.append(" ");
        return stringBuilder.toString();
    }

    private boolean byPassLower(String value) {
        List<String> values = Arrays.asList("true", "false");
        for (String s : values) {
            if (s.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
