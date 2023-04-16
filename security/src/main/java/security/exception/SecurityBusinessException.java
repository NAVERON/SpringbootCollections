package security.exception;

public class SecurityBusinessException extends RuntimeException {

    private String exceptionCode = "security.0000";  // business error code
    private String message = "UnknownError";  // other desc
    private Throwable cause = new Throwable();

    public SecurityBusinessException() {
        super();
    }
    public SecurityBusinessException(String message) {
        super(message);

        this.message = message;
    }
    public SecurityBusinessException(String message, Throwable cause) {
        super(message, cause);

        this.message = message;
        this.cause = cause;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "SecurityBusinessException{" +
            "exceptionCode='" + exceptionCode + '\'' +
            ", message='" + message + '\'' +
            ", cause=" + cause +
            '}';
    }

}
