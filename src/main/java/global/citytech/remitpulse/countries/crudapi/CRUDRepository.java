package global.citytech.remitpulse.countries.crudapi;

import global.citytech.rabbit.core.commons.PageableData;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.PageableSearchRequest;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.SortableSearchRequest;

import java.util.List;
import java.util.Map;

/** @author sunil */
public interface CRUDRepository<
    T extends ServiceObject, P extends PageableSearchRequest, S extends SortableSearchRequest> {

  T create(T serviceObject);

  T findOne(T serviceObject);

  T edit(T serviceObject);

  PageableData<T> search(P pageableSearchRequest);

  List<T> searchAll(S searchRequest);

  Map<String, String> searchAllKeyName(S searchRequest);
}
