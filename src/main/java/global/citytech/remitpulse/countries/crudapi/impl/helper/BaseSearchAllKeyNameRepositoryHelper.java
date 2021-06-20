package global.citytech.remitpulse.countries.crudapi.impl.helper;

import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.AuditableEntity;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.rabbit.core.commons.StringIdentifier;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.SortableSearchRequest;
import global.citytech.remitpulse.countries.commons.domains.sortable.SortableFilterCriteria;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

/** @author sunil */
public interface BaseSearchAllKeyNameRepositoryHelper<
        E extends AuditableEntity, T extends ServiceObject, S extends SortableSearchRequest>
    extends RepositoryHelper<S, Map<String, String>> {

  Logger LOGGER = Logger.getLogger(BaseSearchAllKeyNameRepositoryHelper.class.getName());

  AbstractRepository<E, StringIdentifier> getEntityDao();

  SortableFilterCriteria prepareFilterCriteriaWithSearchParamsOnly(S searchRequest);

  void setSortByCriteria(
      SortableFilterCriteria filterCriteria, S searchRequest);

  @Override
  default Map<String, String> execute(S sortableSearchRequest) {
    SortableFilterCriteria sortableFilterCriteria =
        preparePageableFilterCriteria(sortableSearchRequest);

    Map<String, String> keyNameMap = (Map<String, String>) getEntityDao().findAllKeyName(sortableFilterCriteria);
    LOGGER.info("Got " + keyNameMap.size() + " keyNameMap");

    if (keyNameMap.isEmpty()) {
      return Collections.emptyMap();
    }

    return keyNameMap;
  }

  default SortableFilterCriteria preparePageableFilterCriteria(S sortableSearchRequest) {
    SortableFilterCriteria pageableFilterCriteria =
        this.prepareFilterCriteriaWithSearchParamsOnly(sortableSearchRequest);
    setSortCriteria(pageableFilterCriteria, sortableSearchRequest);
    return pageableFilterCriteria;
  }

  default void setSortCriteria(
      SortableFilterCriteria filterCriteria, S searchRequest) {

    this.setSortByCriteria(filterCriteria, searchRequest);
    this.setSortParameterCriteria(filterCriteria, searchRequest);
  }

  default void setSortParameterCriteria(
      SortableFilterCriteria filterCriteria, SortableSearchRequest searchRequest) {

    filterCriteria.setSortParameter(HelperUtilsLocal.getSortParameter(searchRequest));
  }
}
