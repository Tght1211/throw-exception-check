package com.example.service;

public class AllCasesTestService {
    
    public void testAllCases() {
        // 1. 用户提到的具体案例 - 复杂条件 + 中文字符串
        BizAssert.isTrue(load.getSource() != OrgSourceType.DING.getValue(),"当前机构已经是钉钉机构无需操作上钉");
        
        // 2. String.format在BizAssert中的使用
        BizAssert.isTrue(orgByTp == null,
            String.format("该机构已存在,无法更新机构的tpId为%s", request.getTpId()));
        
        // 3. BizAssert中只有中文字符串字面量
        BizAssert.isTrue(OrgSourceType.isIncluded(request.getSource()), "非法的机构source");
        
        // 4. 简单的中文字符串字面量
        BizAssert.notNull(someObject, "参数不能为空");
        
        // 5. ExceptionUtils.throwException有错误码但使用中文字符串字面量
        ExceptionUtils.throwException(OrgErrorCode.AREA_WRONG, "不存在省份数据");
        
        // 6. ExceptionUtils.throwException有错误码但使用格式化字符串
        ExceptionUtils.throwException(OrgErrorCode.AREA_WRONG, "不存在省份数据%s");
        
        // 7. 正确的案例 - 不应该被识别为不规范
        BizAssert.isTrue(false, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        
        // 8. 另一个正确的案例
        ExceptionUtils.throwException(OrgErrorCode.AREA_WRONG);
    }
}
