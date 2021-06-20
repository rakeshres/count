package global.citytech.remitpulse.countries.crudapi.impl.helper;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.commons.*;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.commons.domains.AuditableEntityConverter;
import global.citytech.remitpulse.countries.crudapi.impl.exceptions.ExceptionManager;

import java.util.Optional;
import java.util.logging.Logger;

/** @author sunil */
public interface BaseViewDetailsRepositoryHelper<E extends AuditableEntity, T extends ServiceObject>
    extends RepositoryHelper<T, T> {

  Logger LOGGER = Logger.getLogger(BaseViewDetailsRepositoryHelper.class.getName());

  AbstractRepository<E, StringIdentifier> getEntityDao();

  AuditableEntityConverter<E, T> getConverter();

  String getPrimaryKey(T serviceObject);

  FilterCriteria prepareFilterCriteriaWithPrimaryKey(T serviceObject);

  @Override
  default T execute(T serviceObject) {

    String primaryKey = getPrimaryKey(serviceObject);

    if (Strings.isNullOrEmpty(primaryKey)) {
      ExceptionManager.throwPrimaryKeyCodeOrIdIsBlank();
    }

    FilterCriteria filterCriteria = this.prepareFilterCriteriaWithPrimaryKey(serviceObject);
    LOGGER.info("Getting entity with :" + Jsons.toJsonObj(filterCriteria));

    Optional<E> entityFromDb = getEntityDao().find(filterCriteria);

    if (entityFromDb.isEmpty()) {
      ExceptionManager.throwEntityNotFound();
    }
    return this.getConverter().toServiceObject(entityFromDb.get());
  }
}
