package global.citytech.remitpulse.countries.repositories.domains;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author bipin on 7/4/19 2:58 PM.
 */
public enum EntityName {
    COUNTRY("COUNTRY","CTR"),
    COUNTRY_DYNAMIC_FORM("COUNTRY_DYNAMIC_FORM","CDF")
    ;

    private String entityName;
    private String moduleIdentifier;

    EntityName(String entityName,String moduleIdentifier) {
        this.entityName = entityName;
        this.moduleIdentifier = moduleIdentifier;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getModuleIdentifier() {
        return moduleIdentifier;
    }

  public static Optional<EntityName> getByName(String entityName) {
    return Arrays.stream(EntityName.values())
        .filter(e -> e.name().equalsIgnoreCase(entityName))
        .findFirst();
  }
}
