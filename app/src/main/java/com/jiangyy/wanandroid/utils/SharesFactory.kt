package com.jiangyy.wanandroid.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.jiangyy.wanandroid.AppContext
import com.jiangyy.wanandroid.entity.Article
import com.tencent.connect.common.Constants
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzoneShare
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError

object SharesFactory {

    private const val TENCENT_APP_ID = "1108139905"
    const val WX_APP_ID = "wxbc646a4d4a48fd01"
    private lateinit var mIWXAPI: IWXAPI
    private lateinit var mTencent: Tencent

    // 微信能力

//    private val mWxRegisterReceiver by lazy {
//        object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent?) {
//                mIWXAPI.registerApp(WX_APP_ID)
//            }
//        }
//    }

    fun registerWXAndQQ(context: Context) {
        mTencent = Tencent.createInstance(TENCENT_APP_ID, context)
        mIWXAPI = WXAPIFactory.createWXAPI(AppContext, WX_APP_ID, true)
        mIWXAPI.registerApp(WX_APP_ID)
//        context.registerReceiver(mWxRegisterReceiver, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))
    }

    fun unregisterWXAndQQ(context: Context) {
        mIWXAPI.unregisterApp()
//        context.unregisterReceiver(mWxRegisterReceiver)
    }

    /**
     * 分享到对话
     */
    fun shareToSession(article: Article) {
        shareToWeChat(SendMessageToWX.Req.WXSceneSession, article)
    }

    /**
     * 分享到朋友圈
     */
    fun shareToTimeline(article: Article) {
        shareToWeChat(SendMessageToWX.Req.WXSceneTimeline, article)
    }

    /**
     * 分享到微信收藏
     * @param article Article
     */
    fun shareToFavorite(article: Article) {
        shareToWeChat(SendMessageToWX.Req.WXSceneFavorite, article)
    }

    /**
     * 分享到微信
     * @param article Article
     */
    private fun shareToWeChat(scene: Int, article: Article) {
        val webpage = WXWebpageObject()
        webpage.webpageUrl = article.link
        val msg = WXMediaMessage(webpage)
        msg.title = article.title
        msg.description = article.desc
        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("webpage")
        req.message = msg
        req.scene = scene
        mIWXAPI?.sendReq(req)
    }

    private fun buildTransaction(type: String? = "webpage"): String {
        return if (type == null) System.currentTimeMillis()
            .toString() else type + System.currentTimeMillis()
    }

    // QQ能力

    private val mQqShareListener: IUiListener = object : IUiListener {
        override fun onCancel() {
            Toast.makeText(AppContext, "分享取消", Toast.LENGTH_SHORT).show()
        }

        override fun onComplete(response: Any) {
            Toast.makeText(AppContext, "分享成功", Toast.LENGTH_SHORT).show()
        }

        override fun onWarning(p0: Int) {

        }

        override fun onError(e: UiError) {
            Toast.makeText(AppContext, "分享失败：${e.errorMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareToQQ(activity: Activity, article: Article) {
        val bundle = Bundle()
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, article.link)
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, article.title)
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, article.desc)
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "玩安卓$TENCENT_APP_ID")
        mTencent.shareToQQ(activity, bundle, mQqShareListener)
    }

    fun shareToQzone(activity: Activity, article: Article) {
        val bundle = Bundle()
        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT)
        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, article.link)
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, article.title)
        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, article.desc)
        val imgUrlList = ArrayList<String>()
        if (article.envelopePic.orEmpty().isEmpty()) {
            imgUrlList.add("http://www.wanandroid.com/resources/image/pc/logo.png")
        } else {
            imgUrlList.add(article.envelopePic.orEmpty())
        }
        bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList)
        bundle.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, "玩安卓$TENCENT_APP_ID")
        mTencent.shareToQzone(activity, bundle, mQqShareListener)
    }

    fun attach(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mQqShareListener)
        }
    }

}