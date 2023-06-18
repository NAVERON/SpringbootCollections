package org.evs.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(BusinessException.class);

    // must has all params, no default value
    private HttpStatus httpStatus;
    private String businessStatus;
    private String businessMessage;

    /**
     *
     */
    public BusinessException() {
        super();

        this.httpStatus = ErrorDescEnum.DEFAULT_ERROR.getHttpStatus();
        this.businessStatus = ErrorDescEnum.DEFAULT_ERROR.getBusinessStatus();
        this.businessMessage = ErrorDescEnum.DEFAULT_ERROR.getBusinessMessage();
    }

    @Deprecated
    public BusinessException(String message) {
        super();

        this.httpStatus = ErrorDescEnum.DEFAULT_ERROR.getHttpStatus();
        this.businessStatus = ErrorDescEnum.DEFAULT_ERROR.getBusinessStatus();
        this.businessMessage = message;
    }

    public BusinessException(ErrorDescEnum errorDescEnum) {
        super();

        this.httpStatus = errorDescEnum.getHttpStatus();
        this.businessStatus = errorDescEnum.getBusinessStatus();
        this.businessMessage = errorDescEnum.getBusinessMessage();
    }

    // not recommended
    @Deprecated
    public BusinessException(HttpStatus httpStatus, String businessStatus, String businessMessage) {
        super();

        this.httpStatus = httpStatus;
        this.businessStatus = businessStatus;
        this.businessMessage = businessMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getBusinessMessage() {
        return businessMessage;
    }

    public void setBusinessMessage(String businessMessage) {
        this.businessMessage = businessMessage;
    }


}
