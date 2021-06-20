package global.citytech.remitpulse.countries.repositories.internal.service;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.commons.constants.CityConstants;
import global.citytech.remitpulse.countries.commons.constants.MasterConstants;
import global.citytech.remitpulse.countries.commons.constants.MasterType;
import global.citytech.remitpulse.countries.commons.constants.PaymentMethodChannel;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.HelperUtilsLocal;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.config.*;
import global.citytech.remitpulse.countries.repositories.domains.services.corridor.Corridor;
import global.citytech.remitpulse.countries.repositories.domains.services.corridor.CorridorDetail;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.Currency;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.*;
import global.citytech.remitpulse.countries.repositories.domains.services.master.*;
import global.citytech.remitpulse.countries.repositories.constants.CountryDataLengthConstants;
import global.citytech.remitpulse.countries.repositories.domains.services.paymentmethod.PaymentMethod;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryEntity;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryFilter;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** @author bipin on 6/19/19 2:29 PM. */
public class CountryValidatorServiceImpl implements CountryValidatorService {
  private static Logger logger = Logger.getLogger(CountryValidatorServiceImpl.class.getName());

  private MasterModuleService masterModuleService;
  private CurrencyModuleService currencyModuleService;
  private CountryDao countryDao;

  @Inject
  public CountryValidatorServiceImpl(
      MasterModuleService masterModuleService,
      CurrencyModuleService currencyModuleService,
      CountryDao countryDao) {
    this.masterModuleService = masterModuleService;
    this.currencyModuleService = currencyModuleService;
    this.countryDao = countryDao;
  }

  @Override
  public void validateBasicInformation(CountryInfo request) {
    logger.info("BASIC INFORMATION VALIDATION " + Jsons.toJsonObj(request));
    if (Strings.isNullOrEmpty(request.getName())) {
      ExceptionManager.throwCountryNameMissing();
    }

    if (!HelperUtils.isOnlyAlphabetsWithSpaces(request.getName())) {
      ExceptionManager.throwCountryNameIsInvalid();
    }

    if (request.getName().length()
        < CountryDataLengthConstants.COUNTRY_NAME_MIN_LENGTH.getValue()) {
      ExceptionManager.throwCountryNameLengthLess();
    }

    if (request.getName().length()
        > CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()) {
      ExceptionManager.throwCountryNameLengthExceeded();
    }

    if (Strings.isNullOrEmpty(request.getIso2())) {
      ExceptionManager.throwIso2Missing();
    }

    if (request.getIso2().length() != CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()) {
      ExceptionManager.throwIso2LengthInvalid();
    }

    if (!HelperUtilsLocal.containsOnlyAlphabeticCharacters(request.getIso2())) {
      ExceptionManager.throwIso2Invalid();
    }

    if (Strings.isNullOrEmpty(request.getIso3())) {
      ExceptionManager.throwIso3Missing();
    }

    if (request.getIso3().length() != CountryDataLengthConstants.ISO_3_VALID_LENGTH.getValue()) {
      ExceptionManager.throwIso3LengthInvalid();
    }

    if (!HelperUtilsLocal.containsOnlyAlphabeticCharacters(request.getIso3())) {
      ExceptionManager.throwIso3Invalid();
    }

    if (HelperUtils.isBlankOrNull(request.getNumericCode())) {
      ExceptionManager.throwNumericCodeMissing();
    }

    if (request.getNumericCode().length()
            != CountryDataLengthConstants.NUMERIC_CODE_LENGTH.getValue()
        || !HelperUtils.isNumeric(request.getNumericCode())) {

      ExceptionManager.throwNumericCodeInvalid();
    }

    this.checkIfValidOperationTypeAndDateValid(request);
  }

  private void checkIfValidOperationTypeAndDateValid(CountryInfo request) {
    MasterInfo info = new MasterInfo();
    info.setMasterTypeList(Collections.singletonList(MasterType.OPERATION_TYPE.getCode()));
    List<MasterInfo> infoList = this.masterModuleService.fetchMasterInfo(info);
    if (request.getOperationTypeList().size() > 0) {
      for (OperationType ot : request.getOperationTypeList()) {
        if (!isValidOperationCode(ot.getCode(), infoList)) {
          ExceptionManager.throwInvalidOperationType();
        }

        checkValidEffectiveFromAndTo(ot.getEffectiveFrom(), ot.getEffectiveTo());
      }
    }
  }

  private boolean isValidOperationCode(String code, List<MasterInfo> infoList) {
    for (MasterInfo info : infoList) {
      if (code.equalsIgnoreCase(info.getCode())) {
        return true;
      }
    }
    return false;
  }

  private boolean isValidContinent(String continentCode) {
    List<Continent> fromMaster = this.masterModuleService.getContinents();
    for (Continent c : fromMaster) {
      if (c.getCode().equals(continentCode)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void validateCurrencyInformation(CountryInfo request) {
    if (request.getCurrencyList().size() == 0) {
      ExceptionManager.throwCurrencyShouldNotBeEmpty();
    }

    if (!this.isDefaultCurrencyProvided(request)) {
      ExceptionManager.throwDefaultCurrencyNotProvided();
    }

    if (this.getDefaultCurrencyCount(request) > 1) {
      ExceptionManager.throwDefaultCurrencyShouldBeOne();
    }
    if (this.currencyRepeated(request)) {
      ExceptionManager.throwRepeatedCurrencyCode();
    }

    if (!this.validateCurrencyCodes(request)) {
      ExceptionManager.throwInvalidCurrencyCode();
    }

    if (this.isOperationTypeNotPresentForCurrency(request)) {
      ExceptionManager.throwOperationTypeForCurrencyMissing();
    }

    if (this.isLimitNotProvidedForAllOperationsTypes(request)) {
      ExceptionManager.throwLimitNotProvidedForOperationType();
    }

    if (this.invalidPaymentChannel(request)) {
      ExceptionManager.throwInvalidPaymentChannel();
    }

    this.validateLimitInformation(request);

    this.checkOverlapDatesInAmountLimit(request);

    this.validateIfCurrencyOperationTypeAndOperationTypeInBasicInfoMatches(request);

    this.validateDateIsWithinSpecifiedInOperationTypes(request);

    this.validateDecimalValueCount(request);
  }

  private void validateIfCurrencyOperationTypeAndOperationTypeInBasicInfoMatches(
      CountryInfo request) {
    HashMap<String, Boolean> b = new HashMap<>();
    request.getOperationTypeList().forEach(obj -> b.put(obj.getCode(), true));
    for (Currency currency : request.getCurrencyList()) {
      for (CurrencyOperationType currencyOperationType : currency.getCurrencyOperationTypeList()) {
        if (!b.containsKey(currencyOperationType.getCode())) {
          logger.info(
              "Operation type given in currency is not mentioned in operation type in basic info page");
          ExceptionManager.throwOperationTypeInCurrencyDoesNotMatchInOperationTyepInBasicInfo();
        }
        if (b.containsKey(currencyOperationType.getCode())) {
          b.replace(currencyOperationType.getCode(), false);
        }
      }
    }
    for (String key : b.keySet()) {
      if (b.get(key)) {
        logger.info(
            "Operation type in basic info page has more operation type than given inside currency ");
        ExceptionManager.throwOperationTypeInCurrencyDoesNotMatchInOperationTyepInBasicInfo();
      }
    }
  }

  private void validateDateIsWithinSpecifiedInOperationTypes(CountryInfo request) {
    for (Currency currency : request.getCurrencyList()) {
      for (CurrencyOperationType currencyOperationType : currency.getCurrencyOperationTypeList()) {
        // check data of this currency operation type
        this.checkCurrencyOperationTypeWithBasicInfoOperationTypes(request, currencyOperationType);
      }
    }
  }

  private void checkCurrencyOperationTypeWithBasicInfoOperationTypes(
      CountryInfo request, CurrencyOperationType currencyOperationType) {

    for (CurrencyLimit currencyLimit : currencyOperationType.getCurrencyLimitList()) {

      for (OperationType operationType : request.getOperationTypeList()) {

        if (operationType.getCode().equals(currencyOperationType.getCode())) {

          if (HelperUtilsLocal.toLocalDateTime(currencyLimit.getEffectiveFrom())
                  .isBefore(HelperUtilsLocal.toLocalDateTime(operationType.getEffectiveFrom()))
              || HelperUtilsLocal.toLocalDateTime(currencyLimit.getEffectiveTo())
                  .isAfter(HelperUtilsLocal.toLocalDateTime(operationType.getEffectiveTo()))
              || HelperUtilsLocal.toLocalDateTime(currencyLimit.getEffectiveFrom())
                  .isAfter(HelperUtilsLocal.toLocalDateTime(operationType.getEffectiveTo()))
              || HelperUtilsLocal.toLocalDateTime(currencyLimit.getEffectiveTo())
                  .isBefore(HelperUtilsLocal.toLocalDateTime(operationType.getEffectiveFrom()))) {

            ExceptionManager.throwCurrencyLimitDateNotWithinRangeMentionedInOperationTypes();
          }
        }
      }
    }
  }

  private boolean currencyRepeated(CountryInfo request) {
    List<String> allCurrencyCodes =
        request.getCurrencyList().stream().map(Currency::getIsoAlpha).collect(Collectors.toList());
    Set<String> currencySet = new HashSet<>(allCurrencyCodes);
    return allCurrencyCodes.size() > currencySet.size();
  }

  private void checkOverlapDatesInAmountLimit(CountryInfo request) {
    for (Currency currency : request.getCurrencyList()) {
      for (CurrencyOperationType currencyOperationType : currency.getCurrencyOperationTypeList()) {
        List<CurrencyLimit> currencyLimitList = currencyOperationType.getCurrencyLimitList();
        for (int i = 0; i < currencyLimitList.size(); i++) {
          for (int j = 0; j < currencyLimitList.size(); j++) {
            if (i == j
                || !currencyLimitList
                    .get(i)
                    .getChannel()
                    .equals(currencyLimitList.get(j).getChannel())) {
              continue;
            }
            if ((HelperUtilsLocal.toLocalDateTime(currencyLimitList.get(i).getEffectiveFrom())
                        .isBefore(
                            HelperUtilsLocal.toLocalDateTime(
                                currencyLimitList.get(j).getEffectiveTo()))
                    || HelperUtilsLocal.toLocalDateTime(currencyLimitList.get(i).getEffectiveFrom())
                        .isEqual(
                            HelperUtilsLocal.toLocalDateTime(
                                currencyLimitList.get(j).getEffectiveTo())))
                && (HelperUtilsLocal.toLocalDateTime(currencyLimitList.get(j).getEffectiveFrom())
                        .isBefore(
                            HelperUtilsLocal.toLocalDateTime(
                                currencyLimitList.get(i).getEffectiveTo()))
                    || HelperUtilsLocal.toLocalDateTime(currencyLimitList.get(j).getEffectiveFrom())
                        .isEqual(
                            HelperUtilsLocal.toLocalDateTime(
                                currencyLimitList.get(i).getEffectiveTo())))) {
              ExceptionManager.throwEffectiveFromAndToShouldNotBeOverlapped();
            }
          }
        }
      }
    }
  }

  private void validateLimitInformation(CountryInfo request) {
    for (Currency currency : request.getCurrencyList()) {
      for (CurrencyOperationType currencyOperationType : currency.getCurrencyOperationTypeList()) {
        for (CurrencyLimit limit : currencyOperationType.getCurrencyLimitList()) {
          if (limit.getMaximumAmount() < 0 || limit.getMinimumAmount() < 0) {
            ExceptionManager.throwAmountLessThanZero();
          }
          if (limit.getMaximumAmount() < limit.getMinimumAmount()) {
            ExceptionManager.throwMaxAmountShouldBeGreaterThanMinAmount();
          }
          checkValidEffectiveFromAndTo(limit.getEffectiveFrom(), limit.getEffectiveTo());
        }
      }
    }
  }

  private void checkValidEffectiveFromAndTo(String effectiveFrom, String effectiveTo) {
    if (!HelperUtilsLocal.isValidDate(effectiveFrom)) {
      ExceptionManager.throwInvalidEffectiveFromDate();
    }
    if (!HelperUtilsLocal.isValidDate(effectiveTo)) {
      ExceptionManager.throwInvalidEffectiveToDate();
    }

    //    if (HelperUtilsLocal.toLocalDateTime(effectiveFrom).isBefore(HelperUtilsLocal.todayDate())
    //    ||
    //        HelperUtilsLocal.toLocalDateTime(effectiveTo).isBefore(HelperUtilsLocal.todayDate())){
    //      ExceptionManager.throwEffectiveFromEffectiveToCannotBePreviousThanToday();
    //    }

    if (HelperUtilsLocal.toLocalDateTime(effectiveFrom)
        .isAfter(HelperUtilsLocal.toLocalDateTime(effectiveTo))) {
      ExceptionManager.throwEffectiveToShouldBeAfterEffectiveFrom();
    }
  }

  private boolean invalidPaymentChannel(CountryInfo request) {
    MasterInfo info = new MasterInfo();
    info.setMasterTypeList(Collections.singletonList(MasterType.PAYMENT_CHANNEL.getCode()));
    List<MasterInfo> paymentChannels = this.masterModuleService.fetchMasterInfo(info);
    logger.info("PAYMENT CHANNELS FROM MASTER:: " + Jsons.toJsonObj(paymentChannels));
    List<String> inputPaymentChannel = new ArrayList<>();
    for (Currency currency : request.getCurrencyList()) {
      for (CurrencyOperationType currencyOperationType : currency.getCurrencyOperationTypeList()) {
        for (CurrencyLimit limit : currencyOperationType.getCurrencyLimitList()) {
          inputPaymentChannel.add(limit.getChannel());
        }
      }
    }

    List<String> validPaymentChannels =
        paymentChannels.stream().map(MasterInfo::getCode).collect(Collectors.toList());

    for (String in : inputPaymentChannel) {
      if (!validPaymentChannels.contains(in)) {
        return true;
      }
    }
    return false;
  }

  private boolean isLimitNotProvidedForAllOperationsTypes(CountryInfo request) {
    for (Currency currency : request.getCurrencyList()) {
      for (CurrencyOperationType operationType : currency.getCurrencyOperationTypeList()) {
        if (operationType.getCurrencyLimitList().size() == 0) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isOperationTypeNotPresentForCurrency(CountryInfo request) {
    return request.getCurrencyList().stream()
        .anyMatch(obj -> obj.getCurrencyOperationTypeList().size() == 0);
  }

  private long getDefaultCurrencyCount(CountryInfo request) {
    return request.getCurrencyList().stream().filter(Currency::getDefault).count();
  }

  private boolean isDefaultCurrencyProvided(CountryInfo request) {
    for (Currency c : request.getCurrencyList()) {
      if (c.getDefault()) {
        return true;
      }
    }
    return false;
  }

  private boolean validateCurrencyCodes(CountryInfo request) {
    List<String> inputCurrencyCodes =
        request.getCurrencyList().stream().map(Currency::getIsoAlpha).collect(Collectors.toList());
    CurrencyInfo currencyInfo = new CurrencyInfo();
    currencyInfo.setIsoAlphaList(inputCurrencyCodes);
    List<CurrencyInfo> currenciesFromService = this.currencyModule(currencyInfo);
    List<String> validCurrencyCodes =
        currenciesFromService.stream().map(CurrencyInfo::getIsoAlpha).collect(Collectors.toList());
    for (String input : inputCurrencyCodes) {
      if (!validCurrencyCodes.contains(input)) {
        return false;
      }
    }
    return true;
  }

  private List<CurrencyInfo> currencyModule(CurrencyInfo currencyInfo) {
    return this.currencyModuleService.fetchCurrencies(currencyInfo);
  }

  private void validateDecimalValueCount(CountryInfo request) {
    List<String> inputCurrencyCodes =
        request.getCurrencyList().stream().map(Currency::getIsoAlpha).collect(Collectors.toList());
    CurrencyInfo currencyInfo = new CurrencyInfo();
    currencyInfo.setIsoAlphaList(inputCurrencyCodes);
    List<CurrencyInfo> currenciesFromService = this.currencyModule(currencyInfo);
    logger.info("CURRENCY FROM SERVICE LIST :: " + Jsons.toJsonList(currenciesFromService));
    for (CurrencyInfo info : currenciesFromService) {
      currencyInfo.setDecimalValue(info.getDecimalValue());
      this.validateAmount(request, info);
    }
  }

  public void validateAmount(CountryInfo request, CurrencyInfo currencyInfo) {
    for (Currency currency : request.getCurrencyList()) {
      if (currency.getIsoAlpha().equals(currencyInfo.getIsoAlpha())) {
        for (CurrencyOperationType currencyOperationType :
            currency.getCurrencyOperationTypeList()) {
          for (CurrencyLimit limit : currencyOperationType.getCurrencyLimitList()) {
            if (countDecimalValue(limit.getMinimumAmount()) > currencyInfo.getDecimalValue()) {
              ExceptionManager.throwInvalidDecimalValueCountInMinAmount();
            }
            if (countDecimalValue(limit.getMaximumAmount()) > currencyInfo.getDecimalValue()) {
              ExceptionManager.throwInvalidDecimalValueCountInMaxAmount();
            }
          }
        }
      }
    }
  }

  public int countDecimalValue(Double amount) {
    String str = String.format("%f", amount);
    str = str.contains(".") ? str.replaceAll("0*$", "").replaceAll("\\.$", "") : str;
    str = str.contains(".") ? str : str.concat(".0");
    StringTokenizer stringTokenizer = new StringTokenizer(str, ".");
    stringTokenizer.nextToken();
    String decimalValue = stringTokenizer.nextToken();
    int decimalValueCount = decimalValue.length();
    if (decimalValueCount != 0 && decimalValue.charAt(decimalValue.length() - 1) == '0') {
      decimalValueCount = decimalValue.length() - 1;
    }
    return decimalValueCount;
  }

  @Override
  public void validateCorridorInformation(CountryInfo request) {
    if (request.getCorridorList().size() == 0) {
      ExceptionManager.throwCorridorShouldNotBeEmpty();
    }
    this.validateSendCurrencyForCorridor(request);

    this.validateCountyForCorridor(request);

    this.validateDestinationCountryForCorridor(request);

    this.validateDestinationCurrencyForCorridor(request);

    this.validatePayoutCurrencyForCorridor(request);

    this.validateOriginCountryForCorridor(request);

    this.validateOriginCurrencyForCorridor(request);

    this.validateDuplicateValues(request);
  }

  private List<String> getSendCurrencyList(CountryInfo request) {
    List<String> currencyListSend = new ArrayList<>();
    for (Currency currency : request.getCurrencyList()) {
      for (CurrencyOperationType currencyOperationType : currency.getCurrencyOperationTypeList()) {
        if (currencyOperationType
            .getCode()
            .equalsIgnoreCase(MasterConstants.OperationType.SEND.getCode())) {
          currencyListSend.add(currency.getIsoAlpha());
        }
      }
    }
    return currencyListSend;
  }

  private List<String> getPayoutCurrencyList(CountryInfo request) {
    List<String> currencyListPay = new ArrayList<>();
    for (Currency currency : request.getCurrencyList()) {
      for (CurrencyOperationType currencyOperationType : currency.getCurrencyOperationTypeList()) {
        if (currencyOperationType
            .getCode()
            .equalsIgnoreCase(MasterConstants.OperationType.PAYOUT.getCode())) {
          currencyListPay.add(currency.getIsoAlpha());
        }
      }
    }
    return currencyListPay;
  }

  private void validateCountyForCorridor(CountryInfo request) {
    for (Corridor corridor : request.getCorridorList()) {
      for (CorridorDetail corridorDetail : corridor.getCorridorDetailList()) {
        if (HelperUtils.isBlankOrNull(corridorDetail.getCountryIso3())) {
          ExceptionManager.throwCorridorCountryMissing();
        }
        if (request.getIso3().equalsIgnoreCase(corridorDetail.getCountryIso3())) {
          ExceptionManager.throwCountryCannotBeSameAsSetupCountry();
        }
      }
    }
  }

  private void validateSendCurrencyForCorridor(CountryInfo request) {
    List<String> currencyListSend = this.getSendCurrencyList(request);
    for (Corridor corridor : request.getCorridorList()) {
      if (corridor.getCode().equalsIgnoreCase(MasterConstants.OperationType.SEND.getCode())) {
        for (CorridorDetail corridorDetail : corridor.getCorridorDetailList()) {
          if (HelperUtils.isBlankOrNull(corridorDetail.getCurrencyIsoAlpha())) {
            ExceptionManager.throwCorridorSendCurrencyMissing();
          }
          if (!currencyListSend.contains(corridorDetail.getCurrencyIsoAlpha())) {
            ExceptionManager.throwInvalidSendCurrency();
          }
        }
      }
    }
  }

  private void validateDestinationCountryForCorridor(CountryInfo request) {
    for (Corridor corridor : request.getCorridorList()) {
      if (corridor.getCode().equalsIgnoreCase(MasterConstants.OperationType.SEND.getCode())) {
        for (CorridorDetail corridorDetail : corridor.getCorridorDetailList()) {
          CountryEntity entity = this.getCountryEntity(corridorDetail);
          List<OperationType> operationTypes =
              Jsons.fromJsonToList(entity.getOperationTypes(), OperationType[].class);
          List<String> operationTypeCodeList = new ArrayList<>();
          for (OperationType operationType : operationTypes) {
            operationTypeCodeList.add(operationType.getCode());
          }
          if (!operationTypeCodeList.contains(MasterConstants.OperationType.PAYOUT.getCode())) {
            ExceptionManager.throwInvalidDestinationCountry();
          }
        }
      }
    }
  }

  private void validateDestinationCurrencyForCorridor(CountryInfo request) {
    for (Corridor corridor : request.getCorridorList()) {
      if (corridor.getCode().equalsIgnoreCase(MasterConstants.OperationType.SEND.getCode())) {
        for (CorridorDetail corridorDetail : corridor.getCorridorDetailList()) {
          if (HelperUtils.isBlankOrNull(corridorDetail.getDestinationCurrencyIsoAlpha())) {
            ExceptionManager.throwCorridorDestinationCurrencyMissing();
          }
          List<String> currencyListPay =
              this.getCurrencyListForDestinationCurrency(this.getCountryEntity(corridorDetail));
          if (!currencyListPay.contains(corridorDetail.getDestinationCurrencyIsoAlpha())) {
            ExceptionManager.throwInvalidDestionationCurrency();
          }
        }
      }
    }
  }

  private List<String> getCurrencyListForDestinationCurrency(CountryEntity countryEntity) {
    List<String> currencyListPayout = new ArrayList<>();
    List<Currency> currencies =
        Jsons.fromJsonToList(countryEntity.getCurrencies(), Currency[].class);
    for (Currency currency : currencies) {
      for (CurrencyOperationType currencyOperationType : currency.getCurrencyOperationTypeList()) {
        if (currencyOperationType
            .getCode()
            .equalsIgnoreCase(MasterConstants.OperationType.PAYOUT.getCode())) {
          currencyListPayout.add(currency.getIsoAlpha());
        }
      }
    }
    return currencyListPayout;
  }

  private void validatePayoutCurrencyForCorridor(CountryInfo request) {
    List<String> currencyListPay = this.getPayoutCurrencyList(request);
    for (Corridor corridor : request.getCorridorList()) {
      if (corridor.getCode().equalsIgnoreCase(MasterConstants.OperationType.PAYOUT.getCode())) {
        for (CorridorDetail corridorDetail : corridor.getCorridorDetailList()) {
          if (HelperUtils.isBlankOrNull(corridorDetail.getCurrencyIsoAlpha())) {
            ExceptionManager.throwCorridorPayoutCurrencyMissing();
          }
          if (!currencyListPay.contains(corridorDetail.getCurrencyIsoAlpha())) {
            ExceptionManager.throwInvalidPayoutCurrency();
          }
        }
      }
    }
  }

  private void validateOriginCountryForCorridor(CountryInfo request) {
    for (Corridor corridor : request.getCorridorList()) {
      if (corridor.getCode().equalsIgnoreCase(MasterConstants.OperationType.PAYOUT.getCode())) {
        for (CorridorDetail corridorDetail : corridor.getCorridorDetailList()) {
          CountryEntity entity = this.getCountryEntity(corridorDetail);
          List<OperationType> operationTypes =
              Jsons.fromJsonToList(entity.getOperationTypes(), OperationType[].class);
          List<String> operationTypeCodeList = new ArrayList<>();
          for (OperationType operationType : operationTypes) {
            operationTypeCodeList.add(operationType.getCode());
          }
          if (!operationTypeCodeList.contains(MasterConstants.OperationType.SEND.getCode())) {
            ExceptionManager.throwInvalidOriginCountry();
          }
        }
      }
    }
  }

  private CountryEntity getCountryEntity(CorridorDetail corridorDetail) {
    CountryFilter countryFilter = new CountryFilter();
    countryFilter.setIso3(corridorDetail.getCountryIso3());
    Optional<CountryEntity> countryEntity = this.countryDao.find(countryFilter);
    if (countryEntity.isEmpty()) ExceptionManager.throwIso3NotFound();
    return countryEntity.get();
  }

  private void validateOriginCurrencyForCorridor(CountryInfo request) {
    for (Corridor corridor : request.getCorridorList()) {
      if (corridor.getCode().equalsIgnoreCase(MasterConstants.OperationType.PAYOUT.getCode())) {
        for (CorridorDetail corridorDetail : corridor.getCorridorDetailList()) {
          if (HelperUtils.isBlankOrNull(corridorDetail.getOriginCurrencyIsoAlpha())) {
            ExceptionManager.throwCorridorOriginCurrencyMissing();
          }
          List<String> currencyListSend =
              this.getCurrencyListForOriginCurrency(this.getCountryEntity(corridorDetail));
          if (!currencyListSend.contains(corridorDetail.getOriginCurrencyIsoAlpha())) {
            ExceptionManager.throwInvalidOriginCurrency();
          }
        }
      }
    }
  }

  private List<String> getCurrencyListForOriginCurrency(CountryEntity countryEntity) {
    List<String> currencyListSend = new ArrayList<>();
    List<Currency> currencies =
        Jsons.fromJsonToList(countryEntity.getCurrencies(), Currency[].class);
    for (Currency currency : currencies) {
      for (CurrencyOperationType currencyOperationType : currency.getCurrencyOperationTypeList()) {
        if (currencyOperationType
            .getCode()
            .equalsIgnoreCase(MasterConstants.OperationType.SEND.getCode())) {
          currencyListSend.add(currency.getIsoAlpha());
        }
      }
    }
    return currencyListSend;
  }

  private static void validateDuplicateValues(CountryInfo request) {
    for (Corridor corridor : request.getCorridorList()) {
      if (corridor.getCorridorDetailList() != null
          && corridor.getCorridorDetailList().size() >= 2) {
        if (isDuplicateCorridorDetailsFound(corridor.getCode(), corridor.getCorridorDetailList())) {
                    ExceptionManager.throwDuplicateCorridorNotAllowed();
        }
      }
    }
  }

  private static boolean isDuplicateCorridorDetailsFound(
      String code, List<CorridorDetail> corridorDetailList) {
    Map<String, Integer> keyCountMap = new HashMap<>();
    corridorDetailList.forEach(
        corridorDetail -> {
          String key = prepareKeyForDuplicateCorridorDetailCheck(code, corridorDetail);
          System.out.println(key);
          if (keyCountMap.containsKey(key)) {
            keyCountMap.put(key, keyCountMap.get(key) + 1);
          } else {
            keyCountMap.put(key, 1);
          }
        });

    return keyCountMap.values().stream().anyMatch(integer -> integer > 1);
  }

  private static String prepareKeyForDuplicateCorridorDetailCheck(
      String code, CorridorDetail corridorDetail) {
    String key = null;
    if (code.equalsIgnoreCase(MasterConstants.OperationType.SEND.getCode())) {
      key =
          Strings.nullToEmpty(corridorDetail.getCurrencyIsoAlpha()).trim()
              + "-"
              + Strings.nullToEmpty(corridorDetail.getCountryIso3()).trim()
              + "-"
              + Strings.nullToEmpty(corridorDetail.getDestinationCurrencyIsoAlpha()).trim();
    } else if (code.equalsIgnoreCase(MasterConstants.OperationType.PAYOUT.getCode())) {
      key =
          Strings.nullToEmpty(corridorDetail.getCurrencyIsoAlpha()).trim()
              + "-"
              + Strings.nullToEmpty(corridorDetail.getCountryIso3()).trim()
              + "-"
              + Strings.nullToEmpty(corridorDetail.getOriginCurrencyIsoAlpha()).trim();
    }
    return key.toLowerCase();
  }

  @Override
  public void validatePaymentMethods(CountryInfo request) {
    if (request.getPaymentMethodList() != null && request.getPaymentMethodList().size() > 0) {
      for (PaymentMethod paymentMethod : request.getPaymentMethodList()) {
        this.validatePaymentChannel(paymentMethod);
        this.validatePaymentTitle(paymentMethod);
        this.validatePaymentCode(paymentMethod);
      }
    }
  }

  private void validatePaymentChannel(PaymentMethod paymentMethod) {
    if (HelperUtils.isBlankOrNull(paymentMethod.getChannel())) {
      ExceptionManager.throwPaymentChannelMissing();
    }
    if (!(paymentMethod.getChannel().equalsIgnoreCase(PaymentMethodChannel.BANK.getCode())
        || paymentMethod
            .getChannel()
            .equalsIgnoreCase(PaymentMethodChannel.MOBILE_WALLET.getCode()))) {
      ExceptionManager.throwInvalidPaymentChannel();
    }
  }

  private void validatePaymentTitle(PaymentMethod paymentMethod) {
    if (HelperUtils.isBlankOrNull(paymentMethod.getTitle())) {
      ExceptionManager.throwPaymentTitleMissing();
    }
    if (paymentMethod.getTitle().length() > CityConstants.PAYMENT_METHOD_TITLE_LENGTH) {
      ExceptionManager.throwPaymentTitleLengthExceed();
    }
  }

  private void validatePaymentCode(PaymentMethod paymentMethod) {
    if (HelperUtils.isBlankOrNull(paymentMethod.getCode())) {
      ExceptionManager.throwPaymentCodeMissing();
    }
    if (paymentMethod.getCode().length() > CityConstants.PAYMENT_METHOD_CODE_LENGTH) {
      ExceptionManager.throwPaymentCodeLengthExceed();
    }
    if (!paymentMethod.getCode().matches(CityConstants.ALPHANUMERIC_ONLY_PATTERN)) {
      ExceptionManager.throwInvalidPaymentCode();
    }
  }

  @Override
  public void validateConfigs(CountryInfo request) {
    if (request.getConfigs() != null) {
      this.validateIdType(request);
      this.validatePurposeOfRemittance(request);
      this.validateSourceOfFund(request);
      this.validateRelationship(request);
      this.validateOccupation(request);
    }
  }

  private void validateIdType(CountryInfo request) {
    List<IdType> idTypeList = request.getConfigs().getIdTypeList();
    System.out.println(Jsons.toJsonList(idTypeList));
    if (idTypeList.isEmpty()) ExceptionManager.throwIdTypeNotFound();

    if (idTypeList != null && idTypeList.size() > 0) {
      List<MasterInfo> masterInfoList =
          this.getMasterByMasterType(MasterConstants.ConfigType.ID.getMasterType());
      for (IdType idType : idTypeList) {
        if (HelperUtils.isBlankOrNull(idType.getCode())) ExceptionManager.throwIdTypeCodeMissing();
          // validation disabled according to enhancement REM-1205
//        if(HelperUtils.isBlankOrNull(idType.getFormat().trim())){
//          ExceptionManager.throwIdTypeFormatMissing();
//        }
//        if (idType.getFormat().trim().length() > CityConstants.ID_TYPE_FORMAT_LENGTH)
//          ExceptionManager.throwInvalidIdTypeFormatLength();
        if (!this.isValidCode(idType.getCode(), masterInfoList))
          ExceptionManager.throwInvalidIdTypeCode();
      }
      if (this.idTypeCodeRepeated(request)) ExceptionManager.throwDuplicateIdTypeNotAllowed();
    }
  }

  private boolean idTypeCodeRepeated(CountryInfo request) {
    List<String> idTypeCode =
        request.getConfigs().getIdTypeList().stream()
            .map(IdType::getCode)
            .collect(Collectors.toList());
    Set<String> codeSet = new HashSet<>(idTypeCode);
    return idTypeCode.size() > codeSet.size();
  }

  private void validatePurposeOfRemittance(CountryInfo request) {
    List<PurposeOfRemittance> purposeOfRemittanceList =
        request.getConfigs().getPurposeOfRemittanceList();
    if (purposeOfRemittanceList.isEmpty()) ExceptionManager.throwPurposeOfRemittanceNotFound();

    if (purposeOfRemittanceList != null && purposeOfRemittanceList.size() > 0) {
      List<MasterInfo> masterInfoList =
          this.getMasterByMasterType(
              MasterConstants.ConfigType.PURPOSE_OF_REMITTANCE.getMasterType());
      for (PurposeOfRemittance purposeOfRemittance : purposeOfRemittanceList) {
        if (HelperUtils.isBlankOrNull(purposeOfRemittance.getCode()))
          ExceptionManager.throwPurposeOfRemittanceCodeMissing();

        if (!this.isValidCode(purposeOfRemittance.getCode(), masterInfoList))
          ExceptionManager.throwInvalidPurposeOfRemittanceCode();
        if (this.remittancePurposeCodeRepeated(request))
          ExceptionManager.throwDuplicatePurposeOfRemittanceNotAllowed();
      }
    }
  }

  private boolean remittancePurposeCodeRepeated(CountryInfo request) {
    List<String> remittanceCodeList =
        request.getConfigs().getPurposeOfRemittanceList().stream()
            .map(PurposeOfRemittance::getCode)
            .collect(Collectors.toList());
    Set<String> codeSet = new HashSet<>(remittanceCodeList);
    return remittanceCodeList.size() > codeSet.size();
  }

  private void validateSourceOfFund(CountryInfo request) {
    List<SourceOfFund> sourceOfFundList = request.getConfigs().getSourceOfFundList();
    if (sourceOfFundList.isEmpty()) ExceptionManager.throwSourceOfFundNotFound();

    if (sourceOfFundList != null && sourceOfFundList.size() > 0) {
      List<MasterInfo> masterInfoList =
          this.getMasterByMasterType(MasterConstants.ConfigType.SOURCE_OF_FUND.getMasterType());
      for (SourceOfFund sourceOfFund : sourceOfFundList) {
        if (HelperUtils.isBlankOrNull(sourceOfFund.getCode()))
          ExceptionManager.throwSourceOfFundCodeMissing();

        if (!this.isValidCode(sourceOfFund.getCode(), masterInfoList))
          ExceptionManager.throwInvalidSourceOfFundCode();

        if (this.fundSourceCodeRepeated(request))
          ExceptionManager.throwDuplicateSourceOfFundNotAllowed();
      }
    }
  }

  private boolean fundSourceCodeRepeated(CountryInfo request) {
    List<String> fundSourceCodeList =
        request.getConfigs().getSourceOfFundList().stream()
            .map(SourceOfFund::getCode)
            .collect(Collectors.toList());
    Set<String> codeSet = new HashSet<>(fundSourceCodeList);
    return fundSourceCodeList.size() > codeSet.size();
  }

  private void validateRelationship(CountryInfo request) {
    List<Relationship> relationshipList = request.getConfigs().getRelationshipList();
    if (relationshipList.isEmpty()) ExceptionManager.throwRelationshipNotFound();

    if (relationshipList != null && relationshipList.size() > 0) {
      List<MasterInfo> masterInfoList =
          this.getMasterByMasterType(MasterConstants.ConfigType.RELATIONSHIP.getMasterType());
      for (Relationship relationship : relationshipList) {
        if (HelperUtils.isBlankOrNull(relationship.getCode()))
          ExceptionManager.throwRelationshipCodeMissing();

        if (!this.isValidCode(relationship.getCode(), masterInfoList))
          ExceptionManager.throwInvalidRelationshipCode();

        if (this.relationshipCodeCodeRepeated(request))
          ExceptionManager.throwDuplicateRelationshipNotAllowed();
      }
    }
  }

  private boolean relationshipCodeCodeRepeated(CountryInfo request) {
    List<String> relationshipCodeList =
        request.getConfigs().getRelationshipList().stream()
            .map(Relationship::getCode)
            .collect(Collectors.toList());
    Set<String> codeSet = new HashSet<>(relationshipCodeList);
    return relationshipCodeList.size() > codeSet.size();
  }

  private void validateOccupation(CountryInfo request) {
    List<Occupation> occupationList = request.getConfigs().getOccupationList();
    if (occupationList.isEmpty()) ExceptionManager.throwOccupationNotFound();

    if (occupationList != null && occupationList.size() > 0) {
      List<MasterInfo> masterInfoList =
          this.getMasterByMasterType(MasterConstants.ConfigType.OCCUPATION.getMasterType());
      for (Occupation occupation : occupationList) {
        if (HelperUtils.isBlankOrNull(occupation.getCode()))
          ExceptionManager.throwOccupationCodeMissing();

        if (!this.isValidCode(occupation.getCode(), masterInfoList))
          ExceptionManager.throwInvalidOccupationCode();

        if (this.occupationCodeRepeated(request))
          ExceptionManager.throwDuplicateOccupationNotAllowed();
      }
    }
  }

  private boolean occupationCodeRepeated(CountryInfo request) {
    List<String> occupationCodeList =
        request.getConfigs().getOccupationList().stream()
            .map(Occupation::getCode)
            .collect(Collectors.toList());
    Set<String> codeSet = new HashSet<>(occupationCodeList);
    return occupationCodeList.size() > codeSet.size();
  }

  private List<MasterInfo> getMasterByMasterType(String masterType) {
    MasterInfo masterInfo = new MasterInfo();
    masterInfo.setMasterTypeList(Collections.singletonList(masterType));
    List<MasterInfo> masterInfoList = this.masterModuleService.fetchMasterInfo(masterInfo);
    return masterInfoList;
  }

  private boolean isValidCode(String code, List<MasterInfo> masterInfoList) {
    for (MasterInfo masterInfo : masterInfoList) {
      if (masterInfo.getCode().equalsIgnoreCase(code)) {
        return true;
      }
    }
    return false;
  }
}
