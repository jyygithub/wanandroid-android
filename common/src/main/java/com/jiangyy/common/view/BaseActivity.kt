package com.jiangyy.common.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : AppCompatActivity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        initValue()
        initWidget()
        initObserver()
        preLoad()
    }

    protected open fun initValue() {}

    protected open fun initWidget() {}

    protected open fun initObserver() {}

    protected open fun preLoad() {}

}