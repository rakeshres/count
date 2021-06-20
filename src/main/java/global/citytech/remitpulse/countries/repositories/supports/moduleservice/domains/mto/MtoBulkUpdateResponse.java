package global.citytech.remitpulse.countries.repositories.supports.moduleservice.domains.mto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sankalpa on 11/11/20
 */
public class MtoBulkUpdateResponse {

    public MtoBulkUpdateResponse(List<String> mtoCodes) {
        this.mtoCodes = mtoCodes;
    }

    public MtoBulkUpdateResponse() {
        this.mtoCodes = new ArrayList<>();
    }

    private List<String> mtoCodes;

    public List<String> getMtoCodes() {
        return mtoCodes;
    }

    public void setMtoCodes(List<String> mtoCodes) {
        this.mtoCodes = mtoCodes;
    }
}
