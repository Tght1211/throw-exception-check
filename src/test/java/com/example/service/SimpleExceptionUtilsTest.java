package com.example.service;

public class SimpleExceptionUtilsTest {
    
    public void test() {
        ExceptionUtils.throwException(OrgErrorCode.AREA_WRONG, "不存在省份数据");
    }
}
