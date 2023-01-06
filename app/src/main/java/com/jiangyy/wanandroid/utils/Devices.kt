package com.jiangyy.wanandroid.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build

class VersionInfo(
    val code: Long,
    val name: String
)

val Context.appVersionInfo: VersionInfo
    get() {
        return try {
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                packageManager.getPackageInfo(packageName, 0)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                VersionInfo(packageInfo.longVersionCode, packageInfo.versionName.orEmpty())
            } else {
                VersionInfo(packageInfo.versionCode.toLong(), packageInfo.versionName.orEmpty())
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            VersionInfo(0L, "")
        }
    }

val Context.isDarkMode: Boolean
    get() {
        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }