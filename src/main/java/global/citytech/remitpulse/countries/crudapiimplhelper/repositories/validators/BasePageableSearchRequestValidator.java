package global.citytech.remitpulse.countries.crudapiimplhelper.repositories.validators;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.commons.Validator;
import global.citytech.remitpulse.countries.commons.domains.searchrequest.PageableSearchRequest;
import global.citytech.remitpulse.countries.crudapi.impl.exceptions.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.domains.SortParameter;

/** @author sunil */
public interface BasePageableSearchRequestValidator<T extends PageableSearchRequest>
        extends Validator<T> {

  boolean isInvalidSortBy(String sortBy);

  @Override
  default void validate(T pageableSearchRequest) {

    if (!Strings.isNullOrEmpty(pageableSearchRequest.getSortParameter())
        && !(pageableSearchRequest.getSortParameter().equalsIgnoreCase(SortParameter.ASC.getCode())
            || pageableSearchRequest
                .getSortParameter()
                .equalsIgnoreCase(SortParameter.DESC.getCode()))) {

      ExceptionManager.throwInvalidSortParameter();
    }

    if (!Strings.isNullOrEmpty(pageableSearchRequest.getSortBy())
        && isInvalidSortBy(pageableSearchRequest.getSortBy())) {

      ExceptionManager.throwInvalidSortByParameter();
    }
  }
}
