package com.example.error;

public enum CommonErrorCode {
    ILLEGAL_ARGUMENT_EXCEPTION("ILLEGAL_ARGUMENT_EXCEPTION", "非法参数异常");
    
    private String code;
    private String message;
    
    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() { return code; }
    public String getMessage() { return message; }
}
