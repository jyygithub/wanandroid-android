package com.jiangyy.wanandroid.ui.todo

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.jiangyy.core.doneToast
import com.jiangyy.core.errorToast
import com.jiangyy.core.getNowString
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityAddTodoBinding
import com.jiangyy.wanandroid.logic.TodoUrl
import kotlinx.coroutines.launch
import rxhttp.awaitResult

class AddTodoActivity : BaseActivity<ActivityAddTodoBinding>() {

    override fun initValue() {

    }

    override fun initWidget() {
        binding.tvDate.text = getNowString("yyyy-MM-dd")

        binding.toolbar.setOnEndListener {
            add()
        }

    }

    private fun add() {
        lifecycleScope.launch {
            TodoUrl.addTodo(
                binding.etTitle.text.toString().trim(),
                binding.etContent.text.toString().trim(),
                binding.tvDate.text.toString().trim(),
            ).awaitResult {
                if (it.errorCode == 0) {
                    doneToast("新增成功")
                    finish()
                } else {
                    errorToast(it.errorMsg.orEmpty())
                }
            }.onFailure {
                errorToast(it.message.orEmpty())
            }
        }
    }

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(
                Intent(context, AddTodoActivity::class.java)
            )
        }
    }

}