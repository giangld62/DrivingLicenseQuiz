package com.giangle.project.ui.choose_contest

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.giangle.project.R
import com.giangle.project.databinding.FragmentChooseContestBinding
import com.giangle.project.ui.main.MainViewModel
import com.giangle.project.util.Const.A1
import com.giangle.project.util.Const.A2
import com.giangle.project.util.Const.B1
import com.giangle.project.util.Const.B2
import com.giangle.project.util.Const.CAU_HOI_DIEM_LIET
import com.giangle.project.util.Const.RANDOM_QUESTION

class ChooseContestFragment : Fragment(), ChooseContestAdapter.IChooseContest,
    View.OnClickListener {
    private lateinit var binding: FragmentChooseContestBinding
    private val args by navArgs<ChooseContestFragmentArgs>()
    private lateinit var adapter: ChooseContestAdapter
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<ChooseContestViewModel> {
        ChooseContestViewModelFactory(args.typeOfContest, requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseContestBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        adapter = ChooseContestAdapter(this)
        binding.ivCauDiemLiet.setOnClickListener(this)
        binding.ivRandomQuestion.setOnClickListener(this)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.rcvChooseContest.adapter = adapter
        viewModel.updateContestStatuses()
        viewModel.listOfContestStatus.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        binding.rcvChooseContest.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_history, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history -> {
                findNavController().navigate(ChooseContestFragmentDirections.actionChooseContestFragmentToHistoryFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(position: Int) {
        val fragmentLabel = when (args.typeOfContest) {
            A1 -> "Đề ${position + 1} A1"
            A2 -> "Đề ${position + 1} A2"
            B1 -> "Đề ${position + 1} B1"
            else -> "Đề ${position + 1} B2"

        }
        findNavController().navigate(
            ChooseContestFragmentDirections.actionChooseContestFragmentToDoContestFragment(
                args.typeOfContest, position, fragmentLabel
            )
        )
    }

    override fun onClick(v: View) {
        val fragmentLabel: String
        when (v.id) {
            R.id.iv_cau_diem_liet -> {
                fragmentLabel = when (args.typeOfContest) {
                    A1 -> "Câu điểm liêt A1"
                    A2 -> "Câu điểm liêt A2"
                    else -> "Câu điểm liêt B1,B2"
                }
                findNavController().navigate(
                    ChooseContestFragmentDirections.actionChooseContestFragmentToDoContestFragment(
                        args.typeOfContest, -1, fragmentLabel
                    )
                )
            }

            R.id.iv_random_question -> {
                fragmentLabel = when (args.typeOfContest) {
                    A1 -> "Random A1"
                    A2 -> "Random A2"
                    B1 -> "Random B1"
                    else -> "Random B2"
                }
                findNavController().navigate(
                    ChooseContestFragmentDirections.actionChooseContestFragmentToDoContestFragment(
                        args.typeOfContest, -2, fragmentLabel
                    )
                )
            }
        }
    }
}