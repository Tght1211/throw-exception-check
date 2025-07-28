package com.example.service;

import com.example.error.CommonErrorCode;
import com.example.error.UserErrorCode;
import com.example.exception.BizException;
import com.example.util.BizAssert;
import com.example.util.ExceptionUtils;

public class UserService {
    
    public void createUser(String username, String email) {
        // 不规范的异常抛出 - 没有错误码
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        
        // 不规范的异常抛出 - 魔法值字符串
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("邮箱地址无效");
        }
        
        // 规范的异常抛出
        BizAssert.notNull(username, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        
        // 不规范的BizAssert - 使用了格式化字符串但没有用正确的format方法
        BizAssert.isTrue(username.length() > 3, UserErrorCode.USERNAME_TOO_SHORT, "用户名长度不能少于3个字符");
        
        // 规范的BizAssert - 参数验证，应该被忽略
        BizAssert.notNull(email, "email must not be null");
        
        // 规范的BizAssert - 参数验证，应该被忽略
        BizAssert.isTrue(email.contains("@"), "email format is invalid");
        
        // 不规范的ExceptionUtils使用
        if (username.equals("admin")) {
            ExceptionUtils.throwException("不允许使用admin作为用户名");
        }
        
        // 规范的ExceptionUtils使用
        ExceptionUtils.throwException(UserErrorCode.USER_ALREADY_EXISTS, UserErrorCode.USER_ALREADY_EXISTS.getMessage());
        
        System.out.println("用户创建成功: " + username);
    }
    
    public void deleteUser(Long userId) {
        // 不规范的异常抛出
        if (userId == null) {
            throw new BizException("用户ID不能为空");
        }
        
        // 规范的异常抛出
        BizAssert.notNull(userId, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        
        System.out.println("用户删除成功: " + userId);
    }
}
