package com.jiangyy.wanandroid.entity

data class User(
    val coinCount: Long?,
    val collectIds: MutableList<Int>?,
    val email: String?,
    val icon: String?,
    val id: String?,
    val nickname: String?,
    val publicName: String?,
    val token: String?,
    val username: String?
)