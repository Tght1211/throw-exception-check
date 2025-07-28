package com.example.service;

import com.example.error.CommonErrorCode;
import com.example.error.UserErrorCode;
import com.example.exception.BizException;
import com.example.util.BizAssert;
import com.example.util.ExceptionUtils;

/**
 * 修复后的规范异常抛出示例
 * 展示如何将不规范的异常抛出改为规范的写法
 */
public class FixedServiceExample {
    
    private Object faceRepository;
    private Object orgId;
    private Object userId;
    
    // 修复示例1: throw new 语句添加错误码
    public void fixedThrowNewExample() {
        // 不规范的写法:
        // throw new RuntimeException("导出任务异常");
        
        // 规范的写法:
        ExceptionUtils.throwException(FixErrorCode.FIX_ERROR_1003, FixErrorCode.FIX_ERROR_1003.getMessage());
    }
    
    // 修复示例2: BizAssert 添加错误码
    public void fixedBizAssertExample() {
        // 不规范的写法:
        // BizAssert.notNull(faceRepository, "人脸库尚未初始化,正在执行初始化人脸库...请稍后再试");
        
        // 规范的写法:
        BizAssert.notNull(faceRepository, FixErrorCode.FIX_ERROR_1004, FixErrorCode.FIX_ERROR_1004.getMessage());
    }
    
    // 修复示例3: BizAssert 格式化字符串使用正确的format方法
    public void fixedFormatExample() {
        String personName = "张三";
        
        // 不规范的写法:
        // BizAssert.isTrue(false, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, "人脸与%s相似");
        
        // 规范的写法 (假设存在WithFormat方法):
        // BizAssert.isTrueWithFormat(false, FixErrorCode.FACE_SIMILAR, FixErrorCode.FACE_SIMILAR.getMessage(), personName);
    }
    
    // 修复示例4: ExceptionUtils 格式化字符串使用正确的format方法
    public void fixedExceptionUtilsFormatExample() {
        String userName = "张三";
        
        // 不规范的写法:
        // ExceptionUtils.throwException("用户%s不存在", "张三");
        
        // 规范的写法 (假设存在WithFormat方法):
        // ExceptionUtils.throwExceptionWithFormat(FixErrorCode.USER_NOT_FOUND, FixErrorCode.USER_NOT_FOUND.getMessage(), userName);
    }
    
    // 修复示例5: ExceptionUtils 添加错误码
    public void fixedExceptionUtilsExample() {
        // 不规范的写法:
        // ExceptionUtils.throwException("参数验证失败");
        
        // 规范的写法:
        ExceptionUtils.throwException(FixErrorCode.PARAM_VALIDATION_FAILED, FixErrorCode.PARAM_VALIDATION_FAILED.getMessage());
    }
    
    // 规范的用法示例 - 已经符合规范的代码
    public void correctUsageExample() {
        // 参数验证 - 应该被忽略的规范用法
        BizAssert.notNull(orgId, "orgId must not be null");
        BizAssert.isTrue(orgId != null, "orgId is valid");
        
        // 规范的错误码使用
        BizAssert.notNull(userId, UserErrorCode.USER_NOT_FOUND, UserErrorCode.USER_NOT_FOUND.getMessage());
        ExceptionUtils.throwException(CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
}
