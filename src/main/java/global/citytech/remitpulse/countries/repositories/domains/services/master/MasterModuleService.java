package global.citytech.remitpulse.countries.repositories.domains.services.master;

import java.util.List;

/**
 * @author bipin on 6/19/19 4:18 PM.
 */
public interface MasterModuleService {
    List<Continent> getContinents();
    List<MasterInfo> fetchMasterInfo(MasterInfo masterInfo);
}
