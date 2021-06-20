package global.citytech.remitpulse.countries.repositories.internal.service;

import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.country.CountryInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.currency.CurrencyModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.Continent;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterInfo;
import global.citytech.remitpulse.countries.repositories.domains.services.master.MasterModuleService;
import global.citytech.remitpulse.countries.repositories.domains.services.master.OperationType;
import global.citytech.remitpulse.countries.repositories.constants.CountryDataLengthConstants;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author bipin on 6/19/19 2:29 PM.
 */
class CountryValidatorServiceImplBasicInformationTest {
    private static CountryValidatorService countryValidatorService;

    @BeforeAll
    static void setup(){
        MasterModuleService masterModuleService= Mockito.mock(MasterModuleService.class);
        CurrencyModuleService currencyModuleService= Mockito.mock(CurrencyModuleService.class);
        CountryDao countryDao = Mockito.mock(CountryDao.class);

        when(masterModuleService.getContinents()).thenReturn(Arrays.asList(new Continent("ASIA","Asia")));
        MasterInfo info= new MasterInfo();
        info.setCode("TTYY");

        MasterInfo masterInfo1= new MasterInfo();
        masterInfo1.setMasterType("OT");
        masterInfo1.setCode("SND");

        MasterInfo masterInfo2= new MasterInfo();
        masterInfo2.setMasterType("OT");
        masterInfo2.setCode("PAT");
        MasterInfo m2= new MasterInfo();
        m2.setMasterTypeList(Arrays.asList("OT"));
        when(masterModuleService.fetchMasterInfo(m2)).thenReturn(Arrays.asList(masterInfo1,masterInfo2));

        MasterInfo info2 = new MasterInfo();
        info2.setCode("SND");
        MasterInfo m3= new MasterInfo();
        m3.setMasterTypeList(Arrays.asList("OT"));
        when(masterModuleService.fetchMasterInfo(m3)).thenReturn(Arrays.asList(info2));
        countryValidatorService= new CountryValidatorServiceImpl(masterModuleService,currencyModuleService, countryDao);
    }


    @Test
    @DisplayName("Should throw exception If country Name missing")
    void shouldThrowExceptionIfCountryNameMissing(){
        CountryInfo countryInfo= new CountryInfo();
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        Assertions.assertEquals(ExceptionManager.CountryError.COUNTRY_NAME_MISSING.getDescription(),exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception if name is invalid")
    void shouldThrowExceptionIfNameInvalid(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName("123 !");
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        Assertions.assertEquals(ExceptionManager.CountryError.COUNTRY_NAME_INVALID.getDescription(),exception.getMessage());

    }


    @Test
    @DisplayName("Should throw exception If country name length exceeds")
    void shouldThrowExceptionIfCountryNameLengthExceeds(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MIN_LENGTH.getValue()-1));
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.COUNTRY_NAME_LENGTH_LESS.getDescription(),exception.getMessage());

    }


    @Test
    @DisplayName("Should throw exception If country name length less")
    void shouldThrowExceptionIfCountryNameLengthLess(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()+1));
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.COUNTRY_NAME_LENGTH_EXCEEDED.getDescription(),exception.getMessage());

    }


    @Test
    @DisplayName("Should throw exception if iso 2 missing")
    void shouldThrowExceptionIfIso2Missing(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.ISO_2_MISSING.getDescription(),exception.getMessage());


    }

    @Test
    @DisplayName("Should throw exception If iso 2 length invalid")
    void shouldThrowExceptionIfIso2LengthInvalid(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()+1));
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.ISO_2_LENGTH_INVALID.getDescription(),exception.getMessage());


    }

    @Test
    @DisplayName("Should throw exception if iso2 invalid")
    void shouldThrowExceptionIfIso2Invalid(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2("1S");
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.ISO2_INVALID.getDescription(),exception.getMessage());
    }



    @Test
    @DisplayName("Should throw exception If iso 3 missing")
    void shouldThrowExceptionIfIso3Missing(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()));
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.ISO_3_MISSING.getDescription(),exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception If iso 3 length invalid")
    void shouldThrowExceptionIfIso3LengthInvalid(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()));
        countryInfo.setIso3(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_3_VALID_LENGTH.getValue()+1));
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.ISO_3_LENGTH_INVALID.getDescription(),exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception if iso3 invalid")
    void shouldThrowExceptionIfIso3Invalid(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2("AS");
        countryInfo.setIso3("123");
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.ISO3_INVALID.getDescription(),exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception if numeric code is empty")
    void shouldThrowExceptionIfNumericCodeIsEmpty(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()));
        countryInfo.setIso3(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_3_VALID_LENGTH.getValue()));
        AppException exception= assertThrows(AppException.class,()->countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.NUMERIC_CODE_MISSING.getDescription(),exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception if numeric code invalid")
    void shouldThrowExceptionIfNumericCodeInvalid(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()));
        countryInfo.setIso3(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_3_VALID_LENGTH.getValue()));
        countryInfo.setNumericCode("12 3");
        AppException exception= assertThrows(AppException.class,()->countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.NUMERIC_CODE_INVALID.getDescription(),exception.getMessage());
    }

    //@Test
    @DisplayName("Should throw exception If operation types code is invalid")
    void shouldThrowExceptionIfOperationTypeCodeIsInvalid(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()));
        countryInfo.setIso3(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_3_VALID_LENGTH.getValue()));
        countryInfo.setNumericCode("123");
        OperationType ot= new OperationType();
        ot.setCode("TTYY");
        countryInfo.setOperationTypeList(Arrays.asList(ot));
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.INVALID_OPERATION_TYPE.getDescription(),exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception if invalid effective from date")
    void shouldThrowExceptionIfInvalidEffectiveFromDate(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()));
        countryInfo.setIso3(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_3_VALID_LENGTH.getValue()));
        countryInfo.setNumericCode("123");
        OperationType ot= new OperationType();
        ot.setCode("SND");
        ot.setEffectiveFrom("asdf");
        countryInfo.setOperationTypeList(Arrays.asList(ot));
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.INVALID_EFFECTIVE_FROM_DATE.getDescription(),exception.getMessage());

    }

    @Test
    @DisplayName("Should throw exception if invalid effective to date")
    void shouldThrowExceptionIfInvalidEffectiveToDate(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()));
        countryInfo.setIso3(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_3_VALID_LENGTH.getValue()));
        countryInfo.setNumericCode("123");
        OperationType ot= new OperationType();
        ot.setCode("SND");
        ot.setEffectiveFrom("2012-12-12");
        ot.setEffectiveTo("asdfasdf");
        countryInfo.setOperationTypeList(Arrays.asList(ot));
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.INVALID_EFFECTIVE_TO_DATE.getDescription(),exception.getMessage());

    }

  //@Test
  @DisplayName("Should throw exception if effective from previous than today")
  void shouldThrowExceptionIfEffectiveFromPreviousToday(){
    CountryInfo countryInfo= new CountryInfo();
    countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
    countryInfo.setIso2(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()));
    countryInfo.setIso3(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_3_VALID_LENGTH.getValue()));
    countryInfo.setNumericCode("123");
    OperationType ot= new OperationType();
    ot.setCode("SND");
    ot.setEffectiveFrom("2012-12-12");
    ot.setEffectiveTo("2014-12-12");
    countryInfo.setOperationTypeList(Arrays.asList(ot));
    AppException exception= assertThrows(AppException.class,()->
        countryValidatorService.validateBasicInformation(countryInfo));
    assertEquals(ExceptionManager.CountryError.EFFECTIVE_FROM_EFFECTIVE_TO_CANNOT_BE_PREVIOUS_THAN_TODAY.getDescription(),exception.getMessage());

  }

  @Test
    @DisplayName("Should throw exception if effective to before effective from date")
    void shouldThrowExceptionIfInvalidEffectiveToBeforeThanEffectiveFromDate(){
        CountryInfo countryInfo= new CountryInfo();
        countryInfo.setName(HelperUtils.randomAlphabets(CountryDataLengthConstants.COUNTRY_NAME_MAX_LENGTH.getValue()));
        countryInfo.setIso2(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_2_VALID_LENGTH.getValue()));
        countryInfo.setIso3(HelperUtils.randomAlphabets(CountryDataLengthConstants.ISO_3_VALID_LENGTH.getValue()));
        countryInfo.setNumericCode("123");
        OperationType ot= new OperationType();
        ot.setCode("SND");
        ot.setEffectiveFrom("2032-12-12");
        ot.setEffectiveTo("2031-12-11");
        countryInfo.setOperationTypeList(Arrays.asList(ot));
        AppException exception= assertThrows(AppException.class,()->
                countryValidatorService.validateBasicInformation(countryInfo));
        assertEquals(ExceptionManager.CountryError.EFFECTIVE_TO_SHOULD_BE_AFTER_EFFECTIVE_FROM.getDescription(),exception.getMessage());

    }
}