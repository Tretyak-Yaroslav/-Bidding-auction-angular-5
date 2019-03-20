package uk.co.afe.model.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * List of all application permissions
 *
 * @author Sergey Teryoshin
 * 20.03.2018 16:17
 */
@Data
@ApiModel(description = "List of all application permissions")
public final class Actions {

    /**
     * Granted access to getting all security permissions of current user
     */
    @ApiModelProperty("Granted access to getting all security permissions of current user")
    private String _SECURITY_PERMISSION_GET = "SECURITY_PERMISSION_GET";

    /**
     * Granted access to checking if client with specified phone number not exists
     */
    @ApiModelProperty("Granted access to checking if client with specified phone number not exists")
    private String _IS_PHONE_NUMBER_AVAILABLE = "IS_PHONE_NUMBER_AVAILABLE";

    /**
     * Granted access to update client info
     */
    @ApiModelProperty("Granted access to update client info")
    private String _CLIENT_UPDATE_INFO = "CLIENT_UPDATE_INFO";

    /**
     * Granted access to update client password
     */
    @ApiModelProperty("Granted access to update client password")
    private String _CLIENT_UPDATE_PASSWORD = "CLIENT_UPDATE_PASSWORD";

    /**
     * Granted access to update client questionnaire answers
     */
    @ApiModelProperty("Granted access to update client questionnaire answers")
    private String _CLIENT_UPDATE_QUESTIONNAIRE_RESULT = "CLIENT_UPDATE_QUESTIONNAIRE_RESULT";

    /**
     * Granted access to getting all clients, include hidden
     */
    @ApiModelProperty("Granted access to getting all clients, include hidden")
    private String _CLIENT_GET_ALL = "CLIENT_GET_ALL";

    /**
     * Role-marker to determine client role
     */
    @ApiModelProperty("Role-marker to determine client role")
    private String __IS_CLIENT = "_IS_CLIENT";

    /**
     * Role-marker to determine back-office role
     */
    @ApiModelProperty("Role-marker to determine back-office role")
    private String __IS_BACK_OFFICE = "_IS_BACK_OFFICE";

    /**
     * Granted access to hide client
     */
    @ApiModelProperty("Granted access to hide client")
    private String _CLIENT_UPDATE_HIDE = "CLIENT_UPDATE_HIDE";

    /**
     * Granted access to unhide client
     */
    @ApiModelProperty("Granted access to unhide client")
    private String _CLIENT_UPDATE_UNHIDE = "CLIENT_UPDATE_UNHIDE";

    /**
     * Granted access to update client risk ranking attribute
     */
    @ApiModelProperty("Granted access to update client risk ranking attribute")
    private String _CLIENT_UPDATE_RISK_ATR = "CLIENT_UPDATE_RISK_ATR";

    /**
     * Granted access to make bid in auction
     */
    @ApiModelProperty("Granted access to make bid in auction")
    private String _AUCTION_MAKE_BID = "AUCTION_MAKE_BID";

    /**
     * Granted access to register new product
     */
    @ApiModelProperty("Granted access to register new product")
    private String _PRODUCT_REGISTER = "PRODUCT_REGISTER";

    /**
     * Granted access to update risk-ranking attribute of product
     */
    @ApiModelProperty("Granted access to update risk-ranking attribute of product")
    private String _PRODUCT_UPDATE_ATR = "PRODUCT_UPDATE_ATR";

    /**
     * Granted access to update info of product
     */
    @ApiModelProperty("Granted access to update info of product")
    private String _PRODUCT_UPDATE_INFO = "PRODUCT_UPDATE_INFO";

    /**
     * Granted access to buyout cation
     */
    @ApiModelProperty("Granted access to buyout cation")
    private String _AUCTION_BUYOUT = "AUCTION_BUYOUT";

    /**
     * Granted access to show risk ranking attribute block of client settings
     */
    @ApiModelProperty("Granted access to show risk ranking attribute block of client settings")
    private String _CLIENT_SHOW_RISK_RANKING_ATR = "CLIENT_SHOW_RISK_RANKING_ATR";

    /**
     * Granted access to withdraw bid
     */
    @ApiModelProperty("Granted access to withdraw bid")
    private String _AUCTION_BID_WITHDRAW = "AUCTION_BID_WITHDRAW";

    /**
     * Granted access to reject product
     */
    @ApiModelProperty("Granted access to reject product")
    private String _PRODUCT_REJECT = "PRODUCT_REJECT";

    /**
     * Granted access to approve product addition
     */
    @ApiModelProperty("Granted access to approve product addition")
    private String _PRODUCT_APPROVE = "PRODUCT_APPROVE";

    /**
     * Granted access to approve deal for select bid
     */
    @ApiModelProperty("Granted access to approve deal for select bid")
    private String _PRODUCT_APPROVE_DEAL = "PRODUCT_APPROVE_DEAL";

    /**
     * Granted access to get all products by status
     */
    @ApiModelProperty("Granted access to get all products by status")
    private String _PRODUCT_GET_ALL_BY_STATUS = "PRODUCT_GET_ALL_BY_STATUS";

    /**
     * Granted access to approve client registration
     */
    @ApiModelProperty("Granted access to approve client registration")
    private String _CLIENT_APPROVE = "CLIENT_APPROVE";

    /**
     * Granted access to update client avatar
     */
    @ApiModelProperty("Granted access to update client avatar")
    private String _CLIENT_UPDATE_IMAGE = "CLIENT_UPDATE_IMAGE";

    /**
     * Granted access to download and upload list of platforms
     */
    @ApiModelProperty("Granted access to download and upload list of platforms")
    private String _DICTIONARY_PLATFORM_UPDATE = "DICTIONARY_PLATFORM_UPDATE";

    private Actions() {
    }
}
