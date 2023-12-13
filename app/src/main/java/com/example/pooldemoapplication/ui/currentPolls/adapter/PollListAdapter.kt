package com.example.pooldemoapplication.ui.currentPolls.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pooldemoapplication.config.room.entity.PollsWithOption
import com.example.pooldemoapplication.databinding.PollRowBinding
import com.example.pooldemoapplication.viewmodel.PollsViewModel

class PollListAdapter(val isHistoryView: Boolean = false, val pollsViewModel: PollsViewModel) :
    RecyclerView.Adapter<PollListAdapter.ViewHolder>() {

    private var pollsList = emptyList<PollsWithOption>()

    inner class ViewHolder(private val binding: PollRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pollsWithOption: PollsWithOption) {
            binding.poolName.text =
                pollsWithOption.pollsTableModel!!.poolName

            val adapter = PollListOptionsAdapter(
                newSelectedIndex = pollsWithOption.pollsTableModel!!.newIndex,
                oldSelectedIndex = pollsWithOption.pollsTableModel!!.oldIndex,
                isHistoryView = isHistoryView,
                optionsList = pollsWithOption.optionTableEntity!!,
                onClickListener = { newIndex, oldIndex ->

                    Log.d(
                        PollListOptionsAdapter::class.java.name,
                        "onBindViewHolder: $newIndex :: $oldIndex"
                    )

                    (binding.pollsOptions.adapter as PollListOptionsAdapter).addNewIndex(
                        newIndex,
                        oldIndex
                    )
                    pollsList[adapterPosition].pollsTableModel!!.newIndex = newIndex
                    pollsList[adapterPosition].pollsTableModel!!.oldIndex = oldIndex
                    if (oldIndex == -1) {

                        pollsList[adapterPosition].pollsTableModel!!.isGivePercentage = true
                        pollsList[adapterPosition].optionTableEntity!![newIndex].percentage = 100
                        notifyItemChanged(adapterPosition)
                    } else {
                        pollsList[adapterPosition].optionTableEntity!![newIndex].percentage = 100
                        pollsList[adapterPosition].optionTableEntity!![oldIndex].percentage = 0

                        binding.pollsOptions.adapter!!.notifyItemChanged(newIndex)
                        binding.pollsOptions.adapter!!.notifyItemChanged(oldIndex)

                    }

                    pollsViewModel.insertPoolWithOption(
                        context = binding.root.context,
                        pollsTableModel = pollsList[adapterPosition].pollsTableModel!!,
                        optionTableEntity = pollsList[adapterPosition].optionTableEntity!!
                    )
                }
            )
            binding.pollsOptions.adapter = adapter
            binding.pollsOptions.setHasFixedSize(true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PollRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pollsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(pollsList[position]) {
            holder.bind(this)
        }

    }

    fun getPollList(): List<PollsWithOption> {
        return this.pollsList
    }

    fun setPoolData(pollsList: List<PollsWithOption>) {
        this.pollsList = pollsList
        notifyDataSetChanged()
    }

}