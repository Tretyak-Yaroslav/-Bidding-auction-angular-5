package uk.co.afe.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Values that describes kinds of errors and contains some useful information for figuring out error reason
 *
 * @author Sergey Teryoshin
 * 13.03.2018 17:35
 */
@Data
@ApiModel(description = "Values that describes kinds of errors and contains some useful information for figuring out error reason")
public final class ErrorCode {

    /**
     * Error is unknown
     */
    public static final String UNKNOWN_ERROR = "ERR001";

    @ApiModelProperty("Error is unknown")
    private String _ERR001 = UNKNOWN_ERROR;

    /**
     * Invocation validation error
     */
    public static final String VALIDATION_ERROR = "ERR002";

    @ApiModelProperty("Invocation validation error")
    private String _ERR002 = VALIDATION_ERROR;

    /**
     * Access denied for current client
     */
    public static final String ACCESS_DENIED = "ATH001";

    @ApiModelProperty("Access denied for current client")
    private String _ATH001 = ACCESS_DENIED;

    /**
     * Unauthorized request
     */
    public static final String UNAUTHORIZED = "ATH002";

    @ApiModelProperty("Unauthorized request")
    private String _ATH002 = UNAUTHORIZED;

    /**
     * Unknown enumerate value
     */
    public static final String UNKNOWN_ENUM_VALUE = "ERR003";

    @ApiModelProperty("Unknown enumerate value")
    private String _ERR003 = UNKNOWN_ENUM_VALUE;

    /**
     * Http method not allowed
     */
    public static final String METHOD_DONT_SUPPORTED = "ERR004";

    @ApiModelProperty("Http method not allowed")
    private String _ERR004 = METHOD_DONT_SUPPORTED;

    /**
     * Specified credentials is incorrect
     */
    public static final String INCORRECT_CREDENTIALS = "ACC001";

    @ApiModelProperty("Specified credentials is incorrect")
    private String _ACC001 = INCORRECT_CREDENTIALS;

    /**
     * Client have to confirm email
     */
    public static final String CLIENT_HAVE_TO_CONFIRM_EMAIL = "ACC006";

    @ApiModelProperty("Client have to confirm email")
    private String _ACC006 = CLIENT_HAVE_TO_CONFIRM_EMAIL;

    /**
     * Client need approve from back-office
     */
    public static final String CLIENT_NEED_APPROVE = "ACC008";

    @ApiModelProperty("Client need approve from back-office")
    private String _ACC008 = CLIENT_NEED_APPROVE;

    /**
     * Too much clients was found by query
     */
    public static final String TOO_MUCH_CLIENTS_FOUND = "ACC002";

    @ApiModelProperty("Too much clients was found by query")
    private String _ACC002 = TOO_MUCH_CLIENTS_FOUND;

    /**
     * Client with specified identifier not found
     */
    public static final String CLIENT_NOT_FOUND = "ACC003";

    @ApiModelProperty("Client with specified identifier not found")
    private String _ACC003 = CLIENT_NOT_FOUND;

    /**
     * Product with specified identifier not found
     */
    public static final String PRODUCT_NOT_FOUND = "PRD001";

    @ApiModelProperty("Product with specified identifier not found")
    private String _PRD001 = PRODUCT_NOT_FOUND;

    /**
     * Dictionary of questionnaires is empty
     */
    public static final String DICT_OF_QUESTIONS_IS_EMPTY = "DIC001";

    @ApiModelProperty("Dictionary of questionnaires is empty")
    private String _DIC001 = DICT_OF_QUESTIONS_IS_EMPTY;

    /**
     * Questionnaire with specified id not found
     */
    public static final String QUESTIONNAIRE_NOT_FOUND = "DIC002";

    @ApiModelProperty("Questionnaire with specified id not found")
    private String _DIC002 = QUESTIONNAIRE_NOT_FOUND;

    /**
     * Error while convert data
     */
    public static final String CONVERT_DATA = "ERR005";

    @ApiModelProperty("Error while convert data")
    private String _ERR005 = CONVERT_DATA;

    /**
     * Client with specified email already exists
     */
    public static final String CLIENT_EMAIL_UNAVAILABLE = "ACC004";

    @ApiModelProperty("Client with specified email already exists")
    private String _ACC004 = CLIENT_EMAIL_UNAVAILABLE;

    /**
     * Client with specified phone number already exists
     */
    public static final String CLIENT_PHONE_NUMBER_UNAVAILABLE = "ACC005";

    @ApiModelProperty("Client with specified phone number already exists")
    private String _ACC005 = CLIENT_PHONE_NUMBER_UNAVAILABLE;

    /**
     * Client is hidden and can't be login in
     */
    public static final String CLIENT_IS_HIDDEN = "ATH003";

    @ApiModelProperty("Client is hidden and can't be login in")
    private String _ATH003 = CLIENT_IS_HIDDEN;

    /**
     * Auction is finished already
     */
    public static final String AUCTION_IS_FINISHED = "AUC001";

    @ApiModelProperty("Auction is finished already")
    private String _AUC001 = AUCTION_IS_FINISHED;

    /**
     * Auction is not start yet
     */
    public static final String AUCTION_IS_NOT_START = "AUC002";

    @ApiModelProperty("Auction is not start yet")
    private String _AUC002 = AUCTION_IS_NOT_START;

    /**
     * Error while checking conditions of changing product status
     */
    public static final String CHANGE_PRODUCT_STATUS_ERROR = "PRD002";

    @ApiModelProperty("Error while checking conditions of changing product status")
    private String _PRD002 = CHANGE_PRODUCT_STATUS_ERROR;

    /**
     * Bid with specified identifier not found
     */
    public static final String BID_NOT_FOUND = "BID001";

    @ApiModelProperty("Bid with specified identifier not found")
    private String _BID001 = BID_NOT_FOUND;

    /**
     * Error while checking conditions of changing tx status
     */
    public static final String CHANGE_TX_STATUS_ERROR = "BID002";

    @ApiModelProperty("Error while checking conditions of changing tx status")
    private String _BID002 = CHANGE_TX_STATUS_ERROR;

    /**
     * Error while withdrawal bid
     */
    public static final String BID_CANT_BE_WITHDRAWAL = "BID003";

    @ApiModelProperty("Error while withdrawal bid")
    private String _BID003 = BID_CANT_BE_WITHDRAWAL;

    /**
     * Avatar of client not exist
     */
    public static final String CLIENT_AVATAR_NOT_FOUND = "ACC007";

    @ApiModelProperty("Avatar of client not exist")
    private String _ACC007 = CLIENT_AVATAR_NOT_FOUND;

    /**
     * Photo of product not exist
     */
    public static final String PRODUCT_PHOTO_NOT_FOUND = "PRD003";

    @ApiModelProperty("Photo of product not exist")
    private String _PRD003 = PRODUCT_PHOTO_NOT_FOUND;

    /**
     * Unknown confirmation token
     */
    public static final String UNKNOWN_CONFIRMATION_TOKEN = "ATH004";

    @ApiModelProperty("Unknown confirmation token")
    private String _ATH004 = UNKNOWN_CONFIRMATION_TOKEN;

    /**
     * Bid less then starting bid
     */
    public static final String BID_LESS_THEN_STATING = "BID004";

    @ApiModelProperty("Bid less then starting bid")
    private String _BID004 = BID_LESS_THEN_STATING;

    /**
     * Bid amount less then last bid of product
     */
    public static final String BID_LESS_THEN_LAST_BID = "BID005";

    @ApiModelProperty("Bid amount less then last bid of product")
    private String _BID005 = BID_LESS_THEN_LAST_BID;

    private ErrorCode() {
    }
}
