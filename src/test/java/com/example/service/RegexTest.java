package com.example.service;

import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        String testLine = "BizAssert.isTrue(load.getSource() != OrgSourceType.DING.getValue(),\"当前机构已经是钉钉机构无需操作上钉\");";
        
        System.out.println("测试行: " + testLine);
        
        // 测试我们的新模式
        Pattern newPattern = Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^)]+,\\s*\"[\\u4e00-\\u9fa5]+[^\"]*\"\\s*\\)");
        System.out.println("新模式匹配结果: " + newPattern.matcher(testLine).find());
        System.out.println("新模式匹配详情: " + newPattern.matcher(testLine).matches());
        
        // 测试修正后的新模式
        Pattern fixedNewPattern = Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^)]*,\\s*\"[\\u4e00-\\u9fa5]+[^\"]*\"\\s*\\)");
        System.out.println("修正后新模式匹配结果: " + fixedNewPattern.matcher(testLine).find());
        
        // 更精确的模式测试
        Pattern precisePattern = Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^)]*,[^)]*\"[\\u4e00-\\u9fa5]+[^\"]*\"\\s*\\)");
        System.out.println("精确模式匹配结果: " + precisePattern.matcher(testLine).find());
        
        // 最简单的模式测试
        Pattern simplePattern = Pattern.compile("BizAssert\\.isTrue\\([^)]*,\\s*\"[\\u4e00-\\u9fa5]+[^\"]*\"\\s*\\)");
        System.out.println("简单模式匹配结果: " + simplePattern.matcher(testLine).find());
        
        // 测试现有的中文字符串模式
        Pattern existingPattern = Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^,]+,\\s*\"[\\u4e00-\\u9fa5]+[^\"]*\"\\s*\\)");
        System.out.println("现有模式匹配结果: " + existingPattern.matcher(testLine).find());
        // 详细测试现有模式
        java.util.regex.Matcher existingMatcher = existingPattern.matcher(testLine);
        if (existingMatcher.find()) {
            System.out.println("现有模式匹配位置: " + existingMatcher.start() + "-" + existingMatcher.end());
            System.out.println("现有模式匹配内容: " + existingMatcher.group());
        }
        
        // 测试魔法字符串模式
        Pattern magicStringPattern = Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^,]+,\\s*\"[^\"]+\"");
        System.out.println("魔法字符串模式匹配结果: " + magicStringPattern.matcher(testLine).find());
        
        // 测试分步匹配
        System.out.println("\n=== 分步分析 ===");
        String firstPart = "BizAssert.isTrue(load.getSource() != OrgSourceType.DING.getValue(),";
        String secondPart = "\"当前机构已经是钉钉机构无需操作上钉\");";
        System.out.println("第一部分: " + firstPart);
        System.out.println("第二部分: " + secondPart);
        
        // 测试第一部分的匹配
        Pattern firstPartPattern = Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^,]+,");
        System.out.println("第一部分模式匹配: " + firstPartPattern.matcher(firstPart).find());
        
        // 测试 [^,]+ 是否能匹配复杂表达式
        Pattern complexParamPattern = Pattern.compile("[^,]+");
        String complexParam = "load.getSource() != OrgSourceType.DING.getValue()";
        System.out.println("复杂参数: " + complexParam);
        System.out.println("[^,]+ 能否匹配复杂参数: " + complexParamPattern.matcher(complexParam).find());
        
        // 详细测试 [^,]+ 的匹配
        java.util.regex.Matcher complexMatcher = complexParamPattern.matcher(complexParam);
        if (complexMatcher.find()) {
            System.out.println("[^,]+ 匹配内容: '" + complexMatcher.group() + "'");
            System.out.println("[^,]+ 匹配位置: " + complexMatcher.start() + "-" + complexMatcher.end());
        }
        
        // 测试整个现有模式的详细匹配过程
        System.out.println("\n=== 现有模式详细分析 ===");
        String fullPattern = "BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\([^,]+,\\s*\"[\\u4e00-\\u9fa5]+[^\"]*\"\\s*\\)";
        System.out.println("完整模式: " + fullPattern);
        
        // 分解匹配过程
        String part1 = "BizAssert.isTrue(";
        String part2 = "load.getSource() != OrgSourceType.DING.getValue()";
        String part3 = ",";
        String part4 = "\"当前机构已经是钉钉机构无需操作上钉\"";
        String part5 = ")";
        
        System.out.println("分解匹配:");
        System.out.println("  Part1: " + part1);
        System.out.println("  Part2: " + part2);
        System.out.println("  Part3: " + part3);
        System.out.println("  Part4: " + part4);
        System.out.println("  Part5: " + part5);
        
        // 测试各部分匹配
        Pattern p1 = Pattern.compile("BizAssert\\.(isTrue|notNull|notBlank|notEmpty)\\(");
        Pattern p2 = Pattern.compile("[^,]+");
        Pattern p3 = Pattern.compile(",");
        Pattern p4 = Pattern.compile("\"[\\u4e00-\\u9fa5]+[^\"]*\"");
        Pattern p5 = Pattern.compile("\\s*\\)");
        
        System.out.println("  P1匹配: " + p1.matcher(part1).find());
        System.out.println("  P2匹配: " + p2.matcher(part2).find());
        System.out.println("  P3匹配: " + p3.matcher(part3).find());
        System.out.println("  P4匹配: " + p4.matcher(part4).find());
        System.out.println("  P5匹配: " + p5.matcher(part5).find());
    }
}
