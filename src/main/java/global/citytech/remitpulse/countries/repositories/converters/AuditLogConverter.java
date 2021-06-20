package global.citytech.remitpulse.countries.repositories.converters;

import com.google.gson.JsonParser;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.core.audits.AuditLogInfo;
import global.citytech.rabbit.core.commons.EntityConverter;
import global.citytech.rabbit.core.utils.HelperUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sankalpa on 5/6/20
 */
public class AuditLogConverter  implements EntityConverter<AuditLogEntity, AuditLogInfo> {
    public AuditLogConverter() {
    }

    public List<AuditLogInfo> toAuditLogInfoList(List<AuditLogEntity> logEntities) {
      List<AuditLogInfo> infos = new ArrayList();
      logEntities.stream().forEach((log) -> {
        infos.add(this.toServiceObject(log));
      });
      return infos;
    }

    public AuditLogEntity toEntity(AuditLogInfo auditLogInfo) {
      AuditLogEntity auditLogEntity = new AuditLogEntity();

      try {
        BeanUtils.copyProperties(auditLogEntity, auditLogInfo);
      } catch (InvocationTargetException | IllegalAccessException var4) {
      }

      return auditLogEntity;
    }

    public AuditLogInfo toServiceObject(AuditLogEntity entity) {
      AuditLogInfo auditLogInfo = new AuditLogInfo();
      auditLogInfo.setId(entity.getId());
      auditLogInfo.setAction(entity.getAction().getDescription());
      auditLogInfo.setChanges(entity.getChanges());
      auditLogInfo.setEntity(entity.getEntity());
      auditLogInfo.setEntityKey(entity.getEntityKey());
      auditLogInfo.setRequestRemarks(entity.getResponseRemarks());
      auditLogInfo.setRequestedBy(entity.getRequestedBy());
      auditLogInfo.setRequestedOn(entity.getRequestedOn());
      auditLogInfo.setRespondedBy(entity.getRespondedBy());
      auditLogInfo.setRespondedOn(entity.getRespondedOn());
      auditLogInfo.setResponseRemarks(entity.getResponseRemarks());
      auditLogInfo.setStatus(entity.getStatus().getDescription());
      if (HelperUtils.isBlankOrNull(entity.getNewData())) {
        auditLogInfo.setNewData((new JsonParser()).parse("{}").getAsJsonObject());
      } else {
        auditLogInfo.setNewData((new JsonParser()).parse(entity.getNewData()).getAsJsonObject());
      }

      return auditLogInfo;
    }
}
