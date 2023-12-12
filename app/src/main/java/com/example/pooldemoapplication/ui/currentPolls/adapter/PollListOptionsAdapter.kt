package com.example.pooldemoapplication.ui.currentPolls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pooldemoapplication.R
import com.example.pooldemoapplication.config.room.entity.OptionTableModel

fun interface OnOptionsClickListener {
    fun onClick(optionIndex: Int)
}

class PollListOptionsAdapter(
    private val isGivePercentage: Boolean,
    private val optionsList: List<OptionTableModel>,
    private val onClickListener: OnOptionsClickListener
) :
    RecyclerView.Adapter<PollListOptionsAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.poll_option_row,
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
        holder.itemView.findViewById<TextView>(R.id.option_name).text = optionItem.optionName

        val percentageTextView = holder.itemView.findViewById<TextView>(R.id.percentage)
        val circleImage = holder.itemView.findViewById<ImageView>(R.id.iv_circle)

        percentageTextView.text = "${optionItem.percentage}%"

        if (isGivePercentage) {
            if (optionItem.percentage == 0) {
                percentageTextView.visibility = View.VISIBLE
                circleImage.visibility = View.GONE
            } else {
                percentageTextView.visibility = View.VISIBLE
                circleImage.setImageResource(R.drawable.ic_checked_circle)
            }
        } else {
            percentageTextView.visibility = View.INVISIBLE
            circleImage.setImageResource(R.drawable.ic_bordered_circle)
        }


        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }
    }

}