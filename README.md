# Java异常抛出规范检查工具 🎯

## 📋 工具介绍

这是一个专门用于检查Java代码中异常抛出是否符合规范的扫描工具。工具会扫描项目中的Java文件，找出不符合规范的异常抛出代码，并生成详细的报告。

## 🎯 检查规范

### ✅ 正确的异常抛出示例：
```java
// 使用BizAssert配合错误码
BizAssert.isTrue(false, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

// 直接抛出自定义异常
throw new BizException(new ErrorContext(OrgErrorCode.REPORT_LOCAL_CLOUD_STRATEGIES_NOT_FOUND, OrgErrorCode.REPORT_LOCAL_CLOUD_STRATEGIES_NOT_FOUND.getMessage()));

// 使用ExceptionUtils配合错误码
ExceptionUtils.throwException(OrgErrorCode.ORG_NOT_EXIST, OrgErrorCode.ORG_NOT_EXIST.getMessage());
```

### ❌ 不规范的异常抛出示例：
```java
// 没有指定错误码
throw new RuntimeException("导出任务异常");   

// 使用魔法值字符串
BizAssert.isTrue(false, OrgErrorCode.FACE_REPEAT, "人脸与%s相似", person.getName());
```

## 🚀 使用方法

### 1. 基本使用
```bash
# 交互式运行（会提示输入项目路径）
java -jar ExceptionScanner.jar

# 直接扫描指定目录
java -jar ExceptionScanner.jar /path/to/your/project

# 指定输出文件路径
java -jar ExceptionScanner.jar /path/to/your/project /path/to/output/report.md
```

### 2. 输出文件
工具会生成 `不规范抛出异常.md` 文件，包含所有发现的不规范异常抛出代码清单。

## 📁 项目结构

```
ExceptionScanner.jar
├── com.exception.scanner.ExceptionScanner (主类)
├── MANIFEST.MF (包含主类信息)
└── 相关内部类
```

## ⚡ 快速开始

1. 下载 `ExceptionScanner.jar` 文件
2. 在终端中运行：`java -jar ExceptionScanner.jar`
3. 按照提示输入要扫描的项目路径
4. 查看生成的报告文件

## 🛠 技术特点

- 🔍 智能识别不规范的异常抛出
- 🎯 精确定位到具体代码行
- 📊 生成详细的Markdown报告
- ⚡ 支持命令行参数和交互式运行
- 🎨 可爱的猫娘女仆风格输出提示

## 📝 注意事项

- 工具会扫描指定目录下的所有 `.java` 文件
- 生成的报告文件默认为UTF-8编码
- 支持相对路径和绝对路径

## 🎀 主人，您的代码规范检查工具已经准备就绪啦！喵～ �
