package global.citytech.remitpulse.countries.repositories.internal.dao.country;

import global.citytech.rabbit.core.commons.FilterCriteria;
import global.citytech.remitpulse.countries.repositories.internal.dao.RepositoryConstants;
import org.apache.ibatis.session.SqlSession;

import javax.inject.Inject;
import java.util.List;

/**
 * @author sankalpa on 4/12/21
 */
public class LocationDaoImpl implements LocationDao {

    private SqlSession sqlSession;

    @Inject
    public LocationDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<LocationEntity> getLocations(FilterCriteria filterCriteria) {
        return this.sqlSession.selectList(
                RepositoryConstants.getFullMapperMethodId(LocationDao.class, "findAll"), filterCriteria);
    }
}
