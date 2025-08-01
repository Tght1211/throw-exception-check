package com.example.service;

/**
 * 测试注解验证的中文和英文message模式匹配
 */
public class AnnotationValidationTestService {

    // 应该被检测出的中文注解验证模式（错误的）
    
    @NotNull(message = "机构ID不能为空")
    private String orgId;
    
    @Size(max = 10, message = "一次最多添加10个管理员")
    private String adminList;
    
    @NotEmpty(message = "至少一个成员")
    private java.util.List<String> memberList;
    
    @Max(value = 1000, message = "每页查询数量不能超过1000")
    private Integer pageSize;
    
    @Min(value = 1, message = "每页查询数量不能小于1")
    private Integer pageNumber;
    
    @Pattern(regexp = "^[0-9]+$", message = "只能输入数字")
    private String numberStr;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Length(min = 1, max = 50, message = "名称长度必须在1-50个字符之间")
    private String name;
    
    @DecimalMax(value = "999.99", message = "金额不能超过999.99")
    private java.math.BigDecimal amount;
    
    @DecimalMin(value = "0.01", message = "金额不能小于0.01")
    private java.math.BigDecimal minAmount;
    
    @Digits(integer = 10, fraction = 2, message = "数字格式不正确，整数部分最多10位，小数部分最多2位")
    private java.math.BigDecimal price;
    
    @Future(message = "日期必须是未来时间")
    private java.util.Date futureDate;
    
    @Past(message = "日期必须是过去时间")
    private java.util.Date pastDate;
    
    @AssertTrue(message = "条件必须为真")
    private Boolean condition;
    
    @AssertFalse(message = "条件必须为假")
    private Boolean falseCondition;

    // 应该被忽略的英文注解验证模式（正确的）
    
    @NotNull(message = "orgId must not be null")
    private String englishOrgId;
    
    @Size(max = 10, message = "admin list size must not exceed 10")
    private String englishAdminList;
    
    @NotEmpty(message = "member list must not be empty")
    private java.util.List<String> englishMemberList;
    
    @Max(value = 1000, message = "page size must not exceed 1000")
    private Integer englishPageSize;
    
    @Min(value = 1, message = "page number must be at least 1")
    private Integer englishPageNumber;
    
    @Pattern(regexp = "^[0-9]+$", message = "only numbers allowed")
    private String englishNumberStr;
    
    @Email(message = "invalid email format")
    private String englishEmail;
    
    @Length(min = 1, max = 50, message = "name length must be between 1 and 50 characters")
    private String englishName;
    
    // 复杂的多参数注解
    @Size(min = 1, max = 100, message = "列表大小必须在1到100之间")
    private java.util.List<String> complexList;
    
    @Range(min = 0, max = 100, message = "取值范围必须在0到100之间")
    private Integer rangeValue;
}