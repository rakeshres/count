package global.citytech.remitpulse.countries.repositories;

import global.citytech.rabbit.core.search.dynamic.MultiValueData;
import global.citytech.remitpulse.countries.commons.domains.auditlog.DynamicAuditSearchCriteria;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfoSearchRequest;
import global.citytech.remitpulse.countries.repositories.domains.country.GetLocationsRequest;
import global.citytech.remitpulse.countries.repositories.internal.auditlog.CountryPendingInfoSearchRequest;
import global.citytech.remitpulse.countries.repositories.internal.dto.audit.AuditLogDto;
import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.commons.PageableData;

import java.util.List;

/**
 * @author bipin on 6/20/19 12:11 PM.
 */
public interface CountryRepository {
    CountryInfo create(CountryInfo countryInfo);
    PageableData findAuditList(CountryPendingInfoSearchRequest request);
    AuditLogDto findAuditDetail(AuditLogInfo auditLogInfo);
    PageableData findAll(CountryInfoSearchRequest request);
    AuditLogInfo approve(AuditLogInfo auditLogInfo);
    CountryInfo findOne(CountryInfo countryInfo);
    CountryInfo getCountryWithMasterDataConf(CountryInfo countryInfo);
    CountryInfo update(CountryInfo countryInfo);
    List<CountryInfo> findByValues(CountryInfo countryInfo);
    AuditLogInfo reject(AuditLogInfo auditLogInfo);
    PageableData<AuditLogInfo> getAuditList(DynamicAuditSearchCriteria auditSearchCriteria);
    List<MultiValueData> findLocationInfoByCountryCode(GetLocationsRequest countryInfo);
}
