package com.example.service;

import com.example.response.BaseDataResponse;
import com.example.response.BaseResponse;

public class TestResponsePatterns {
    
    public BaseDataResponse<String> testMethod1() {
        // 这个应该被识别为不规范 - 使用了字符串字面量
        return new BaseDataResponse<>("1", "您没有取消权限");
    }
    
    public BaseResponse<String> testMethod2() {
        // 这个也应该被识别为不规范 - 使用了字符串字面量
        return new BaseResponse<>("1", "您没有取消权限");
    }
    
    public BaseDataResponse<String> testMethod3() {
        // 这个应该是规范的 - 使用了错误码（模拟）
        return new BaseDataResponse<>("1", "ORG_ID_NULL");
    }
    
    public BaseResponse<String> testMethod4() {
        // 这个应该是规范的 - 使用了错误码（模拟）
        return new BaseResponse<>("1", "ILLEGAL_ARGUMENT_EXCEPTION");
    }
}
