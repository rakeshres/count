package global.citytech.remitpulse.countries.repositories;

import global.citytech.remitpulse.countries.repositories.domains.EntityName;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.modulesconfig.ModulesConfigService;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.Entity;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;

import javax.inject.Inject;

/**
 * @author bipin on 7/11/19 1:43 PM.
 */
public class AutoVerifyServiceImpl implements AutoVerifyService {
    private EntityApproveService entityApproveService;
    private ModulesConfigService modulesConfigService;

    @Inject
    public AutoVerifyServiceImpl(EntityApproveService entityApproveService,
                                 ModulesConfigService modulesConfigService) {
        this.entityApproveService = entityApproveService;
        this.modulesConfigService = modulesConfigService;
    }

    @Override
    public boolean verifyAndApprove(AuditLogEntity auditLogEntity, AuditDao auditDao, Entity entity, AbstractRepository dao, RabbitRequestContext rabbitRequestContext) {
        boolean autoApproveEnabled= isAutoVerifyEnabled(auditLogEntity.getEntity());
        if (autoApproveEnabled){
            this.entityApproveService.approve(auditLogEntity,auditDao,entity,dao,rabbitRequestContext,autoApproveEnabled);
        }
        return autoApproveEnabled;
    }

    private boolean isAutoVerifyEnabled(String entityName){
        if(EntityName.COUNTRY.name().equalsIgnoreCase(entityName)){
            return modulesConfigService.isAutoVerifyEnabled(EntityName.COUNTRY.getModuleIdentifier());
        }else{
            ExceptionManager.throwInvalidAuditLogEntity();
            return false;
        }
    }
}
