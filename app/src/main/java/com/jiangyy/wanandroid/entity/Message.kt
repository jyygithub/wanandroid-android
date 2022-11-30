package com.jiangyy.wanandroid.entity

data class Message(
    var category: Int?,
    var date: Long?,
    var fromUser: String?,
    var fromUserId: Int?,
    var fullLink: String?,
    var id: Int?,
    var isRead: Int?,
    var link: String?,
    var message: String?,
    var niceDate: String?,
    var tag: String?,
    var title: String?,
    var userId: Int?
)