package com.security.rest.api.handler.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * rest api exception handler
 * @author benson0217
 *
 */
@EnableWebMvc
@RestControllerAdvice
public class SecurityApiExceptionHandler {

    private final static String ERROR_OCCURRED = "error occurred";
    private final static String NO_HANDLER_FOUNF = "No handler found for : ";
    private final static String EXECUTE_SQL_FAILED = "Executing SQL failed : ";
    private final static String ACCESS_DENIED = "Role not match : ";
    private final static String METHOD_NOT_SUPPORT = " method is not supported for this request. Supported methods are : ";
    private final static String MISSING_REQUEST_PARA = " parameter is missing ";

    /**
     * handle Unexpectedly error
     * @param exception - the exception to handle.
     * @param request - request - WebRequest
     * @return ResponseEntity
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<SecurityApiDefaultErrorMessage> handleAll(Exception exception) {
        SecurityApiDefaultErrorMessage errorMessage = 
                new SecurityApiDefaultErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), ERROR_OCCURRED);
        return new ResponseEntity<SecurityApiDefaultErrorMessage>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    /**
     * handle servlet not handler found error
     * @param exception - the exception to handle.
     * @param request - request - WebRequest
     * @return ResponseEntity
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<SecurityApiDefaultErrorMessage> handleNoHandlerFound(NoHandlerFoundException exception) {
        StringBuffer error = new StringBuffer(NO_HANDLER_FOUNF).append(exception.getHttpMethod())
                .append("[").append(exception.getRequestURL()).append("]");
        SecurityApiDefaultErrorMessage errorMessage = 
                new SecurityApiDefaultErrorMessage(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(), error.toString());
        return new ResponseEntity<SecurityApiDefaultErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * handle JdbcTemplate error
     * @param exception - the exception to handle.
     * @param request - WebRequest
     * @return ResponseEntity
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<SecurityApiDefaultErrorMessage> handleDataAccess(DataAccessException exception) {
        StringBuffer error = new StringBuffer(EXECUTE_SQL_FAILED).append(exception.getLocalizedMessage());
        SecurityApiDefaultErrorMessage errorMessage = 
                new SecurityApiDefaultErrorMessage(HttpStatus.CONFLICT, exception.getClass().getSimpleName(), error.toString());
        return new ResponseEntity<SecurityApiDefaultErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    /**
     * handle AccessDenied error
     * @param exception - the exception to handle.
     * @param request - WebRequest
     * @return ResponseEntity
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<SecurityApiDefaultErrorMessage> handleAccessDenied(AccessDeniedException exception) {
        StringBuffer error = new StringBuffer(ACCESS_DENIED).append(exception.getLocalizedMessage());
        SecurityApiDefaultErrorMessage errorMessage = 
                new SecurityApiDefaultErrorMessage(HttpStatus.FORBIDDEN, exception.getLocalizedMessage(), error.toString());
        return new ResponseEntity<SecurityApiDefaultErrorMessage>(errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    /**
     * handle httpRequestMethodNotSupport error
     * @param exception
     * @param request
     * @return ResponseEntity
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<SecurityApiDefaultErrorMessage> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
        StringBuilder error = new StringBuilder(exception.getMethod());
        error.append(METHOD_NOT_SUPPORT);
        exception.getSupportedHttpMethods().forEach(method -> error.append(method + StringUtils.SPACE));
        SecurityApiDefaultErrorMessage errorMessage = new SecurityApiDefaultErrorMessage(HttpStatus.METHOD_NOT_ALLOWED,
                exception.getLocalizedMessage(), error.toString());
        return new ResponseEntity<SecurityApiDefaultErrorMessage>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }
    
    /**
     * handle missingServletRequestParameter error
     * @param exception
     * @param request
     * @return ResponseEntity
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<SecurityApiDefaultErrorMessage> handleMissingServletRequestParameter(MissingServletRequestParameterException exception) {
        String error = exception.getParameterName() + MISSING_REQUEST_PARA;
        SecurityApiDefaultErrorMessage errorMessage = 
                new SecurityApiDefaultErrorMessage(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), error);
        return new ResponseEntity<SecurityApiDefaultErrorMessage>(errorMessage,  new HttpHeaders(), errorMessage.getStatus());
    }

    /**
     * 
     * handle missingServletRequestParameter error
     * @param exception
     * @param request
     * @return ResponseEntity
     */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<SecurityApiDefaultErrorMessage> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        String error = exception.getName() + " should be of type " + exception.getRequiredType().getName();
        SecurityApiDefaultErrorMessage errorMessage = 
                new SecurityApiDefaultErrorMessage(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(),error);
        return new ResponseEntity<SecurityApiDefaultErrorMessage>(errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

}
