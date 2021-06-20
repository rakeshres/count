package global.citytech.remitpulse.countries.repositories.impl.helpers;

import global.citytech.remitpulse.countries.crudapi.impl.helper.BasePageableSearchRepositoryHelper;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;
import global.citytech.remitpulse.countries.repositories.serviceobjects.CountryDynamicFormSearchRequest;

/** @author bipin on 2020-03-05 15:11 */
public interface CountryDynamicFormPageableSearchRepositoryHelper
    extends BasePageableSearchRepositoryHelper<
        CountryDynamicFormEntity, CountryDynamicFormInfo, CountryDynamicFormSearchRequest> {}
