package uk.co.afe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.co.afe.core.DictionaryBean;
import uk.co.afe.model.Platform;
import uk.co.afe.model.questionaries.Questionnaire;
import uk.co.afe.model.response.dictionary.WarGetCurrentServerTimeResponse;
import uk.co.afe.model.response.dictionary.WarGetPlatformsResponse;
import uk.co.afe.model.response.dictionary.WarGetQuestionnaireResponse;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Services of providing dictionaries
 *
 * @author Sergey Teryoshin
 * 20.03.2018 15:14
 */
@RestController
@RequestMapping("api/v1/dictionary")
@Api(value = "Services of providing dictionaries", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class DictionaryController {

    private static final Logger LOG = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryBean dictionaryBean;

    /**
     * Getting list of all platforms, include hidden
     * <p>Required permissions: permitAll()</p>
     */
    @ApiOperation(value = "Getting list of all platforms, include hidden", notes = "<p>Required permissions: permitAll()</p>")
    @PreAuthorize("permitAll()")
    @GetMapping("platform/get")
    @Valid
    public WarGetPlatformsResponse getPlatforms() {
        LOG.info("-> api/v1/dictionary/platform/get");
        List<Platform> platforms = dictionaryBean.getAllPlatforms();
        WarGetPlatformsResponse response = new WarGetPlatformsResponse(platforms);
        LOG.info("<- api/v1/dictionary/platform/get. " + response);
        return response;
    }

    /**
     * Getting current questionnaire
     * <p>Required permissions: isAuthenticated()</p>
     */
    @ApiOperation(value = "Getting current questionnaire", notes = "<p>Required permissions: isAuthenticated()</p>")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("questionnaire/get/current")
    @Valid
    public WarGetQuestionnaireResponse getCurrentQuestionnaire() {
        LOG.info("-> api/v1/dictionary/questionnaire/get/current");
        Questionnaire questionnaire = dictionaryBean.getCurrentQuestionnaire();
        WarGetQuestionnaireResponse response = new WarGetQuestionnaireResponse(questionnaire);
        LOG.info("<- api/v1/dictionary/questionnaire/get/current. " + response);
        return response;
    }

    /**
     * Getting questionnaire by specified identifier
     * <p>Required permissions: isAuthenticated()</p>
     */
    @ApiOperation(value = "Getting questionnaire by specified identifier", notes = "<p>Required permissions: isAuthenticated()</p>")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("questionnaire/get/{id}")
    @Valid
    public WarGetQuestionnaireResponse getCurrentQuestionnaire(@PathVariable Long id) {
        LOG.info("-> api/v1/dictionary/questionnaire/get/{id}");
        Questionnaire questionnaire = dictionaryBean.getQuestionnaireById(id);
        WarGetQuestionnaireResponse response = new WarGetQuestionnaireResponse(questionnaire);
        LOG.info("<- api/v1/dictionary/questionnaire/get/{id}. " + response);
        return response;
    }

    /**
     * Getting current server date and time
     * <p>Required permissions: permitAll()</p>
     */
    @ApiOperation(value = "Getting current server date and time", notes = "<p>Required permissions: permitAll()</p>")
    @PreAuthorize("permitAll()")
    @GetMapping("current-time")
    @Valid
    public WarGetCurrentServerTimeResponse getCurrentTime() {
        LOG.info("-> api/v1/dictionary/current-time");
        WarGetCurrentServerTimeResponse response = new WarGetCurrentServerTimeResponse();
        response.setCurrentTime(LocalDateTime.now());
        LOG.info("-> api/v1/dictionary/current-time. " + response);
        return response;
    }

    /**
     * Download current list of platforms
     * <p>Required permissions: hasRole('DICTIONARY_PLATFORM_UPDATE')</p>
     */
    @ApiOperation(value = "Download current list of platforms", notes = "<p>Required permissions: hasRole('DICTIONARY_PLATFORM_UPDATE')</p>")
    @PreAuthorize("hasRole('DICTIONARY_PLATFORM_UPDATE')")
    @GetMapping(value = "platform/download")
    public byte[] downloadPlatforms(HttpServletResponse response) throws IOException {
        LOG.info("-> api/v1/dictionary/platform/download");
        byte[] bytes = dictionaryBean.downloadPlatforms();
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-disposition", "attachment; filename=platforms.xlsx");
        LOG.info("<- api/v1/dictionary/platform/download");
        return bytes;
    }

    /**
     * Upload updated list of platforms
     * <p>Required permissions: hasRole('DICTIONARY_PLATFORM_UPDATE')</p>
     */
    @ApiOperation(value = "Upload updated list of platforms", notes = "<p>Required permissions: hasRole('DICTIONARY_PLATFORM_UPDATE')</p>")
    @PreAuthorize("hasRole('DICTIONARY_PLATFORM_UPDATE')")
    @PutMapping("platform/upload")
    public void uploadPlatforms(@RequestParam("file") MultipartFile file) throws IOException {
        LOG.info("-> api/v1/dictionary/platform/upload");
        if (file != null) {
            dictionaryBean.uploadList(file.getBytes());
        }
        LOG.info("<- api/v1/dictionary/platform/upload");
    }

}
