# Mshk - 免ROOT Patch框架

一个无需ROOT权限即可对Android应用进行修改和增强的Patch框架，支持Android 16及以上版本。

## 功能特性

- 支持对原包和过签包进行修补
- 提供Hook框架集成能力
- 模块化设计，易于扩展
- 现代UI界面，操作简洁
- 兼容最新Android系统

## 项目结构

```
/workspace/
├── app/                      # 主应用模块
│   ├── src/main/
│   │   ├── java/com/mshk/app/
│   │   │   ├── MainActivity.kt          # 主界面
│   │   │   ├── service/
│   │   │   │   └── PatchService.kt      # 修补服务
│   │   │   └── core/
│   │   │       ├── ApkProcessor.kt      # APK处理
│   │   │       ├── HookInjector.kt      # Hook框架注入
│   │   │       ├── SignatureManager.kt  # 签名管理
│   │   │       ├── HookBridge.kt        # Hook桥接
│   │   │       └── ModuleManager.kt     # 模块管理
│   │   └── res/                # 资源文件
│   └── build.gradle.kts        # 应用构建配置
├── gradle/                     # Gradle wrapper
├── build.gradle.kts            # 项目构建配置
├── settings.gradle.kts         # 项目设置
└── gradle.properties           # Gradle属性
```

## 构建项目

### 前置要求

- JDK 8 或更高版本
- Android SDK (API Level 35+)
- Android Studio (可选，推荐使用)

### 使用Gradle构建

```bash
# 克隆或下载项目
cd /workspace

# 赋予执行权限
chmod +x gradlew

# 构建Debug版本
./gradlew assembleDebug

# 构建Release版本
./gradlew assembleRelease

# 安装到设备
./gradlew installDebug
```

## 使用说明

1. **启动应用**：在Android设备上安装并启动Mshk
2. **选择APK类型**：选择是原包还是过签包
3. **选择APK文件**：点击"选择APK"按钮，从存储中选择要修补的APK
4. **开始修补**：点击"开始修补"按钮，等待处理完成
5. **安装修改后的APK**：修补完成后，应用会提示安装修改后的APK

## 核心模块

### ApkProcessor
负责APK文件的解压、提取和重新打包。

### HookInjector
向目标APK中注入Hook框架相关的配置和库文件。

### SignatureManager
处理APK的签名相关工作。

### PatchService
协调各个模块，完成完整的APK修补流程。

## 兼容性

- 最低支持：Android 7.0 (API Level 24)
- 目标版本：Android 16 (API Level 35)
- 测试设备：主流Android手机和平板

## 注意事项

1. 请确保在使用前已授予应用必要的存储权限
2. 修改后的APK可能需要卸载原应用才能安装
3. 本工具仅供学习和研究使用，请遵守相关法律法规

## 许可证

本项目采用Apache License 2.0许可证。
