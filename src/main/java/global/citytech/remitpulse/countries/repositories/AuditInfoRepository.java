package global.citytech.remitpulse.countries.repositories;

import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.rabbit.core.audits.AuditLogInfo;

/**
 * @author bipin on 6/25/19 11:59 AM.
 */
public interface AuditInfoRepository {
    AuditLogInfo add(CountryInfo countryInfo);
    AuditLogInfo update(CountryInfo countryInfo);
}
