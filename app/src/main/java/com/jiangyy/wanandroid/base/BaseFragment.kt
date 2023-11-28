package com.jiangyy.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPrepareData()
        onPrepareValue()
        onPrepareWidget()
    }

    open fun onPrepareValue() {
    }

    open fun onPrepareWidget() {
    }

    open fun onPrepareData() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}