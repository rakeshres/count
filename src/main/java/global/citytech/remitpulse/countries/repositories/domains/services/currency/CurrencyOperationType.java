package global.citytech.remitpulse.countries.repositories.domains.services.currency;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bipin on 6/21/19 12:07 PM.
 */
public class CurrencyOperationType {
    private String code;
    private List<CurrencyLimit> currencyLimitList;

    public CurrencyOperationType() {
        this.currencyLimitList= new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CurrencyLimit> getCurrencyLimitList() {
        return currencyLimitList;
    }

    public void setCurrencyLimitList(List<CurrencyLimit> currencyLimitList) {
        this.currencyLimitList = currencyLimitList;
    }
}
