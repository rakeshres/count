package global.citytech.remitpulse.countries.repositories.impl.helpers;

import global.citytech.remitpulse.countries.crudapi.impl.helper.BaseSearchAllRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;
import global.citytech.remitpulse.countries.repositories.serviceobjects.CountryDynamicFormSearchRequest;

/**
 * @author bipin on 2020-03-12 14:16
 */
public interface CountryDynamicFormSearchAllRepositoryHelper extends
    BaseSearchAllRepositoryHelper<CountryDynamicFormEntity, CountryDynamicFormInfo, CountryDynamicFormSearchRequest> {
}
