package com.mshk.app.core

import android.content.Context
import android.os.Build
import java.io.File

class HookInjector(private val context: Context) {

    fun injectFramework(apkFile: File, isSigned: Boolean): File {
        val tempDir = File(apkFile.parent, "extracted")
        val apkProcessor = ApkProcessor(context)
        apkProcessor.unzipApk(apkFile, tempDir)

        addConfigFiles(tempDir)
        addNativeLibs(tempDir)

        val outputApk = File(apkFile.parent, "modified.apk")
        apkProcessor.repackApk(tempDir, outputApk)

        tempDir.deleteRecursively()
        return outputApk
    }

    private fun addNativeLibs(extractedDir: File) {
        val libDir = File(extractedDir, "lib")
        val abiDirs = listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        
        abiDirs.forEach { abi ->
            val abiDir = File(libDir, abi).apply { mkdirs() }
            val placeholderLib = File(abiDir, "libmshk.so")
            placeholderLib.createNewFile()
        }
    }

    private fun addConfigFiles(extractedDir: File) {
        val resXmlDir = File(extractedDir, "res/xml").apply { mkdirs() }
        
        val networkConfig = File(resXmlDir, "network_security_config.xml")
        networkConfig.writeText("""
            <?xml version="1.0" encoding="utf-8"?>
            <network-security-config>
                <base-config cleartextTrafficPermitted="true">
                    <trust-anchors>
                        <certificates src="system" />
                        <certificates src="user" />
                    </trust-anchors>
                </base-config>
            </network-security-config>
        """.trimIndent())

        val assetsDir = File(extractedDir, "assets/mshk").apply { mkdirs() }
        val configFile = File(assetsDir, "mshk_config.json")
        configFile.writeText("""
            {
                "version": "1.0.0",
                "enabled": true,
                "modules": []
            }
        """.trimIndent())
    }
}

