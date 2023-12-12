package com.example.pooldemoapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pooldemoapplication.databinding.FragmentHomeBinding
import com.example.pooldemoapplication.ui.create.ui.login.LoginViewModel
import com.example.pooldemoapplication.viewmodel.PoolViewModel

//https://github.com/kevinadhiguna/kotlin-room-database/blob/master/app/src/main/java/com/example/kotlinroomdatabase/fragments/list/ListFragment.kt
class HomeFragment : Fragment() {

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

        val poolViewModel = ViewModelProvider(this)[PoolViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val poolAdapter = PoolListAdapter()

        _binding!!.poolList.adapter = poolAdapter
        _binding!!.poolList.layoutManager = LinearLayoutManager(requireContext())


        poolViewModel.getPoolWithOption(requireContext())!!.observe(viewLifecycleOwner, Observer {
            it?.let { it1 -> poolAdapter.setPoolData(it1) }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}