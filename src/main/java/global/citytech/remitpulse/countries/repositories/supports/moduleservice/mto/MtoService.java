package global.citytech.remitpulse.countries.repositories.supports.moduleservice.mto;

import global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.mto.MtoBulkUpdateResponse;
import global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.mto.MtoUpdateRequest;

import java.util.Optional;

/** @author rajudhital on 4/16/20 */
public interface MtoService {
  Optional<MtoBulkUpdateResponse> updateMtoMasterDetail(MtoUpdateRequest request);
}
