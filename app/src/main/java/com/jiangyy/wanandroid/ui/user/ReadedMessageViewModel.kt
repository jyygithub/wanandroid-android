package com.jiangyy.wanandroid.ui.user

import com.jiangyy.wanandroid.data.PageViewModel
import com.jiangyy.wanandroid.entity.Message
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.Bean
import com.jiangyy.wanandroid.logic.PageData

class ReadedMessageViewModel : PageViewModel<Message>() {

    override var firstPage: Int = 1

    override suspend fun realRequest(page: Int): Bean<PageData<Message>> {
        return API_SERVICE.listReadedMessage(page)
    }

}