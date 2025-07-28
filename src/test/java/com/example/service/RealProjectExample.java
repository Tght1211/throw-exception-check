package com.example.service;

import com.example.error.CommonErrorCode;
import com.example.error.UserErrorCode;
import com.example.exception.BizException;
import com.example.util.BizAssert;
import com.example.util.ExceptionUtils;
import java.util.Collection;
import java.util.List;

public class RealProjectExample {
    
    private Object userRepository;
    private Object orgRepository;
    
    // 用户服务方法
    public void createUser(String username, String email, Long orgId) {
        // === 应该被忽略的参数验证 ===
        BizAssert.notNull(username, "username must not be null");
        BizAssert.notBlank(email, "email must not be blank");
        BizAssert.isTrue(orgId != null, "orgId is required");
        BizAssert.isTrue(username.length() > 3, "username is valid");
        BizAssert.notNull(orgId, "orgId cannot be null");
        
        // === 不规范的异常抛出 ===
        
        // 1. 没有错误码的throw语句
        if (userRepository == null) {
            throw new RuntimeException("用户仓库未初始化");
        }
        
        // 2. BizAssert魔法值中文消息
        BizAssert.notNull(userRepository, "用户服务不可用");
        
        // 3. ExceptionUtils魔法值
        ExceptionUtils.throwException("创建用户失败");
        
        // 4. 带格式化的不规范用法
        BizAssert.isTrue(false, "用户%s已存在", username);
        
        // === 规范的异常抛出（应该被忽略）===
        
        // 正确使用错误码
        BizAssert.notNull(orgId, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        ExceptionUtils.throwException(UserErrorCode.USER_ALREADY_EXISTS, UserErrorCode.USER_ALREADY_EXISTS.getMessage());
    }
    
    public void deleteUser(Long userId) {
        // === 应该被忽略的参数验证 ===
        BizAssert.notNull(userId, "userId must not be null");
        BizAssert.isTrue(userId > 0, "userId is valid");
        
        // === 不规范的异常抛出 ===
        if (userId < 0) {
            throw new BizException("用户ID无效");
        }
        
        BizAssert.notNull(userRepository, "用户不存在或已被删除");
    }
    
    public void updateUser(Long userId, String email) {
        // === 应该被忽略的参数验证 ===
        BizAssert.notNull(userId, "userId is required");
        BizAssert.notBlank(email, "email cannot be null");
        BizAssert.isTrue(email.contains("@"), "email format is invalid");
        
        // === 不规范的异常抛出 ===
        ExceptionUtils.throwException("邮箱格式不正确: %s", email);
    }
    
    // 部门管理方法
    public void createDepartment(String deptName, Long parentId) {
        // === 应该被忽略的参数验证 ===
        BizAssert.notNull(deptName, "deptName must not be null");
        BizAssert.isTrue(parentId != null, "parentId is required");
        BizAssert.notBlank(deptName, "deptName is valid");
        
        // === 不规范的异常抛出 ===
        BizAssert.notNull(orgRepository, "组织服务不可用");
        throw new RuntimeException("创建部门失败");
    }
    
    // 批量操作方法
    public void batchDeleteUsers(List<Long> userIds) {
        // === 应该被忽略的参数验证 ===
        BizAssert.notNull(userIds, "userIds must not be null");
        BizAssert.isTrue(!userIds.isEmpty(), "userIds must not be empty");
        
        // === 不规范的异常抛出 ===
        BizAssert.notNull(userRepository, "批量删除服务未启动");
        ExceptionUtils.throwException("批量删除用户时发生错误");
    }
}
