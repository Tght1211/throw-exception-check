package com.example.response;

public class BaseResponse<T> {
    private String code;
    private T data;
    
    public BaseResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }
    
    // getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
