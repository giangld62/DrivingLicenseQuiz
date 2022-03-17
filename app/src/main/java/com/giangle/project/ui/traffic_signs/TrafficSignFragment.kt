package com.giangle.project.ui.traffic_signs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.giangle.project.databinding.FragmentTrafficSignBinding
import com.giangle.project.util.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrafficSignFragment: Fragment(), TrafficSignAdapter.ITrafficSign {
    private lateinit var binding: FragmentTrafficSignBinding
    private val viewModel by viewModels<TrafficSignViewModel> {
        ViewModelFactory(requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrafficSignBinding.inflate(inflater,container,false)
        val adapter = TrafficSignAdapter(this)
        requireActivity().lifecycleScope.launch(Dispatchers.IO){
            val trafficSigns = viewModel.getAllTrafficSigns()
            withContext(Dispatchers.Main){
                adapter.submitList(trafficSigns)
            }
        }
        binding.rcvTrafficSign.adapter = adapter
        binding.rcvTrafficSign.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun setTitleType(type: String) {
        binding.tvType.text = type
    }
}