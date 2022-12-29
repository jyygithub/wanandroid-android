package com.jiangyy.wanandroid.ui.user

import com.jiangyy.wanandroid.data.PageViewModel
import com.jiangyy.wanandroid.entity.CoinHistory
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData

class CoinHistoryViewModel : PageViewModel<CoinHistory>() {

    override var firstPage: Int = 1

    override suspend fun realRequest(page: Int): Bean<PageData<CoinHistory>> {
        return API_SERVICE.pageCoinHistory(page)
    }

}