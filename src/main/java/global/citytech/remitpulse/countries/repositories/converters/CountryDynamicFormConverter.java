package global.citytech.remitpulse.countries.repositories.converters;

import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.repositories.constants.FieldConstant;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.CountryDynamicFormInfo;
import global.citytech.remitpulse.countries.repositories.domains.countrydynamicform.FieldInfo;
import global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform.CountryDynamicFormEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** @author bipin on 2020-03-04 15:30 */
public class CountryDynamicFormConverter
    implements AuditableEntityConverter<CountryDynamicFormEntity, CountryDynamicFormInfo> {

  @Override
  public CountryDynamicFormEntity toEntity(CountryDynamicFormInfo info) {
    CountryDynamicFormEntity ent = new CountryDynamicFormEntity();
    ent.setId(info.getId());
    ent.setName(info.getName());
    ent.setCountryIso3(info.getCountryIso3());
    ent.setType(info.getType());
    ent.setEffectiveFrom(info.getEffectiveFrom());
    ent.setEffectiveTo(info.getEffectiveTo());
    ent.setFieldInfoList(Jsons.toJsonObj(info.getFieldInfoList()));
    return ent;
  }

  @Override
  public CountryDynamicFormInfo toServiceObject(CountryDynamicFormEntity countryDynamicFormEntity) {
    CountryDynamicFormInfo info = new CountryDynamicFormInfo();
    info.setId(countryDynamicFormEntity.getId());
    info.setName(countryDynamicFormEntity.getName());
    info.setCountryIso3(countryDynamicFormEntity.getCountryIso3());
    info.setEffectiveFrom(countryDynamicFormEntity.getEffectiveFrom());
    info.setEffectiveTo(countryDynamicFormEntity.getEffectiveTo());
    info.setType(countryDynamicFormEntity.getType());
    info.setFieldInfoList(
        Jsons.fromJsonToList(countryDynamicFormEntity.getFieldInfoList(), FieldInfo[].class));
    return info;
  }
}
