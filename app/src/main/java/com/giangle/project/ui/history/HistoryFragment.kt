package com.giangle.project.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.giangle.project.R
import com.giangle.project.databinding.FragmentHistoryBinding
import com.giangle.project.model.ContestState
import com.giangle.project.util.Const.A1
import com.giangle.project.util.Const.A2
import com.giangle.project.util.Const.B1
import com.giangle.project.util.Const.B2
import com.giangle.project.util.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HistoryFragment : Fragment(), HistoryAdapter.IHistoryAdapter {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by viewModels<HistoryViewModel> {
        ViewModelFactory(requireActivity().application)
    }
    private var contestStates = arrayListOf<ContestState>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val adapter = HistoryAdapter(this)
        contestStates.clear()
        contestStates.addAll(viewModel.getAllContestStates())
        adapter.submitList(contestStates)
        binding.rcvHistory.adapter = adapter
        binding.rcvHistory.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onItemClick(position: Int) {
        val fragmentLabel =
            "Đề ${contestStates[position].examNumber} ${contestStates[position].typeOfContest}"
        findNavController().navigate(
            HistoryFragmentDirections.actionHistoryFragmentToDetailContestFragment(
                contestStates[position].idType,
                contestStates[position].typeOfContest,
                fragmentLabel
            )
        )
    }

}