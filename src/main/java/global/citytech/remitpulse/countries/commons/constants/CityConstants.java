package global.citytech.remitpulse.countries.commons.constants;

import java.time.format.DateTimeFormatter;

/** @author sunil */
public class CityConstants {
  public static final int PAGINATION_DEFAULT_PAGE_NUMBER = 1;
  public static final int PAGINATION_DEFAULT_PAGE_SIZE = 10;

  public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
  public static final DateTimeFormatter INPUT_DATE_FORMATTER =
      DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
  public static final String PARTNER_NAME_VALID_PATTERN = "[^a-zA-Z#'.,/() " + "-]";
  public static final String PARTNER_ADDRESS_VALID_PATTERN = "[^a-zA-Z0-9#'.,/() " + "-]";
  public static final String ALPHABET_ONLY_PATTERN = "[^a-zA-Z]";
  public static final String ALPHANUMERIC_ONLY_PATTERN = "^[a-zA-Z0-9]*$";
  public static final String NUMBER_ONLY_PATTERN = "[^0-9]";
  public static final int PAYMENT_METHOD_TITLE_LENGTH = 20;
  public static final int PAYMENT_METHOD_CODE_LENGTH = 10;
  public static final int ID_TYPE_FORMAT_LENGTH = 20;
}
