package com.jiangyy.wanandroid.ui.home

import com.jiangyy.wanandroid.base.BaseFragment
import com.jiangyy.wanandroid.databinding.FragmentHomeMyBinding
import com.jiangyy.wanandroid.kit.click
import com.jiangyy.wanandroid.ui.user.LoginActivity

class HomeMyFragment : BaseFragment<FragmentHomeMyBinding>(FragmentHomeMyBinding::inflate) {

    override fun onPrepareWidget() {
        super.onPrepareWidget()
        binding.ivAvatar.click {
            LoginActivity.actionStart(requireActivity())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeMyFragment()
    }

}