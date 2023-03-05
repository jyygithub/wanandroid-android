package com.jiangyy.wanandroid.entity

class UserInfo(
    var coinInfo: Coin?,
    var userInfo: User?,
    val collectArticleInfo: CollectCount?,
) {
    class CollectCount(val count: Int)
}