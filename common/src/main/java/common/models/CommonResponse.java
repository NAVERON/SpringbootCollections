package common.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class CommonResponse<T> {

    private static final Logger log = LoggerFactory.getLogger(CommonResponse.class);

    private String businessCode;
    private String message;
    private T object;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
            "businessCode='" + businessCode + '\'' +
            ", message='" + message + '\'' +
            ", data=" + object +
            '}';
    }

    public static CommonResponse<Object> success(Object object) {
        CommonResponse<Object> response = new CommonResponse<>();
        response.setBusinessCode(HttpStatus.OK.name());
        response.setMessage("BUSINESS SUCCESSFUL");
        response.setObject(object);

        return response;
    }
}
