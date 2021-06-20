package global.citytech.remitpulse.countries.repositories;

import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.rabbit.core.audits.AuditAction;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditStatus;
import global.citytech.rabbit.core.commons.AbstractRepository;
import global.citytech.rabbit.core.commons.Entity;
import global.citytech.rabbit.core.utils.HelperUtils;
import global.citytech.rabbit.core.utils.Jsons;
import global.citytech.rabbit.microprofile.security.context.RabbitRequestContext;

import java.util.logging.Logger;

/** @author bipin on 7/11/19 1:46 PM. */
public class EntityApproveServiceImpl implements EntityApproveService {
  private static Logger logger = Logger.getLogger(EntityApproveServiceImpl.class.getName());

  public EntityApproveServiceImpl() {
  }

  @Override
  public Entity approve(AuditLogEntity auditLogEntity, AuditDao auditDao, Entity entity, AbstractRepository dao, RabbitRequestContext rabbitRequestContext,boolean autoApproveEnabled) {
    logger.info("APPROVE REQUEST :: " + Jsons.toJsonObj(entity));
    if (auditLogEntity.getAction().equals(AuditAction.ADD)){
      dao.insert(entity);
    }
    if (auditLogEntity.getAction().equals(AuditAction.UPDATE)){
      dao.update(entity);
    }
    this.updateAuditLogEntity(auditLogEntity,auditDao,rabbitRequestContext,autoApproveEnabled);
    return entity;
  }

  private void updateAuditLogEntity(AuditLogEntity entity,AuditDao auditDao,RabbitRequestContext rabbitRequestContext,boolean autoApproveEnabled) {
    AuditLogEntity entToEdit = new AuditLogEntity();
    entToEdit.setId(entity.getId());
    entToEdit.setStatus(AuditStatus.APPROVED);
    entToEdit.setRespondedBy(autoApproveEnabled?"SYSTEM":rabbitRequestContext.getUserName());
    entToEdit.setRespondedOn(HelperUtils.now());
    auditDao.update(entToEdit);
    logger.info("AUDIT LOG UPDATED ");
  }
}
