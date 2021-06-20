package global.citytech.remitpulse.countries.rest.adaptors;

import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.config.*;
import global.citytech.remitpulse.countries.repositories.domains.services.corridor.Corridor;
import global.citytech.remitpulse.countries.repositories.domains.services.corridor.CorridorDetail;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.Currency;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyLimit;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyOperationType;
import global.citytech.remitpulse.countries.repositories.domains.services.master.OperationType;
import global.citytech.rabbit.core.commons.RequestAdaptor;
import global.citytech.remitpulse.countries.repositories.domains.services.paymentmethod.PaymentMethod;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

/** @author bipin on 6/28/19 11:19 AM. */
public interface CountryDataAdaptor extends RequestAdaptor<CountryInfo> {

  default CountryInfo convertCommonData(JsonObject jsonObject) {
    CountryInfo info = new CountryInfo();
    info.setName(jsonObject.getString("name", ""));
    info.setIso2(jsonObject.getString("iso2", ""));
    info.setNumericCode(jsonObject.getString("numericCode", ""));
    info.setIso3(jsonObject.getString("iso3", ""));
    info.setActive(jsonObject.getBoolean("active", false));
    if (jsonObject.containsKey("operationTypeList") & jsonObject.isNull("operationTypeList")) {
      ExceptionManager.throwJsonError();
    }
    JsonArray operationsArray = jsonObject.getJsonArray("operationTypeList");
    List<OperationType> operationTypes = new ArrayList<>();
    for (int i = 0; i < operationsArray.size(); i++) {
      OperationType ot = new OperationType();
      ot.setCode(operationsArray.get(i).asJsonObject().getString("code", ""));
      ot.setEffectiveFrom(operationsArray.get(i).asJsonObject().getString("effectiveFrom", ""));
      ot.setEffectiveTo(operationsArray.get(i).asJsonObject().getString("effectiveTo", ""));
      operationTypes.add(ot);
    }
    info.setOperationTypeList(operationTypes);

    if (jsonObject.containsKey("currencyList") & jsonObject.isNull("currencyList")) {
      ExceptionManager.throwJsonError();
    }
    JsonArray currenciesArray = jsonObject.getJsonArray("currencyList");
    List<Currency> currencies = new ArrayList<>();
    for (int i = 0; i < currenciesArray.size(); i++) {
      Currency currency = new Currency();
      currency.setIsoAlpha(currenciesArray.get(i).asJsonObject().getString("isoAlpha", ""));
      currency.setDefault(currenciesArray.get(i).asJsonObject().getBoolean("isDefault", false));
      if (currenciesArray.get(i).getValueType() == JsonValue.ValueType.NULL) continue;
      JsonArray currencyOperationTypeArray =
          currenciesArray.get(i).asJsonObject().getJsonArray("currencyOperationTypeList");
      List<CurrencyOperationType> currencyOperationTypes = new ArrayList<>();
      for (int j = 0; j < currencyOperationTypeArray.size(); j++) {
        CurrencyOperationType currencyOperationType = new CurrencyOperationType();
        currencyOperationType.setCode(
            currencyOperationTypeArray.get(j).asJsonObject().getString("code", ""));
        if (currencyOperationTypeArray.get(j).asJsonObject().containsKey("currencyLimitList")
            & currencyOperationTypeArray.get(j).asJsonObject().isNull("currencyLimitList")) {
          ExceptionManager.throwJsonError();
        }
        JsonArray currencyLimitArray =
            currencyOperationTypeArray.get(j).asJsonObject().getJsonArray("currencyLimitList");

        List<CurrencyLimit> currencyLimits = new ArrayList<>();
        for (int k = 0; k < currencyLimitArray.size(); k++) {
          if (currencyLimitArray.get(k).getValueType() == JsonValue.ValueType.NULL) continue;
          CurrencyLimit currencyLimit = new CurrencyLimit();
          if (currencyLimitArray.get(k).asJsonObject().isNull("maximumAmount")
              || currencyLimitArray.get(k).asJsonObject().isNull("minimumAmount")) {
            ExceptionManager.throwJsonError();
          }
          currencyLimit.setMaximumAmount(
              currencyLimitArray
                  .get(k)
                  .asJsonObject()
                  .getJsonNumber("maximumAmount")
                  .doubleValue());
          currencyLimit.setMinimumAmount(
              currencyLimitArray
                  .get(k)
                  .asJsonObject()
                  .getJsonNumber("minimumAmount")
                  .doubleValue());
          currencyLimit.setChannel(
              currencyLimitArray.get(k).asJsonObject().getString("channel", ""));
          currencyLimit.setEffectiveTo(
              currencyLimitArray.get(k).asJsonObject().getString("effectiveTo", ""));
          currencyLimit.setEffectiveFrom(
              currencyLimitArray.get(k).asJsonObject().getString("effectiveFrom", ""));
          currencyLimits.add(currencyLimit);
        }

        currencyOperationType.setCurrencyLimitList(currencyLimits);
        currencyOperationTypes.add(currencyOperationType);
        currency.setCurrencyOperationTypeList(currencyOperationTypes);
      }

      currencies.add(currency);
    }
    info.setCurrencyList(currencies);

    if (jsonObject.containsKey("corridorList") & jsonObject.isNull("corridorList")) {
      ExceptionManager.throwJsonError();
    }
    JsonArray corridorArray = jsonObject.getJsonArray("corridorList");
    List<Corridor> corridors = new ArrayList<>();
    for (int i = 0; i < corridorArray.size(); i++) {
      Corridor corridor = new Corridor();
      corridor.setCode(corridorArray.get(i).asJsonObject().getString("code", ""));
      if (corridorArray.get(i).getValueType() == JsonValue.ValueType.NULL) continue;
      JsonArray corridorDetailArray =
          corridorArray.get(i).asJsonObject().getJsonArray("corridorDetailList");
      List<CorridorDetail> corridorDetails = new ArrayList<>();
      for (int j = 0; j < corridorDetailArray.size(); j++) {
        CorridorDetail corridorDetail = new CorridorDetail();
        corridorDetail.setCurrencyIsoAlpha(
            corridorDetailArray.get(j).asJsonObject().getString("currencyIsoAlpha", ""));
        corridorDetail.setCountryIso3(
            corridorDetailArray.get(j).asJsonObject().getString("countryIso3", ""));
        corridorDetail.setDestinationCurrencyIsoAlpha(
            corridorDetailArray.get(j).asJsonObject().getString("destinationCurrencyIsoAlpha", ""));
        corridorDetail.setOriginCurrencyIsoAlpha(
            corridorDetailArray.get(j).asJsonObject().getString("originCurrencyIsoAlpha", ""));
        corridorDetail.setActive(
            corridorDetailArray.get(j).asJsonObject().getBoolean("isActive", false));
        corridorDetails.add(corridorDetail);
      }
      corridor.setCorridorDetailList(corridorDetails);
      corridors.add(corridor);
    }
    info.setCorridorList(corridors);

    if (jsonObject.containsKey("paymentMethodList") & jsonObject.isNull("paymentMethodList")) {
      ExceptionManager.throwJsonError();
    }
    JsonArray paymentMethodArray = jsonObject.getJsonArray("paymentMethodList");
    List<PaymentMethod> paymentMethods = new ArrayList<>();
    for (int i = 0; i < paymentMethodArray.size(); i++) {
      PaymentMethod paymentMethod = new PaymentMethod();
      paymentMethod.setChannel(paymentMethodArray.get(i).asJsonObject().getString("channel", ""));
      paymentMethod.setTitle(paymentMethodArray.get(i).asJsonObject().getString("title", ""));
      paymentMethod.setCode(paymentMethodArray.get(i).asJsonObject().getString("code", ""));
      paymentMethod.setIsActive(
          paymentMethodArray.get(i).asJsonObject().getBoolean("isActive", false));
      paymentMethods.add(paymentMethod);
    }
    info.setPaymentMethodList(paymentMethods);
    info.setConfigs(this.getConfig(jsonObject));
    return info;
  }

  private List<IdType> getIdTypeList(JsonObject configObject) {
    if (configObject.containsKey("idTypeList") & configObject.isNull("idTypeList")) {
      ExceptionManager.throwJsonError();
    }
    JsonArray idTypeArray = configObject.getJsonArray("idTypeList");
    List<IdType> idTypeList = new ArrayList<>();
    for (int i = 0; i < idTypeArray.size(); i++) {
      IdType idType = new IdType();
      idType.setCode(idTypeArray.get(i).asJsonObject().getString("code", ""));
      idType.setHasIssueDate(idTypeArray.get(i).asJsonObject().getBoolean("hasIssueDate", false));
      idType.setHasExpiryDate(idTypeArray.get(i).asJsonObject().getBoolean("hasExpiryDate", false));
      idType.setFormat(idTypeArray.get(i).asJsonObject().getString("format", ""));
      idType.setActive(idTypeArray.get(i).asJsonObject().getBoolean("isActive", false));
      idTypeList.add(idType);
    }
    return idTypeList;
  }

  private List<PurposeOfRemittance> getPurposeOfRemittanceList(JsonObject configObject) {
    if (configObject.containsKey("purposeOfRemittanceList")
        & configObject.isNull("purposeOfRemittanceList")) {
      ExceptionManager.throwJsonError();
    }
    JsonArray remittanceArray = configObject.getJsonArray("purposeOfRemittanceList");
    List<PurposeOfRemittance> remittanceArrayList = new ArrayList<>();
    for (int i = 0; i < remittanceArray.size(); i++) {
      PurposeOfRemittance purposeOfRemittance = new PurposeOfRemittance();
      purposeOfRemittance.setCode(remittanceArray.get(i).asJsonObject().getString("code", ""));
      purposeOfRemittance.setActive(
          remittanceArray.get(i).asJsonObject().getBoolean("isActive", false));
      remittanceArrayList.add(purposeOfRemittance);
    }
    return remittanceArrayList;
  }

  private List<SourceOfFund> getSourceOfFundList(JsonObject configObject) {
    if (configObject.containsKey("sourceOfFundList") & configObject.isNull("sourceOfFundList")) {
      ExceptionManager.throwJsonError();
    }
    JsonArray sourceOfFundArray = configObject.getJsonArray("sourceOfFundList");
    List<SourceOfFund> sourceOfFundList = new ArrayList<>();
    for (int i = 0; i < sourceOfFundArray.size(); i++) {
      SourceOfFund sourceOfFund = new SourceOfFund();
      sourceOfFund.setCode(sourceOfFundArray.get(i).asJsonObject().getString("code", ""));
      sourceOfFund.setActive(sourceOfFundArray.get(i).asJsonObject().getBoolean("isActive", false));
      sourceOfFundList.add(sourceOfFund);
    }
    return sourceOfFundList;
  }

  private List<Relationship> getRelationshipList(JsonObject configObject) {
    if (configObject.containsKey("relationshipList") & configObject.isNull("relationshipList")) {
      ExceptionManager.throwJsonError();
    }
    JsonArray relationshipArray = configObject.getJsonArray("relationshipList");
    List<Relationship> relationshipList = new ArrayList<>();
    for (int i = 0; i < relationshipArray.size(); i++) {
      Relationship relationship = new Relationship();
      relationship.setCode(relationshipArray.get(i).asJsonObject().getString("code", ""));
      relationship.setActive(relationshipArray.get(i).asJsonObject().getBoolean("isActive", false));
      relationshipList.add(relationship);
    }
    return relationshipList;
  }

  private List<Occupation> getOccupationList(JsonObject configObject) {
    if (configObject.containsKey("occupationList") & configObject.isNull("occupationList")) {
      ExceptionManager.throwJsonError();
    }
    JsonArray occupationArray = configObject.getJsonArray("occupationList");
    List<Occupation> occupationList = new ArrayList<>();
    for (int i = 0; i < occupationArray.size(); i++) {
      Occupation Occupation = new Occupation();
      Occupation.setCode(occupationArray.get(i).asJsonObject().getString("code", ""));
      Occupation.setActive(occupationArray.get(i).asJsonObject().getBoolean("isActive", false));
      occupationList.add(Occupation);
    }
    return occupationList;
  }

  private Config getConfig(JsonObject jsonObject) {
    JsonObject configObject = jsonObject.get("configs").asJsonObject();
    Config config = new Config();
    config.setIdTypeList(this.getIdTypeList(configObject));
    config.setPurposeOfRemittanceList(this.getPurposeOfRemittanceList(configObject));
    config.setSourceOfFundList(this.getSourceOfFundList(configObject));
    config.setRelationshipList(this.getRelationshipList(configObject));
    config.setOccupationList(this.getOccupationList(configObject));
    return config;
  }
}
