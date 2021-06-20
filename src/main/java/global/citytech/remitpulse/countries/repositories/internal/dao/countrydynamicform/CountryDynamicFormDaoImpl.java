package global.citytech.remitpulse.countries.repositories.internal.dao.countrydynamicform;

import global.citytech.rabbit.core.commons.FilterCriteria;
import global.citytech.rabbit.core.commons.KeyValuePair;
import global.citytech.rabbit.core.commons.StringIdentifier;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.repositories.internal.dao.RepositoryConstants;
import org.apache.ibatis.session.SqlSession;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/** @author bipin on 2020-03-04 16:43 */
@Named
@Dependent
public class CountryDynamicFormDaoImpl implements CountryDynamicFormDao {
  private static final Logger LOGGER = Logger.getLogger(CountryDynamicFormDaoImpl.class.getName());
  private SqlSession sqlSession;

  @Inject
  public CountryDynamicFormDaoImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public long getCount(FilterCriteria filterCriteria) {
    return 0;
  }

  @Override
  public Optional<CountryDynamicFormEntity> insert(
      CountryDynamicFormEntity countryDynamicFormEntity) {
    LOGGER.info("INSERT IN DB:: " + Jsons.toJsonObj(countryDynamicFormEntity));

    return this.sqlSession.insert(
                RepositoryConstants.getFullMapperMethodId(CountryDynamicFormDao.class, "add"),
                countryDynamicFormEntity)
            > 0
        ? Optional.of(countryDynamicFormEntity)
        : Optional.empty();
  }

  @Override
  public List<CountryDynamicFormEntity> findAll(FilterCriteria filterCriteria) {
    LOGGER.info("FIND ALL DAO IMPL :: " + Jsons.toJsonObj(filterCriteria));
    return this.sqlSession.selectList(
        RepositoryConstants.getFullMapperMethodId(CountryDynamicFormDao.class, "findAll"),
        filterCriteria);
  }

  @Override
  public Optional<CountryDynamicFormEntity> update(
      CountryDynamicFormEntity countryDynamicFormEntity) {
    LOGGER.info("EXECUTING QUERY FOR UPDATING COUNTRY DYNAMIC FORM" + Jsons.toJsonObj(countryDynamicFormEntity));
    return this.sqlSession.update(
        RepositoryConstants.getFullMapperMethodId(CountryDynamicFormDao.class, "update"),
        countryDynamicFormEntity)
        > 0
        ? Optional.of(countryDynamicFormEntity)
        : Optional.empty();
  }

  @Override
  public Optional<CountryDynamicFormEntity> find(FilterCriteria filterCriteria) {
    LOGGER.info("FIND ONE DAO IMPL :: " + Jsons.toJsonObj(filterCriteria));
    return Optional.ofNullable(
        this.sqlSession.selectOne(
            RepositoryConstants.getFullMapperMethodId(CountryDynamicFormDao.class, "findOne"),
            filterCriteria));
  }

  @Override
  public void delete(CountryDynamicFormEntity countryDynamicFormEntity) {}

  @Override
  public long getTotalRecord(FilterCriteria filterCriteria) {
    return this.sqlSession.selectOne(
        RepositoryConstants.getFullMapperMethodId(CountryDynamicFormDao.class, "countTotalRecord"),
        filterCriteria);
  }

  @Override
  public Optional<CountryDynamicFormEntity> findByIdentifier(StringIdentifier stringIdentifier) {
    return Optional.empty();
  }

  @Override
  public List<KeyValuePair> findAllKeyName(FilterCriteria filterCriteria) {
    return null;
  }
}
