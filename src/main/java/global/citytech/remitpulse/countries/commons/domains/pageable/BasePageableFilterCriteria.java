package global.citytech.remitpulse.countries.commons.domains.pageable;

/** @author sunil */
public class BasePageableFilterCriteria extends BasePageable implements PageableFilterCriteria {
  Integer startingIndex;

  @Override
  public Integer getStartingIndex() {
    return startingIndex;
  }

  @Override
  public void setStartingIndex(Integer startingIndex) {
    this.startingIndex = startingIndex;
  }
}
