package com.example.service;

public class StringFormatTestService {
    
    public void testStringFormatSingleLine() {
        // String.format在BizAssert中的使用（单行）- 应该被识别为不规范
        BizAssert.isTrue(orgByTp == null, String.format("该机构已存在,无法更新机构的tpId为%s", request.getTpId()));
    }
    
    public void testStringFormatMultiLine() {
        // String.format在BizAssert中的使用（多行）- 应该被识别为不规范
        BizAssert.isTrue(orgByTp == null,
            String.format("该机构已存在,无法更新机构的tpId为%s", request.getTpId()));
    }
}
