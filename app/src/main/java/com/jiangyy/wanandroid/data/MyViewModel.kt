package com.jiangyy.wanandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    private val loggedLiveDate = MutableLiveData<Boolean>()

    fun loginOrOut(status: Boolean) {
        loggedLiveDate.postValue(status)
    }

    fun loggerStatus(): LiveData<Boolean> {
        return loggedLiveDate
    }

    private val myDataLiveData = MutableLiveData<Pair<Long, Int>>()

    fun myData(): LiveData<Pair<Long, Int>> {
        return myDataLiveData
    }

    fun myData(coin: Pair<Long, Int>) {
        myDataLiveData.postValue(coin)
    }

}