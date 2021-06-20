package global.citytech.remitpulse.countries.repositories.supports.moduleservice.modulesconfig;

import global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.ModuleConfigurationInfo;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.ModuleConfigurationSearchRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author sunil
 */
public interface ModulesConfigService {
    List<ModuleConfigurationInfo> getModuleConfigurationList(
            ModuleConfigurationSearchRequest moduleConfigurationSearchRequest);

    Optional<ModuleConfigurationInfo> getModuleConfiguration(
            ModuleConfigurationSearchRequest moduleConfigurationSearchRequest);

    boolean isAutoVerifyEnabled(String moduleIdentifier);
}
