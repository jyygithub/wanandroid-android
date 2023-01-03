package com.jiangyy.common.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : AppCompatActivity(), IBaseView {

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

    override fun initValue() {}

    override fun initWidget() {}

    override fun initObserver() {}

    override fun preLoad() {}

}