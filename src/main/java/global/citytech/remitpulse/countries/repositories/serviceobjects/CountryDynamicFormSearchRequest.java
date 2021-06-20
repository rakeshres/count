package global.citytech.remitpulse.countries.repositories.serviceobjects;

import global.citytech.remitpulse.countries.commons.domains.pageable.BasePageable;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.PageableSearchRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bipin on 2020-03-04 14:50
 */
public class CountryDynamicFormSearchRequest extends BasePageable implements PageableSearchRequest {
  private String countryIso3;
  private String type;
  private String name;
  private List<String> idList;
  private String activeDate;



  public CountryDynamicFormSearchRequest() {
    this.idList= new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getActiveDate() {
    return activeDate;
  }

  public void setActiveDate(String activeDate) {
    this.activeDate = activeDate;
  }

  public List<String> getIdList() {
    return idList;
  }

  public void setIdList(List<String> idList) {
    this.idList = idList;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCountryIso3() {
    return countryIso3;
  }

  public void setCountryIso3(String countryIso3) {
    this.countryIso3 = countryIso3;
  }
}
