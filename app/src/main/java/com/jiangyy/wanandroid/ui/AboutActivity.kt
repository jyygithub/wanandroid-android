package com.jiangyy.wanandroid.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.jiangyy.wanandroid.data.Api
import com.jiangyy.wanandroid.data.RetrofitHelper
import com.jiangyy.wanandroid.databinding.ActivityAboutBinding
import com.jiangyy.wanandroid.entity.AppVersion
import com.jiangyy.wanandroid.utils.AppUpdateDialog
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.core.appVersion
import com.koonny.appcompat.core.click
import com.koonny.appcompat.core.toast
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.tvAppVersion.text = "Version ${appVersion.versionName}"
        binding.tvAppVersion.click {
            getLatestVersion()
        }
    }

    override fun onPrepareData() {
        super.onPrepareData()

    }

    private fun getLatestVersion() {
        lifecycleScope.launch {
            flow {
                emit(RetrofitHelper.getInstance().create(Api::class.java).latestVersion())
            }.catch {
                toast(it.message.orEmpty())
            }.collect {
                showVersion(it)
            }
        }
    }

    private fun showVersion(version: AppVersion) {
        if (version.tag_name == appVersion.versionName) {
            toast("未发现新版本")
            return
        }
        if (version.assets.isEmpty()) {
            toast("未发现新版本")
            return
        }
        AppUpdateDialog()
            .version(version)
            .show(supportFragmentManager)
    }

    companion object {
        fun actionStart(context: Context) {
            Intent(context, AboutActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

}