package com.example.service;

import com.example.util.BizAssert;

public class TestIgnorePattern {
    
    public void testIgnorePatterns() {
        Object orgId = null;
        Object email = null;
        Object username = null;
        
        // 这些应该被忽略 - 纯英文参数验证
        BizAssert.notNull(orgId, "orgId is null");
        BizAssert.notNull(email, "email is required");
        BizAssert.notNull(username, "username cannot be null");
        BizAssert.isTrue(orgId != null, "orgId must not be null");
        BizAssert.notBlank(username.toString(), "username is valid");
        
        // 这些不应该被忽略 - 中文错误消息（不规范）
        BizAssert.notNull(orgId, "组织ID不能为空");
        BizAssert.notNull(email, "邮箱地址无效");
        
        // 这些也不应该被忽略 - 没有错误码的throw语句
        if (orgId == null) {
            throw new RuntimeException("组织ID不能为空");
        }
    }
}
