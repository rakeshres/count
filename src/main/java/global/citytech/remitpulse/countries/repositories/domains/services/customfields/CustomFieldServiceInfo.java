package global.citytech.remitpulse.countries.repositories.domains.services.customfields;

import global.citytech.rabbit.core.commons.ServiceObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bipin on 2020-03-06 16:10
 */
public class CustomFieldServiceInfo implements ServiceObject {
  private List<SingleFieldServiceInfo> fields;

  public CustomFieldServiceInfo() {
    this.fields= new ArrayList<>();
  }

  public List<SingleFieldServiceInfo> getFields() {
    return fields;
  }

  public void setFields(List<SingleFieldServiceInfo> fields) {
    this.fields = fields;
  }
}
