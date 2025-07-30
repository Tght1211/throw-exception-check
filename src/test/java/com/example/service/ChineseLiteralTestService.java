package com.example.service;

public class ChineseLiteralTestService {
    
    public void testChineseLiteralPattern() {
        // 这种应该被识别为不规范：BizAssert中只有中文字符串字面量
        BizAssert.isTrue(OrgSourceType.isIncluded(request.getSource()), "非法的机构source");
    }
    
    public void testStringFormatPattern() {
        // 这种应该被识别为不规范：String.format在BizAssert中的使用
        BizAssert.isTrue(orgByTp == null, String.format("该机构已存在,无法更新机构的tpId为%s", request.getTpId()));
    }
}

// 模拟BizAssert类
class BizAssert {
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
    
    public static void isTrue(boolean condition, Object errorCode, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

// 模拟OrgSourceType类
class OrgSourceType {
    public static boolean isIncluded(String source) {
        return true;
    }
}
