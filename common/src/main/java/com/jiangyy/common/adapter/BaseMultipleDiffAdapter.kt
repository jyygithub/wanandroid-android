package com.jiangyy.common.adapter

import androidx.viewbinding.ViewBinding

abstract class BaseMultipleDiffAdapter<T : Any>(id: (T) -> Any? = { it }) : BaseDiffAdapter<T, ViewBinding>(id) {

}