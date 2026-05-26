package com.mshk.app.core

import android.content.Context
import android.net.Uri
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

class ApkProcessor(private val context: Context) {

    fun extractApk(apkUri: Uri, outputDir: File): File {
        val inputFile = File(outputDir, "input.apk")
        context.contentResolver.openInputStream(apkUri)?.use { input ->
            FileOutputStream(inputFile).use { output ->
                input.copyTo(output)
            }
        } ?: throw IllegalStateException("无法打开APK文件")
        return inputFile
    }

    fun repackApk(sourceDir: File, outputFile: File) {
        ZipOutputStream(BufferedOutputStream(FileOutputStream(outputFile))).use { zos ->
            sourceDir.walkTopDown()
                .filter { it.isFile }
                .forEach { file ->
                    val entryName = file.relativeTo(sourceDir).path
                    val entry = ZipEntry(entryName)
                    zos.putNextEntry(entry)
                    BufferedInputStream(FileInputStream(file)).use { fis ->
                        fis.copyTo(zos, 8192)
                    }
                    zos.closeEntry()
                }
        }
    }

    fun unzipApk(apkFile: File, outputDir: File) {
        outputDir.mkdirs()
        ZipInputStream(BufferedInputStream(FileInputStream(apkFile))).use { zis ->
            var entry: ZipEntry?
            while (zis.nextEntry.also { entry = it } != null) {
                val entryFile = File(outputDir, entry!!.name)
                
                if (entry!!.isDirectory) {
                    entryFile.mkdirs()
                } else {
                    entryFile.parentFile?.mkdirs()
                    BufferedOutputStream(FileOutputStream(entryFile)).use { fos ->
                        zis.copyTo(fos, 8192)
                    }
                }
                zis.closeEntry()
            }
        }
    }
}

