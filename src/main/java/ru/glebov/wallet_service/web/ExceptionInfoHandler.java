package ru.glebov.wallet_service.web;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.glebov.wallet_service.util.ValidationUtil;
import ru.glebov.wallet_service.util.exception.ErrorInfo;
import ru.glebov.wallet_service.util.exception.ErrorType;
import ru.glebov.wallet_service.util.exception.IllegalRequestDataException;
import ru.glebov.wallet_service.util.exception.NotFoundException;

import java.util.Arrays;

import static ru.glebov.wallet_service.util.exception.ErrorType.*;

@RestControllerAdvice()
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BindException.class)
    public ErrorInfo validationError(HttpServletRequest request, BindException e) {
        return logAndGetValidationErrorInfo(request, e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo notFoundError(HttpServletRequest request, NotFoundException e) {
        return logAndGetErrorInfo(request, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo validationError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo internalError(HttpServletRequest request, Exception e) {
        return logAndGetErrorInfo(request, e, true, APP_ERROR);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest request, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error("{} at request {}", errorType, request.getRequestURL(), rootCause);
        } else {
            warmLog(request, errorType, rootCause.toString());
        }
        return new ErrorInfo(request.getRequestURL(), errorType, rootCause.toString());
    }

    private static ErrorInfo logAndGetValidationErrorInfo(HttpServletRequest request, BindingResult result) {
        String[] details = result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .toArray(String[]::new);
        warmLog(request, VALIDATION_ERROR, details);

        return new ErrorInfo(request.getRequestURL(), VALIDATION_ERROR, details);
    }

    private static void warmLog(HttpServletRequest request, ErrorType errorType, String... details) {
        log.warn("{} at request  {}: {}", errorType, request.getRequestURL(), Arrays.toString(details));
    }
}