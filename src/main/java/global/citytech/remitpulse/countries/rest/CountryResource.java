package global.citytech.remitpulse.countries.rest;

import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.core.commons.PageableData;
import global.citytech.rabbit.core.search.dynamic.MultiValueData;
import global.citytech.rabbit.microprofile.RestResponse;
import global.citytech.rabbit.microprofile.security.RequiredPermission;
import global.citytech.remitpulse.countries.commons.constants.PaymentMethodChannel;
import global.citytech.remitpulse.countries.commons.domains.auditlog.DynamicAuditSearchCriteria;
import global.citytech.remitpulse.countries.commons.domains.auditlog.FieldAggregator;
import global.citytech.remitpulse.countries.repositories.CountryRepository;
import global.citytech.remitpulse.countries.repositories.constants.Permissions;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.country.GetLocationsRequest;
import global.citytech.remitpulse.countries.repositories.domains.services.paymentmethod.PaymentMethod;
import global.citytech.remitpulse.countries.repositories.internal.auditlog.CountryPendingInfoSearchRequest;
import global.citytech.remitpulse.countries.repositories.internal.dto.audit.AuditLogDto;
import global.citytech.remitpulse.countries.rest.adaptors.*;
import global.citytech.remitpulse.countries.rest.adaptors.auditlog.CountryModelJsonAdapter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bipin on 5/20/19 3:49 PM.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Named
public class CountryResource {

    private CountryAddRequestAdaptor countryAddRequestAdaptor;
    private CountryRepository countryRepository;
    private CountryFindAllRequestAdaptor countryFindAllRequestAdaptor;
    private CountryUpdateRequestAdaptor countryUpdateRequestAdaptor;
    private CountryFindByIso3sRequestAdaptor countryFindByIso3sRequestAdaptor;
    private CountryPendingFindAllRequestAdaptor countryPendingFindAllRequestAdaptor;
    private CountryVerifyRequestAdaptor countryVerifyRequestAdaptor;

    @Inject
    public CountryResource(
            CountryAddRequestAdaptor countryAddRequestAdaptor,
            CountryRepository countryRepository,
            CountryFindAllRequestAdaptor countryFindAllRequestAdaptor,
            CountryUpdateRequestAdaptor countryUpdateRequestAdaptor,
            CountryFindByIso3sRequestAdaptor countryFindByIso3sRequestAdaptor,
            CountryPendingFindAllRequestAdaptor countryPendingFindAllRequestAdaptor,
            CountryVerifyRequestAdaptor countryVerifyRequestAdaptor) {
        this.countryAddRequestAdaptor = countryAddRequestAdaptor;
        this.countryRepository = countryRepository;
        this.countryFindAllRequestAdaptor = countryFindAllRequestAdaptor;
        this.countryUpdateRequestAdaptor = countryUpdateRequestAdaptor;
        this.countryFindByIso3sRequestAdaptor = countryFindByIso3sRequestAdaptor;
        this.countryPendingFindAllRequestAdaptor = countryPendingFindAllRequestAdaptor;
        this.countryVerifyRequestAdaptor = countryVerifyRequestAdaptor;
    }

    @POST
    @RequiredPermission({Permissions.COUNTRY_ADD})
    public Response addCountry(JsonObject jsonObject) {
        return RestResponse.ok(
                this.countryRepository.create(this.countryAddRequestAdaptor.toServiceObject(jsonObject)));
    }

    @GET
    public Response getCountryList() {
        List<CountryInfo> countryInfoList = this.countryRepository.findByValues(new CountryInfo());
        return RestResponse.ok(
                countryInfoList.stream()
                        .sorted(Comparator.comparing(CountryInfo::getName, String::compareToIgnoreCase))
                        .collect(Collectors.toList()));
    }

    @POST
    @Path("changelog/pending/search")
    @RequiredPermission({Permissions.COUNTRY_PENDING_LIST})
    public Response findPendingList(JsonObject jsonObject) {
        CountryPendingInfoSearchRequest info =
                this.countryPendingFindAllRequestAdaptor.toServiceObject(jsonObject);
        info.setStatusList(Arrays.asList(AuditStatus.PENDING.getDescription()));
        return RestResponse.ok(countryRepository.findAuditList(info));
    }

    @POST
    @Path("changelog/search")
    @RequiredPermission({Permissions.COUNTRY_CHANGE_LOG_LIST})
    public Response findChangeLogList(JsonObject jsonObject) {
        CountryPendingInfoSearchRequest info =
                this.countryPendingFindAllRequestAdaptor.toServiceObject(jsonObject);
        info.setStatusList(Arrays.asList(AuditStatus.APPROVED.getDescription()));
        return RestResponse.ok(this.countryRepository.findAuditList(info));
    }

    @GET
    @Path("changelog/{id}")
    @RequiredPermission({Permissions.COUNTRY_CHANGE_LOG_DETAIL})
    public Response findChangeLogDetail(@PathParam("id") String id) {
        AuditLogInfo info = new AuditLogInfo();
        info.setId(id);
        AuditLogDto auditLogInfo = countryRepository.findAuditDetail(info);
        return RestResponse.ok(auditLogInfo);
    }

    @GET
    @Path("changelog/pending/{id}")
    @RequiredPermission({Permissions.COUNTRY_PENDING_DETAIL})
    public Response findPendingDetail(@PathParam("id") String id) {
        AuditLogInfo info = new AuditLogInfo();
        info.setId(id);
        return RestResponse.ok(countryRepository.findAuditDetail(info));
    }

    @POST
    @Path("search")
    @RequiredPermission({Permissions.COUNTRY_LIST})
    public Response findAllCountries(JsonObject jsonObject) {
        PageableData response =
                countryRepository.findAll(this.countryFindAllRequestAdaptor.toServiceObject(jsonObject));
        return RestResponse.ok(response);
    }

    @POST
    @Path("changelog/pending/approve/{id}")
    @RequiredPermission({Permissions.COUNTRY_VERIFY})
    public Response approvePendingCountry(JsonObject jsonObject, @PathParam("id") String id) {
        AuditLogInfo auditLogInfo = this.countryVerifyRequestAdaptor.toServiceObject(jsonObject);
        auditLogInfo.setId(id);
        return RestResponse.ok(this.countryRepository.approve(auditLogInfo));
    }

    @POST
    @Path("changelog/pending/reject/{id}")
    @RequiredPermission({Permissions.COUNTRY_VERIFY})
    public Response rejectPendingCountry(JsonObject jsonObject, @PathParam("id") String id) {
        AuditLogInfo auditLogInfo = this.countryVerifyRequestAdaptor.toServiceObject(jsonObject);
        auditLogInfo.setId(id);
        return RestResponse.ok(this.countryRepository.reject(auditLogInfo));
    }

    @GET
    @Path("{id}")
    @RequiredPermission({Permissions.COUNTRY_DETAIL})
    public Response findOneCountryDetail(@PathParam("id") String id) {
        CountryInfo info = new CountryInfo();
        info.setIso3(id);
        return RestResponse.ok(this.countryRepository.findOne(info));
    }

    @GET
    @Path("{id}/configs")
    @RequiredPermission({Permissions.COUNTRY_DETAIL})
    public Response findCountryConfig(@PathParam("id") String id) {
        CountryInfo info = new CountryInfo();
        info.setIso3(id);
        return RestResponse.ok(this.countryRepository.findOne(info).getConfigs());
    }

    @GET
    @Path("{id}/idtype")
    @RequiredPermission({Permissions.COUNTRY_DETAIL})
    public Response findCountryIdType(@PathParam("id") String id) {
        CountryInfo info = new CountryInfo();
        info.setIso3(id);
        return RestResponse.ok(this.countryRepository.findOne(info).getConfigs().getIdTypeList());
    }

    @PUT
    @Path("{id}")
    @RequiredPermission({Permissions.COUNTRY_EDIT})
    public Response updateCountry(JsonObject jsonObject, @PathParam("id") String id) {
        CountryInfo info = this.countryUpdateRequestAdaptor.toServiceObject(jsonObject);
        info.setId(id);
        return RestResponse.ok(this.countryRepository.update(info));
    }

    @POST
    @Path("search/values")
    public Response findAllCountryByTheirIso3(JsonObject jsonObject) {
        List<CountryInfo> values =
                this.countryRepository.findByValues(
                        this.countryFindByIso3sRequestAdaptor.toServiceObject(jsonObject));
        return RestResponse.ok(
                values.stream()
                        .sorted(Comparator.comparing(CountryInfo::getName, String::compareToIgnoreCase))
                        .collect(Collectors.toList()));
    }

    @POST
    @Path("changelog/reject/search")
    @RequiredPermission({Permissions.COUNTRY_REJECTED_LIST})
    public Response findRejectedList(JsonObject jsonObject) {
        CountryPendingInfoSearchRequest info =
                this.countryPendingFindAllRequestAdaptor.toServiceObject(jsonObject);
        info.setStatusList(Arrays.asList(AuditStatus.REJECTED.getDescription()));
        return RestResponse.ok(countryRepository.findAuditList(info));
    }

    @GET
    @Path("changelog/reject/{id}")
    @RequiredPermission({Permissions.COUNTRY_REJECTED_DETAIL})
    public Response findRejectedDetail(@PathParam("id") String id) {
        AuditLogInfo info = new AuditLogInfo();
        info.setId(id);
        return RestResponse.ok(countryRepository.findAuditDetail(info));
    }

    @GET
    @Path("payment-channels")
    public Response getOperationType() {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Arrays.stream(PaymentMethodChannel.values())
                .forEach(
                        paymentMethodChannel -> {
                            JsonObject jsonObject =
                                    Json.createObjectBuilder()
                                            .add("title", paymentMethodChannel.getTitle())
                                            .add("code", paymentMethodChannel.getCode())
                                            .add("variant", paymentMethodChannel.getVariant())
                                            .build();
                            jsonArrayBuilder.add(jsonObject);
                        });
        return RestResponse.okWithJsonObject(jsonArrayBuilder);
    }

    @GET
    @Path("fields/audit")
    public Response getAuditSearchFields() {
        return RestResponse.ok(FieldAggregator.getSearchColumnForCountryAudit());
    }

    @POST
    @Path("search/audits")
    @RequiredPermission({Permissions.COUNTRY_AUDIT_LOG})
    public Response getAuditList(JsonObject jsonObject) {
        DynamicAuditSearchCriteria searchCriteria = CountryModelJsonAdapter.toDynamicAuditSearchRequest(jsonObject);
        return RestResponse.ok(this.countryRepository.getAuditList(searchCriteria));
    }


    @GET
    @Path("search/audits/{id}")
    @RequiredPermission({Permissions.COUNTRY_AUDIT_LOG})
    public Response getAuditDetail(@PathParam("id") String id) {
        AuditLogInfo info = new AuditLogInfo();
        info.setId(id);
        return RestResponse.ok(this.countryRepository.findAuditDetail(info));
    }

    @GET
    @Path("{countryCode}/banks/select-item")
    public Response getBankListByCountryCode(@PathParam("countryCode") String countryCode) {
        CountryInfo info = new CountryInfo();
        info.setIso3(countryCode);
        List<PaymentMethod> paymentMethods = this.countryRepository.findOne(info).getPaymentMethodList().stream()
                .filter(ps -> ps.getIsActive()).collect(Collectors.toList());
        List<MultiValueData> multiValueDataList = new ArrayList<>();
        paymentMethods.forEach(pm -> {
            MultiValueData multiValueData = new MultiValueData(pm.getCode(), pm.getTitle());
            multiValueDataList.add(multiValueData);
        });
        return RestResponse.ok(multiValueDataList);
    }

    @GET
    @Path("{countryCode}/{categoryCode}")
    public Response getLocationsByCountryCode(@PathParam("countryCode")String countryCode,
                                              @PathParam("categoryCode")String categoryCode){
        GetLocationsRequest info = new GetLocationsRequest();
        info.setCountryCode(countryCode);
        info.setCategory(categoryCode);
        return RestResponse.ok(this.countryRepository.findLocationInfoByCountryCode(info));
    }
}
