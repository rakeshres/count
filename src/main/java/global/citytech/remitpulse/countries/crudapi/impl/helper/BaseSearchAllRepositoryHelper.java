package global.citytech.remitpulse.countries.crudapi.impl.helper;

import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.AuditableEntity;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.rabbit.core.commons.StringIdentifier;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.SortableSearchRequest;
import global.citytech.remitpulse.countries.commons.domains.sortable.SortableFilterCriteria;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/** @author sunil */
public interface BaseSearchAllRepositoryHelper<
        E extends AuditableEntity, T extends ServiceObject, S extends SortableSearchRequest>
    extends RepositoryHelper<S, List<T>> {

  Logger LOGGER = Logger.getLogger(BaseSearchAllRepositoryHelper.class.getName());

  AuditableEntityConverter<E, T> getConverter();

  AbstractRepository<E, StringIdentifier> getEntityDao();

  SortableFilterCriteria prepareFilterCriteriaWithSearchParamsOnly(S searchRequest);

  void setSortByCriteria(
      SortableFilterCriteria filterCriteria, S searchRequest);

  @Override
  default List<T> execute(S sortableSearchRequest) {
    SortableFilterCriteria sortableFilterCriteria =
        preparePageableFilterCriteria(sortableSearchRequest);

    List<E> entities = getEntityDao().findAll(sortableFilterCriteria);
    LOGGER.info("Got " + entities.size() + " entities");

    if (entities.isEmpty()) {
      return Collections.emptyList();
    }

    List<T> serviceObjects = new ArrayList<>();
    entities.forEach(entity -> serviceObjects.add(this.getConverter().toServiceObject(entity)));

    return serviceObjects;
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
