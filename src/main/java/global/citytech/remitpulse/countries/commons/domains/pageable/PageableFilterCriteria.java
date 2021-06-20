package global.citytech.remitpulse.countries.commons.domains.pageable;


import global.citytech.remitpulse.countries.commons.domains.sortable.SortableFilterCriteria;

/** @author sunil */
public interface PageableFilterCriteria extends SortableFilterCriteria, Pageable {
  Integer getStartingIndex();

  void setStartingIndex(Integer startingIndex);
}
