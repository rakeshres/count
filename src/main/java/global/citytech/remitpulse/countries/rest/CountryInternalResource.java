package global.citytech.remitpulse.countries.rest;

import global.citytech.rabbit.microprofile.RestResponse;
import global.citytech.remitpulse.countries.repositories.CountryRepository;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.rest.adaptors.CountryFindByIso3sRequestAdaptor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * @author sankalpa on 11/5/19
 */
@Path("ms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Named
public class CountryInternalResource {
    private CountryRepository countryRepository;
    private CountryFindByIso3sRequestAdaptor countryFindByIso3sRequestAdaptor;

    @Inject
    public CountryInternalResource(
            CountryRepository countryRepository,
            CountryFindByIso3sRequestAdaptor countryFindByIso3sRequestAdaptor) {
        this.countryRepository = countryRepository;
        this.countryFindByIso3sRequestAdaptor = countryFindByIso3sRequestAdaptor;
    }

    @GET
    @Path("{code}")
    public Response findOneCountryDetail(@PathParam("code") String code) {
        CountryInfo info = new CountryInfo();
        info.setIso3(code);
        return RestResponse.ok(this.countryRepository.findOne(info));
    }

    @POST
    @Path("search/values")
    public Response findAllCountryByTheirIso3(JsonObject jsonObject) {
        return RestResponse.ok(
                this.countryRepository.findByValues(
                        this.countryFindByIso3sRequestAdaptor.toServiceObject(jsonObject)));
    }

    @GET
    @Path("public/{code}")
    public Response findOneCountryDetailForInternal(@PathParam("code") String code) {
        CountryInfo info = new CountryInfo();
        info.setIso3(code);
        return RestResponse.ok(this.countryRepository.getCountryWithMasterDataConf(info));
    }

    @GET
    @Path("{code}/master-data/{type}")
    public Response findCountryMasterData(@PathParam("code") String code, @PathParam("type") String type) {
        CountryInfo info = new CountryInfo();
        info.setIso3(code);
        info = this.countryRepository.getCountryWithMasterDataConf(info);
        if (type.equalsIgnoreCase("idType")) {
            return RestResponse.ok(info.getMasterDataConfiguration().getIdType());
        }
        return RestResponse.ok(new ArrayList<>());
    }
}
