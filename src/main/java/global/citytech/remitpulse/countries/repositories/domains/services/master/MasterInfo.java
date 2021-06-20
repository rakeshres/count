package global.citytech.remitpulse.countries.repositories.domains.services.master;

import global.citytech.rabbit.core.commons.ServiceObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author bipin on 5/20/19 3:26 PM.
 */
public class MasterInfo implements ServiceObject {
    private String id;
    private String code;
    private String description;
    private String masterType;
    private String masterTypeName;
    private List<String> masterTypeList;

    private Boolean active;

    public MasterInfo() {
        this.masterTypeList = new ArrayList<>();
    }

    public List<String> getMasterTypeList() {
        return masterTypeList;
    }

    public void setMasterTypeList(List<String> masterTypeList) {
        this.masterTypeList = masterTypeList;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterTypeName() {
        return masterTypeName;
    }

    public void setMasterTypeName(String masterTypeName) {
        this.masterTypeName = masterTypeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMasterType() {
        return masterType;
    }

    public void setMasterType(String masterType) {
        this.masterType = masterType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterInfo that = (MasterInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(description, that.description) &&
                Objects.equals(masterType, that.masterType) &&
                Objects.equals(masterTypeName, that.masterTypeName) &&
                Objects.equals(masterTypeList, that.masterTypeList) &&
                Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, description, masterType, masterTypeName, masterTypeList, active);
    }
}
