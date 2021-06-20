package global.citytech.remitpulse.countries.repositories.internal.dao.country;

import global.citytech.rabbit.core.commons.FilterCriteria;

import java.util.List;

/**
 * @author sankalpa on 4/12/21
 */
public interface LocationDao {
    List<LocationEntity> getLocations(FilterCriteria filterCriteria);
}
