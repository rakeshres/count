package global.citytech.remitpulse.countries.repositories.constants;

/**
 * @author sankalpa on 4/12/21
 */
public enum LocationConstant {
    STATE("states","STATE")
    ;

    private final String code;
    private final String description;

    LocationConstant(String code,
                     String description) {
        this.code = code;
        this.description = description;
    }



    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static LocationConstant getByCode(String fieldName) {
        for (LocationConstant v : values())
            if (v.getCode().equalsIgnoreCase(fieldName)) return v;
        throw new IllegalArgumentException();
    }
}
