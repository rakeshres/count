package global.citytech.remitpulse.countries.rest;

import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.microprofile.RestResponse;
import global.citytech.rabbit.microprofile.security.RequiredPermission;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.AuditLogRepository;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogSearchRequest;
import global.citytech.remitpulse.countries.crudapiimplhelper.repositories.dto.AuditLogVerifyRequest;
import global.citytech.remitpulse.countries.repositories.constants.Permissions;
import global.citytech.remitpulse.countries.rest.adaptors.auditlog.AuditLogSearchRequestAdaptor;
import global.citytech.remitpulse.countries.rest.adaptors.auditlog.AuditLogVerifyRequestAdaptor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

/** @author bipin on 2020-03-05 16:32 */
@Path("/dynamic-form/changelog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Named
public class AuditLogResource {
  private AuditLogSearchRequestAdaptor auditLogSearchRequestAdaptor;
  private AuditLogVerifyRequestAdaptor auditLogVerifyRequestAdaptor;
  private AuditLogRepository auditLogRepository;

  @Inject
  public AuditLogResource(
      AuditLogSearchRequestAdaptor auditLogSearchRequestAdaptor,
      AuditLogVerifyRequestAdaptor auditLogVerifyRequestAdaptor,
      AuditLogRepository auditLogRepository) {
    this.auditLogSearchRequestAdaptor = auditLogSearchRequestAdaptor;
    this.auditLogVerifyRequestAdaptor = auditLogVerifyRequestAdaptor;
    this.auditLogRepository = auditLogRepository;
  }

  @Path("/search")
  @RequiredPermission({Permissions.COUNTRY_CHANGE_LOG_LIST})
  @POST
  public Response searchAuditLogs(JsonObject jsonObject) {
    AuditLogSearchRequest searchRequest =
        this.auditLogSearchRequestAdaptor.toServiceObject(jsonObject);
    searchRequest.setStatusList(
        List.of(AuditStatus.APPROVED.name()));

    return RestResponse.ok(auditLogRepository.searchAuditLog(searchRequest));
  }

  @GET
  @Path("{id}")
  @RequiredPermission({Permissions.COUNTRY_CHANGE_LOG_DETAIL})
  public Response findOnePartner(@PathParam("id") String id) {
    AuditLogSearchRequest auditLogSearchRequest = new AuditLogSearchRequest();
    auditLogSearchRequest.setId(id);
    AuditLogInfo auditLogInfo = this.auditLogRepository.findOne(auditLogSearchRequest);
    return RestResponse.ok(auditLogInfo);
  }

  @Path("pending/search")
  @RequiredPermission({Permissions.COUNTRY_CHANGE_LOG_LIST})
  @POST
  public Response searchPendingAuditLogs(JsonObject jsonObject) {
    AuditLogSearchRequest searchRequest =
        this.auditLogSearchRequestAdaptor.toServiceObject(jsonObject);
    searchRequest.setStatusList(Collections.singletonList(AuditStatus.PENDING.name()));
    return RestResponse.ok(auditLogRepository.searchAuditLog(searchRequest));
  }

  @GET
  @RequiredPermission({Permissions.COUNTRY_CHANGE_LOG_DETAIL})
  @Path("pending/{id}")
  public Response findOnePendingDetail(@PathParam("id") String id) {
    AuditLogSearchRequest auditLogSearchRequest = new AuditLogSearchRequest();
    auditLogSearchRequest.setId(id);
    AuditLogInfo auditLogInfo = this.auditLogRepository.findOne(auditLogSearchRequest);
    return RestResponse.ok(auditLogInfo);
  }

  @POST
  @RequiredPermission({Permissions.COUNTRY_VERIFY})
  @Path("pending/approve/{id}")
  public Response approvePendingLog(@PathParam("id") String id, JsonObject jsonObject) {
    AuditLogVerifyRequest auditLogVerifyRequest =
        this.auditLogVerifyRequestAdaptor.toServiceObject(jsonObject);
    auditLogVerifyRequest.setId(id);
    auditLogVerifyRequest.setActionToPerform(AuditLogVerifyRequest.ActionToPerform.APPROVE);
    AuditLogInfo auditLogInfo = this.auditLogRepository.approve(auditLogVerifyRequest);
    return RestResponse.ok(auditLogInfo);
  }

  @POST
  @RequiredPermission({Permissions.COUNTRY_VERIFY})
  @Path("pending/reject/{id}")
  public Response rejectPendingLog(@PathParam("id") String id, JsonObject jsonObject) {

    AuditLogVerifyRequest auditLogVerifyRequest =
        this.auditLogVerifyRequestAdaptor.toServiceObject(jsonObject);
    auditLogVerifyRequest.setId(id);
    auditLogVerifyRequest.setActionToPerform(AuditLogVerifyRequest.ActionToPerform.REJECT);
    AuditLogInfo auditLogInfo = this.auditLogRepository.reject(auditLogVerifyRequest);
    return RestResponse.ok(auditLogInfo);
  }

  @Path("reject/search")
  @POST
  @RequiredPermission({Permissions.COUNTRY_CHANGE_LOG_LIST})
  public Response searchRejectedAuditLogs(JsonObject jsonObject) {
    AuditLogSearchRequest searchRequest =
        this.auditLogSearchRequestAdaptor.toServiceObject(jsonObject);
    searchRequest.setStatusList(Collections.singletonList(AuditStatus.REJECTED.getCode()));
    return RestResponse.ok(auditLogRepository.searchAuditLog(searchRequest));
  }

  @GET
  @Path("reject/{id}")
  @RequiredPermission({Permissions.COUNTRY_CHANGE_LOG_DETAIL})
  public Response findOneRejectedDetail(@PathParam("id") String id) {
    AuditLogSearchRequest auditLogSearchRequest = new AuditLogSearchRequest();
    auditLogSearchRequest.setId(id);
    AuditLogInfo auditLogInfo = this.auditLogRepository.findOne(auditLogSearchRequest);
    return RestResponse.ok(auditLogInfo);
  }
}
