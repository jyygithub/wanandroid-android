package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Tree
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.ResultMutableLiveData
import com.jiangyy.wanandroid.logic.flowRequest

class WechatViewModel : ViewModel() {

    private val wechatLiveData = ResultMutableLiveData<MutableList<Tree>>()

    val wechatResult get() = wechatLiveData

    fun listWechat() {
        flowRequest {
            request { API_SERVICE.listWechat() }
            response { wechatLiveData.value = it }
        }
    }

}