package global.citytech.remitpulse.countries.repositories.internal.dao.country;

import global.citytech.remitpulse.countries.repositories.internal.filters.CountryIdentifier;
import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.FilterCriteria;

/**
 * @author bipin on 6/20/19 12:12 PM.
 */
public interface CountryDao extends AbstractRepository<CountryEntity, CountryIdentifier> {
    Long getCount(FilterCriteria fc);
}
