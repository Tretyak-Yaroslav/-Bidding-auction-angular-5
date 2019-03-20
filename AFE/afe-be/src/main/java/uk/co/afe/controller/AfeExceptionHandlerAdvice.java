package uk.co.afe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uk.co.afe.model.exceptions.BaseException;
import uk.co.afe.model.exceptions.BaseRuntimeException;
import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.exceptions.ObjectNotFoundException;
import uk.co.afe.model.response.ErrorResponse;

import javax.servlet.http.HttpServletResponse;

//import uk.co.afe.model.exceptions.AccessDeniedException;

/**
 * Handlers of unhandled exceptions.
 * <p> All handled exceptions will be logged and send to web-client as {@link ErrorResponse} with correct Http status
 *
 * @author Sergey Teryoshin
 * @see ErrorResponse
 * 13.03.2018 17:16
 */
@RestControllerAdvice
@RequestMapping
public class AfeExceptionHandlerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(AfeExceptionHandlerAdvice.class);
    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    /**
     * Handling known unhandled unchecked exceptions
     */
    @ExceptionHandler(BaseRuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse baseRunTimeExcHandler(BaseRuntimeException e) {
        LOG.warn("Exception handled by baseRunTimeExcHandler. Cause: " + e, e);
        ErrorResponse response = new ErrorResponse(e.getCode(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        LOG.info("<- baseRunTimeExcHandler. " + response);
        return response;
    }

    /**
     * Handling known unhandled exceptions
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse baseExcHandler(BaseException e) {
        LOG.warn("Exception handled by baseExcHandler. Cause: " + e, e);
        ErrorResponse response = new ErrorResponse(e.getCode(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        LOG.info("<- baseExcHandler. " + response);
        return response;
    }

    /**
     * Handling unknown unhandled exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse unknownExcHandler(Exception e) {
        LOG.warn("Exception handled by unknownExcHandler. Cause: " + e, e);
        ErrorResponse response = new ErrorResponse(ErrorCode.UNKNOWN_ERROR, e + "", HttpStatus.INTERNAL_SERVER_ERROR);
        LOG.info("<- unknownExcHandler. " + response);
        return response;
    }

    /**
     * Handling validation unhandled exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse valExcHandler(MethodArgumentNotValidException e) {
        LOG.warn("Exception handled by valExcHandler. Cause: " + e, e);
        ErrorResponse response = new ErrorResponse(ErrorCode.VALIDATION_ERROR, e.getMessage() + "", HttpStatus.INTERNAL_SERVER_ERROR);
        LOG.info("<- valExcHandler. " + response);
        return response;
    }

    /**
     * Handling access denied unhandled exceptions
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse accessDeniedExcHandler(AccessDeniedException e, HttpServletResponse httpResponse) {
        LOG.warn("Exception handled by accessDeniedExcHandler. Cause: " + e, e);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String code;
        HttpStatus status;
        if (authenticationTrustResolver.isAnonymous(authentication) || authenticationTrustResolver.isRememberMe(authentication)) {
            code = ErrorCode.UNAUTHORIZED;
            status = HttpStatus.UNAUTHORIZED;
        } else {
            code = ErrorCode.ACCESS_DENIED;
            status = HttpStatus.FORBIDDEN;
        }
        httpResponse.setStatus(status.value());
        ErrorResponse response = new ErrorResponse(code, e.getMessage() + "", status);
        LOG.info("<- accessDeniedExcHandler. " + response);
        return response;
    }

    /**
     * Handling bad request unhandled exceptions
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestExcHandler(HttpRequestMethodNotSupportedException e) {
        LOG.warn("Exception handled by badRequestExcHandler. Cause: " + e, e);
        ErrorResponse response = new ErrorResponse(ErrorCode.METHOD_DONT_SUPPORTED, e.getMessage() + "", HttpStatus.BAD_REQUEST);
        LOG.info("<- badRequestExcHandler. " + response);
        return response;
    }

    /**
     * Handling known unhandled not found exception
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundExcHandler(ObjectNotFoundException e) {
        LOG.warn("Exception handled by notFoundExcHandler. Cause: " + e, e);
        ErrorResponse response = new ErrorResponse(e.getCode(), e.getMessage(), HttpStatus.NOT_FOUND);
        LOG.info("<- notFoundExcHandler. " + response);
        return response;
    }

}
