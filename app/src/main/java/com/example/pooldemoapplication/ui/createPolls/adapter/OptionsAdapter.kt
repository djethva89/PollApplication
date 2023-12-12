package com.example.pooldemoapplication.ui.createPolls.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pooldemoapplication.R
import java.util.Collections


class OptionsAdapter(private val startDragListener: ItemMoveCallbackListener.OnStartDragListener) :
    RecyclerView.Adapter<OptionsAdapter.MyViewHolder>(), ItemMoveCallbackListener.Listener {

    val optionsList: MutableList<String?> = mutableListOf()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.create_option_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val optionItem = optionsList[position]

        val optionName =
            holder.itemView.findViewById<EditText>(R.id.option_name)
        val cancel =
            holder.itemView.findViewById<ImageView>(R.id.cancel)
        val ivDrag =
            holder.itemView.findViewById<ImageView>(R.id.iv_drag)
        optionName.setText(optionItem)

        if (optionsList.size == position) {
            optionName.imeOptions = EditorInfo.IME_ACTION_DONE

        } else {
            optionName.imeOptions = EditorInfo.IME_ACTION_NEXT
        }

        ivDrag.setOnTouchListener(OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                this.startDragListener.onStartDrag(holder)
            }
            return@OnTouchListener true
         })


        cancel.setOnClickListener {
            optionsList.removeAt(position)
            notifyItemRemoved(position)
        }
        optionName.setOnEditorActionListener(
            OnEditorActionListener { v, actionId, event -> // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    addOption("")
                    return@OnEditorActionListener true
                }
                // Return true if you have consumed the action, else false.
                false
            })
    }

    fun addOption(options: String) {
        if (5 > this.optionsList.size) {
            this.optionsList.add(options)
            notifyItemInserted(this.optionsList.size - 1)
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

    override fun onRowSelected(itemViewHolder: MyViewHolder) {
    }

    override fun onRowClear(itemViewHolder: MyViewHolder) {
    }
}