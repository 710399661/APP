package com.mshk.app.core

import android.content.Context
import java.io.File

class SignatureManager(private val context: Context) {

    fun signApk(apkFile: File, tempDir: File): File {
        val signedApk = File(tempDir, "mshk_signed.apk")
        
        apkFile.copyTo(signedApk, overwrite = true)
        
        return signedApk
    }
}

