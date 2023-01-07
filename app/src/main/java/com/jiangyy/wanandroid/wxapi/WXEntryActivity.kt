package com.jiangyy.wanandroid.wxapi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jiangyy.wanandroid.utils.SharesFactory
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class WXEntryActivity : AppCompatActivity(), IWXAPIEventHandler {

    private var api: IWXAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, SharesFactory.WX_APP_ID, false)
            api?.registerApp(SharesFactory.WX_APP_ID)
        }
        try {
            api?.handleIntent(intent, this)?.let {
                if (it.not()) {
                    finish()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResp(resp: BaseResp?) {
        // 发送到微信请求的响应结果
        when (resp?.type) {
            ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX -> Log.d("wxshare0", "get")
            ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX -> Log.d("wxshare0", "show")
            else -> Log.d("wxshare0", "else")
        }
    }

    override fun onReq(req: BaseReq?) {
        // 微信发送的请求回调
        when (req?.type) {
            BaseResp.ErrCode.ERR_OK -> Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show()
            BaseResp.ErrCode.ERR_USER_CANCEL -> Log.d("wxshare", "cancel")
            BaseResp.ErrCode.ERR_AUTH_DENIED -> Log.d("wxshare", "denied")
            BaseResp.ErrCode.ERR_UNSUPPORT -> Log.d("wxshare", "unsupport")
            else -> Log.d("wxshare", "error")
        }
        finish()
    }
}