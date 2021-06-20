package global.citytech.remitpulse.countries.repositories.internal.dao.audit;

import com.google.common.flogger.FluentLogger;
import global.citytech.rabbit.core.commons.KeyValuePair;
import global.citytech.remitpulse.countries.repositories.internal.dao.RepositoryConstants;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogIdentifier;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.commons.FilterCriteria;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditReportFilterCriteria;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/** @author bipin on 6/5/19 2:14 PM. */
public class AuditDaoImpl implements AuditDao {
  private static Logger logger = Logger.getLogger(AuditDaoImpl.class.getName());
  private SqlSession sqlSession;

  @Inject
  public AuditDaoImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public long countByDynamicFilter(AuditReportFilterCriteria auditReportFilterCriteria) {
    return this.sqlSession.selectOne(
        RepositoryConstants.getFullMapperMethodId(AuditDao.class, "countByDynamicFilter"),
        auditReportFilterCriteria);
  }

  @Override
  public List<AuditLogEntity> findWithDynamicFilter(AuditReportFilterCriteria auditReportFilterCriteria) {
    FluentLogger.forEnclosingClass().atInfo().log("SEARCH " + Jsons.toJsonObj(auditReportFilterCriteria));
    return this.sqlSession.selectList(
        RepositoryConstants.getFullMapperMethodId(AuditDao.class, "findByDynamicQuery"),
        auditReportFilterCriteria);
  }

  @Override
  public Optional<AuditLogEntity> insert(AuditLogEntity auditEntity) {
    logger.info("INSERTING AUDIT LOG ENTITY ::"+auditEntity.getEntity()+" :: "+
        Jsons.toJsonObj(auditEntity));
    return this.sqlSession.insert(
                RepositoryConstants.getFullMapperMethodId(AuditDao.class, "insert"), auditEntity)
            > 0
        ? Optional.of(auditEntity)
        : Optional.empty();
  }

  @Override
  public List<AuditLogEntity> findAll(FilterCriteria filterCriteria) {
    logger.info("THE FITLER TO FIND ALL AUDIT ENTITY IS ::: " + Jsons.toJsonObj(filterCriteria));
    return this.sqlSession.selectList(
        RepositoryConstants.getFullMapperMethodId(AuditDao.class, "findAll"), filterCriteria);
  }

  @Override
  public Optional<AuditLogEntity> update(AuditLogEntity auditEntity) {
    return this.sqlSession.update(
                RepositoryConstants.getFullMapperMethodId(AuditDao.class, "update"), auditEntity)
            > 0
        ? Optional.of(auditEntity)
        : Optional.empty();
  }

  @Override
  public Optional<AuditLogEntity> find(FilterCriteria filterCriteria) {
    return Optional.ofNullable(
        this.sqlSession.selectOne(
            RepositoryConstants.getFullMapperMethodId(AuditDao.class, "findOne"), filterCriteria));
  }

  @Override
  public void delete(AuditLogEntity auditLogEntity) {}

  @Override
  public long getTotalRecord(FilterCriteria filterCriteria) {
    return this.sqlSession.selectOne(
        RepositoryConstants.getFullMapperMethodId(AuditDao.class, "countTotalRecord"),
        filterCriteria);
  }

  @Override
  public Optional<AuditLogEntity> findByIdentifier(AuditLogIdentifier auditLogIdentifier) {
    return Optional.empty();
  }

  @Override
  public List<KeyValuePair> findAllKeyName(FilterCriteria filterCriteria) {
    return null;
  }
}
