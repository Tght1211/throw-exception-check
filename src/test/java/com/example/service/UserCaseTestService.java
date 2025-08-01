package com.example.service;

public class UserCaseTestService {
    
    public void testUserCase() {
        // 用户提到的具体案例 - 应该被识别为不规范
        BizAssert.isTrue(load.getSource() != OrgSourceType.DING.getValue(),"当前机构已经是钉钉机构无需操作上钉");
    }
}
