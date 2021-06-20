package global.citytech.remitpulse.countries.commons.domains;

import global.citytech.rabbit.core.commons.AuditableEntity;
import global.citytech.rabbit.core.commons.EntityConverter;
import global.citytech.rabbit.core.commons.ServiceObject;

/** @author sunil */
public interface AuditableEntityConverter<E extends AuditableEntity, T extends ServiceObject>
    extends EntityConverter<E, T> {}
