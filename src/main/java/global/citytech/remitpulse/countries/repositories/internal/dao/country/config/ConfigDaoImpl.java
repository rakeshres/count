package global.citytech.remitpulse.countries.repositories.internal.dao.country.config;

import global.citytech.rabbit.core.commons.FilterCriteria;
import global.citytech.rabbit.core.commons.KeyValuePair;
import global.citytech.remitpulse.countries.repositories.internal.dao.RepositoryConstants;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryIdentifier;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/** @author roslina */
public class ConfigDaoImpl implements ConfigDao {
  private SqlSession sqlSession;

  @Inject
  public ConfigDaoImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public Optional<ConfigEntity> insert(ConfigEntity configEntity) {
    return this.sqlSession.insert(
                RepositoryConstants.getFullMapperMethodId(ConfigDao.class, "add"), configEntity)
            > 0
        ? Optional.of(configEntity)
        : Optional.empty();
  }

  @Override
  public List<ConfigEntity> findAll(FilterCriteria filterCriteria) {
    return null;
  }

  @Override
  public Optional<ConfigEntity> update(ConfigEntity configEntity) {
    return this.sqlSession.update(
                RepositoryConstants.getFullMapperMethodId(ConfigDao.class, "update"), configEntity)
            > 0
        ? Optional.of(configEntity)
        : Optional.empty();
  }

  @Override
  public Optional<ConfigEntity> find(FilterCriteria filterCriteria) {
    return Optional.empty();
  }

  @Override
  public void delete(ConfigEntity configEntity) {}

  @Override
  public long getTotalRecord(FilterCriteria filterCriteria) {
    return 0;
  }

  @Override
  public Optional<ConfigEntity> findByIdentifier(CountryIdentifier countryIdentifier) {
    return Optional.empty();
  }

  @Override
  public List<KeyValuePair> findAllKeyName(FilterCriteria filterCriteria) {
    return null;
  }
}
