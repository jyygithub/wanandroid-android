package com.jiangyy.wanandroid.entity

class AppVersion(
    val tag_name: String,
    val name: String,
    val body: String,
    val assets: MutableList<ApkAsset>
) {
    class ApkAsset(
        val size: Long,
        val name: String,
        val content_type: String,
        val browser_download_url: String,
    )
}