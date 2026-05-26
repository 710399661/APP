package com.mshk.app.service

import android.content.Context
import android.net.Uri
import com.mshk.app.core.ApkProcessor
import com.mshk.app.core.HookInjector
import com.mshk.app.core.SignatureManager
import java.io.File

class PatchService(private val context: Context) {

    fun processApk(apkUri: Uri, isSigned: Boolean): File {
        val apkProcessor = ApkProcessor(context)
        val hookInjector = HookInjector(context)
        val signatureManager = SignatureManager(context)

        val tempDir = File(context.cacheDir, "mshk_temp").apply { mkdirs() }
        val extractedApk = apkProcessor.extractApk(apkUri, tempDir)
        val modifiedApk = hookInjector.injectFramework(extractedApk, isSigned)
        val signedApk = signatureManager.signApk(modifiedApk, tempDir)

        return signedApk
    }
}

