package global.citytech.remitpulse.countries.repositories.internal.filters;

import global.citytech.rabbit.core.commons.FilterCriteria;

import java.util.List;
import java.util.Objects;

/**
 * @author bipin on 6/20/19 2:38 PM.
 */
public class CountryFilter implements FilterCriteria {
    private String id;
    private String name;
    private String iso2;
    private String iso3;
    private String numericCode;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer startingIndex;
    private String sortParameter;
    private String sortBy;
    private Boolean active;
    private List<String> iso3List;

    public List<String> getIso3List() {
        return iso3List;
    }

    public void setIso3List(List<String> iso3List) {
        this.iso3List = iso3List;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSortParameter() {
        return sortParameter;
    }

    public void setSortParameter(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartingIndex() {
        return startingIndex;
    }

    public void setStartingIndex(Integer startingIndex) {
        this.startingIndex = startingIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryFilter that = (CountryFilter) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(iso2, that.iso2) &&
                Objects.equals(iso3, that.iso3) &&
                Objects.equals(numericCode, that.numericCode) &&
                Objects.equals(pageNumber, that.pageNumber) &&
                Objects.equals(pageSize, that.pageSize) &&
                Objects.equals(startingIndex, that.startingIndex) &&
                Objects.equals(sortParameter, that.sortParameter) &&
                Objects.equals(sortBy, that.sortBy) &&
                Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, iso2, iso3, numericCode, pageNumber, pageSize, startingIndex, sortParameter, sortBy, active);
    }
}
