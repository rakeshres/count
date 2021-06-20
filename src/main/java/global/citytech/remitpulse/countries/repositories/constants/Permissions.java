package global.citytech.remitpulse.countries.repositories.constants;

/**
 * @author sankalpa on 11/6/19
 */
public class Permissions {

    //country:create, country:approve,country:list, country:modify, country:list:pending,country:pending:detail,country:detail,country;:audit:log,country:change_log_list,country:change_log_detail

    public static final String COUNTRY_ADD = "country:create";
    public static final String COUNTRY_EDIT = "country:modify";
    public static final String COUNTRY_DETAIL = "country:detail";
    public static final String COUNTRY_LIST = "country:list";
    public static final String COUNTRY_CHANGE_LOG_LIST = "country:change_log_list";
    public static final String COUNTRY_CHANGE_LOG_DETAIL = "country:change_log_detail";
    public static final String COUNTRY_PENDING_LIST = "country:list:pending";
    //public static final String COUNTRY_REJECTED_LIST = "country:rejected_list";
    public static final String COUNTRY_REJECTED_LIST = "country:change_log_list";
    public static final String COUNTRY_PENDING_DETAIL = "country:pending:detail";
    //public static final String COUNTRY_REJECTED_DETAIL = "country:rejected_detail";
    public static final String COUNTRY_REJECTED_DETAIL = "country:change_log_detail";
    public static final String COUNTRY_VERIFY = "country:approve";
    public static final String COUNTRY_AUDIT_LOG = "country:audit:log";

    private Permissions(){}
}
