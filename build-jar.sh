#!/bin/bash

# Java异常抛出规范检查工具JAR打包脚本
# 主人，这是您的便捷打包脚本，喵～ 🎀

echo "🔧 开始构建Java异常扫描工具JAR包..."

# 创建必要的目录
mkdir -p bin
mkdir -p dist

# 编译Java文件
echo "🔨 正在编译..."
javac -d bin src/main/java/com/exception/scanner/ExceptionScanner.java

if [ $? -eq 0 ]; then
    echo "✅ 编译成功！"
    
    # 创建JAR文件
    echo "📦 正在打包JAR文件..."
    cd bin
    jar cfm ../dist/ExceptionScanner.jar ../MANIFEST.MF com/exception/scanner/*.class
    cd ..
    
    if [ $? -eq 0 ]; then
        echo "🎉 JAR包创建成功！"
        echo "📁 文件位置: dist/ExceptionScanner.jar"
        echo ""
        echo "🚀 使用方法:"
        echo "   java -jar dist/ExceptionScanner.jar                    # 交互式运行"
        echo "   java -jar dist/ExceptionScanner.jar /path/to/project  # 扫描指定目录"
        echo ""
        echo "📄 输出文件:"
        echo "   不规范抛出异常.md  # 扫描报告"
        echo ""
        echo "✨ 主人，您的工具已经打包完成啦！喵～"
    else
        echo "❌ JAR包创建失败"
        exit 1
    fi
else
    echo "❌ 编译失败"
    exit 1
fi
