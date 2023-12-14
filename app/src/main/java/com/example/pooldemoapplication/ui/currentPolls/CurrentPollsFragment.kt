package com.example.pooldemoapplication.ui.currentPolls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pooldemoapplication.databinding.FragmentCurrentPollsBinding
import com.example.pooldemoapplication.ui.currentPolls.adapter.PollListAdapter
import com.example.pooldemoapplication.viewmodel.PollsViewModel

class CurrentPollsFragment : Fragment() {

    private lateinit var pollsViewModel: PollsViewModel

    private var _binding: FragmentCurrentPollsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    private lateinit var poolAdapter: PollListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        pollsViewModel = ViewModelProvider(this)[PollsViewModel::class.java]
        _binding = FragmentCurrentPollsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        bindAdapter(pollsViewModel)
    }

    private fun bindAdapter(pollsViewModel: PollsViewModel) {
        poolAdapter = PollListAdapter(pollsViewModel = pollsViewModel)
        _binding!!.poolList.adapter = poolAdapter
        _binding!!.poolList.layoutManager = LinearLayoutManager(requireContext())
        getCurrentPollData()
    }

    private fun getCurrentPollData() {
        with(pollsViewModel) {
            clearPollList()
            getPoolWithOption(
                requireContext(),
                viewLifecycleOwner = viewLifecycleOwner
            )
            pollWithOptionList.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    showEmptyScreen()
                } else {
                    hideEmptyScreen()
                    if (poolAdapter.getPollList().isEmpty()) {
                        poolAdapter.setPoolData(it)
                    }

                }
            }
        }
    }

    private fun showEmptyScreen() {
        _binding!!.poolList.visibility = View.GONE
        _binding!!.emptyView.visibility = View.VISIBLE
    }

    private fun hideEmptyScreen() {
        _binding!!.poolList.visibility = View.VISIBLE
        _binding!!.emptyView.visibility = View.GONE
    }
}