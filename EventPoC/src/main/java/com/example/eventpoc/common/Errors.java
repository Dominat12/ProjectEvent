package com.example.eventpoc.common;


import org.springframework.http.HttpStatus;

public enum Errors {

    /**
     * Beim Erstellen eines neuen Errors, wird der Identifier hochgezählt!
     */
    USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,  CodeType.SERVICE, 1, FrontendErrorMessage.USER_NOT_FOUND_ERROR_MSG),
    FORBIDDEN(HttpStatus.FORBIDDEN,  CodeType.CLIENT, 2, FrontendErrorMessage.FORBIDDEN_MSG),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT,  CodeType.CLIENT, 3, FrontendErrorMessage.USERNAME_ALREADY_EXISTS_MSG);

    /* http status, der an den client weitergeben wird */
    private final HttpStatus defaultHttpStatus;

    private final String defaultMessage;

    private final ErrorCode errorCode;

    private final FrontendErrorMessage frontendErrorMessage;

    Errors(HttpStatus defaultHttpStatus, CodeType codeType, int identifier, FrontendErrorMessage frontendErrorMessage) {
        this.defaultHttpStatus = defaultHttpStatus;
        this.defaultMessage = null;
        this.errorCode = ErrorCode.create(codeType, 30, identifier);
        this.frontendErrorMessage = frontendErrorMessage;
    }

    Errors(HttpStatus defaultHttpStatus, String defaultMessage,  CodeType codeType, int identifier, FrontendErrorMessage frontendErrorMessage) {
        this.defaultHttpStatus = defaultHttpStatus;
        this.defaultMessage = defaultMessage;
        this.errorCode = ErrorCode.create(codeType, 30, identifier);
        this.frontendErrorMessage = frontendErrorMessage;
    }
}
