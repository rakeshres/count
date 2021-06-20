package global.citytech.remitpulse.countries.repositories.domains.services.currency;

import java.util.List;

/**
 * @author bipin on 6/21/19 2:39 PM.
 */
public interface CurrencyModuleService {
    List<CurrencyInfo> fetchCurrencies(CurrencyInfo currencyInfo);
}
