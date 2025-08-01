package com.example.service;

public class ComplexConditionTestService {
    
    public void testComplexCondition() {
        // 这种应该被识别为不规范：复杂条件表达式 + 中文字符串字面量
        BizAssert.isTrue(load.getSource() != OrgSourceType.DING.getValue(), "当前机构已经是钉钉机构无需操作上钉");
    }
    
    public void testSimpleCondition() {
        // 这种应该被识别为不规范：简单条件 + 中文字符串字面量
        BizAssert.isTrue(someCondition, "非法的机构source");
    }
}

// 模拟类
class BizAssert {
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

class OrgSourceType {
    public static OrgSourceType DING = new OrgSourceType();
    
    public static class DING {
        public static int getValue() {
            return 1;
        }
    }
    
    public int getValue() {
        return 1;
    }
}

class load {
    public static OrgSourceType getSource() {
        return new OrgSourceType();
    }
}
