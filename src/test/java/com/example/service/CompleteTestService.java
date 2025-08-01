package com.example.service;

public class CompleteTestService {
    
    public void testUserCase() {
        // 用户提到的具体案例 - 应该被识别为不规范
        BizAssert.isTrue(load.getSource() != OrgSourceType.DING.getValue(),"当前机构已经是钉钉机构无需操作上钉");
    }
    
    public void testStringFormat() {
        // String.format在BizAssert中的使用 - 应该被识别为不规范
        BizAssert.isTrue(orgByTp == null,
            String.format("该机构已存在,无法更新机构的tpId为%s", request.getTpId()));
    }
    
    public void testChineseLiteral() {
        // 只有中文字符串字面量的情况 - 应该被识别为不规范
        BizAssert.isTrue(OrgSourceType.isIncluded(request.getSource()), "非法的机构source");
    }
    
    public void testSimpleChinese() {
        // 简单的中文字符串字面量 - 应该被识别为不规范
        BizAssert.notNull(someObject, "参数不能为空");
    }
    
    public void testCorrectCase() {
        // 正确的案例 - 不应该被识别为不规范
        BizAssert.isTrue(false, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }
}
