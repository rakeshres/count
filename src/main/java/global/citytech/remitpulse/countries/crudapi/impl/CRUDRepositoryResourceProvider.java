package global.citytech.remitpulse.countries.crudapi.impl;

import global.citytech.rabbit.core.commons.AuditableEntity;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.rabbit.core.commons.Validator;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.PageableSearchRequest;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.SearchRequest;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.SortableSearchRequest;
import global.citytech.remitpulse.countries.crudapi.impl.helper.*;

/** @author sunil */
public interface CRUDRepositoryResourceProvider<
    E extends AuditableEntity,
    T extends ServiceObject,
    P extends PageableSearchRequest,
    S extends SortableSearchRequest> {

  Validator<T> getAddValidator();

  BaseAddRepositoryHelper<E, T> getAddRepositoryHelper();

  Validator<T> getViewDetailsValidator();

  BaseViewDetailsRepositoryHelper<E, T> getViewDetailsRepositoryHelper();

  Validator<T> getUpdateValidator();

  BaseUpdateRepositoryHelper<E, T> getUpdateRepositoryHelper();

  Validator<PageableSearchRequest> getSearchValidator();

  BasePageableSearchRepositoryHelper<E, T, P> getSearchRepositoryHelper();

  Validator<SearchRequest> getSearchAllValidator();

  BaseSearchAllRepositoryHelper<E, T, S> getSearchAllRepositoryHelper();

  Validator<SearchRequest> getSearchAllKeyNameValidator();

  BaseSearchAllKeyNameRepositoryHelper<E, T, S> getSearchAllKeyNameRepositoryHelper();
}
