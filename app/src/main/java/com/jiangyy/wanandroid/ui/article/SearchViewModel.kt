package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _key = MutableLiveData<String?>()

    val key: LiveData<String?> = _key

    fun search(key: String?) {
        _key.value = key
    }

}