package global.citytech.remitpulse.countries.repositories.domains.services.master;

/**
 * @author bipin on 6/21/19 2:24 PM.
 */
public class Continent {
    private String code;
    private String name;

    public Continent(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
