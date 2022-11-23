package com.jiangyy.wanandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Coin

class MyViewModel : ViewModel() {

    private val loggedLiveDate = MutableLiveData<Boolean>()

    fun loginOrOut(status: Boolean) {
        loggedLiveDate.postValue(status)
    }

    fun loggerStatus(): LiveData<Boolean> {
        return loggedLiveDate
    }

    private val coinLiveData = MutableLiveData<Coin>()

    fun coin(): LiveData<Coin> {
        return coinLiveData
    }

    fun coin(coin: Coin) {
        coinLiveData.postValue(coin)
    }

}