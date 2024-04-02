package com.jiangyy.wanandroid.kit

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build

val Context.appVersion: Pair<Long, String>
    get() {
        return try {
            val packageInfo: PackageInfo
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                    packageInfo =
                        applicationContext.packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
                    packageInfo.longVersionCode to packageInfo.versionName
                }

                Build.VERSION.SDK_INT in Build.VERSION_CODES.P until Build.VERSION_CODES.TIRAMISU -> {
                    packageInfo = applicationContext.packageManager.getPackageInfo(packageName, 0)
                    packageInfo.longVersionCode to packageInfo.versionName
                }

                else -> {
                    packageInfo = applicationContext.packageManager.getPackageInfo(packageName, 0)
                    packageInfo.versionCode.toLong() to packageInfo.versionName
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            0L to ""
        }
    }

val Context.isDarkMode: Boolean
    get() = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES