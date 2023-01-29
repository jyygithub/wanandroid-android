package com.jiangyy.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jiangyy.dialog.databinding.DialogBottomListBinding
import com.jiangyy.dialog.utils.asCenterRadii
import com.jiangyy.dialog.utils.asTopRadii
import com.jiangyy.dialog.utils.isGone

class BottomListDialog<T> : BaseDialogFragment<DialogBottomListBinding>(DialogBottomListBinding::inflate) {

    private var block: ((Int, T) -> Unit)? = null
    private var convert: (MyAdapter.MyVH.(T) -> Unit)? = null
    private var mCancelClick: View.OnClickListener? = null

    private var items: MutableList<T>? = null

    override val dialogWidth: Float
        get() = if (isLandscape()) 0.5f else super.dialogWidth

    override val dialogStyle: Int
        get() = R.style.Theme_Jiang_Dialog_Bottom

    override val dialogGravity: Int
        get() = Gravity.BOTTOM

    fun items(items: MutableList<T>, block: (Int, T) -> Unit): BottomListDialog<T> {
        this.block = block
        this.items = items
        return this
    }

    fun items(vararg items: T, block: (Int, T) -> Unit): BottomListDialog<T> {
        this.block = block
        this.items = items.toMutableList()
        return this
    }

    fun convert(convert: MyAdapter.MyVH.(T) -> Unit): BottomListDialog<T> {
        this.convert = convert
        return this
    }

    fun cancel(listener: View.OnClickListener?): BottomListDialog<T> {
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

    fun bindConfig(bl: Config.() -> Unit): BottomListDialog<T> {
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
        binding.recyclerView.adapter = MyAdapter(mConfig.title.isNullOrBlank(), items!!, {
            convert?.invoke(this, it)
        }) { position, item ->
            block?.invoke(position, item)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            mCancelClick?.onClick(it)
            dismiss()
        }
    }

    class MyAdapter<T>(
        private var hideTitle: Boolean,
        private val items: MutableList<T>,
        private val convert: MyVH.(T) -> Unit,
        private val block: (Int, T) -> Unit
    ) :
        RecyclerView.Adapter<MyAdapter.MyVH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
            return MyVH(LayoutInflater.from(parent.context).inflate(R.layout.dialog_recycler_item, parent, false))
        }

        override fun onBindViewHolder(holder: MyVH, position: Int) {
            convert.invoke(holder, items[position])
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