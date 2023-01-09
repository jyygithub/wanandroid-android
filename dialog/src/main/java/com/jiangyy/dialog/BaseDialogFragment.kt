package com.jiangyy.dialog

import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    DialogFragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    open val dialogWidth get() = 1f

    open val dialogHeight get() = 0f

    open val dialogGravity get() = Gravity.CENTER

    open val dialogStyle get() = R.style.Theme_Jiang_Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, dialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate(inflater, container, false)
        setDialogSize(dialogWidth, dialogHeight)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValue()
        initWidget()
        initObserver()
        preLoad()
    }

    private fun setDialogSize(widthPercent: Float, heightPercent: Float) {
        val dm = DisplayMetrics()
        val activity = activity
        val dialog = dialog
        if (activity != null && dialog != null) {
            activity.windowManager.defaultDisplay.getMetrics(dm)
            val window = dialog.window

            val screenWidth = dm.widthPixels
            val screenHeight = dm.heightPixels

            if (window != null) {
                window.decorView.setPadding(0, 0, 0, 0)
                val lp = window.attributes

                lp.width =
                    when (widthPercent) {
                        0f -> WindowManager.LayoutParams.WRAP_CONTENT
                        1f -> WindowManager.LayoutParams.MATCH_PARENT
                        else -> (screenWidth * widthPercent).toInt()
                    }

                lp.height =
                    when (heightPercent) {
                        0f -> WindowManager.LayoutParams.WRAP_CONTENT
                        1f -> WindowManager.LayoutParams.MATCH_PARENT
                        else -> (screenHeight * heightPercent).toInt()
                    }
                window.attributes = lp
                window.setGravity(dialogGravity)
                window.setBackgroundDrawable(ColorDrawable())
            }
        }
    }

    fun show(manager: FragmentManager) {
        show(manager, javaClass.name)
    }

    protected fun isLandscape(): Boolean =
        requireActivity().resources.configuration.orientation == ActivityInfo.SCREEN_ORIENTATION_USER

    open fun initValue() {}

    abstract fun initWidget()

    open fun initObserver() {}

    open fun preLoad() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}