package com.mshk.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.mshk.app.databinding.ActivityMainBinding
import com.mshk.app.service.PatchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedApkUri: Uri? = null
    private var isSignedApk = false

    private val apkPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedApkUri = uri
                binding.tvSelectedApk.text = uri.lastPathSegment ?: "已选择APK"
            }
        }
    }

    private val storagePermissionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                pickApkFile()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.rgApkType.setOnCheckedChangeListener { _, checkedId ->
            isSignedApk = checkedId == R.id.rbSigned
        }

        binding.btnSelectApk.setOnClickListener {
            checkStoragePermission()
        }

        binding.btnPatch.setOnClickListener {
            startPatchProcess()
        }
    }

    private fun checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                storagePermissionLauncher.launch(intent)
                return
            }
        }
        pickApkFile()
    }

    private fun pickApkFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/vnd.android.package-archive"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        apkPickerLauncher.launch(Intent.createChooser(intent, "选择APK文件"))
    }

    private fun startPatchProcess() {
        val uri = selectedApkUri ?: run {
            Toast.makeText(this, R.string.select_apk_first, Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.tvStatus.text = getString(R.string.patching)
        binding.btnPatch.isEnabled = false

        lifecycleScope.launch {
            val result = runCatching {
                withContext(Dispatchers.IO) {
                    val service = PatchService(this@MainActivity)
                    service.processApk(uri, isSignedApk)
                }
            }
            
            result.onSuccess { outputFile ->
                binding.progressBar.visibility = android.view.View.GONE
                binding.tvStatus.text = getString(R.string.patch_success)
                binding.btnPatch.isEnabled = true
                showInstallPrompt(outputFile)
            }.onFailure { e ->
                binding.progressBar.visibility = android.view.View.GONE
                binding.tvStatus.text = "${getString(R.string.patch_failed)}: ${e.message}"
                binding.btnPatch.isEnabled = true
            }
        }
    }

    private fun showInstallPrompt(file: File) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    this@MainActivity,
                    "${packageName}.fileprovider",
                    file
                )
            } else {
                Uri.fromFile(file)
            }
            setDataAndType(uri, "application/vnd.android.package-archive")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(intent)
    }
}

