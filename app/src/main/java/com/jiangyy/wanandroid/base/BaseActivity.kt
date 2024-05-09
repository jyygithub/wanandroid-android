package com.jiangyy.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : AppCompatActivity() {

    protected lateinit var binding: VB
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate.invoke(layoutInflater)
        setContentView(binding.root)
        onPrepareValue()
        onPrepareWidget()
        onPrepareData()
    }

    open fun onPrepareValue() {
    }

    open fun onPrepareWidget() {
    }

    open fun onPrepareData() {
    }

}