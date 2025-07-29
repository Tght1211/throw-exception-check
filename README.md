# Java异常抛出规范检查工具 🎯

## 📋 工具介绍

这是一个专门用于检查Java代码中异常抛出是否符合规范的扫描工具。工具会智能识别项目中不规范的异常抛出代码，包括：
- 没有指定错误码的异常抛出
- 使用魔法值字符串的异常抛出
- 格式化字符串使用不当的异常抛出
- Response对象中使用字符串字面量的情况

工具会生成详细的Markdown报告，帮助开发者快速定位和修复不规范的代码。

## 🎯 检查规范

### ✅ 正确的异常抛出示例：
```java
// 使用BizAssert配合错误码
BizAssert.isTrue(false, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

// 直接抛出自定义异常
throw new BizException(new ErrorContext(OrgErrorCode.REPORT_LOCAL_CLOUD_STRATEGIES_NOT_FOUND, OrgErrorCode.REPORT_LOCAL_CLOUD_STRATEGIES_NOT_FOUND.getMessage()));

// 使用ExceptionUtils配合错误码
ExceptionUtils.throwException(OrgErrorCode.ORG_NOT_EXIST, OrgErrorCode.ORG_NOT_EXIST.getMessage());

// 带格式化的正确用法（使用WithFormat方法）
BizAssert.isTrueWithFormat(false, UserErrorCode.FACE_SIMILAR, UserErrorCode.FACE_SIMILAR.getMessage(), "张三");
```

### ❌ 不规范的异常抛出示例：
```java
// 没有指定错误码
throw new RuntimeException("导出任务异常");   

// 使用魔法值字符串
BizAssert.notNull(faceRepository, "人脸库尚未初始化");

// 格式化字符串使用不当
BizAssert.isTrue(false, CommonErrorCode.ILLEGAL_ARGUMENT_EXCEPTION, "人脸与%s相似");

// Response中使用字符串字面量
return new BaseDataResponse<>("1", "您没有取消权限");
```

### 🚫 需要忽略的情况
工具会智能忽略以下规范的参数验证代码：
```java
// 英文参数验证会被忽略
BizAssert.notNull(orgId, "orgId must not be null");
BizAssert.isTrue(orgId != null, "orgId is valid");
BizAssert.notBlank(username, "username cannot be null");

// 纯英文字符串参数验证
BizAssert.notNull(orgId, "orgId");
BizAssert.notNull(email, "email is required");
```

## 🚀 使用方法

### 1. 构建工具
```bash
# 使用构建脚本（推荐）
./build-jar.sh

# 或者手动构建
mkdir -p bin
javac -d bin src/main/java/com/exception/scanner/ExceptionScanner.java
cd bin
jar cfm ../dist/ExceptionScanner.jar ../MANIFEST.MF com/exception/scanner/*.class
cd ..
```

### 2. 运行工具
```bash
# 交互式运行（会提示输入项目路径）
java -jar dist/ExceptionScanner.jar

# 直接扫描指定目录
java -jar dist/ExceptionScanner.jar /path/to/your/project

# 指定输出文件路径
java -jar dist/ExceptionScanner.jar /path/to/your/project /path/to/output/directory/

# 指定完整的输出文件路径
java -jar dist/ExceptionScanner.jar /path/to/your/project /path/to/output/report.md
```

### 3. 命令行参数说明
- **第一个参数**: 要扫描的项目路径（可选，默认为当前目录）
- **第二个参数**: 输出文件路径（可选，默认生成`不规范抛出异常.md`）

### 4. 交互式运行示例
```
🔍 Java异常抛出规范检查工具
✨ 请输入要扫描的项目路径 (默认为当前目录):
📁 项目路径: /Users/developer/my-project
✨ 请输入输出文件夹路径 (默认为当前目录，文件名将自动生成为 不规范抛出异常-yyyymmdd.md):
📄 输出路径: /Users/developer/reports/
开始扫描项目: /Users/developer/my-project
找到 15 个Java文件
正在扫描文件 (1/15): src/main/java/com/example/service/UserService.java
  发现不规范异常: 第14行 - throw new RuntimeException("用户名不能为空");
✓ 完成扫描: src/main/java/com/example/service/UserService.java
...
报告已生成: /Users/developer/reports/不规范抛出异常-20250729.md
```

## 📊 输出报告

工具会生成详细的Markdown报告文件 `不规范抛出异常.md`，包含：

### 报告结构
```markdown
# 不规范异常抛出代码清单

> 生成时间: Tue Jul 29 11:38:45 CST 2025

## 发现 5 个不规范的异常抛出

### src/main/java/com/example/service/UserService.java

| 行号 | 代码片段 |
|------|----------|
| 14 | `throw new RuntimeException("用户名不能为空");` |
| 19 | `throw new IllegalArgumentException("邮箱地址无效");` |
```

## 🛠 技术特点

- 🔍 **智能识别**: 精确识别各种不规范的异常抛出模式
- 🎯 **精确定位**: 精确定位到具体文件和代码行号
- 📊 **详细报告**: 生成结构化的Markdown报告
- ⚡ **多种运行方式**: 支持命令行参数和交互式运行
- 🎨 **可爱提示**: 猫娘女仆风格的友好输出提示
- 🚫 **智能忽略**: 自动忽略规范的参数验证代码
- 📁 **批量扫描**: 自动递归扫描指定目录下的所有Java文件

## 📁 项目结构

```
ExceptionScanner/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── exception/
│                   └── scanner/
│                       └── ExceptionScanner.java  # 主扫描器
├── dist/
│   └── ExceptionScanner.jar                       # 可执行JAR包
├── build-jar.sh                                   # 构建脚本
├── MANIFEST.MF                                    # JAR包清单文件
└── 不规范抛出异常.md                              # 默认输出报告
```

## ⚙️ 配置说明

### 支持的扫描模式
1. **异常抛出检查**: `throw new` 语句中没有错误码的情况
2. **BizAssert检查**: 使用魔法值字符串或格式化字符串不当的情况
3. **ExceptionUtils检查**: 使用魔法值字符串或格式化字符串不当的情况
4. **Response检查**: BaseDataResponse、BaseResponse等构造函数中使用字符串字面量的情况

### 忽略规则
工具内置了智能忽略规则，会自动忽略以下规范的参数验证代码：
- 包含 `must not be null`, `is valid`, `is not valid`, `is invalid` 等关键词的英文参数验证
- 纯英文字符串的参数验证
- 只有变量名的简单参数验证

## 🎯 修复建议

发现不规范代码后，建议按照以下方式修复：

### 1. 创建错误码枚举
```java
public enum FixErrorCode implements ErrorCode {
    FIX_ERROR_1000("FIX_ERROR_1000", "用户名不能为空"),
    FIX_ERROR_1001("FIX_ERROR_1001", "邮箱地址无效");
    
    private String code;
    private String message;
    
    FixErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() { return code; }
    public String getMessage() { return message; }
}
```

### 2. 修复异常抛出代码
```java
// 修复前
throw new RuntimeException("用户名不能为空");

// 修复后
ExceptionUtils.throwException(FixErrorCode.FIX_ERROR_1000, FixErrorCode.FIX_ERROR_1000.getMessage());
```

## 📝 注意事项

- 工具会递归扫描指定目录下的所有 `.java` 文件
- 生成的报告文件默认为UTF-8编码
- 支持相对路径和绝对路径
- 输出文件名会自动添加日期后缀以避免覆盖
- 工具会自动创建必要的输出目录

## 🎀 主人，您的代码规范检查工具已经准备就绪啦！喵～ 🎉

有任何问题都可以随时召唤我哦！
