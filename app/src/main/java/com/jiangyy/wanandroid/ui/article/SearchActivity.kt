package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.jiangyy.wanandroid.databinding.ActivitySearchBinding
import com.koonny.appcompat.BaseActivity
import com.koonny.appcompat.core.click

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {

    private val mViewModel by viewModels<SearchViewModel>()

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.tvCancel.click { finish() }
        binding.etInput.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(v.windowToken, 0)
                mViewModel.search(binding.etInput.text.toString().trim())
            }
            false
        }
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }

}