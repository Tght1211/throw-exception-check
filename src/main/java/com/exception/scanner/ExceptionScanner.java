package com.exception.scanner;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

/**
 * Java异常抛出规范检查工具
 * 主人，这是您的异常扫描工具，喵～
 */
public class ExceptionScanner {
    
    private static String OUTPUT_FILE = "不规范抛出异常.md";
    private static final String ERROR_CODE_ENUM_FILE = "FixErrorCode.java";
    
    // 需要忽略的模式
    private static final List<Pattern> IGNORE_PATTERNS = Arrays.asList(
        // 忽略纯英文参数验证 - 包含must not be null, is valid, is not valid, is invalid等关键词
        Pattern.compile("BizAssert\\.(notNull|isTrue|notBlank|notEmpty)\\([^,]+,\\s*\"[a-zA-Z0-9_\\s]+\\s+(must not be null|is valid|is not valid|is invalid|must not|is null|is required|cannot be null|must not be blank|format is invalid)(\\s+.*)?\""),
        // 忽略纯英文字符串参数验证（只有英文字母、数字、下划线、空格）
        Pattern.compile("BizAssert\\.(notNull|isTrue|notBlank|notEmpty)\\([^,]+,\\s*\"[a-zA-Z0-9_\\s]+\"\\s*\\)"),
        // 忽略无参数或只有参数的BizAssert调用
        Pattern.compile("BizAssert\\.(notNull|isTrue|notBlank|notEmpty)\\([^,)]+\\s*\\)"),
        // 忽略特定的英文业务逻辑消息模式
        Pattern.compile("BizAssert\\.(notNull|isTrue|notBlank|notEmpty)\\([^,]+,\\s*\"(not support more than|Root sector is missing|org not exist|org info must not be null|Invalid currentPath|batchCreateDept request must not be null)[^\"]*\""),
        // 忽略常见的英文参数验证模式
        Pattern.compile("BizAssert\\.(notNull|isTrue|notBlank|notEmpty)\\([^,]+,\\s*\"[a-zA-Z0-9_]+\\s+(must not|must not be|is|is not|cannot be|should not)(\\s+.*)?\""),
        // 忽略只有变量名的简单参数验证
        Pattern.compile("BizAssert\\.(notNull|isTrue|notBlank|notEmpty)\\([^,]+,\\s*\"[a-zA-Z0-9_]+\"\\s*\\)"),
        // 忽略包含常见英文关键词的参数验证
        Pattern.compile("BizAssert\\.(notNull|isTrue|notBlank|notEmpty)\\([^,]+,\\s*\"[a-zA-Z0-9_\\s]*(must not be null|is valid|is not valid|is invalid|must not|is null|is required|cannot be null|must not be blank|format is invalid|should not be null)[a-zA-Z0-9_\\s]*\"")
    );
    
    // 正确的模式
    private static final List<Pattern> CORRECT_PATTERNS = Arrays.asList(
        Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty).*?\\w+ErrorCode\\.\\w+"),
        Pattern.compile("ExceptionUtils\\.throwException\\(\\w+ErrorCode\\.\\w+"),
        Pattern.compile("new BizException\\(new ErrorContext\\(\\w+ErrorCode\\.\\w+"),
        Pattern.compile("new \\w+Exception\\([^)]*\\w+ErrorCode\\.\\w+")
    );
    
    // 错误的模式
    private static final List<Pattern> INCORRECT_PATTERNS = Arrays.asList(
        Pattern.compile("throw new (?!\\w*Exception\\([^)]*\\w+ErrorCode)[^(]*\\([^)]*\"[^\"]+\""),
        Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^,]+,\\s*\\w+ErrorCode\\.\\w+,\\s*\"[^\"]*%[sdf][^\"]*\""),
        Pattern.compile("ExceptionUtils\\.throwException\\([^,]+,\\s*\"[^\"]*%[sdf][^\"]*\""),
        // 匹配new BaseDataResponse<>("1", "字符串字面量")模式
        Pattern.compile("new\\s+BaseDataResponse<[^>]*>\\s*\\([^,]+,\\s*\"[^\"]+\"\\s*\\)"),
        // 匹配new BaseResponse<>("1", "字符串字面量")模式  
        Pattern.compile("new\\s+BaseResponse<[^>]*>\\s*\\([^,]+,\\s*\"[^\"]+\"\\s*\\)"),
        // 匹配通用的new Response<>("1", "字符串字面量")模式
        Pattern.compile("new\\s+\\w+Response<[^>]*>\\s*\\([^,]+,\\s*\"[^\"]+\"\\s*\\)")
    );
    
    private List<String> scanFiles = new ArrayList<>();
    private List<Violation> violations = new ArrayList<>();
    private int errorCodeCounter = 1000;
    
    public static void main(String[] args) {
        ExceptionScanner scanner = new ExceptionScanner();
        String projectPath;
        String outputPath;
        
        // 如果有命令行参数，使用参数；否则提示用户输入
        if (args.length > 0) {
            projectPath = args[0];
            outputPath = args.length > 1 ? args[1] : "不规范抛出异常.md";
        } else {
            Scanner inputScanner = new Scanner(System.in);
            System.out.println("🔍 Java异常抛出规范检查工具");
            System.out.println("✨ 请输入要扫描的项目路径 (默认为当前目录):");
            System.out.print("📁 项目路径: ");
            projectPath = inputScanner.nextLine().trim();
            
            if (projectPath.isEmpty()) {
                projectPath = ".";
            }
            
            System.out.println("✨ 请输入输出文件夹路径 (默认为当前目录，文件名将自动生成为 不规范抛出异常-yyyymmdd.md):");
            System.out.print("📄 输出路径: ");
            outputPath = inputScanner.nextLine().trim();
            
            if (outputPath.isEmpty()) {
                outputPath = "不规范抛出异常.md";
            } else {
                // 如果输入的是文件夹路径，则生成带日期的文件名
                File outputDir = new File(outputPath);
                
                // 简单的目录判断 - 如果路径以分隔符结尾或者是一个已存在的目录
                if (outputPath.endsWith(File.separator) || outputPath.endsWith("/") || outputPath.endsWith("\\") || outputDir.isDirectory()) {
                    // 确保目录存在
                    if (!outputDir.exists()) {
                        outputDir.mkdirs();
                    }
                    // 生成带日期的文件名
                    String dateStr = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
                    outputPath = new File(outputDir, "不规范抛出异常-" + dateStr + ".md").getPath();
                } else {
                    // 确保父目录存在
                    File outputFile = new File(outputPath);
                    File parentDir = outputFile.getParentFile();
                    if (parentDir != null && !parentDir.exists()) {
                        parentDir.mkdirs();
                    }
                }
            }
            
            inputScanner.close();
        }
        
        OUTPUT_FILE = outputPath;
        scanner.scanProject(projectPath);
        scanner.generateReport();
    }
    
    /**
     * 扫描项目中的Java文件
     */
    public void scanProject(String projectPath) {
        try {
            System.out.println("开始扫描项目: " + projectPath);
            
            // 查找所有Java文件
            findJavaFiles(projectPath);
            
            System.out.println("找到 " + scanFiles.size() + " 个Java文件");
            
            // 扫描每个文件
            for (int i = 0; i < scanFiles.size(); i++) {
                String filePath = scanFiles.get(i);
                System.out.println("正在扫描文件 (" + (i + 1) + "/" + scanFiles.size() + "): " + filePath);
                scanFile(filePath);
                System.out.println("✓ 完成扫描: " + filePath);
            }
            
        } catch (Exception e) {
            System.err.println("扫描过程中出现错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 查找项目中的Java文件
     */
    private void findJavaFiles(String projectPath) throws IOException {
        Files.walk(Paths.get(projectPath))
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".java"))
            .forEach(path -> scanFiles.add(path.toString()));
    }
    
    /**
     * 扫描单个文件
     */
    private void scanFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                int lineNumber = i + 1;
                
                // 检查是否为不规范的异常抛出
                if (isViolation(line)) {
                    Violation violation = new Violation(filePath, lineNumber, line.trim());
                    violations.add(violation);
                    System.out.println("  发现不规范异常: 第" + lineNumber + "行 - " + line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件失败: " + filePath + " - " + e.getMessage());
        }
    }
    
    /**
     * 判断是否为不规范的异常抛出
     */
    private boolean isViolation(String line) {
        // 跳过空行和注释
        if (line.isEmpty() || line.startsWith("//") || line.startsWith("/*") || line.startsWith("*")) {
            return false;
        }
        
        // 检查是否需要忽略
        if (shouldIgnore(line)) {
            return false;
        }
        
        // 检查是否已经是正确的
        if (isCorrect(line)) {
            return false;
        }
        
        // 检查是否为不规范的
        return isIncorrect(line);
    }
    
    /**
     * 判断是否应该忽略
     */
    private boolean shouldIgnore(String line) {
        for (Pattern pattern : IGNORE_PATTERNS) {
            if (pattern.matcher(line).find()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 判断是否为正确的异常抛出
     */
    private boolean isCorrect(String line) {
        for (Pattern pattern : CORRECT_PATTERNS) {
            if (pattern.matcher(line).find()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 判断是否为不规范的异常抛出
     */
    private boolean isIncorrect(String line) {
        // 检查throw new语句
        if (line.contains("throw new") && line.contains("\"")) {
            // 没有指定错误码的throw语句
            Pattern noErrorCodePattern = Pattern.compile("throw new (?!\\w*Exception\\([^)]*\\w+ErrorCode)[^(]*\\([^)]*\"[^\"]+\"");
            if (noErrorCodePattern.matcher(line).find()) {
                return true;
            }
        }
        
        // 检查BizAssert中的不规范用法 - 只有魔法值字符串，没有错误码
        if (line.contains("BizAssert.") && line.contains("\"") && !line.contains("ErrorCode")) {
            Pattern magicStringPattern = Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^,]+,\\s*\"[^\"]+\"");
            if (magicStringPattern.matcher(line).find()) {
                return true;
            }
        }
        
        // 检查BizAssert中的格式化字符串问题 - 有错误码但使用了格式化字符串却没有用format方法
        if (line.contains("BizAssert.") && line.contains("%") && line.contains("\"")) {
            Pattern formatPattern = Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^,]+,\\s*\\w+ErrorCode\\.\\w+,\\s*\"[^\"]*%[sdf][^\"]*\"");
            if (formatPattern.matcher(line).find()) {
                return true;
            }
            // 检查是否有格式化字符串但没有使用正确的format方法
            Pattern formatWithoutFormatMethod = Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^)]*\"[^\"]*%[sdf][^\"]*\"");
            if (formatWithoutFormatMethod.matcher(line).find() && !line.contains("WithFormat")) {
                return true;
            }
        }
        
        // 检查ExceptionUtils中的格式化字符串问题
        if (line.contains("ExceptionUtils.throwException") && line.contains("%") && line.contains("\"")) {
            Pattern formatPattern = Pattern.compile("ExceptionUtils\\.throwException\\([^,]+,\\s*\"[^\"]*%[sdf][^\"]*\"");
            if (formatPattern.matcher(line).find()) {
                return true;
            }
        }
        
        // 检查BaseDataResponse模式
        Pattern baseDataResponsePattern = Pattern.compile("new\\s+BaseDataResponse<[^>]*>\\s*\\([^,]+,\\s*\"[^\"]+\"\\s*\\)");
        if (baseDataResponsePattern.matcher(line).find()) {
            return true;
        }
        
        // 检查BaseResponse模式  
        Pattern baseResponsePattern = Pattern.compile("new\\s+BaseResponse<[^>]*>\\s*\\([^,]+,\\s*\"[^\"]+\"\\s*\\)");
        if (baseResponsePattern.matcher(line).find()) {
            return true;
        }
        
        // 检查通用Response模式
        Pattern genericResponsePattern = Pattern.compile("new\\s+\\w+Response<[^>]*>\\s*\\([^,]+,\\s*\"[^\"]+\"\\s*\\)");
        if (genericResponsePattern.matcher(line).find()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 生成报告
     */
    public void generateReport() {
        try {
            StringBuilder report = new StringBuilder();
            report.append("# 不规范异常抛出代码清单\n\n");
            report.append("> 生成时间: ").append(new Date()).append("\n\n");
            
            if (violations.isEmpty()) {
                report.append("## 恭喜主人！没有发现不规范的异常抛出！喵～ 🎉\n\n");
            } else {
                report.append("## 发现 ").append(violations.size()).append(" 个不规范的异常抛出\n\n");
                
                // 按文件分组
                Map<String, List<Violation>> groupedViolations = violations.stream()
                    .collect(Collectors.groupingBy(Violation::getFilePath));
                
                for (Map.Entry<String, List<Violation>> entry : groupedViolations.entrySet()) {
                    String filePath = entry.getKey();
                    List<Violation> fileViolations = entry.getValue();
                    
                    report.append("### ").append(filePath).append("\n\n");
                    report.append("| 行号 | 代码片段 |\n");
                    report.append("|------|----------|\n");
                    
                    for (Violation violation : fileViolations) {
                        String escapedCode = violation.getCode().replace("|", "\\|");
                        report.append("| ").append(violation.getLineNumber())
                              .append(" | `").append(escapedCode).append("` |\n");
                    }
                    report.append("\n");
                }
            }
            
            Files.write(Paths.get(OUTPUT_FILE), report.toString().getBytes("UTF-8"));
            System.out.println("报告已生成: " + OUTPUT_FILE);
            
        } catch (IOException e) {
            System.err.println("生成报告失败: " + e.getMessage());
        }
    }
    
    /**
     * 生成修复建议
     */
    private String generateFixSuggestion(Violation violation) {
        String code = violation.getCode();
        String errorCode = "FIX_ERROR_" + errorCodeCounter++;
        
        if (code.contains("throw new")) {
            return "使用 `" + errorCode + "` 错误码替换魔法值字符串";
        } else if (code.contains("BizAssert") && code.contains("%")) {
            return "使用带Format的方法并指定 `" + errorCode + "` 错误码";
        }
        return "请指定正确的错误码";
    }
    
    /**
     * 提取错误消息
     */
    private String extractMessage(String code) {
        // 提取引号中的内容
        Pattern pattern = Pattern.compile("\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "未知错误";
    }
    
    /**
     * 不规范异常信息类
     */
    private static class Violation {
        private String filePath;
        private int lineNumber;
        private String code;
        
        public Violation(String filePath, int lineNumber, String code) {
            this.filePath = filePath;
            this.lineNumber = lineNumber;
            this.code = code;
        }
        
        // Getters
        public String getFilePath() { return filePath; }
        public int getLineNumber() { return lineNumber; }
        public String getCode() { return code; }
    }
    
    /**
     * 错误码接口
     */
    public interface ErrorCode {
        String getCode();
        String getMessage();
    }
}
