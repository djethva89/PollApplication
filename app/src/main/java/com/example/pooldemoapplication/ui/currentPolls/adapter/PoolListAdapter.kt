package com.example.pooldemoapplication.ui.currentPolls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pooldemoapplication.R
import com.example.pooldemoapplication.config.room.entity.PollsWithOption
import com.example.pooldemoapplication.viewmodel.PollsViewModel

class PoolListAdapter(val pollsViewModel: PollsViewModel) :
    RecyclerView.Adapter<PoolListAdapter.MyViewHolder>() {

    private var pollsList = emptyList<PollsWithOption>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.poll_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return pollsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val poolItem = pollsList[position]
        holder.itemView.findViewById<TextView>(R.id.pool_name).text =
            poolItem.pollsTableModel!!.poolName
        holder.itemView.findViewById<RecyclerView>(R.id.polls_options).adapter =
            PollListOptionsAdapter(
                isGivePercentage = poolItem.pollsTableModel!!.isGivePercentage,
                optionsList = poolItem.optionTableEntity!!,
                onClickListener = { index ->
                    pollsList[position].pollsTableModel!!.isGivePercentage = true
                    pollsList[position].optionTableEntity!![index].percentage = 100

                    pollsViewModel.insertPoolWithOption(
                        context = holder.itemView.context,
                        pollsTableModel = pollsList[position].pollsTableModel!!,
                        optionTableEntity = pollsList[position].optionTableEntity!!
                    )
                    notifyItemChanged(position)
                })
    }

    fun setPoolData(pollsList: List<PollsWithOption>) {
        this.pollsList = pollsList
        notifyDataSetChanged()
    }

}