package global.citytech.remitpulse.countries.crudapi.impl.exceptions;

import global.citytech.rabbit.core.exceptions.RabbitExceptionType;
import global.citytech.rabbit.microprofile.exceptions.AppException;

/** @author sunil */
public class ExceptionManager extends AppException {
  public ExceptionManager(RabbitExceptionType rabbitExceptionType) {
    super(rabbitExceptionType);
  }

  public enum PlatformError implements RabbitExceptionType {
    PRIMARY_KEY_CODE_OR_ID_IS_BLANK("BLP10001", "Primary key code or id is blank"),
    ENTITY_NOT_FOUND("BLP10002", "Entity not found"),

    INVALID_SORT_PARAMETER("BLP10003", "Invalid sort parameter."),
    INVALID_SORT_BY_PARAMETER("BLP10004", "Invalid sort by parameter."),
    ;

    private String code;
    private String description;

    PlatformError(String code, String description) {
      this.code = code;
      this.description = description;
    }

    @Override
    public String getCode() {
      return this.code;
    }

    @Override
    public String getDescription() {
      return this.description;
    }
  }

  public enum AuditLogError implements RabbitExceptionType {
    INVALID_AUDIT_LOG_ACTION("BPV20002", "Invalid audit log action"),
    MAKER_AND_CHECKER_CANNOT_BE_SAME("BPV1003", "Maker and checker cannot be same person"),

    ERROR_ON_INSERTING_INTO_ENTITY_TABLE("BPV20004", "Error on inserting into entity table"),
    ERROR_ON_UPDATING_INTO_ENTITY_TABLE("BPV20005", "Error on updating into entity table"),

    INSERTING_INTO_AUDIT_LOG_FAILED("BPV20006", "Inserting into audit log failed"),
    INSERTING_UPDATED_ENTITY_INTO_AUDIT_LOG_FAILED(
        "BPV20007", "Inserting updated entity into audit log failed"),
    UPDATING_AUDIT_LOG_FAILED("BPV20007", "Updating into audit log table failed"),
    INVALID_AUDIT_LOG_ENTITY("BPV20008", "Invalid audit log entity"),

    INVALID_AUDIT_LOG_STATUS("BPV20009", "Invalid audit log status"),
    INVALID_AUDIT_LOG_NEW_DATA_ENTITY("BPV20010", "Invalid audit log new data entity"),
    INVALID_AUDIT_LOG_OLD_DATA_ENTITY("BPV20011", "Invalid audit log old data entity"),

    AUDIT_LOG_ID_IS_BLANK("CFL20011", "Audit log id is blank"),
    AUDIT_LOG_NOT_FOUND("CFL20012", "Audit log not found"),
    INVALID_AUDIT_LOG_STATUS_TO_VERIFY("CFL20013", "Invalid audit log status to verify"),
    AUDIT_LOG_REMARK_IS_BLANK("CFL20013", "Audit log remark is blank"),

    ERROR_ON_COMMUNICATION_BETWEEN_VALIDATOR_AND_HELPER_CLASS(
        "CFL20014", "Error on communication between validation and helper class"),
    ;

    private String code;
    private String description;

    AuditLogError(String code, String description) {
      this.code = code;
      this.description = description;
    }

    @Override
    public String getCode() {
      return this.code;
    }

    @Override
    public String getDescription() {
      return this.description;
    }
  }

  public static void throwPrimaryKeyCodeOrIdIsBlank() {
    throw new AppException(PlatformError.PRIMARY_KEY_CODE_OR_ID_IS_BLANK);
  }

  public static void throwEntityNotFound() {
    throw new AppException(PlatformError.ENTITY_NOT_FOUND);
  }

  public static void throwInvalidSortParameter() {
    throw new AppException(PlatformError.INVALID_SORT_PARAMETER);
  }

  public static void throwInvalidSortByParameter() {
    throw new AppException(PlatformError.INVALID_SORT_BY_PARAMETER);
  }

  public static void throwInvalidAuditLogAction() {
    throw new AppException(AuditLogError.INVALID_AUDIT_LOG_ACTION);
  }

  public static void throwInsertingIntoAuditLogFailed() {
    throw new AppException(AuditLogError.INSERTING_INTO_AUDIT_LOG_FAILED);
  }

  public static void throwInsertingUpdatedEntityIntoAuditLogFailed() {
    throw new AppException(AuditLogError.INSERTING_UPDATED_ENTITY_INTO_AUDIT_LOG_FAILED);
  }

  public static void throwUpdatingAuditLogFailed() {
    throw new AppException(AuditLogError.UPDATING_AUDIT_LOG_FAILED);
  }

  public static void throwMakerAndCheckerCannotBeSame() {
    throw new AppException(AuditLogError.MAKER_AND_CHECKER_CANNOT_BE_SAME);
  }

  public static void throwErrorOnInsertingIntoEntityTable() {
    throw new AppException(AuditLogError.ERROR_ON_INSERTING_INTO_ENTITY_TABLE);
  }

  public static void throwErrorOnUpdatingIntoEntityTable() {
    throw new AppException(AuditLogError.ERROR_ON_UPDATING_INTO_ENTITY_TABLE);
  }

  public static void throwInvalidAuditLogEntity() {
    throw new AppException(AuditLogError.INVALID_AUDIT_LOG_ENTITY);
  }

  public static void throwInvalidAuditLogStatus() {
    throw new AppException(AuditLogError.INVALID_AUDIT_LOG_STATUS);
  }

  public static void throwInvalidAuditLogNewDataEntity() {
    throw new AppException(AuditLogError.INVALID_AUDIT_LOG_NEW_DATA_ENTITY);
  }

  public static void throwInvalidAuditLogOldDataEntity() {
    throw new AppException(AuditLogError.INVALID_AUDIT_LOG_OLD_DATA_ENTITY);
  }

  public static void throwAuditLogIdIsBlank() {
    throw new AppException(AuditLogError.AUDIT_LOG_ID_IS_BLANK);
  }

  public static void throwAuditLogNotFound() {
    throw new AppException(AuditLogError.AUDIT_LOG_NOT_FOUND);
  }

  public static void throwInvalidAuditLogStatusToVerify() {
    throw new AppException(AuditLogError.INVALID_AUDIT_LOG_STATUS_TO_VERIFY);
  }

  public static void throwAuditLogRemarkIsBlank() {
    throw new AppException(AuditLogError.AUDIT_LOG_REMARK_IS_BLANK);
  }

  public static void throwErrorOnCommunicationBetweenValidatorAndHelperClass() {
    throw new AppException(AuditLogError.ERROR_ON_COMMUNICATION_BETWEEN_VALIDATOR_AND_HELPER_CLASS);
  }
}
