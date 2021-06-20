package global.citytech.remitpulse.countries.repositories.domains.services.corridor;

import java.util.ArrayList;
import java.util.List;

/** @author roslina */
public class Corridor {
  private String code;
  private List<CorridorDetail> corridorDetailList;

  public Corridor() {
    this.corridorDetailList = new ArrayList<>();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<CorridorDetail> getCorridorDetailList() {
    return corridorDetailList;
  }

  public void setCorridorDetailList(List<CorridorDetail> corridorDetailList) {
    this.corridorDetailList = corridorDetailList;
  }
}
