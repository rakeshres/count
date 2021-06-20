package global.citytech.remitpulse.countries.rest;

import global.citytech.rabbit.core.commons.RequestAdaptor;
import global.citytech.rabbit.core.commons.ServiceObject;
import global.citytech.rabbit.microprofile.RestResponse;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.PageableSearchRequest;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.SortableSearchRequest;
import global.citytech.remitpulse.countries.crudapi.CRUDRepository;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/** @author sunil */
public abstract class CommonResource<
    T extends ServiceObject, P extends PageableSearchRequest, S extends SortableSearchRequest> {

  protected abstract CRUDRepository<T, P, S> getCRUDRepository();

  protected abstract RequestAdaptor<T> getAddRequestAdaptor();

  protected abstract RequestAdaptor<T> getUpdateRequestAdaptor();

  protected abstract void setPrimaryKey(T serviceObject, String code);

  protected abstract T getServiceObjectWithPrimaryKey(String code);

  protected abstract RequestAdaptor<P> getPageableSearchRequestAdaptor();

  protected abstract RequestAdaptor<S> getSearchRequestAdaptor();

  @POST
  public Response add(JsonObject jsonObject) {
    T serviceObject = getAddRequestAdaptor().toServiceObject(jsonObject);
    return RestResponse.ok(getCRUDRepository().create(serviceObject));
  }

  @GET
  @Path("{code}")
  public Response viewDetails(@PathParam("code") String code) {
    T serviceObject = getServiceObjectWithPrimaryKey(code);
    return RestResponse.ok(getCRUDRepository().findOne(serviceObject));
  }

  @PUT
  @Path("{code}")
  public Response update(@PathParam("code") String code, JsonObject jsonObject) {
    T serviceObject = getUpdateRequestAdaptor().toServiceObject(jsonObject);
    setPrimaryKey(serviceObject, code);
    return RestResponse.ok(getCRUDRepository().edit(serviceObject));
  }

  @Path("/search")
  @POST
  public Response search(JsonObject jsonObject) {

    P pageableSearchRequest = getPageableSearchRequestAdaptor().toServiceObject(jsonObject);
    return RestResponse.ok(getCRUDRepository().search(pageableSearchRequest));
  }

  @Path("/search/all")
  @POST
  public Response searchAll(JsonObject jsonObject) {

    S searchRequest = getSearchRequestAdaptor().toServiceObject(jsonObject);
    return RestResponse.ok(getCRUDRepository().searchAll(searchRequest));
  }

  @Path("/search/key-name")
  @POST
  public Response searchAllKeyName(JsonObject jsonObject) {

    S searchRequest = getSearchRequestAdaptor().toServiceObject(jsonObject);
    return RestResponse.ok(getCRUDRepository().searchAllKeyName(searchRequest));
  }
}
