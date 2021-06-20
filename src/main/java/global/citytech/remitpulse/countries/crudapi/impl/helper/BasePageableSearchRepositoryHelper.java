package global.citytech.remitpulse.countries.crudapi.impl.helper;

import global.citytech.rabbit.core.commons.*;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.commons.domains.pageable.PageableFilterCriteria;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.PageableSearchRequest;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/** @author sunil */
public interface BasePageableSearchRepositoryHelper<
        E extends AuditableEntity, T extends ServiceObject, P extends PageableSearchRequest>
    extends RepositoryHelper<P, PageableData<T>> {

  Logger LOGGER = Logger.getLogger(BasePageableSearchRepositoryHelper.class.getName());

  AuditableEntityConverter<E, T> getConverter();

  AbstractRepository<E, StringIdentifier> getEntityDao();

  PageableFilterCriteria prepareFilterCriteriaWithSearchParamsOnly(P pageableSearchRequest);

  void setSortByCriteria(PageableFilterCriteria filterCriteria, P searchRequest);

  @Override
  default PageableData<T> execute(P pageableSearchRequest) {

    PageableFilterCriteria pageableFilterCriteria =
        preparePageableFilterCriteria(pageableSearchRequest);

    List<E> entities = getEntityDao().findAll(pageableFilterCriteria);
    LOGGER.info("Got " + entities.size() + " entities");

    if (entities.isEmpty()) {
      return preparePageableData(Collections.emptyList(), pageableFilterCriteria);
    }

    List<T> serviceObjects = new ArrayList<>();
    entities.forEach(entity -> serviceObjects.add(this.getConverter().toServiceObject(entity)));

    return preparePageableData(serviceObjects, pageableFilterCriteria);
  }

  default PageableFilterCriteria preparePageableFilterCriteria(P pageableSearchRequest) {
    PageableFilterCriteria pageableFilterCriteria =
        this.prepareFilterCriteriaWithSearchParamsOnly(pageableSearchRequest);
    setSortCriteria(pageableFilterCriteria, pageableSearchRequest);
    HelperUtilsLocal.preparePaginationCriteria(pageableSearchRequest, pageableFilterCriteria);
    return pageableFilterCriteria;
  }

  default void setSortCriteria(PageableFilterCriteria filterCriteria, P searchRequest) {

    this.setSortByCriteria(filterCriteria, searchRequest);
    this.setSortParameterCriteria(filterCriteria, searchRequest);
  }

  default void setSortParameterCriteria(
      PageableFilterCriteria filterCriteria, PageableSearchRequest searchRequest) {

    filterCriteria.setSortParameter(HelperUtilsLocal.getSortParameter(searchRequest));
  }

  private PageableData<T> preparePageableData(
      List<T> currencyPairLimitInfos, PageableFilterCriteria filter) {

    return new PageableData<>(
        filter.getPageNumber(),
        this.getEntityDao().getTotalRecord(filter),
        filter.getPageSize(),
        currencyPairLimitInfos);
  }
}
