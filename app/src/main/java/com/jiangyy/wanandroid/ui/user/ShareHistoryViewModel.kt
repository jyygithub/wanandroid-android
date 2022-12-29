package com.jiangyy.wanandroid.ui.user

import androidx.lifecycle.MutableLiveData
import com.jiangyy.wanandroid.data.PageViewModel
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.netRequest

class ShareHistoryViewModel : PageViewModel<Article>() {

    private val _unshare = MutableLiveData<Result<Any?>>()

    val unshare get() = _unshare

    fun unshare(id: String) {
        netRequest {
            request { API_SERVICE.unshare(id) }
            success { _unshare.value = Result.success(it) }
            error { _unshare.value = Result.failure(it) }
        }
    }

    // ---

    override var firstPage: Int = 1

    override suspend fun realRequest(page: Int): Bean<PageData<Article>> {
        return API_SERVICE.listShareHistory(page)
    }

}