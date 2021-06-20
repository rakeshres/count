package global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform;

import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.FilterCriteria;
import global.citytech.rabbit.core.commons.StringIdentifier;

/**
 * @author bipin on 2020-03-04 16:42
 */
public interface CountryDynamicFormDao extends AbstractRepository<CountryDynamicFormEntity, StringIdentifier> {
  long getCount(FilterCriteria filterCriteria);
}
