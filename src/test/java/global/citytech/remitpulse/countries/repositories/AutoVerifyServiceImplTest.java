package global.citytech.remitpulse.countries.repositories;

import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryDao;
import global.citytech.remitpulse.countries.repositories.internal.dao.country.CountryEntity;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.modulesconfig.ModulesConfigService;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author sankalpa on 1/20/20
 */
public class AutoVerifyServiceImplTest {
    private EntityApproveService entityApproveService;
    private ModulesConfigService modulesConfigService;
    private AutoVerifyServiceImpl autoVerifyService;
    private AuditDao auditLogDao;
    private RabbitRequestContext rabbitRequestContext;
    private CountryDao countryDao;

    @BeforeEach
    void setup(){
        entityApproveService = Mockito.mock(EntityApproveService.class);
        modulesConfigService = Mockito.mock(ModulesConfigService.class);
        auditLogDao = Mockito.mock(AuditDao.class);
        rabbitRequestContext = Mockito.mock(RabbitRequestContext.class);
        countryDao = Mockito.mock(CountryDao.class);
        autoVerifyService = new AutoVerifyServiceImpl(entityApproveService,modulesConfigService);
    }

    @Test
    @DisplayName("should throw exception if invalid entity name")
    void shouldThrowExceptionIfInvalidEntityName(){
        AuditLogEntity auditLogEntity = new AuditLogEntity();
        auditLogEntity.setEntity("INVALID_ENTITY");
        AppException exception = assertThrows(AppException.class,()->autoVerifyService.verifyAndApprove(auditLogEntity,auditLogDao,new CountryEntity(),countryDao,rabbitRequestContext));
        assertEquals(ExceptionManager.AuditLogError.INVALID_AUDIT_LOG_ENTITY.getDescription(),exception.getMessage());
    }

    @Test
    @DisplayName("should work well")
    void shouldWorkWell(){
        AuditLogEntity auditLogEntity = new AuditLogEntity();
        auditLogEntity.setEntity(EntityName.COUNTRY.name());
        assertNotNull(autoVerifyService.verifyAndApprove(auditLogEntity,auditLogDao,new CountryEntity(),countryDao,rabbitRequestContext));
    }
}
