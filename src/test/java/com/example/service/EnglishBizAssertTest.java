package com.example.service;

import com.example.util.BizAssert;
import com.example.util.ExceptionUtils;

public class EnglishBizAssertTest {
    
    public void testEnglishBizAssertPatterns() {
        Object orgId = null;
        Object email = null;
        Object username = null;
        Object dept = null;
        Object request = null;
        Object mainOrgId = null;
        Object spaceId = null;
        Object faceData = null;
        Object bizIdEmpty = true;
        Object idEmpty = true;
        Object exceedOrgId = true;
        Object isNotEmptyFaceData = false;
        
        // === 这些都应该被忽略 - 纯英文BizAssert消息 ===
        BizAssert.isTrue(isNotEmptyFaceData, "face data must not be null");
        BizAssert.isTrue(!bizIdEmpty, "tag id list must not be null");
        BizAssert.notNull(orgId, "orgId must not");
        BizAssert.isTrue(!bizIdEmpty, "tag relation data must not be null");
        BizAssert.isTrue(!idEmpty, "tag_relation_id must not be null");
        BizAssert.isTrue(!exceedOrgId, "not support more than 1 orgId");
        BizAssert.notNull(dept, "Root sector is missing");
        BizAssert.notNull(request, "batchCreateDept request must not be null");
        BizAssert.notNull(orgId, "org id must not be null");
        BizAssert.notNull(spaceId, "space id must not be null");
        BizAssert.notNull(mainOrgId, "org id must not be null");
        BizAssert.notNull(org, "org not exist");
        BizAssert.notNull(org, "org info must not be null");
        BizAssert.isTrue(false, "Invalid currentPath");
        
        // === 这些不应该被忽略 - 中文错误消息（不规范）===
        BizAssert.notNull(orgId, "组织ID不能为空");
        BizAssert.notNull(email, "邮箱地址无效");
        BizAssert.isTrue(username != null, "用户名长度不足");
        
        // === 这些不应该被忽略 - 没有错误码的throw语句 ===
        if (orgId == null) {
            throw new RuntimeException("组织ID不能为空");
        }
        
        // === 这些不应该被忽略 - ExceptionUtils魔法值 ===
        ExceptionUtils.throwException("系统错误");
    }
}
