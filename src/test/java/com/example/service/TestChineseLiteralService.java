package com.example.service;

/**
 * 测试中文字符串字面量的不规范异常抛出
 * 用于测试BizAssert.notNull(pageNumber, BizErrorCode.PAGE_NUMBER_NULL, "pageNumber必须有值")这种模式
 */
public class TestChineseLiteralService {
    
    public void testPageNumberValidation(Integer pageNumber) {
        BizAssert.notNull(pageNumber, BizErrorCode.PAGE_NUMBER_NULL, "pageNumber必须有值");
        
        BizAssert.isTrue(pageNumber > 0, BizErrorCode.PAGE_NUMBER_INVALID, "页码必须大于0");
        
        BizAssert.notNull(pageNumber, BizErrorCode.PAGE_NUMBER_NULL, BizErrorCode.PAGE_NUMBER_NULL.getMessage());
        
        BizAssert.isTrue(pageNumber > 0, BizErrorCode.PAGE_NUMBER_INVALID, BizErrorCode.PAGE_NUMBER_INVALID.getMessage());
    }
    
    public void testOtherValidations(String name, String email) {
        BizAssert.notBlank(name, BizErrorCode.USER_NAME_EMPTY, "用户名不能为空");
        
        BizAssert.isTrue(email.contains("@"), BizErrorCode.EMAIL_FORMAT_INVALID, "邮箱格式不正确");
        
        BizAssert.notNull(name, BizErrorCode.USER_NAME_EMPTY);
        
        BizAssert.notBlank(name, BizErrorCode.USER_NAME_EMPTY, BizErrorCode.USER_NAME_EMPTY.getMessage());
    }
}
