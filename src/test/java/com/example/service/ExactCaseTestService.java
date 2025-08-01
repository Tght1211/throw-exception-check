package com.example.service;

public class ExactCaseTestService {
    
    public void testExactCase() {
        // 用户提到的具体案例
        BizAssert.isTrue(load.getSource() != OrgSourceType.DING.getValue(),"当前机构已经是钉钉机构无需操作上钉");
    }
    
    public void testSimpleCase() {
        // 简单案例用于对比
        BizAssert.isTrue(condition, "简单中文消息");
    }
}
