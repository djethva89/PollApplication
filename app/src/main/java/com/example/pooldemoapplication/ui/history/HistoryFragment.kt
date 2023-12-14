package com.example.pooldemoapplication.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pooldemoapplication.databinding.FragmentHistoryBinding
import com.example.pooldemoapplication.ui.currentPolls.adapter.PollListAdapter
import com.example.pooldemoapplication.viewmodel.PollsViewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var pollsViewModel: PollsViewModel

    private lateinit var poolAdapter: PollListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        pollsViewModel = ViewModelProvider(this)[PollsViewModel::class.java]

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bindAdapter(pollsViewModel)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindAdapter(pollsViewModel: PollsViewModel) {

        poolAdapter = PollListAdapter(isHistoryView = true, pollsViewModel = pollsViewModel)

        _binding!!.historyRecyclerview.adapter = poolAdapter
        _binding!!.historyRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        getPollHistoryData()
    }

    private fun getPollHistoryData() {
        with(pollsViewModel) {
            clearPollList()
            getPoolWithOption(
                context = requireContext(),
                isHistoryData = true,
                viewLifecycleOwner = viewLifecycleOwner
            )
            pollWithOptionList.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    showEmptyScreen()
                } else {
                    hideEmptyScreen()
                    poolAdapter.setPoolData(it)
                }
            }
        }
    }

    private fun showEmptyScreen() {
        _binding!!.historyRecyclerview.visibility = View.GONE
        _binding!!.emptyView.visibility = View.VISIBLE
    }

    private fun hideEmptyScreen() {
        _binding!!.historyRecyclerview.visibility = View.VISIBLE
        _binding!!.emptyView.visibility = View.GONE
    }

}