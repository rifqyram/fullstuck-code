package com.fullstuckcode.fullstuckcode.model.response;

public class WebResponse<T> {

    private final String status;
    private final Integer code;
    private final String message;
    private final T data;

    public WebResponse(String status, Integer code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
