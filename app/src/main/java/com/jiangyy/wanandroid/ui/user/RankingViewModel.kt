package com.jiangyy.wanandroid.ui.user

import com.jiangyy.wanandroid.data.PageViewModel
import com.jiangyy.wanandroid.entity.Coin
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData

class RankingViewModel : PageViewModel<Coin>() {

    override var firstPage: Int = 1

    override suspend fun realRequest(page: Int): Bean<PageData<Coin>> {
        return API_SERVICE.ranking(page)
    }

}