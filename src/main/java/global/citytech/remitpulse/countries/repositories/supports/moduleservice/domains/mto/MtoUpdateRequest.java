package global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.mto;

import global.citytech.remitpulse.countries.repositories.domains.country.MasterDataConfiguration;

/**
 * @author sankalpa on 11/11/20
 */
public class MtoUpdateRequest {
    private MasterDataConfiguration masterDataConfiguration;
    private String country;

    public MtoUpdateRequest() {
    }

    public MtoUpdateRequest(String country,MasterDataConfiguration masterDataConfiguration) {
        this.masterDataConfiguration = masterDataConfiguration;
        this.country = country;
    }

    public MasterDataConfiguration getMasterDataConfiguration() {
        return masterDataConfiguration;
    }

    public void setMasterDataConfiguration(MasterDataConfiguration masterDataConfiguration) {
        this.masterDataConfiguration = masterDataConfiguration;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
