package global.citytech.remitpulse.countries.repositories.internal.dao.country;

import global.citytech.rabbit.core.commons.KeyValuePair;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.repositories.internal.dao.RepositoryConstants;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.config.ConfigEntity;
import global.citytech.remitpulse.countries.repositories.internal.filters.CountryIdentifier;
import global.citytech.rabbit.core.commons.FilterCriteria;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/** @author bipin on 6/20/19 12:12 PM. */
public class CountryDaoImpl implements CountryDao {
  private SqlSession sqlSession;

  @Inject
  public CountryDaoImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public Optional<CountryEntity> insert(CountryEntity countryEntity) {
    return this.sqlSession.insert(
                RepositoryConstants.getFullMapperMethodId(CountryDao.class, "add"), countryEntity)
            > 0
        ? Optional.of(countryEntity)
        : Optional.empty();
  }

  @Override
  public List<CountryEntity> findAll(FilterCriteria filterCriteria) {
    return this.sqlSession.selectList(
        RepositoryConstants.getFullMapperMethodId(CountryDao.class, "findAll"), filterCriteria);
  }

  @Override
  public Optional<CountryEntity> update(CountryEntity countryEntity) {
    return this.sqlSession.update(
                RepositoryConstants.getFullMapperMethodId(CountryDao.class, "update"),
                countryEntity)
            > 0
        ? Optional.of(countryEntity)
        : Optional.empty();
  }

  @Override
  public Optional<CountryEntity> find(FilterCriteria filterCriteria) {
    Optional<CountryView> countryView =
        Optional.ofNullable(
            this.sqlSession.selectOne(
                RepositoryConstants.getFullMapperMethodId(CountryDao.class, "findOne"),
                filterCriteria));
    if (countryView.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(this.toCountryEntity(countryView.get()));
  }

  private CountryEntity toCountryEntity(CountryView countryView) {
    CountryEntity countryEntity = new CountryEntity();
    countryEntity.setId(countryView.getId());
    countryEntity.setActive(countryView.isActive());
    countryEntity.setName(countryView.getName());
    countryEntity.setNumericCode(countryView.getNumericCode());
    countryEntity.setIso2(countryView.getIso2());
    countryEntity.setIso3(countryView.getIso3());
    countryEntity.setCurrencies(countryView.getCurrencies());
    countryEntity.setOperationTypes(countryView.getOperationTypes());
    countryEntity.setCorridors(countryView.getCorridors());
    countryEntity.setPaymentMethods(countryView.getPaymentMethods());
    countryEntity.setConfigs(this.prepareConfigs(countryView));
    return countryEntity;
  }

  private String prepareConfigs(CountryView countryView) {
    ConfigEntity configEntity = new ConfigEntity();
    configEntity.setCountryIso3Code(countryView.getCountryIso3Code());
    configEntity.setIdTypes(countryView.getIdTypes());
    configEntity.setPurposeOfRemittances(countryView.getPurposeOfRemittances());
    configEntity.setSourceOfFunds(countryView.getSourceOfFunds());
    configEntity.setRelationships(countryView.getRelationships());
    configEntity.setOccupations(countryView.getOccupations());
    return Jsons.toJsonObj(configEntity);
  }

  @Override
  public void delete(CountryEntity countryEntity) {}

  @Override
  public long getTotalRecord(FilterCriteria filterCriteria) {
    return 0;
  }

  @Override
  public Optional<CountryEntity> findByIdentifier(CountryIdentifier countryIdentifier) {
    return Optional.empty();
  }

  @Override
  public List<KeyValuePair> findAllKeyName(FilterCriteria filterCriteria) {
    return null;
  }

  @Override
  public Long getCount(FilterCriteria fc) {
    return this.sqlSession.selectOne(
        RepositoryConstants.getFullMapperMethodId(CountryDao.class, "countTotalRecord"), fc);
  }
}
