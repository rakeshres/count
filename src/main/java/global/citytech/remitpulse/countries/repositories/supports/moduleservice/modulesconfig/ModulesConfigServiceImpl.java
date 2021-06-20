package global.citytech.remitpulse.countries.repositories.supports.moduleservice.modulesconfig;

import com.google.common.base.Strings;
import global.citytech.rabbit.core.rest.servicediscovery.Service;
import global.citytech.rabbit.core.rest.servicediscovery.ServiceDiscoveryImpl;
import global.citytech.remitpulse.countries.repositories.domains.ExceptionManager;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.BaseModuleService;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.ModuleConfigurationInfo;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.ModuleConfigurationSearchRequest;
import global.citytech.rabbit.core.rest.HttpClientRequest;
import global.citytech.rabbit.core.rest.HttpClientResponse;
import global.citytech.rabbit.core.utils.Jsons;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author sunil
 */
@Named
@Dependent
public class ModulesConfigServiceImpl extends BaseModuleService implements ModulesConfigService {

    private static final Logger LOGGER = Logger.getLogger(ModulesConfigServiceImpl.class.getName());

    @Override
    public String getModuleUrl() {
        Service service = ServiceDiscoveryImpl.getInstance().getService("modules-config");
        return service.getUrl();
    }

    @Override
    public String getModuleName() {
        return "MODULES_CONFIG";
    }

    @Override
    public List<ModuleConfigurationInfo> getModuleConfigurationList(
            ModuleConfigurationSearchRequest moduleConfigurationSearchRequest) {

        String serviceUri = "/v1/modules/config/search/all";
        HttpClientRequest httpClientRequest =
                this.prepareHttpClientRequest(serviceUri, moduleConfigurationSearchRequest);

        LOGGER.info("REQUESTING MODULES CONFIG " + Jsons.toJsonObj(moduleConfigurationSearchRequest));
        LOGGER.info("URL:: " + getModuleUrl() + serviceUri);
        Optional<HttpClientResponse> httpClientResponse =
                this.getHttpClientResponse(httpClientRequest);
        if (httpClientResponse.isEmpty()) {
            LOGGER.info(
                    "GOT NO RESPONSE: code "
                            + httpClientResponse.get().getCode()
                            + " , message: "
                            + httpClientResponse.get().getMessage());
            return Collections.emptyList();
        }

        throwIfExceptionOnResponse(httpClientResponse.get());

        LOGGER.info("MODULES CONFIG RESPONSE " + Jsons.toJsonObj(httpClientResponse.get().getData()));
        return Jsons.fromJsonToList(
                Jsons.toJsonObj(httpClientResponse.get().getData()),
                ModuleConfigurationInfo[].class);
    }

    @Override
    public Optional<ModuleConfigurationInfo> getModuleConfiguration(
            ModuleConfigurationSearchRequest moduleConfigurationSearchRequest) {

        return getModuleConfigurationList(moduleConfigurationSearchRequest).stream().findFirst();
    }

    @Override
    public boolean isAutoVerifyEnabled(String moduleIdentifier) {

        if (Strings.isNullOrEmpty(moduleIdentifier)) {
            ExceptionManager.throwModuleIdentifierIsInvalid();
        }

        ModuleConfigurationSearchRequest moduleConfigurationSearchRequest =
                new ModuleConfigurationSearchRequest();
        moduleConfigurationSearchRequest.setModuleIdentifier(moduleIdentifier);

        Optional<ModuleConfigurationInfo> moduleConfiguration =
                getModuleConfiguration(moduleConfigurationSearchRequest);

        if (moduleConfiguration.isEmpty()) {
            ExceptionManager.throwCouldNotGetModuleConfiguration();
        }

        if (moduleConfiguration.get().getAutoApproveEnabled() == null) {
            ExceptionManager.throwAutoVerifyConfigurationForTheModuleIsInvalid();
        }

        return moduleConfiguration.get().getAutoApproveEnabled();
    }
}
