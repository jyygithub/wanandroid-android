package com.jiangyy.wanandroid.ui.todo

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jiangyy.core.doneToast
import com.jiangyy.core.errorToast
import com.jiangyy.core.getNowString
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityAddTodoBinding

class AddTodoActivity : BaseActivity<ActivityAddTodoBinding>() {

    private val mViewModel by viewModels<AddTodoViewModel>()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.tvDate.text = getNowString("yyyy-MM-dd")

        binding.toolbar.setOnEndListener {
            mViewModel.addTodo(
                binding.etTitle.text.toString().trim(),
                binding.etContent.text.toString().trim(),
                binding.tvDate.text.toString().trim(),
            )
        }

        mViewModel.add.observe(this){
            if(it.isSuccess){
                doneToast("新增成功")
                finish()
            }else{
                errorToast(it.exceptionOrNull()?.message.orEmpty())
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