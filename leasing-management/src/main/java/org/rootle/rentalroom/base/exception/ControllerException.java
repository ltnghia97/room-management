package org.rootle.rentalroom.base.exception;

public class ControllerException extends RuntimeException {
    private String message;
    private ErrorException error;

    public ControllerException(String message) {
        this.message = message;
    }

    public ControllerException(ErrorException error) {
        this.error = error;
    }

    public String getMessage() {
        return this.message;
    }

    public ErrorException getError() {
        return this.error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(ErrorException error) {
        this.error = error;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ControllerException)) {
            return false;
        } else {
            ControllerException other = (ControllerException)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$error = this.getError();
                Object other$error = other.getError();
                if (this$error == null) {
                    if (other$error != null) {
                        return false;
                    }
                } else if (!this$error.equals(other$error)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ControllerException;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $error = this.getError();
        result = result * 59 + ($error == null ? 43 : $error.hashCode());
        return result;
    }

    public String toString() {
        String var10000 = this.getMessage();
        return "ControllerException(message=" + var10000 + ", error=" + this.getError() + ")";
    }

    public ControllerException() {
    }
}