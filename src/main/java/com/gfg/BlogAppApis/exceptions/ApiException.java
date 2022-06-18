package com.gfg.BlogAppApis.exceptions;

public class ApiException extends RuntimeException{
    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }
}
