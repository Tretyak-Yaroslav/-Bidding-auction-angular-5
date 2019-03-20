package uk.co.afe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.co.afe.core.ClientBean;
import uk.co.afe.model.client.Client;
import uk.co.afe.model.request.client.*;
import uk.co.afe.model.response.client.WarGetAllClientsResponse;
import uk.co.afe.model.response.client.WarGetClientResponse;
import uk.co.afe.model.response.client.WarIsEmailAvailableResponse;
import uk.co.afe.model.response.client.WarIsPhoneAvailableResponse;
import uk.co.afe.utils.SessionUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Services of clients
 *
 * @author Sergey Teryoshin
 * 17.03.2018 23:34
 */
@RestController
@RequestMapping("api/v1/client")
@Api(description = "Services of clients", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientBean clientBean;

    /**
     * Checking email to availability
     * <p>Required permissions: permitAll()</p>
     *
     * @param request Email to be check
     */
    @ApiOperation(value = "Checking email to availability", notes = "<p>Required permissions: permitAll()</p>")
    @PreAuthorize("permitAll()")
    @GetMapping("email-available")
    @Valid
    public WarIsEmailAvailableResponse isEmailAvailable(@Valid @RequestBody WarIsEmailAvailableRequest request) {
        LOG.info("-> api/v1/client/email-available. " + request);
        boolean available = clientBean.isEmailAvailable(request.getEmail());
        WarIsEmailAvailableResponse response = new WarIsEmailAvailableResponse(available);
        LOG.info("<- api/v1/client/email-available. " + response);
        return response;
    }

    /**
     * Checking phone number to availability
     * <p>Required permissions: hasRole('IS_PHONE_NUMBER_AVAILABLE')</p>
     *
     * @param request phone number to be check
     */
    @ApiOperation(value = "Checking email to availability", notes = "<p>Required permissions: hasRole('IS_PHONE_NUMBER_AVAILABLE')</p>")
    @PreAuthorize("hasRole('IS_PHONE_NUMBER_AVAILABLE')")
    @GetMapping("phone-available")
    @Valid
    public WarIsPhoneAvailableResponse isPhoneNumberAvailable(@Valid @RequestBody WarIsPhoneNumberAvailableRequest request) {
        LOG.info("-> api/v1/client/phone-available. " + request);
        boolean available = clientBean.isPhoneAvailable(request.getPhoneNumber());
        WarIsPhoneAvailableResponse response = new WarIsPhoneAvailableResponse(available);
        LOG.info("<- api/v1/client/phone-available. " + response);
        return response;
    }

    /**
     * Update client info
     * <p>Required permissions: hasRole('CLIENT_UPDATE_INFO')</p>
     *
     * @param request New client info
     */
    @ApiOperation(value = "Update client info", notes = "<p>Required permissions: hasRole('CLIENT_UPDATE_INFO')</p>")
    @PreAuthorize("hasRole('CLIENT_UPDATE_INFO')")
    @PostMapping("update/info")
    public void updateInfo(@Valid @RequestBody WarUpdateInfoRequest request) {
        LOG.info("-> api/v1/client/update/info. " + request);
        Client client = request.toClient();
        Long clientId = SessionUtils.getClientId();
        client.setClientId(clientId);
        clientBean.updateInfo(client);
        LOG.info("<- api/v1/client/update/info");
    }

    /**
     * Update client password
     * <p>Required permissions: hasRole('CLIENT_UPDATE_PASSWORD')</p>
     *
     * @param request Current password and new password
     */
    @ApiOperation(value = "Update client password", notes = "<p>Required permissions: hasRole('CLIENT_UPDATE_PASSWORD')</p>")
    @PreAuthorize("hasRole('CLIENT_UPDATE_PASSWORD')")
    @PostMapping("update/password")
    public void updatePassword(@Valid @RequestBody WarUpdatePasswordRequest request) {
        LOG.info("-> api/v1/client/update/password. " + request);
        Long clientId = SessionUtils.getClientId();
        clientBean.updatePassword(clientId, request.getCurrentPassword(), request.getNewPassword());
        LOG.info("<- api/v1/client/update/password");
    }

    /**
     * Save answers of questionnaire
     * <p>Required permissions: hasRole('CLIENT_UPDATE_QUESTIONNAIRE_RESULT')</p>
     *
     * @param request Fill full questionnaire
     */
    @ApiOperation(value = "Save answers of questionnaire", notes = "<p>Required permissions: hasRole('CLIENT_UPDATE_QUESTIONNAIRE_RESULT')</p>")
    @PreAuthorize("hasRole('CLIENT_UPDATE_QUESTIONNAIRE_RESULT')")
    @PostMapping("update/questionnaire/atr")
    public void updateQuestionnaireResult(@Valid @RequestBody WarUpdateQuestionnaireResultsRequest request) {
        LOG.info("-> api/v1/client/update/questionnaire/atr. " + request);
        Long clientId = SessionUtils.getClientId();
        clientBean.updateQuestionnaireAnswers(clientId, request.toQuestionnaire());
        LOG.info("-> api/v1/client/update/questionnaire/atr");
    }

    /**
     * Getting current client
     * <p>Required permissions: isAuthenticated()</p>
     */
    @ApiOperation(value = "Getting current client", notes = "<p>Required permissions: isAuthenticated()</p>")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("get/current")
    @Valid
    public WarGetClientResponse getCurrentClient() {
        LOG.info("-> api/v1/client/get/current");
        Long clientId = SessionUtils.getClientId();
        Client client = clientBean.getClientById(clientId);
        WarGetClientResponse response = new WarGetClientResponse(client);
        LOG.info("<- api/v1/client/get/current. " + response);
        return response;
    }

    /**
     * Getting client by id
     * <p>Required permissions: isAuthenticated()</p>
     *
     * @param clientId Identifier of client to lookup
     */
    @ApiOperation(value = "Getting client by id", notes = "<p>Required permissions: isAuthenticated()</p>")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("get/{id}")
    @Valid
    public WarGetClientResponse getCurrentClient(@PathVariable("id") Long clientId) {
        LOG.info("-> api/v1/client/get/{id}. " + clientId);
        Client client = clientBean.getClientById(clientId);
        WarGetClientResponse response = new WarGetClientResponse(client);
        LOG.info("<- api/v1/client/get/{id}. " + response);
        return response;
    }

    /**
     * Getting array of all clients, include hidden
     * <p>Required permissions: hasRole('CLIENT_GET_ALL')</p>
     */
    @ApiOperation(value = "Getting array of all clients, include hidden", notes = "<p>Required permissions: hasRole('CLIENT_GET_ALL')</p>")
    @PreAuthorize("hasRole('CLIENT_GET_ALL')")
    @GetMapping("get/all")
    @Valid
    public WarGetAllClientsResponse getAllClients() {
        LOG.info("-> api/v1/client/get/all");
        List<Client> clients = clientBean.getAllClients();
        WarGetAllClientsResponse response = new WarGetAllClientsResponse(clients);
        LOG.info("<- api/v1/client/get/all. " + response);
        return response;
    }

    /**
     * Hide client, his products and lots. Delete his bids
     * <p>Required permissions: hasRole('CLIENT_UPDATE_HIDE')</p>
     *
     * @param clientId Identifier of client to lookup and hide
     */
    @ApiOperation(value = "Hide client, his products and lots. Delete his bids", notes = "<p>Required permissions: hasRole('CLIENT_UPDATE_HIDE')</p>")
    @PreAuthorize("hasRole('CLIENT_UPDATE_HIDE')")
    @PostMapping("update/hide/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hideClient(@PathVariable("id") Long clientId) {
        LOG.info("-> api/v1/client/update/hide/{id}");
        clientBean.changeClientHideStatus(clientId, true);
        LOG.info("<- api/v1/client/update/hide/{id}");
    }

    /**
     * Unhide client, his products and lots. Delete his bids
     * <p>Required permissions: hasRole('CLIENT_UPDATE_UNHIDE')</p>
     *
     * @param clientId Identifier of client to lookup and unhide
     */
    @ApiOperation(value = "Unhide client, his products and lots", notes = "<p>Required permissions: hasRole('CLIENT_UPDATE_UNHIDE')</p>")
    @PreAuthorize("hasRole('CLIENT_UPDATE_UNHIDE')")
    @PostMapping("update/unhide/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unhideClient(@PathVariable("id") Long clientId) {
        LOG.info("-> api/v1/client/update/unhide/{id}");
        clientBean.changeClientHideStatus(clientId, false);
        LOG.info("<- api/v1/client/update/unhide/{id}");
    }

    /**
     * Update client risk ranking attribute
     * <p>Required permissions: hasRole('CLIENT_UPDATE_RISK_ATR')</p>
     *
     * @param request Identifier of client and new risk ranking attribute
     */
    @ApiOperation(value = "Update client risk ranking attribute", notes = "<p>Required permissions: hasRole('CLIENT_UPDATE_RISK_ATR')</p>")
    @PreAuthorize("hasRole('CLIENT_UPDATE_RISK_ATR')")
    @PostMapping("update/risk-atr")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRiskRankingAtt(@Valid @RequestBody WarUpdateRiskRankingAttRequest request) {
        LOG.info("-> api/v1/client/update/risk-atr. " + request);
        clientBean.updateInfo(request.toClient());
        LOG.info("<- api/v1/client/update/risk-atr");
    }

    /**
     * Approve client registration
     * <p>Required permissions: hasRole('CLIENT_APPROVE')</p>
     *
     * @param clientId Identifier of client
     */
    @ApiOperation(value = "Approve client registration", notes = "<p>Required permissions: hasRole('CLIENT_APPROVE')</p>")
    @PreAuthorize("hasRole('CLIENT_APPROVE')")
    @PostMapping("approve/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveClient(@PathVariable("id") Long clientId) {
        LOG.info("-> api/v1/client/approve/{id}. " + clientId);
        clientBean.approveClient(clientId);
        LOG.info("<- api/v1/client/approve/{id}");
    }

    /**
     * Avatar of client looked up by client id
     * <p>Required permissions: isAuthenticated()</p>
     *
     * @param clientId Identifier of client
     */
    @ApiOperation(value = "Avatar of client looked up by client id", notes = "<p>Required permissions: isAuthenticated()</p>")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("avatar/get/{id}")
    public byte[] getAvatar(@PathVariable("id") Long clientId) {
        LOG.info("-> api/v1/client/avatar/get/{id}");
        byte[] body = clientBean.getAvatar(clientId);
        LOG.info("<- api/v1/client/avatar/get/{id}. " + body);
        return body;
    }

    /**
     * Set new avatar for current client
     * <p>Required permissions: isAuthenticated()</p>
     *
     * @param file New avatar
     */
    @ApiOperation(value = "Set new avatar for current client", notes = "<p>Required permissions: isAuthenticated()</p>")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("avatar/set")
    public void setAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] body = file.getBytes();
        LOG.info("-> api/v1/client/avatar/set. " + body);
        Long clientId = SessionUtils.getClientId();
        clientBean.setAvatar(clientId, body);
        LOG.info("<- api/v1/client/avatar/set");
    }
}
