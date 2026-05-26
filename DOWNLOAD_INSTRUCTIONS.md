# Mshk项目下载说明

## 📦 项目压缩包

文件名：`mshk-project.zip`  
大小：27 KB  
创建时间：2026-05-26

## 📂 压缩包内容

### 核心代码
- MainActivity.kt - 主界面
- PatchService.kt - 修补服务
- ApkProcessor.kt - APK处理
- HookInjector.kt - Hook注入
- SignatureManager.kt - 签名管理
- HookBridge.kt - Hook桥接
- ModuleManager.kt - 模块管理

### 配置文件
- AndroidManifest.xml - 应用清单
- build.gradle.kts (app) - 应用构建配置
- build.gradle.kts (项目) - 项目构建配置
- settings.gradle.kts - 项目设置
- gradle.properties - Gradle属性
- local.properties.template - 本地配置模板
- proguard-rules.pro - ProGuard规则

### 资源文件
- activity_main.xml - 主界面布局
- strings.xml, themes.xml, colors.xml - 资源配置
- file_paths.xml - FileProvider配置
- backup_rules.xml, data_extraction_rules.xml - 备份配置
- 启动器图标

### 文档文件
- README.md - 项目说明
- VERIFICATION.md - 验证报告
- BUG_FIXES.md - Bug修复记录
- FINAL_CHECK_REPORT.md - 最终检查报告

## 🚀 使用说明

1. **解压项目**
   ```bash
   unzip mshk-project.zip -d mshk
   cd mshk
   ```

2. **使用Android Studio打开**
   - 启动Android Studio
   - 选择 "Open an existing project"
   - 选择解压后的项目目录

3. **配置local.properties**
   ```properties
   sdk.dir=/path/to/your/android/sdk
   ```

4. **同步并构建**
   - 等待Gradle同步完成
   - 点击 "Make Project" 按钮构建

5. **运行应用**
   - 连接Android设备或启动模拟器
   - 点击 "Run" 按钮安装并运行

## 🔧 系统要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17 或更高版本
- Android SDK API 35
- 最小支持 Android 7.0 (API 24)

## 📱 功能特性

- 无需ROOT权限修改APK
- 支持原包和过签包处理
- 集成Hook框架
- Material Design界面
- 支持Android 16

## ⚠️ 注意事项

- 本工具仅供学习和研究使用
- 请遵守相关法律法规
- 修改后的APK需要卸载原应用再安装

## 📞 支持

如有问题请参考项目文档：
- README.md - 基础说明
- VERIFICATION.md - 验证报告
- BUG_FIXES.md - 已知问题和修复
- FINAL_CHECK_REPORT.md - 最终检查报告
