package com.example.service;

import com.example.util.BizAssert;
import com.example.util.ExceptionUtils;

public class SpecialCaseTest {
    
    public void testSpecialCases() {
        Object orgId = null;
        Object email = null;
        Object username = null;
        
        // === 用户特别提到的应该被忽略的情况 ===
        BizAssert.notNull(orgId, "orgId is null");
        
        // === 其他应该被忽略的英文参数验证 ===
        BizAssert.notNull(email, "email is required");
        BizAssert.notNull(username, "username cannot be null");
        BizAssert.isTrue(orgId != null, "orgId must not be null");
        BizAssert.notBlank(username.toString(), "username must not be blank");
        BizAssert.isTrue(email != null, "email is valid");
        BizAssert.isTrue(orgId != null, "orgId is not valid");
        BizAssert.isTrue(email != null, "email is invalid");
        BizAssert.isTrue(username != null, "username format is invalid");
        
        // === 这些不应该被忽略 - 不规范的异常抛出 ===
        BizAssert.notNull(orgId, "组织ID不能为空");
        BizAssert.notNull(email, "邮箱地址格式错误");
        BizAssert.isTrue(username != null, "用户名验证失败");
        
        // 没有错误码的throw语句
        if (orgId == null) {
            throw new RuntimeException("组织ID不能为空");
        }
        
        // ExceptionUtils魔法值
        ExceptionUtils.throwException("系统错误");
        
        // 带格式化的不规范用法
        BizAssert.isTrue(false, "用户%s不存在", "张三");
    }
}
