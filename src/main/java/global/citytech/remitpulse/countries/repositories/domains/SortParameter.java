package global.citytech.remitpulse.countries.repositories.domains;

import java.util.Arrays;
import java.util.Optional;

/** @author bipin on 5/22/19 10:15 AM. */
public enum SortParameter {
  ASC("asc"),
  DESC("desc");

  private String code;

  SortParameter(String param) {
    this.code = param;
  }

  public String getCode() {
    return this.code;
  }

  public static SortParameter getByCode(String code) {

    Optional<SortParameter> sortParameter =
        Arrays.stream(SortParameter.values())
            .filter(e -> e.getCode().equalsIgnoreCase(code))
            .findFirst();

    if (sortParameter.isPresent()) return sortParameter.get();

    throw new IllegalArgumentException(code);
  }
}
