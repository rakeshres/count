package global.citytech.remitpulse.countries.repositories.domains.services.currency;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bipin on 6/21/19 11:47 AM.
 */
public class Currency {
    private String isoAlpha;
    private Boolean isDefault;
    private List<CurrencyOperationType> currencyOperationTypeList;

    public Currency() {
        this.currencyOperationTypeList= new ArrayList<>();
    }

    public String getIsoAlpha() {
        return isoAlpha;
    }

    public void setIsoAlpha(String isoAlpha) {
        this.isoAlpha = isoAlpha;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public List<CurrencyOperationType> getCurrencyOperationTypeList() {
        return currencyOperationTypeList;
    }

    public void setCurrencyOperationTypeList(List<CurrencyOperationType> currencyOperationTypeList) {
        this.currencyOperationTypeList = currencyOperationTypeList;
    }
}
