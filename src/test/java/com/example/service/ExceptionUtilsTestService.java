package com.example.service;

public class ExceptionUtilsTestService {
    
    public void testExceptionUtilsChineseLiteral() {
        // ExceptionUtils.throwException有错误码但使用中文字符串字面量 - 应该被识别为不规范
        ExceptionUtils.throwException(OrgErrorCode.AREA_WRONG, "不存在省份数据");
    }
    
    public void testExceptionUtilsFormatString() {
        // ExceptionUtils.throwException有错误码但使用格式化字符串 - 应该被识别为不规范
        ExceptionUtils.throwException(OrgErrorCode.AREA_WRONG, "不存在省份数据%s");
    }
    
    public void testExceptionUtilsCorrect() {
        // ExceptionUtils.throwException正确的用法 - 不应该被识别为不规范
        ExceptionUtils.throwException(OrgErrorCode.AREA_WRONG);
    }
}
