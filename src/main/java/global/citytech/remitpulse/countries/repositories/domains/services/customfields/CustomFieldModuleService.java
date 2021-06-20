package global.citytech.remitpulse.countries.repositories.domains.services.customfields;

import java.util.List;

/**
 * @author bipin on 2020-03-06 16:19
 */
public interface CustomFieldModuleService {
  List<CustomFieldServiceInfo> getCustomFieldList(CustomFieldServiceInfo inputList);
}
