package com.giangle.project.ui.tips.theory_tips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.giangle.project.databinding.FragmentTheoryBinding
import com.giangle.project.util.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TheoryFragment: Fragment(), TheoryAdapter.ITheoryAdapter {
    private lateinit var binding: FragmentTheoryBinding
    private val viewModel by viewModels<TheoryViewModel> {
        ViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTheoryBinding.inflate(inflater,container,false)

        val adapter = TheoryAdapter(this)
        requireActivity().lifecycleScope.launch(Dispatchers.IO){
            val tips = viewModel.getAllTips()
            withContext(Dispatchers.Main){
                adapter.submitList(tips)
            }
        }
        binding.rcvTheory.adapter = adapter
        binding.rcvTheory.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun setTitleType(type: String) {
        binding.tvKindOfTip.text = type
    }
}