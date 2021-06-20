package global.citytech.remitpulse.countries.repositories.domains.services.config;

import java.util.ArrayList;
import java.util.List;

/** @author roslina */
public class Config {
  private List<IdType> idTypeList;
  private List<PurposeOfRemittance> purposeOfRemittanceList;
  private List<SourceOfFund> sourceOfFundList;
  private List<Relationship> relationshipList;
  private List<Occupation> occupationList;

  public Config() {
    this.idTypeList = new ArrayList<>();
    this.purposeOfRemittanceList = new ArrayList<>();
    this.sourceOfFundList = new ArrayList<>();
    this.relationshipList = new ArrayList<>();
    this.occupationList = new ArrayList<>();
  }

  public List<IdType> getIdTypeList() {
    return idTypeList;
  }

  public void setIdTypeList(List<IdType> idTypeList) {
    this.idTypeList = idTypeList;
  }

  public List<PurposeOfRemittance> getPurposeOfRemittanceList() {
    return purposeOfRemittanceList;
  }

  public void setPurposeOfRemittanceList(List<PurposeOfRemittance> purposeOfRemittanceList) {
    this.purposeOfRemittanceList = purposeOfRemittanceList;
  }

  public List<SourceOfFund> getSourceOfFundList() {
    return sourceOfFundList;
  }

  public void setSourceOfFundList(List<SourceOfFund> sourceOfFundList) {
    this.sourceOfFundList = sourceOfFundList;
  }

  public List<Relationship> getRelationshipList() {
    return relationshipList;
  }

  public void setRelationshipList(List<Relationship> relationshipList) {
    this.relationshipList = relationshipList;
  }

  public List<Occupation> getOccupationList() {
    return occupationList;
  }

  public void setOccupationList(List<Occupation> occupationList) {
    this.occupationList = occupationList;
  }
}
