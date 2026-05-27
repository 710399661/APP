# AIDE 打包指南

本项目可以在 AIDE 上打包，但需要做一些配置调整。

## AIDE 兼容性问题

### 1. Gradle 插件版本
- 当前：AGP 8.5.0
- 建议：降级到 AGP 7.4.2 或更低版本

### 2. Kotlin DSL
- 当前：使用 `build.gradle.kts`
- 建议：改为传统的 `build.gradle` (Groovy DSL)

### 3. 编译 SDK
- 当前：API 35
- 建议：降低到 API 33 或 34

### 4. Java 版本
- 当前：Java 17
- 建议：Java 11 或 Java 8

## 快速适配步骤

### 方案一：手动修改配置（推荐）

1. **修改根 build.gradle.kts**
   ```kotlin
   plugins {
       id("com.android.application") version "7.4.2" apply false
       id("org.jetbrains.kotlin.android") version "1.8.22" apply false
   }
   ```

2. **修改 app/build.gradle.kts**
   ```kotlin
   android {
       compileSdk = 33
       defaultConfig {
           targetSdk = 33
       }
       compileOptions {
           sourceCompatibility = JavaVersion.VERSION_11
           targetCompatibility = JavaVersion.VERSION_11
       }
       kotlinOptions {
           jvmTarget = "11"
       }
   }
   ```

3. **修改 settings.gradle.kts**
   - 移除或注释掉 `dependencyResolutionManagement` 块
   - 改用传统的 repositories 配置方式

### 方案二：使用 AIDE 友好的分支

如果项目提供了 `aide-compat` 分支，可以直接切换：
```bash
git checkout aide-compat
```

## 在 AIDE 中导入项目

1. 打开 AIDE
2. 选择 "File" -> "New" -> "Import Project"
3. 选择项目根目录
4. 等待 Gradle 同步完成
5. 点击 "Run" 按钮构建应用

## 注意事项

- AIDE 可能无法识别最新的 AndroidX 库版本
- 建议使用 AIDE 内置的 Gradle 版本
- 如果遇到依赖问题，可以尝试降低依赖库版本
