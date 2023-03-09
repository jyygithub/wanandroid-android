package com.jiangyy.wanandroid.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.azhon.appupdate.listener.OnDownloadListenerAdapter
import com.azhon.appupdate.manager.DownloadManager
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.entity.AppVersion
import com.koonny.appcompat.core.click
import com.koonny.appcompat.core.toast
import com.koonny.dialog.base.BaseDialogFragment
import com.koonny.dialog.base.DialogConfig
import java.io.File

class AppUpdateDialog : BaseDialogFragment(R.layout.dialog_app_update) {

    private var tvVersion: TextView? = null
    private var tvNetwork: TextView? = null
    private var tvDes: TextView? = null
    private var tvCancel: TextView? = null
    private var tvUpdate: TextView? = null
    private var ivClose: ImageView? = null
    private var viewLine: View? = null

    private var mAppVersion: AppVersion? = null

    private var manager: DownloadManager? = null

    override fun config(): DialogConfig {
        return super.config().apply {
            width = 0.7f
            gravity = Gravity.CENTER
            dismissOnBackPressed = false
            dismissOnTouchOutside = false
        }
    }

    override fun preView(rootView: View) {
        viewLine = rootView.findViewById(R.id.viewLine)
        tvVersion = rootView.findViewById(R.id.tvVersion)
        tvNetwork = rootView.findViewById(R.id.tvNetwork)
        tvDes = rootView.findViewById(R.id.tvDes)
        tvCancel = rootView.findViewById(R.id.tvCancel)
        tvUpdate = rootView.findViewById(R.id.tvUpdate)
        ivClose = rootView.findViewById(R.id.ivClose)
    }

    fun version(version: AppVersion): AppUpdateDialog {
        mAppVersion = version
        return this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvVersion?.text = mAppVersion?.name.orEmpty()
        tvNetwork?.text = getNetworkType()
        tvDes?.text = mAppVersion?.body.orEmpty()
        tvCancel?.click {
            manager?.cancel()
        }
        tvUpdate?.click { downloadApk() }
        ivClose?.click {
            manager?.cancel()
            dismiss()
        }
    }

    private fun downloadApk() {
        tvUpdate?.isEnabled = false
        tvUpdate?.text = "准备下载..."
        manager = DownloadManager.Builder(requireActivity()).run {
            apkUrl(mAppVersion!!.assets[0].browser_download_url.orEmpty())
            apkName("wanandroid-${mAppVersion!!.name.orEmpty()}.apk")
            smallIcon(R.mipmap.ic_launcher)
            showNotification(false)
            showBgdToast(false)
            showNewerToast(false)
            onDownloadListener(listenerAdapter)
            build()
        }
        manager?.download()
    }

    private val listenerAdapter: OnDownloadListenerAdapter = object : OnDownloadListenerAdapter() {

        override fun downloading(max: Int, progress: Int) {
            val curr = (progress / max.toDouble() * 100.0).toInt()
            tvUpdate?.text = "下载中(${curr}%)"
        }

        override fun done(apk: File) {
            super.done(apk)
            tvUpdate?.text = "下载完成，点击安装"
            tvUpdate?.isEnabled = true
            tvCancel?.visibility = View.GONE
        }

        override fun start() {
            super.start()
            tvCancel?.visibility = View.VISIBLE
        }

        override fun cancel() {
            super.cancel()
            toast("下载已取消")
            tvUpdate?.isEnabled = true
            tvUpdate?.text = "立即下载"
            tvCancel?.visibility = View.GONE
        }

        override fun error(e: Throwable) {
            super.error(e)
            tvUpdate?.text = "下载失败，点击重试"
            tvUpdate?.isEnabled = true
        }

    }

    private fun getNetworkType(): String {
        val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager? ?: return "请打开网络"
        val netWorkCapabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        return when {
            netWorkCapabilities == null -> "请打开网络"
            netWorkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "当前使用移动网络，请注意流量消耗"
            netWorkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "已连接WIFI网络，可放心下载"
            else -> "未知网络"
        }
    }

}