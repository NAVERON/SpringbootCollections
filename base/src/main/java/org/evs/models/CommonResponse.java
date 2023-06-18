package org.evs.models;

import org.evs.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public class CommonResponse {
    private static final Logger log = LoggerFactory.getLogger(CommonResponse.class);

    // error must standard response
    private String businessStatus = "0000";
    private String businessMessage = "OK";
    private Object data = null;

    public CommonResponse() {
        this.businessStatus = ErrorDescEnum.DEFAULT_ERROR.getBusinessStatus();
        this.businessMessage = ErrorDescEnum.DEFAULT_ERROR.getBusinessMessage();
    }

    public CommonResponse(Object data) {
        if(ObjectUtils.isEmpty(data)) {
            throw new BusinessException(ErrorDescEnum.RESPONSE_NOTNULL_WARN);
        }

        this.data = data;
    }

    public CommonResponse(ErrorDescEnum errorDescEnum) {
        throw new BusinessException(errorDescEnum);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return GsonUtils.toJsonString(this);
    }


    // ============================================================ //
    // 自定义builder 快速创建返回对象

    public static CommonResponse buildSuccess(Object data) {
        return new CommonResponse(data);
    }

    public static CommonResponse buildError(ErrorDescEnum errorDescEnum) {
        return new CommonResponse(errorDescEnum);
    }

    public static CommonResponse buildOk() {
        return new CommonResponse("OK");
    }

}
