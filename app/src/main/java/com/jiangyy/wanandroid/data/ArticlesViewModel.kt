package com.jiangyy.wanandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticlesViewModel : ViewModel() {

    private val scanLiveData = MutableLiveData<Any>()

    fun clearScan() {
        scanLiveData.value = scanLiveData.value
    }

    fun scans(): LiveData<Any> {
        return scanLiveData
    }

}