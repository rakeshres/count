package global.citytech.remitpulse.countries.crudapi.impl;

import global.citytech.rabbit.core.commons.AuditableEntity;
import global.citytech.rabbit.core.commons.PageableData;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.PageableSearchRequest;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.SortableSearchRequest;
import global.citytech.remitpulse.countries.crudapi.CRUDRepository;

import java.util.List;
import java.util.Map;

/** @author sunil */
public interface BaseCRUDRepository<
        E extends AuditableEntity,
        T extends ServiceObject,
        P extends PageableSearchRequest,
        S extends SortableSearchRequest>
    extends CRUDRepository<T, P, S> {

  CRUDRepositoryResourceProvider<E, T, P, S> getResourceProvider();

  @Override
  default T create(T serviceObject) {
    getResourceProvider().getAddValidator().validate(serviceObject);
    return getResourceProvider().getAddRepositoryHelper().execute(serviceObject);
  }

  @Override
  default T findOne(T serviceObject) {
    getResourceProvider().getViewDetailsValidator().validate(serviceObject);
    return getResourceProvider().getViewDetailsRepositoryHelper().execute(serviceObject);
  }

  @Override
  default T edit(T serviceObject) {
    getResourceProvider().getUpdateValidator().validate(serviceObject);
    return getResourceProvider().getUpdateRepositoryHelper().execute(serviceObject);
  }

  @Override
  default PageableData<T> search(P pageableSearchRequest) {
    getResourceProvider().getSearchValidator().validate(pageableSearchRequest);
    return getResourceProvider().getSearchRepositoryHelper().execute(pageableSearchRequest);
  }

  @Override
  default List<T> searchAll(S searchRequest) {
    getResourceProvider().getSearchAllValidator().validate(searchRequest);
    return getResourceProvider().getSearchAllRepositoryHelper().execute(searchRequest);
  }

  @Override
  default Map<String, String> searchAllKeyName(S searchRequest) {
    getResourceProvider().getSearchAllKeyNameValidator().validate(searchRequest);
    return getResourceProvider().getSearchAllKeyNameRepositoryHelper().execute(searchRequest);
  }
}
