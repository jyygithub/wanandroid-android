package com.jiangyy.dialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jiangyy.dialog.databinding.DialogBottomListBinding
import com.jiangyy.dialog.utils.asCenterRadii
import com.jiangyy.dialog.utils.asTopRadii
import com.jiangyy.dialog.utils.isGone

class StringBottomListDialog : BaseDialogFragment<DialogBottomListBinding>(DialogBottomListBinding::inflate) {

    private var block: ((Int, String) -> Unit)? = null
    private var mCancelClick: View.OnClickListener? = null

    private var items: MutableList<String>? = null

    override val dialogWidth: Float
        get() = if (isLandscape()) 0.5f else super.dialogWidth

    override val dialogGravity: Int
        get() = Gravity.BOTTOM

    override val dialogStyle: Int
        get() = R.style.Theme_Jiang_Dialog_Bottom

    fun items(items: MutableList<String>, block: (Int, String) -> Unit): StringBottomListDialog {
        this.block = block
        this.items = items
        return this
    }

    fun items(vararg items: String, block: (Int, String) -> Unit): StringBottomListDialog {
        this.block = block
        this.items = items.toMutableList()
        return this
    }

    fun cancel(listener: View.OnClickListener?): StringBottomListDialog {
        mCancelClick = listener
        return this
    }

    class Config {
        var width: Float = 0f
        var height: Float = 0f
        var title: CharSequence? = null
        var cancelText: CharSequence? = "取消"
        var dismissOnBackPressed: Boolean = true
        var dismissOnTouchOutside: Boolean = true
    }

    private var mConfig = Config()

    fun bindConfig(bl: Config.() -> Unit): StringBottomListDialog {
        bl.invoke(mConfig)
        return this
    }

    override fun initValue() {
        dialog?.setCancelable(mConfig.dismissOnBackPressed)
        dialog?.setCanceledOnTouchOutside(mConfig.dismissOnTouchOutside)
    }

    override fun initWidget() {
        binding.tvTitle.isGone = mConfig.title.isNullOrBlank()
        binding.tvTitle.text = mConfig.title
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnCancel.text = mConfig.cancelText
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = MyAdapter(context, mConfig.title.isNullOrBlank(), items!!) { position, item ->
            block?.invoke(position, item)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            mCancelClick?.onClick(it)
            dismiss()
        }
    }

    class MyAdapter(
        private val context: Context?,
        private var hideTitle: Boolean,
        private val items: MutableList<String>,
        private val block: (Int, String) -> Unit
    ) :
        RecyclerView.Adapter<MyAdapter.MyVH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
            return MyVH(LayoutInflater.from(context).inflate(R.layout.dialog_recycler_item, parent, false))
        }

        override fun onBindViewHolder(holder: MyVH, position: Int) {
            holder.tvTitle.text = items[position]
            if (hideTitle) {
                if (position == 0) {
                    holder.itemView.asTopRadii()
                } else {
                    holder.itemView.asCenterRadii()
                }
            }
            holder.itemView.setOnClickListener {
                block.invoke(position, items[position])
            }
        }

        override fun getItemCount(): Int {
            return items.size
        }

        class MyVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        }

    }

}