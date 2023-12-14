package com.example.pooldemoapplication.ui.createPolls.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pooldemoapplication.R
import com.example.pooldemoapplication.config.room.entity.OptionTableModel
import com.example.pooldemoapplication.config.room.entity.PollsTableModel
import com.example.pooldemoapplication.config.utils.Constant.Companion.fixedOptionCount
import com.example.pooldemoapplication.databinding.ActivityCreatePoolBinding
import com.example.pooldemoapplication.ui.createPolls.adapter.ItemMoveCallbackListener
import com.example.pooldemoapplication.ui.createPolls.adapter.OptionsAdapter
import com.example.pooldemoapplication.ui.createPolls.adapter.PollsListener
import com.example.pooldemoapplication.viewmodel.PollsViewModel


class CreatePollsActivity : AppCompatActivity(), ItemMoveCallbackListener.OnStartDragListener {

    private lateinit var binding: ActivityCreatePoolBinding

    //For Drag-Drop item on recyclerview
    private lateinit var touchHelper: ItemTouchHelper

    //For fetching history data from database
    private lateinit var pollsViewModel: PollsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pollsViewModel = ViewModelProvider(this)[PollsViewModel::class.java]

        pollsViewModel.optionCount.observe(this) {
            binding.labelAnswerQuestionHint.text = resources.getString(
                R.string.you_can_add_4_more_options, it
            )
        }

        val optionsAdapter = OptionsAdapter(
            pollsListener = pollsListener,
            startDragListener = this,
            fixedOptionCount = fixedOptionCount
        )
        bindAdapter(optionsAdapter)
        handleClicks(optionsAdapter)
    }

    // get callback when new item and and remaining option count
    private val pollsListener = object : PollsListener {
        override fun addNewItem(result: String?, position: Int) {
            if (position != 0) binding.questions.smoothScrollToPosition(position)
        }

        override fun remainingCount(count: Int) {
            pollsViewModel.updateOptionCount(fixedOptionCount - count)
        }
    }

    // Handle all clicks
    private fun handleClicks(optionsAdapter: OptionsAdapter) {
        binding.addOption.setOnClickListener {
            optionsAdapter.addOption("")
        }
        binding.create.setOnClickListener {
            createValidation(optionsAdapter)
        }
        binding.backNavigation.setOnClickListener {
            finish()
        }
    }

    // Bind history list adapter
    private fun bindAdapter(optionsAdapter: OptionsAdapter) {
        val callback: ItemTouchHelper.Callback = ItemMoveCallbackListener(optionsAdapter)
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.questions)
        binding.questions.adapter = optionsAdapter
        binding.questions.layoutManager = LinearLayoutManager(this)
    }

    // Add poll validation
    private fun createValidation(optionsAdapter: OptionsAdapter) {
        val optionCount = optionsAdapter.optionsList.count { s: String? -> s!!.isNotEmpty() }

        if (binding.poolQuestion.text!!.isBlank()) {
            Toast.makeText(this, "Please enter poll options!", Toast.LENGTH_SHORT).show()
        } else if (2 > optionCount) {
            Toast.makeText(
                this, "At list two options is required for create polls!", Toast.LENGTH_SHORT
            ).show()
        } else {
            createPolls(
                poolName = binding.poolQuestion.run { binding.poolQuestion.text.toString() },
                optionsAdapter.optionsList
            )
        }
    }

    // New poll adding on database
    private fun createPolls(poolName: String, optionsList: MutableList<String?>) {

        val poolEntity = PollsTableModel(
            poolName = poolName, createAt = System.currentTimeMillis()
        )

        val optionList = optionsList.filter {
            return@filter !it.isNullOrEmpty()
        }.map {
            OptionTableModel(
                optionName = it!!, createAt = poolEntity.createAt
            )
        }

        pollsViewModel.insertPoolWithOption(
            context = this, pollsTableModel = poolEntity, optionTableEntity = optionList
        ).let {
            finish()
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }
}

