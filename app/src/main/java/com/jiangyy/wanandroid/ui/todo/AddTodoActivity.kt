package com.jiangyy.wanandroid.ui.todo

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.viewModels
import com.jiangyy.common.utils.*
import com.jiangyy.common.view.BaseActivity
import com.jiangyy.wanandroid.databinding.ActivityAddTodoBinding

class AddTodoActivity : BaseActivity<ActivityAddTodoBinding>(ActivityAddTodoBinding::inflate) {

    private val mViewModel by viewModels<AddTodoViewModel>()

    override fun initWidget() {
        super.initWidget()
        binding.tvDate.text = NOW_DATE.date2string("yyyy-MM-dd")

        binding.toolbar.setOnEndListener {
            mViewModel.addTodo(
                binding.etTitle.text.toString().trim(),
                binding.etContent.text.toString().trim(),
                binding.tvDate.text.toString().trim(),
            )
        }

        val dialog = DatePickerDialog(this)
        dialog.setOnDateSetListener { _, year, month, dayOfMonth ->
            binding.tvDate.text = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
        }

        binding.tvDate.click {
            dialog.show()
        }
    }

    override fun initObserver() {
        super.initObserver()
        mViewModel.add.observe(this) {
            if (it.isSuccess) {
                doneToast("新增成功")
                finish()
            } else {
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