package com.mshk.app.core

import android.content.Context
import java.io.File

object HookBridge {
    
    private const val TAG = "MshkHook"
    
    fun init(context: Context) {
        val configDir = File(context.filesDir, "mshk").apply { mkdirs() }
        val modulesDir = File(configDir, "modules").apply { mkdirs() }
    }
    
    fun loadModule(modulePath: String): Boolean {
        return try {
            true
        } catch (e: Exception) {
            false
        }
    }
    
    fun isEnabled(): Boolean {
        return true
    }
}

