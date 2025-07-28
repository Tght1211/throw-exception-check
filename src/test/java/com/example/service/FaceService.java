package com.example.service;

import com.example.error.CommonErrorCode;
import com.example.exception.BizException;
import com.example.util.BizAssert;
import com.example.util.ExceptionUtils;

public class FaceService {
    
    private Object faceRepository;
    
    public void initializeFaceRepository() {
        // 这是用户提到的不规范异常抛出 - 魔法值消息内容
        BizAssert.notNull(faceRepository, "人脸库尚未初始化,正在执行初始化人脸库...请稍后再试");
        
        // 规范的写法应该是：
        // BizAssert.notNull(faceRepository, FixErrorCode.FACE_REPOSITORY_NOT_INITIALIZED, FixErrorCode.FACE_REPOSITORY_NOT_INITIALIZED.getMessage());
        
        System.out.println("人脸库初始化完成");
    }
    
    public void processFaceData() {
        // 另一个不规范的例子 - 魔法值消息
        BizAssert.notNull(faceRepository, "人脸库未初始化");
        
        // 带格式化的不规范例子 - 有错误码但使用了格式化字符串却没有用format方法
        BizAssert.isTrue(false, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, "人脸与%s相似");
        
        // 另一个格式化字符串的例子
        ExceptionUtils.throwException("用户%s不存在", "张三");
        
        // 规范的带格式化的写法应该是：
        // BizAssert.isTrueWithFormat(false, FixErrorCode.FACE_SIMILAR, FixErrorCode.FACE_SIMILAR.getMessage(), personName);
        // ExceptionUtils.throwExceptionWithFormat(FixErrorCode.USER_NOT_FOUND, FixErrorCode.USER_NOT_FOUND.getMessage(), "张三");
    }
}
