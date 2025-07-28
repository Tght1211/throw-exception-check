package com.example.service;

import com.example.util.BizAssert;
import com.example.util.ExceptionUtils;

public class CompleteIgnoreTest {
    
    public void testAllIgnorePatterns() {
        Object orgId = null;
        Object email = null;
        Object username = null;
        Object groupId = null;
        Object appId = null;
        
        // === 这些都应该被忽略 ===
        
        // 英文参数验证 - must not be null
        BizAssert.notNull(orgId, "orgId must not be null");
        BizAssert.isTrue(email != null, "email must not be null");
        BizAssert.notBlank(username.toString(), "username must not be blank");
        BizAssert.notEmpty(groupId.toString(), "groupId must not be null");
        
        // 英文参数验证 - is valid
        BizAssert.notNull(orgId, "orgId is valid");
        BizAssert.isTrue(appId != null, "appId is not valid");
        BizAssert.isTrue(email != null, "email is valid");
        
        // 英文参数验证 - is not valid
        BizAssert.isTrue(orgId != null, "orgId is not valid");
        
        // 文参数验证 - is invalid
        BizAssert.isTrue(email != null, "email is invalid");
        
        // 纯英文字符串
        BizAssert.notNull(orgId, "orgId");
        BizAssert.notNull(email, "email");
        
        // is null 模式
        BizAssert.notNull(orgId, "orgId is null");
        BizAssert.notNull(email, "email is null");
        
        // is required 模式
        BizAssert.notNull(orgId, "orgId is required");
        BizAssert.notNull(groupId, "groupId is required");
        
        // cannot be null 模式
        BizAssert.notNull(orgId, "orgId cannot be null");
        BizAssert.notNull(username, "username cannot be null");
        
        // === 这些不应该被忽略 ===
        
        // 中文错误消息（不规范）
        BizAssert.notNull(orgId, "组织ID不能为空");
        BizAssert.notNull(email, "邮箱地址格式错误");
        BizAssert.isTrue(username != null, "用户名长度不能少于3位");
        
        // 没有错误码的throw语句（不规范）
        if (orgId == null) {
            throw new RuntimeException("组织ID不能为空");
        }
        
        // ExceptionUtils魔法值（不规范）
        ExceptionUtils.throwException("参数验证失败");
        
        // 带格式化的错误消息（不规范）
        BizAssert.isTrue(false, "用户%s不存在", "张三");
    }
}
