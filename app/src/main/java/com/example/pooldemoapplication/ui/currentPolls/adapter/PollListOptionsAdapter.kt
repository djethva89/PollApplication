package com.example.pooldemoapplication.ui.currentPolls.adapter

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pooldemoapplication.R
import com.example.pooldemoapplication.config.room.entity.OptionTableModel
import com.example.pooldemoapplication.config.utils.Constant.Companion.CURRENT_POLL_VIEW_TYPE
import com.example.pooldemoapplication.config.utils.Constant.Companion.HISTORY_POLL_VIEW_TYPE
import com.example.pooldemoapplication.databinding.PollOptionRowBinding


fun interface OnOptionsClickListener {
    fun onClick(newIndex: Int, oldIndex: Int)
}

class PollListOptionsAdapter(
    private var oldSelectedIndex: Int = -1,
    private var newSelectedIndex: Int = -1,
    private val optionsList: List<OptionTableModel>,
    private val onClickListener: OnOptionsClickListener,
    val isHistoryView: Boolean
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class HistoryPollViewHolder(private val binding: PollOptionRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(optionTableModel: OptionTableModel) {
            binding.optionName.text = optionTableModel.optionName
            "${optionTableModel.percentage}%".also { binding.percentage.text = it }
            binding.percentage.visibility = View.VISIBLE
            binding.progressHorizontal.progress = optionTableModel.percentage
            binding.ivCircle.setImageResource(R.drawable.ic_checked_circle)
            if (optionTableModel.percentage == 100) {
                binding.ivCircle.visibility = View.VISIBLE
            } else {
                binding.ivCircle.visibility = View.GONE
            }
        }
    }

    inner class CurrentPollViewHolder(private val binding: PollOptionRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(optionTableModel: OptionTableModel) {
            binding.optionName.text = optionTableModel.optionName
            "${optionTableModel.percentage}%".also { binding.percentage.text = it }
            if (newSelectedIndex != -1) {
                if (newSelectedIndex == adapterPosition) {
                    binding.percentage.visibility = View.VISIBLE
                    binding.ivCircle.visibility = View.VISIBLE
                    binding.ivCircle.setImageResource(R.drawable.ic_checked_circle)
                    ObjectAnimator.ofInt(
                        binding.progressHorizontal,
                        "progress",
                        0,
                        optionTableModel.percentage
                    )
                        .setDuration(1000).start()
                } else if (oldSelectedIndex != -1 && oldSelectedIndex == adapterPosition) {
                    binding.percentage.visibility = View.VISIBLE
                    binding.ivCircle.visibility = View.GONE
                    ObjectAnimator.ofInt(
                        binding.progressHorizontal,
                        "progress",
                        100,
                        optionTableModel.percentage
                    )
                        .setDuration(1000).start()
                } else {
                    binding.progressHorizontal.progress = optionTableModel.percentage
                    binding.percentage.visibility = View.VISIBLE
                    binding.ivCircle.visibility = View.GONE
                }
            } else {
                binding.percentage.visibility = View.INVISIBLE
                binding.ivCircle.visibility = View.VISIBLE
                binding.ivCircle.setImageResource(R.drawable.ic_bordered_circle)
            }

            binding.root.setOnClickListener {
                if (!isHistoryView) {
                    val oldSelectedOption =
                        optionsList.indexOfFirst { optionTableModel -> optionTableModel.percentage == 100 }
                    if (oldSelectedOption != adapterPosition) {
                        onClickListener.onClick(adapterPosition, oldSelectedOption)
                    }

                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            PollOptionRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (isHistoryView) HistoryPollViewHolder(binding) else CurrentPollViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHistoryView) HISTORY_POLL_VIEW_TYPE else CURRENT_POLL_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HistoryPollViewHolder) {
            with(optionsList[position]) {
                holder.bind(this)
            }
        } else {
            with(optionsList[position]) {
                (holder as CurrentPollViewHolder).bind(this)
            }
        }

    }

    fun addNewIndex(newIndex: Int, oldIndex: Int) {
        newSelectedIndex = newIndex
        oldSelectedIndex = oldIndex
    }

}