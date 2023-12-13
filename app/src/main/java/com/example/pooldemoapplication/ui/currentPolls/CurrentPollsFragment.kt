package com.example.pooldemoapplication.ui.currentPolls

import android.os.Bundle
import android.util.Log
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        pollsViewModel = ViewModelProvider(this)[PollsViewModel::class.java]
        _binding = FragmentCurrentPollsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        bindAdapter(pollsViewModel)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindAdapter(pollsViewModel: PollsViewModel) {

        val poolAdapter = PollListAdapter(pollsViewModel = pollsViewModel)

        _binding!!.poolList.adapter = poolAdapter
        _binding!!.poolList.layoutManager = LinearLayoutManager(requireContext())

        pollsViewModel.getPoolWithOption(requireContext()).observe(
            this.viewLifecycleOwner
        ) {
            Log.d(
                CurrentPollsFragment::class.java.name,
                "bindAdapter: ${poolAdapter.getPollList().isEmpty()}"
            )
            if (it.isNullOrEmpty()) {
                _binding!!.poolList.visibility = View.GONE
                _binding!!.emptyView.visibility = View.VISIBLE
            } else {
                _binding!!.poolList.visibility = View.VISIBLE
                _binding!!.emptyView.visibility = View.GONE

                if (poolAdapter.getPollList().isEmpty()) {
                    poolAdapter.setPoolData(it)
                }

            }
        }
    }
}