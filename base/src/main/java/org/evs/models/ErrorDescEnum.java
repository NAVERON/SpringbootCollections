package org.evs.models;

import org.springframework.http.HttpStatus;

public enum ErrorDescEnum {

    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "", "default unknown error"),
    RESPONSE_NOTNULL_WARN(HttpStatus.INTERNAL_SERVER_ERROR, "", "response must not null");

    private final HttpStatus httpStatus;
    private final String businessStatus;
    private final String businessMessage;


    ErrorDescEnum(HttpStatus httpStatus, String businessStatus, String businessMessage) {
        this.httpStatus = httpStatus;
        this.businessStatus = businessStatus;
        this.businessMessage = businessMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public String getBusinessMessage() {
        return businessMessage;
    }

}
