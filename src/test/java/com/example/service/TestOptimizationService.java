package com.example.service;

import com.example.error.CommonErrorCode;

public class TestOptimizationService {
    
    private Object orgByTp;
    private Request request = new Request();
    
    public void testStringFormatPattern() {
        // 这种应该被识别为不规范：String.format在BizAssert中的使用
        BizAssert.isTrue(orgByTp == null,
            String.format("该机构已存在,无法更新机构的tpId为%s", request.getTpId()));
    }
    
    public void testChineseLiteralPattern() {
        // 这种应该被识别为不规范：BizAssert中只有中文字符串字面量
        BizAssert.isTrue(OrgSourceType.isIncluded(request.getSource()), "非法的机构source");
    }
    
    public void testCorrectPattern() {
        // 这种是正确的，不应该被识别为不规范
        BizAssert.isTrue(false, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
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

// 模拟请求类
class Request {
    public String getTpId() {
        return "tpId";
    }
    
    public String getSource() {
        return "source";
    }
}

// 模拟orgByTp变量
class OrgByTp {
    // empty
}
