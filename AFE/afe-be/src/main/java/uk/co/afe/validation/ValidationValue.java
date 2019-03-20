package uk.co.afe.validation;

/**
 * Collections of validation values
 *
 * @author Sergey Teryoshin
 * 13.03.2018 17:48
 */
public final class ValidationValue {

    /**
     * Min length of email
     */
    public static final int EMAIL_SIZE_MIN = 5;

    /**
     * Max length of email
     */
    public static final int EMAIL_SIZE_MAX = 256;

    /**
     * Min value of money
     */
    public static final String MONEY_MIN = "0";

    /**
     * Min risk ranking value
     */
    public static final String RISK_RANKING_ATR_MIN = "0";

    /**
     * Max risk ranking value
     */
    public static final String RISK_RANKING_ATR_MAX = "100";

    private ValidationValue() {
    }
}
