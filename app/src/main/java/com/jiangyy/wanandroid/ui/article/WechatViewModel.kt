package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.netRequest

class WechatViewModel : ViewModel() {

    private val wechatLiveData = MutableLiveData<Result<MutableList<Tree>?>>()

    val wechatResult get() = wechatLiveData

    fun listWechat() {
        netRequest {
            request { API_SERVICE.listWechat() }
            success { wechatLiveData.value = Result.success(it) }
            error { wechatLiveData.value = Result.failure(it) }
        }
    }

}