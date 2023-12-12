package com.example.pooldemoapplication.ui.currentPolls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pooldemoapplication.databinding.FragmentHomeBinding
import com.example.pooldemoapplication.ui.createPolls.ui.login.LoginViewModel
import com.example.pooldemoapplication.ui.currentPolls.adapter.PoolListAdapter
import com.example.pooldemoapplication.viewmodel.PollsViewModel

//https://github.com/kevinadhiguna/kotlin-room-database/blob/master/app/src/main/java/com/example/kotlinroomdatabase/fragments/list/ListFragment.kt
class CurrentPollsFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val pollsViewModel = ViewModelProvider(this)[PollsViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        bindAdapter(pollsViewModel)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun bindAdapter(pollsViewModel: PollsViewModel) {

        val poolAdapter = PoolListAdapter(pollsViewModel)

        _binding!!.poolList.adapter = poolAdapter
        _binding!!.poolList.layoutManager = LinearLayoutManager(requireContext())


        pollsViewModel.getPoolWithOption(requireContext())!!.observe(
            viewLifecycleOwner, Observer {
                it?.let { it1 -> poolAdapter.setPoolData(it1) }
            }
        )
    }
}