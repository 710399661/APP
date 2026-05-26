# 发现和修复的 Bug 报告

## 发现的 Bug 列表

### 1. 未使用的导入
**文件**: [SignatureManager.kt](file:///workspace/app/src/main/java/com/mshk/app/core/SignatureManager.kt)
**问题**: 导入了 `KeyStore`, `SecureRandom`, `java.util.*` 但从未使用
**修复**: 移除了未使用的导入和未使用的 `generateKeyStore()` 方法

### 2. AndroidManifest.xml 二进制解析问题
**文件**: [HookInjector.kt](file:///workspace/app/src/main/java/com/mshk/app/core/HookInjector.kt)
**问题**: APK中的AndroidManifest.xml是二进制AXML格式，不能用标准DOM解析器解析
**修复**: 移除了 `modifyManifest()` 方法，暂时不修改APK的Manifest

### 3. Zip流处理方式不当
**文件**: [ApkProcessor.kt](file:///workspace/app/src/main/java/com/mshk/app/core/ApkProcessor.kt)
**问题**: 直接使用Java.util.zip处理APK可能导致文件损坏，且缺少异常处理
**修复**: 
- 改用系统命令行工具 (zip/unzip) 处理APK文件
- 添加了更好的异常处理
- 为 `extractApk()` 添加了空值检查

### 4. 缺少 FileProvider 配置
**文件**: [MainActivity.kt](file:///workspace/app/src/main/java/com/mshk/app/MainActivity.kt) 和 [AndroidManifest.xml](file:///workspace/app/src/main/AndroidManifest.xml)
**问题**: Android 7.0+ 不能直接使用 `Uri.fromFile()` 共享文件，会抛出 `FileUriExposedException`
**修复**:
- 添加了 `FileProvider` 配置到 AndroidManifest.xml
- 创建了 `file_paths.xml` 配置文件
- 更新了 `showInstallPrompt()` 方法，根据Android版本使用不同的Uri获取方式

### 5. 未使用的导入
**文件**: [HookInjector.kt](file:///workspace/app/src/main/java/com/mshk/app/core/HookInjector.kt)
**问题**: 导入了未使用的包 `android.content.pm.ApplicationInfo` 和 XML相关类
**修复**: 移除了所有未使用的导入

## 技术改进

### APK处理方式变更
为了更好地处理APK文件，我们将原本使用的Java ZIP API替换为系统命令行工具调用：
- 使用 `unzip` 命令解压APK
- 使用 `zip` 命令重新打包APK
这种方式更可靠，能更好地处理Android APK的特殊结构

### 权限处理优化
- 添加了完整的FileProvider配置
- 保持了对Android各个版本的兼容性

## 后续建议

### 1. 添加真实的APK签名功能
当前的 `SignatureManager` 只是复制文件，没有实际签名APK。建议使用apksigner或其他工具来实现真实的签名功能。

### 2. 使用更专业的APK处理库
考虑使用成熟的第三方库：
- `apktool` - 优秀的APK反编译和回编译工具
- `zip4j` - 更强大的ZIP处理库

### 3. 添加AXML解析功能
如果需要实际修改AndroidManifest.xml，需要使用专门的AXML解析库：
- `apk-parser`
- `androidbinaryxml`
