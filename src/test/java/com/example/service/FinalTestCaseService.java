package com.example.service;

public class FinalTestCaseService {
    
    public void testCase1() {
        // 用户提到的具体案例 - 应该被识别为不规范
        BizAssert.isTrue(load.getSource() != OrgSourceType.DING.getValue(),"当前机构已经是钉钉机构无需操作上钉");
    }
    
    public void testCase2() {
        // 另一个复杂条件案例 - 应该被识别为不规范
        BizAssert.notNull(someObject, "参数不能为空");
    }
    
    public void testCase3() {
        // 正确的案例 - 不应该被识别为不规范
        BizAssert.isTrue(false, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
}
