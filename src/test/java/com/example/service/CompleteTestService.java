package com.example.service;

import com.example.error.CommonErrorCode;
import com.example.error.UserErrorCode;
import com.example.exception.BizException;
import com.example.util.BizAssert;
import com.example.util.ExceptionUtils;

public class CompleteTestService {
    
    private Object faceRepository;
    private Object orgId;
    
    // 测试各种不规范的异常抛出
    public void testAllViolations() {
        // 1. throw new 语句没有错误码 - 不规范
        throw new RuntimeException("导出任务异常");
    }
    
    public void testBizAssertViolations() {
        // 2. BizAssert 只有魔法值字符串，没有错误码 - 不规范
        BizAssert.notNull(faceRepository, "人脸库尚未初始化,正在执行初始化人脸库...请稍后再试");
        
        // 3. BizAssert 有错误码但使用了格式化字符串却没有用format方法 - 不规范
        String personName = "张三";
        BizAssert.isTrue(false, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, "人脸与%s相似");
        
        // 4. 另一个BizAssert魔法值字符串例子
        BizAssert.notNull(orgId, "组织ID不能为空");
    }
    
    public void testExceptionUtilsViolations() {
        // 5. ExceptionUtils 使用了格式化字符串但没有用正确的format方法 - 不规范
        ExceptionUtils.throwException("用户%s不存在", "张三");
        
        // 6. ExceptionUtils 魔法值字符串 - 不规范
        ExceptionUtils.throwException("参数验证失败");
    }
    
    // 测试应该被忽略的规范用法
    public void testIgnoredCases() {
        // 这些应该被忽略 - 参数验证
        BizAssert.notNull(orgId, "orgId must not be null");
        BizAssert.isTrue(orgId != null, "orgId is valid");
        BizAssert.notBlank(orgId.toString(), "orgId must not be blank");
        
        // 这些是规范的用法，不应该被标记
        BizAssert.notNull(orgId, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        ExceptionUtils.throwException(UserErrorCode.USER_NOT_FOUND, UserErrorCode.USER_NOT_FOUND.getMessage());
    }
    
    // 测试规范的格式化字符串用法（如果存在的话）
    public void testCorrectFormatUsage() {
        // 规范的格式化字符串用法应该是这样的（假设存在WithFormat方法）：
        // BizAssert.isTrueWithFormat(false, UserErrorCode.FACE_SIMILAR, UserErrorCode.FACE_SIMILAR.getMessage(), "张三");
        // ExceptionUtils.throwExceptionWithFormat(UserErrorCode.USER_NOT_FOUND, UserErrorCode.USER_NOT_FOUND.getMessage(), "张三");
    }
}
