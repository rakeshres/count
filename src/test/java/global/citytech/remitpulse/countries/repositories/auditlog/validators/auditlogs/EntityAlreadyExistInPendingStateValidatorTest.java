package global.citytech.remitpulse.countries.repositories.auditlog.validators.auditlogs;

import global.citytech.remitpulse.countries.repositories.auditlog.dto.WithKey;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.internal.dao.audit.AuditDao;
import global.citytech.remitpulse.countries.repositories.internal.filters.AuditLogFilter;
import global.citytech.rabbit.core.audits.AuditLogEntity;
import global.citytech.rabbit.microprofile.exceptions.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author sankalpa on 1/24/20
 */
class EntityAlreadyExistInPendingStateValidatorTest {

    private static final String PENDING_ID = "PENDING_ID";
    private static final String NON_PENDING_ID = "NON_PENDING_ID";

    private EntityAlreadyExistInPendingStateValidator entityAlreadyExistInPendingStateValidator;

    @BeforeEach
    void setup() {
        AuditDao auditLogDao = Mockito.mock(AuditDao.class);
        entityAlreadyExistInPendingStateValidator =
                Mockito.spy(new EntityAlreadyExistInPendingStateValidator(auditLogDao));

        AuditLogFilter filterCriteria = new AuditLogFilter();
        filterCriteria.setEntityKey(PENDING_ID);
        filterCriteria.setStatusList(List.of("PENDING"));
        Mockito.when(auditLogDao.find(filterCriteria)).thenReturn(Optional.of(new AuditLogEntity()));
    }

    @Test
    @DisplayName("Entity already is pending state")
    void shouldThrowEntityAlreadyInPendingState() {
        WithKey withKey = () -> PENDING_ID;

        AppException exception =
                assertThrows(
                        AppException.class, () -> entityAlreadyExistInPendingStateValidator.validate(withKey));
        assertEquals(
                ExceptionManager.AuditLogError.ENTITY_ALREADY_IN_PENDING_STATE.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should pass validation successfully")
    void shouldPassValidationSuccessfully() {
        WithKey withKey = () -> NON_PENDING_ID;

        doCallRealMethod().when(entityAlreadyExistInPendingStateValidator).validate(withKey);
        entityAlreadyExistInPendingStateValidator.validate(withKey);

        verify(entityAlreadyExistInPendingStateValidator, times(1)).validate(withKey);
    }
}
