package com.ra.project5.exception;

public class BaseException extends RuntimeException {
    private ErrorMessage errorMessage;
    private ErrorMessageLoader errorMessageLoader = new ErrorMessageLoader();


    public BaseException(String errorCode) {
        super(errorCode);
        errorMessage = new ErrorMessage();
        errorMessage.setCode(errorCode);
        errorMessage.setMessage(errorMessageLoader.getMessage(errorCode));
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }
}
