package com.example.pooldemoapplication.ui.createPolls.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pooldemoapplication.config.utils.MyUtil
import com.example.pooldemoapplication.databinding.CreateOptionRowBinding
import java.util.Collections


interface PollsListener {
    // you can define any parameter as per your requirement
    fun addNewItem(result: String?, position: Int)

    fun remainingCount(count: Int)
}

class OptionsAdapter(
    val pollsListener: PollsListener,
    private val startDragListener: ItemMoveCallbackListener.OnStartDragListener,
    val fixedOptionCount: Int
) :
    RecyclerView.Adapter<OptionsAdapter.ViewHolder>(), ItemMoveCallbackListener.Listener {

    val optionsList: MutableList<String?> = mutableListOf()

    @SuppressLint("ClickableViewAccessibility")
    inner class ViewHolder(private val binding: CreateOptionRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: String?) {
            binding.optionName.setText(option)
            binding.optionName.requestFocus()
            if (adapterPosition == (fixedOptionCount - 1)) {
                binding.optionName.imeOptions = EditorInfo.IME_ACTION_DONE
            } else {
                binding.optionName.imeOptions = EditorInfo.IME_ACTION_NEXT
            }

            binding.ivDrag.setOnTouchListener(
                OnTouchListener { v, motionEvent ->
                    if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                        MyUtil.vibrate(v.context)
                        startDragListener.onStartDrag(this)
                    }
                    return@OnTouchListener true

                },
            )

            binding.optionName.addTextChangedListener(/* watcher = */ object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (!s.isNullOrEmpty() && optionsList.contains(s.toString())) {
                        binding.optionName.error = "already exist"
                    } else {
                        binding.optionName.error = null
                        optionsList[adapterPosition] = s.toString()
                    }

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.isNullOrEmpty()) {
                        binding.optionName.error = null
                    }
                }
            })

            binding.cancel.setOnClickListener {

                MyUtil.alertDialog(binding.root.context) { _, _ ->
                    optionsList.removeAt(adapterPosition)
                    pollsListener.remainingCount(optionsList.size)
                    notifyItemRemoved(adapterPosition)
                }

            }
            binding.optionName.setOnEditorActionListener(
                OnEditorActionListener { view, actionId, _ -> // Identifier of the action. This will be either the identifier you supplied,
                    // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        addOption(context = view.context)
                        return@OnEditorActionListener true
                    }
                    // Return true if you have consumed the action, else false.
                    false
                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            CreateOptionRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(optionsList[holder.adapterPosition]) {
            holder.bind(this)
        }
    }

    fun addOption(context: Context) {
        if (fixedOptionCount > this.optionsList.size) {
            this.optionsList.add("")
            notifyItemInserted(this.optionsList.size - 1)
            pollsListener.addNewItem("", this.optionsList.size)
            pollsListener.remainingCount(this.optionsList.size)
        } else {
            Toast.makeText(context, "You have reached option limit.", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(optionsList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(optionsList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(itemViewHolder: ViewHolder) {
    }

    override fun onRowClear(itemViewHolder: ViewHolder) {
    }

}