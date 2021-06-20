package global.citytech.remitpulse.countries.repositories.domains.country;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sankalpa on 9/4/20
 */
public class MasterDataConfiguration {
  private List<MasterDataInfo> relationship;
  private List<MasterDataInfo> occupation;
  private List<MasterDataInfo> sourceOfFund;
  private List<MasterDataInfo> purpose;
  private List<MasterDataInfo> idType;

  public MasterDataConfiguration() {
    this.relationship = new ArrayList<>();
    this.occupation = new ArrayList<>();
    this.sourceOfFund = new ArrayList<>();
    this.purpose = new ArrayList<>();
    this.idType = new ArrayList<>();
  }

  public List<MasterDataInfo> getRelationship() {
    return relationship;
  }

  public void setRelationship(List<MasterDataInfo> relationship) {
    this.relationship = relationship;
  }

  public List<MasterDataInfo> getOccupation() {
    return occupation;
  }

  public void setOccupation(List<MasterDataInfo> occupation) {
    this.occupation = occupation;
  }

  public List<MasterDataInfo> getSourceOfFund() {
    return sourceOfFund;
  }

  public void setSourceOfFund(List<MasterDataInfo> sourceOfFund) {
    this.sourceOfFund = sourceOfFund;
  }

  public List<MasterDataInfo> getPurpose() {
    return purpose;
  }

  public void setPurpose(List<MasterDataInfo> purpose) {
    this.purpose = purpose;
  }

  public List<MasterDataInfo> getIdType() {
    return idType;
  }

  public void setIdType(List<MasterDataInfo> idType) {
    this.idType = idType;
  }
}
