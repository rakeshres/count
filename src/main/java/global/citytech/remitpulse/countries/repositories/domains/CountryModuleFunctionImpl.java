package global.citytech.remitpulse.countries.repositories.domains;

import global.citytech.rabbit.microprofile.security.ModuleFunctionInfoService;
import global.citytech.rabbit.microprofile.security.ModuleType;

import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author bipin on 5/20/19 3:46 PM.
 */
@Named
public class CountryModuleFunctionImpl implements ModuleFunctionInfoService {

    private final static Logger LOGGER = Logger.getLogger(CountryModuleFunctionImpl.class.getName());

    @Override
    public List<ModuleType> getModuleInfo() {
        return null;
    }
}
