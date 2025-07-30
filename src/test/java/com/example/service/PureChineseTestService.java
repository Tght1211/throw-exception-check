package com.example.service;

public class PureChineseTestService {
    
    public void testChineseLiteralPattern() {
        // 这种应该被识别为不规范：BizAssert中只有中文字符串字面量
        BizAssert.isTrue(someCondition, "非法的机构source");
    }
    
    public void testAnotherChinesePattern() {
        // 另一个中文字符串字面量测试
        BizAssert.notNull(someObject, "参数不能为空");
    }
}
